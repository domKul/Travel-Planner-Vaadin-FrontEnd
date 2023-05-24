package com.example.travelplannervaadinfrontend.destination.get;

import com.example.travelplannervaadinfrontend.booking.create.BookingCreator;
import com.example.travelplannervaadinfrontend.destination.enums.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Route("getDestinations")
public class DestinationGet extends VerticalLayout {
    private final Grid<DestinationDTOGet> destinationGrid;
    private final Grid<LocationDTO> locationDTOGrid;
    private ComboBox<LocationTypeEnum> destTypeComboBox;
    private ComboBox<LocaleOptionEnum> localeComboBox;
    private ComboBox<CurrencyEnum> filterByCurrencyComboBox;
    private ComboBox<SortOptionEnum> orderedByComboBox;
    private ComboBox<UnitsEnum> unitsEnumComboBox;
    private final TextField adultsNumber = new TextField("adults_number");
    private final TextField checkinDate = new TextField("checkin_date");
    private final TextField checkoutDate = new TextField("checkout_date");
    private final TextField destId = new TextField("dest_id");
    private final TextField roomNumber = new TextField("room_number");
    private final TextField name = new TextField("Name");
    private final TextField locale = new TextField("Locale");


    public DestinationGet() {

        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        destinationGrid = new Grid<>();
        destinationGrid.addColumn(DestinationDTOGet::getHotelId).setHeader("_id").setSortable(true);
        destinationGrid.addColumn(DestinationDTOGet::getId).setHeader("Id_Name").setSortable(true);
        destinationGrid.addColumn(DestinationDTOGet::getName).setHeader("Destination_Name").setSortable(true);
        destinationGrid.addColumn(DestinationDTOGet::getCountryCode).setHeader("Country_Code").setSortable(true);
        destinationGrid.addColumn(DestinationDTOGet::getCurrency).setHeader("Currency").setSortable(true);
        destinationGrid.addColumn(DestinationDTOGet::getHotelPrice).setHeader("_Price").setSortable(true);

        Div gapDiv = new Div();
        gapDiv.setHeight("3em");

        locationDTOGrid = new Grid<>();
        locationDTOGrid.addColumn(LocationDTO::getName).setHeader("Location Name").setSortable(true);
        locationDTOGrid.addColumn(LocationDTO::getDest_id).setHeader("dest_id");
        locationDTOGrid.addColumn(LocationDTO::getCountry).setHeader("country");
        locationDTOGrid.addColumn(LocationDTO::getRegion).setHeader("Region").setSortable(true);
        locationDTOGrid.addColumn(LocationDTO::getHotels).setHeader("Hotels").setSortable(true);


        HorizontalLayout horizontalLayoutDestination = new HorizontalLayout();
        HorizontalLayout horizontalLayoutLocation = new HorizontalLayout();

        Dialog dialogbooking = new Dialog();
        BookingCreator bookingCreator = new BookingCreator();
        dialogbooking.add(bookingCreator);
        Button createBooking = new Button("Add booking", e -> {
            dialogbooking.open();
        });

        Button showHotelsButton = new Button("Show Destination List", event -> showDestinationsInDB());
        Button showLocationsList = new Button("Show Locations List", event -> showLocationsInDB());
        Button searchButton = new Button("Search Destinations", event -> {
            Dialog dialog = new Dialog();
            initforDestination(dialog);
            dialog.open();
        });
        horizontalLayoutDestination.add(createBooking, showHotelsButton,searchButton);
        Button searchLocation = new Button("Search Location", event -> {
            Dialog dialogg = new Dialog();
            initForLocation(dialogg);
            dialogg.open();
        });
        horizontalLayoutLocation.add(showLocationsList,searchLocation);
        Button backButton = new Button("Back", event -> navigateBack());
        backButton.getElement().getStyle().set("top", "20%");
        backButton.getElement().getStyle().set("left", "45%");
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        add(backButton, horizontalLayoutLocation, locationDTOGrid,gapDiv, horizontalLayoutDestination, destinationGrid);
    }

    private void initForLocation(Dialog dialogg) {
        Button searchLocation = new Button("Search Location", event -> {
            searchLocation();
            dialogg.close();
        });
        VerticalLayout verticalLayout = new VerticalLayout(name, locale, searchLocation);
        dialogg.add(verticalLayout);
    }

    public void navigateBack() {

        getUI().ifPresent(ui -> ui.navigate("GetStart"));
    }

