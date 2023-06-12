package com.example.travelplannervaadinfrontend.complaint;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;

@Route("createComplaint")
public class ComplaintCreate extends VerticalLayout {
    private TextField titleField;
    private TextArea descriptionField;
    private DatePicker complaintDatePicker;
    private TextField statusField;
    private TextField customerIdField;
    private TextField customer;

    private RestTemplate restTemplate;

    public ComplaintCreate() {
        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        Button backButton = new Button("Back",ev->navigateBack());
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        backButton.getElement().getStyle().set("top", "20%");
        backButton.getElement().getStyle().set("left", "45%");
        restTemplate = new RestTemplate();

        titleField = new TextField("Title");
        descriptionField = new TextArea("Description");
        descriptionField.setWidth("300px");
        complaintDatePicker = new DatePicker("Complaint Date");
        statusField = new TextField("Status");
        customerIdField = new TextField("Customer ID");
        customer = new TextField("Customer");

        Button saveButton = new Button("Save Complaint", e -> createComplaint());

        add(titleField, descriptionField, complaintDatePicker, statusField, customerIdField, saveButton,backButton);
    }

    public void navigateBack() {

        getUI().ifPresent(ui -> ui.navigate("getComplaints"));
    }

    public void createComplaint() {
        String title = titleField.getValue();
        String description = descriptionField.getValue();
        LocalDateTime complaintDate = complaintDatePicker.getValue().atStartOfDay();
        String status = statusField.getValue();
        long customerId = Long.parseLong(customerIdField.getValue());

        try {
            ComplaintDTOCreate complaintDTOCreate = new ComplaintDTOCreate(title, description,
                    complaintDate, status, customerId);

            WebClient webClient = WebClient.create("http://localhost:8080/v1/complaints");

            webClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(complaintDTOCreate))
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            Notification.show("Complaint added");
        } catch (WebClientResponseException e) {
            Notification.show("Error adding Complaint: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error adding Complaint: " + e.getMessage());
        }
    }



}
