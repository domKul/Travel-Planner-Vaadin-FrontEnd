package com.example.travelplannervaadinfrontend.booking.get;

import com.example.travelplannervaadinfrontend.booking.create.BookingCreator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;

@Route("bookingList")
public class BookingFinder extends VerticalLayout {
    private Grid<BookingDTOGetInfo> bookingGrid;

    public BookingFinder() {
        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        bookingGrid = new Grid<>();
        bookingGrid.addColumn(BookingDTOGetInfo::getBookingId).setHeader("Booking ID").setSortable(true);
        bookingGrid.addColumn(BookingDTOGetInfo::getCustomerId).setHeader("Customer ID").setSortable(true);
        bookingGrid.addColumn(BookingDTOGetInfo::getHotelId).setHeader("Hotel ID").setSortable(true);
        bookingGrid.addColumn(BookingDTOGetInfo::getHotelName).setHeader("Name").setSortable(true);
        bookingGrid.addColumn(BookingDTOGetInfo::getHotelPrice).setHeader("Price").setSortable(true);
        bookingGrid.addColumn(BookingDTOGetInfo::getStartBooking).setHeader("Strart").setSortable(true);
        bookingGrid.addColumn(BookingDTOGetInfo::getEndBooking).setHeader("End").setSortable(true);

        Button createBooking = new Button("Add booking", e -> UI.getCurrent().navigate(BookingCreator.class));
        Button deleteBooking = new Button("Delete booking", e -> deleteSelectedBooking());
        Button findBookings = new Button(" Refresh list", e -> refreshBookingList());
        Button backButton = new Button("Back", event -> navigateBack());

        add(createBooking,bookingGrid,findBookings,deleteBooking,backButton);

    }
    public void navigateBack() {

        getUI().ifPresent(ui -> ui.navigate("GetStart"));
    }

    private void deleteSelectedBooking() {
        BookingDTOGetInfo selectedBooking = bookingGrid.asSingleSelect().getValue();
        if (selectedBooking != null) {
            try {
                WebClient webClient = WebClient.create("http://localhost:8080/v1/bookings");
                webClient.delete()
                        .uri("/{bookingId}", selectedBooking.getBookingId())
                        .retrieve()
                        .toBodilessEntity()
                        .block();
                refreshBookingList();
                Notification.show("Booking Deleted !");
            } catch (WebClientResponseException e) {
                Notification.show("Booking deletion error: " + e.getResponseBodyAsString());
            } catch (Exception e) {
                Notification.show("Booking deletion error: " + e.getMessage());
            }
        } else {
            Notification.show("Please select a traveler to delete");
        }
    }

    private void refreshBookingList() {
        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/bookings");
            List<BookingDTOGetInfo> bookings = webClient.get()
                    .retrieve()
                    .bodyToFlux(BookingDTOGetInfo.class)
                    .collectList()
                    .block();

            if (bookings != null && !bookings.isEmpty()) {
                bookingGrid.setItems(bookings);

            } else {
                bookingGrid.setItems(Collections.emptyList());
            }
        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving booking: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving booking: " + e.getMessage());
        }
    }
}
