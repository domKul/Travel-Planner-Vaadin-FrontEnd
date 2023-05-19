package com.example.travelplannervaadinfrontend.hotel.get;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Route("getHotels")
public class HotelGet extends VerticalLayout {
    private Grid<HotelDTOGet> hotelGrid;

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

        Button showHotelsButton = new Button("Show Hotel List");
        showHotelsButton.addClickListener(event -> showHotelsInDB());

        Button backButton = new Button("Back", event -> navigateBack());
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        add(hotelGrid, showHotelsButton,backButton);
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
