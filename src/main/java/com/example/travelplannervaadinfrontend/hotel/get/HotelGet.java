package com.example.travelplannervaadinfrontend.hotel.get;

import com.example.travelplannervaadinfrontend.hotel.enums.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Route("getHotels")
public class HotelGet extends VerticalLayout {
    private final Grid<HotelDTOGet> hotelGrid;
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


    public HotelGet() {
        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        hotelGrid = new Grid<>();
        hotelGrid.addColumn(HotelDTOGet::getHotelId).setHeader("Hotel_id");
        hotelGrid.addColumn(HotelDTOGet::getId).setHeader("Id_Name");
        hotelGrid.addColumn(HotelDTOGet::getName).setHeader("Hotel_Name");
        hotelGrid.addColumn(HotelDTOGet::getCountryCode).setHeader("Country_Code");
        hotelGrid.addColumn(HotelDTOGet::getCurrency).setHeader("Currency");
        hotelGrid.addColumn(HotelDTOGet::getHotelPrice).setHeader("Hotel_Price");

        Button showHotelsButton = new Button("Show Hotel List", event -> showHotelsInDB());
        // showHotelsButton.addClickListener(event -> showHotelsInDB());

        Button searchButton = new Button("search", event -> init());
        Button backButton = new Button("Back", event -> navigateBack());
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        add(searchButton, hotelGrid, showHotelsButton, backButton);
    }

    public void navigateBack() {

        getUI().ifPresent(ui -> ui.navigate("GetStart"));
    }

    public void getHotelsFromdataBase() {
        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/hotels");
            List<HotelDTOGet> hotels = webClient.get()
                    .retrieve()
                    .bodyToFlux(HotelDTOGet.class)
                    .collectList()
                    .block();

            if (hotels != null && !hotels.isEmpty()) {
                hotelGrid.setItems(hotels);
            } else {
                hotelGrid.setItems(Collections.emptyList());
            }
        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving traveler: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving traveler: " + e.getMessage());
        }
    }

    private void init() {

        orderedByComboBox = new ComboBox<>("Sort By", Arrays.asList(SortOptionEnum.values()));

        filterByCurrencyComboBox = new ComboBox<>("Filter by Currency", Arrays.asList(CurrencyEnum.values()));

        localeComboBox = new ComboBox<>("Locale", Arrays.asList(LocaleOptionEnum.values()));

        unitsEnumComboBox = new ComboBox<>("metric", Arrays.asList(UnitsEnum.values()));

        destTypeComboBox = new ComboBox<>("Destination Type", Arrays.asList(LocationTypeEnum.values()));

        Button searchButton = new Button("search Hotels", event -> searchHotels());

        add(destTypeComboBox, roomNumber, destId, checkinDate, checkoutDate, adultsNumber, unitsEnumComboBox,
                localeComboBox, filterByCurrencyComboBox, orderedByComboBox, searchButton);
    }


    private void searchHotels() {
        if (orderedByComboBox.isEmpty() || adultsNumber.isEmpty() || checkinDate.isEmpty()
                || filterByCurrencyComboBox.isEmpty() || destId.isEmpty() || localeComboBox.isEmpty()
                || checkoutDate.isEmpty() || unitsEnumComboBox.isEmpty() || roomNumber.isEmpty()
                || destTypeComboBox.isEmpty()) {
            Notification.show("Please fill in all fields");
            return;
        }

        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/hotel/hotelsSave");

            List<HotelDTOGet> hotels = webClient.get()
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
                    .bodyToFlux(HotelDTOGet.class)
                    .collectList()
                    .block();

            if (hotels != null && !hotels.isEmpty()) {
                hotelGrid.setItems(hotels);
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
                Notification.show("Hotels loaded successfully");
            } else {
                hotelGrid.setItems(Collections.emptyList());
                Notification.show("List is empty");
            }
        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving Hotels: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving Hotels: " + e.getMessage());
        }
    }


    private void showHotelsInDB() {
        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/hotels");

            List<HotelDTOGet> hotels = webClient.get()
                    .retrieve()
                    .bodyToFlux(HotelDTOGet.class)
                    .collectList()
                    .block();

            if (hotels != null && !hotels.isEmpty()) {
                hotelGrid.setItems(hotels);
                Notification.show("Hotels loaded successfully");
            } else {
                Notification.show("List are empty");

            }
        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving Hotels: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving Hotels: " + e.getMessage());
        }
    }
}