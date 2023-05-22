package com.example.travelplannervaadinfrontend.booking.create;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Route("creteBooking")
public class BookingCreator extends VerticalLayout {

    private DatePicker startDateField;
    private DatePicker endDateField;
    private TextField customerIdField;
    private TextField hotelIdField;

    private RestTemplate restTemplate;

    public BookingCreator() {
        restTemplate = new RestTemplate();
        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        Button saveButton = new Button("Save", event -> saveBooking());
        Button bkButton = new Button("Back", event -> navigateBack());
        bkButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        startDateField = new DatePicker("Start Date");
        endDateField = new DatePicker("End Date");
        customerIdField = new TextField("Customer ID");
        hotelIdField = new TextField("Hotel ID");


        add(startDateField, endDateField, customerIdField, hotelIdField, saveButton,bkButton);
    }

    public void navigateBack() {

        getUI().ifPresent(ui -> ui.navigate("bookingList"));
    }

    private void saveBooking() {
        String startDate = String.valueOf(startDateField.getValue());
        String endDate = String.valueOf(endDateField.getValue());
        long customerId = Long.parseLong(customerIdField.getValue());
        long hotelId = Long.parseLong(hotelIdField.getValue());

        BookingDTOCreate bookingDTO = new BookingDTOCreate(startDate, endDate, customerId, hotelId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BookingDTOCreate> requestEntity = new HttpEntity<>(bookingDTO, headers);

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:8080/v1/bookings", requestEntity, Void.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Notification.show("Booking saved successfully your");
        } else {
            Notification.show("Error saving booking");
        }
    }
}
