package com.example.travelplannervaadinfrontend.booking.create;

import com.example.travelplannervaadinfrontend.traveler.get.TravelersGet;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Route("creteBooking")
public class BookingCreator extends VerticalLayout {

    private DatePicker startDateField;
    private DatePicker endDateField;
    private TextField customerIdField;
    private TextField destinationIdField;
    private TravelersGet travelersGet;

    private RestTemplate restTemplate;

    public BookingCreator() {
        restTemplate = new RestTemplate();
        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        Button saveButton = new Button("Save", event -> saveBooking());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        startDateField = new DatePicker("Start Date");
        endDateField = new DatePicker("End Date");
        customerIdField = new TextField("Customer ID");
        destinationIdField = new TextField("Destination ID");

        add(startDateField, endDateField, customerIdField, destinationIdField, saveButton);
    }

    public void navigateBack() {
        getUI().ifPresent(ui -> ui.navigate("bookingList"));
    }

    private void saveBooking() {
        String startDate = String.valueOf(startDateField.getValue());
        String endDate = String.valueOf(endDateField.getValue());
        long customerId = Long.parseLong(customerIdField.getValue());
        long destinationId = Long.parseLong(destinationIdField.getValue());

        try{
        BookingDTOCreate bookingDTO = new BookingDTOCreate(startDate, endDate, customerId, destinationId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BookingDTOCreate> requestEntity = new HttpEntity<>(bookingDTO, headers);

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:8080/v1/bookings", requestEntity, Void.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Notification.show("Booking saved successfully");
            navigateBack();
        }
        }catch (HttpClientErrorException e ){
            Notification.show( e.getResponseBodyAsString());
        }
    }
}