    private void initforDestination(Dialog dialog) {
        orderedByComboBox = new ComboBox<>("Sort By", Arrays.asList(SortOptionEnum.values()));
        filterByCurrencyComboBox = new ComboBox<>("Filter by Currency", Arrays.asList(CurrencyEnum.values()));
        localeComboBox = new ComboBox<>("Locale", Arrays.asList(LocaleOptionEnum.values()));
        unitsEnumComboBox = new ComboBox<>("Metric", Arrays.asList(UnitsEnum.values()));
        destTypeComboBox = new ComboBox<>("Destination Type", Arrays.asList(LocationTypeEnum.values()));

        Button searchButton = new Button("Search Destinations", event -> searchDestination());

        VerticalLayout contentLayout = new VerticalLayout(destTypeComboBox, roomNumber, destId,
                checkinDate, checkoutDate, adultsNumber, unitsEnumComboBox,
                localeComboBox, filterByCurrencyComboBox, orderedByComboBox, searchButton);

        dialog.add(contentLayout);
    }

    private void searchLocation() {

        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/locations/getHotelslocation");

             webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("name", name.getValue())
                            .queryParam("locale", locale.getValue())
                            .build())
                    .retrieve()
                    .bodyToFlux(LocationDTO.class)
                    .collectList()
                    .block();
            showLocationsInDB();
        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving Locations: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving Locations: " + e.getMessage());
        }
    }
    private void searchLocationFromData() {

        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/locations/getLocations");

            List<DestinationDTOGet> destinationDTOGetList = webClient.get()
                    .uri("/getCustomers")
                    .retrieve()
                    .bodyToFlux(DestinationDTOGet.class)
                    .collectList()
                    .block();

        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving Destinations: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving Destinations: " + e.getMessage());
        }
    }



    private void searchDestination() {
        if (orderedByComboBox.isEmpty() || adultsNumber.isEmpty() || checkinDate.isEmpty()
                || filterByCurrencyComboBox.isEmpty() || destId.isEmpty() || localeComboBox.isEmpty()
                || checkoutDate.isEmpty() || unitsEnumComboBox.isEmpty() || roomNumber.isEmpty()
                || destTypeComboBox.isEmpty()) {
            Notification.show("Please fill in all fields");
            return;
        }

        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/hotel/destinationSave");

            List<DestinationDTOGet> hotels = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("orderedby", orderedByComboBox.getValue().name())
                            .queryParam("adults_number", adultsNumber.getValue())
                            .queryParam("checkin_date", checkinDate.getValue())
                            .queryParam("filter_by_currency", filterByCurrencyComboBox.getValue().name())
                            .queryParam("dest_id", destId.getValue())
                            .queryParam("locale", localeComboBox.getValue().name())
                            .queryParam("checkout_date", checkoutDate.getValue())
                            .queryParam("units", unitsEnumComboBox.getValue().name())
                            .queryParam("room_number", roomNumber.getValue())
                            .queryParam("dest_type", destTypeComboBox.getValue().name())
                            .build())
                    .retrieve()
                    .bodyToFlux(DestinationDTOGet.class)
                    .collectList()
                    .block();

            if (hotels != null && !hotels.isEmpty()) {
                destinationGrid.setItems(hotels);
                orderedByComboBox.clear();
                adultsNumber.clear();
                checkinDate.clear();
                filterByCurrencyComboBox.clear();
                destId.clear();
                localeComboBox.clear();
                checkoutDate.clear();
                unitsEnumComboBox.clear();
                roomNumber.clear();
                destTypeComboBox.clear();
                showDestinationsInDB();
                Notification.show("Destinations loaded successfully");
            } else {
                destinationGrid.setItems(Collections.emptyList());
                Notification.show("List is empty");
            }
        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving Destinations: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving Destinations: " + e.getMessage());
        }
    }

    private void showLocationsInDB(){
        try{
            WebClient webClient = WebClient.create("http://localhost:8080/v1/locations/getLocations");

            List<LocationDTO> locations = webClient.get()
                    .retrieve()
                    .bodyToFlux(LocationDTO.class)
                    .collectList()
                    .block();

            if (locations != null && !locations.isEmpty()){
                locationDTOGrid.setItems(locations);
                Notification.show("Locations loaded successfully");
            }else {
                Notification.show("List are empty");
            }
        }catch (WebClientResponseException e) {
            Notification.show("Error retrieving Destinations: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving Destinations: " + e.getMessage());
        }
    }


    private void showDestinationsInDB() {
        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/destinations");

            List<DestinationDTOGet> hotels = webClient.get()
                    .retrieve()
                    .bodyToFlux(DestinationDTOGet.class)
                    .collectList()
                    .block();

            if (hotels != null && !hotels.isEmpty()) {
                destinationGrid.setItems(hotels);
                Notification.show("Destinations loaded successfully");
            } else {
                Notification.show("List are empty");

            }
        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving Destinations: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving Destinations: " + e.getMessage());
        }
    }
}
