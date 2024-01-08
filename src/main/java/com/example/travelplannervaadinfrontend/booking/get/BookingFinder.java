package com.example.travelplannervaadinfrontend.booking.get;

import com.example.travelplannervaadinfrontend.booking.create.BookingCreator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;

@Route("bookingList")
public class BookingFinder extends VerticalLayout {
    private Grid<BookingDTOGet> bookingGrid;
    @Autowired
    private  RestTemplate restTemplate;

    public BookingFinder() {
        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        bookingGrid = new Grid<>();
        bookingGrid.addColumn(BookingDTOGet::getBookingId).setHeader("Booking ID").setSortable(true);
        bookingGrid.addColumn(BookingDTOGet::getCustomerId).setHeader("Customer ID").setSortable(true);
        bookingGrid.addColumn(BookingDTOGet::getDestinationId).setHeader("Destination ID").setSortable(true);
        bookingGrid.addColumn(BookingDTOGet::getHotelName).setHeader("Name").setSortable(true);
        bookingGrid.addColumn(BookingDTOGet::getHotelPrice).setHeader("Price").setSortable(true);
        bookingGrid.addColumn(BookingDTOGet::getStartBooking).setHeader("Strart").setSortable(true);
        bookingGrid.addColumn(BookingDTOGet::getEndBooking).setHeader("End").setSortable(true);
        bookingGrid.addItemClickListener(event -> {
            BookingDTOGet booking = event.getItem();
            Long bookingId = booking.getBookingId();
            showBookingDetails(bookingId);
        });

        Dialog dialog = new Dialog();
        BookingCreator bookingCreator = new BookingCreator();
        dialog.add(bookingCreator);
        Button createBooking = new Button("Add booking", e -> {
            dialog.open();
        });
        Button deleteBooking = new Button("Delete booking", e -> deleteSelectedBooking());
        Button findBookings = new Button(" Refresh list", e -> refreshBookingList());
        Button backButton = new Button("Back", event -> navigateBack());
        backButton.getElement().getStyle().set("top", "20%");
        backButton.getElement().getStyle().set("left", "45%");
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        horizontalLayout.add(backButton,findBookings,deleteBooking);



        add(dialog,backButton,createBooking,bookingGrid,horizontalLayout);

    }
    public void navigateBack() {

        getUI().ifPresent(ui -> ui.navigate("GetStart"));
    }



    private void showBookingDetails(Long bookingId) {

        try {
            ResponseEntity<BookingDTOGet> response = restTemplate.getForEntity("https://travel-planner-jimh.onrender.com/v1/bookings/" + bookingId, BookingDTOGet.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                BookingDTOGet booking = response.getBody();
                showBookingDialog(booking);
            } else {
                Notification.show("Error retrieving booking: " + response.getStatusCodeValue());
            }
        } catch (HttpClientErrorException ex) {
            Notification.show("Error retrieving booking: " + ex.getRawStatusCode() + " - " + ex.getStatusText());
        }
    }


    private void showBookingDialog(BookingDTOGet booking) {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        VerticalLayout layout = new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        layout.add(new Label("Book Time: " + booking.getBookTime()));
        layout.add(new Label("Customer ID: " + booking.getCustomerId()));
        layout.add(new Label("Customer Name: " + booking.getCustomerFirstName() + " " + booking.getCustomerLastName()));
        layout.add(new Label("Birth Date: " + booking.getBirthDate()));
        layout.add(new Label("Country: " + booking.getCountry()));
        layout.add(new Label("City: " + booking.getCity()));
        layout.add(new Label("Street Name: " + booking.getStreetName()));
        layout.add(new Label("Postal Code: " + booking.getPostalCode()));
        layout.add(new Label("Email: " + booking.getEmail()));
        layout.add(new Label("Phone Number: " + booking.getPhoneNumber()));
        layout.add(new Label("Destination Name: " + booking.getHotelName()));
        layout.add(new Label("Destination ID: " + booking.getDestinationId()));
        layout.add(new Label("Start Booking: " + booking.getStartBooking()));
        layout.add(new Label("End Booking: " + booking.getEndBooking()));
        layout.add(new Label("Price: " + booking.getHotelPrice()));
        layout.add(new Label("Currency " + booking.getCurrency()));

        Button closeButton = new Button("Close", event -> dialog.close());
        layout.add(closeButton);

        dialog.add(layout);
        dialog.open();
    }


    private void deleteSelectedBooking() {
        BookingDTOGet selectedBooking = bookingGrid.asSingleSelect().getValue();
        if (selectedBooking != null) {
            try {
                WebClient webClient = WebClient.create("https://travel-planner-jimh.onrender.com/v1/bookings");
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
            WebClient webClient = WebClient.create("https://travel-planner-jimh.onrender.com/v1/bookings");
            List<BookingDTOGet> bookings = webClient.get()
                    .retrieve()
                    .bodyToFlux(BookingDTOGet.class)
                    .collectList()
                    .block();

            if (bookings != null && !bookings.isEmpty()) {
                bookingGrid.setItems(bookings);
            } else {
                Notification.show("List is Empty");
                bookingGrid.setItems(Collections.emptyList());
            }
        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving booking: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving booking: " + e.getMessage());
        }
    }
}
