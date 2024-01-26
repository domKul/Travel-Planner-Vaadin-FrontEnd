package com.example.travelplannervaadinfrontend.booking.get;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Route("bookings")
public class BookingView extends VerticalLayout {

    private final RestTemplate restTemplate;


    public BookingView(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        Button showBookingButton = new Button("Show Booking");
        showBookingButton.addClickListener(e -> {
            Long bookingId = 1L;
            try {
                ResponseEntity<BookingDTOGet> response = restTemplate.getForEntity("http://vps-7c561477.vps.ovh.net:8080/v1/bookings/" + bookingId, BookingDTOGet.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    BookingDTOGet booking = response.getBody();
                    showBookingDialog(booking);
                } else {
                    Notification.show("Error retrieving booking: " + response.getStatusCodeValue());
                }
            } catch (HttpClientErrorException ex) {
                Notification.show("Error retrieving booking: " + ex.getRawStatusCode() + " - " + ex.getStatusText());
            }
        });

        add(showBookingButton);
    }

    private void showBookingDialog(BookingDTOGet booking) {
        Dialog dialog = new Dialog();
        dialog.open();
    }
}

