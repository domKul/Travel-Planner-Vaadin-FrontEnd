package com.example.travelplannervaadinfrontend.hotel.get;

import com.example.travelplannervaadinfrontend.hotel.enums.CurrencyEnum;
import com.example.travelplannervaadinfrontend.hotel.enums.LocaleOptionEnum;
import com.example.travelplannervaadinfrontend.hotel.enums.LocationTypeEnum;
import com.example.travelplannervaadinfrontend.hotel.enums.SortOptionEnum;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.List;

@Route("getHotels")
public class HotelGet extends VerticalLayout {
    private Grid<HotelDTOGet> hotelGrid;
    private ComboBox<LocationTypeEnum> destTypeComboBox;
    private ComboBox<LocaleOptionEnum> localeComboBox;
    private ComboBox<CurrencyEnum> filterByCurrencyComboBox;
    private ComboBox<SortOptionEnum> orderedByComboBox;

    private void init() {
        // Inicjalizacja pól typu ComboBox
        destTypeComboBox = new ComboBox<>("Destination Type", Arrays.asList(LocationTypeEnum.values()));
        localeComboBox = new ComboBox<>("Locale", Arrays.asList(LocaleOptionEnum.values()));
        filterByCurrencyComboBox = new ComboBox<>("Filter by Currency", Arrays.asList(CurrencyEnum.values()));
        orderedByComboBox = new ComboBox<>("Ordered By", Arrays.asList(SortOptionEnum.values()));

        // Dodanie pól do interfejsu użytkownika
        // ...

        // ...
    }

    public void navigateBack() {

        getUI().ifPresent(ui -> ui.navigate("GetStart"));
    }

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

        Button showHotelsButton = new Button("Show Hotel List",event -> showHotelsInDB());
       // showHotelsButton.addClickListener(event -> showHotelsInDB());

        Button searchButton = new Button("search", event -> searchHotels());
        Button backButton = new Button("Back", event -> navigateBack());
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        add(searchButton,hotelGrid, showHotelsButton,backButton);
    }



    private void searchHotels() {
        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/hotel/hotelsSave");

            List<HotelDTOGet> hotels = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("orderedby", "price")
                            .queryParam("adults_number", 2)
                            .queryParam("checkin_date", "2023-05-20")
                            .queryParam("filter_by_currency", "USD")
                            .queryParam("dest_id", -553173)
                            .queryParam("locale", "pl")
                            .queryParam("checkout_date", "2023-05-25")
                            .queryParam("units", "metric")
                            .queryParam("room_number", 1)
                            .queryParam("dest_type", "city")
                            .build())
                    .retrieve()
                    .bodyToFlux(HotelDTOGet.class)
                    .collectList()
                    .block();

            if (hotels != null && !hotels.isEmpty()) {
                hotelGrid.setItems(hotels);
                Notification.show("Hotels loaded successfully");
            } else {
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
