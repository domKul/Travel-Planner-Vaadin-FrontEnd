package com.example.travelplannervaadinfrontend.customer.get;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
@Route("customer-form")
public class Get extends VerticalLayout {
    private final Grid<CustomerDTOGet> customerGrid;

    public Get() {
        customerGrid = new Grid<>();
        customerGrid.addColumn(CustomerDTOGet::getCustomerId).setHeader("Customer Id");
        customerGrid.addColumn(CustomerDTOGet::getFirstName).setHeader("First Name");
        customerGrid.addColumn(CustomerDTOGet::getLastName).setHeader("Last Name");
        customerGrid.addColumn(CustomerDTOGet::getBirthdate).setHeader("Birthdate");
        customerGrid.addColumn(CustomerDTOGet::getCountry).setHeader("Country");
        customerGrid.addColumn(CustomerDTOGet::getCity).setHeader("City");
        customerGrid.addColumn(CustomerDTOGet::getStreetName).setHeader("Street Name");
        customerGrid.addColumn(CustomerDTOGet::getPostalCode).setHeader("Postal Code");
        customerGrid.addColumn(CustomerDTOGet::getEmail).setHeader("Email");
        customerGrid.addColumn(CustomerDTOGet::getPhoneNumber).setHeader("Phone Number");

        Button showCustomersButton = new Button("Show Customers");
        showCustomersButton.addClickListener(event -> showCustomers());

        add(customerGrid, showCustomersButton);
    }

    private void showCustomers() {
        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/customers");

            List<CustomerDTOGet> customers = webClient.get()
                    .uri("/getCustomers")
                    .retrieve()
                    .bodyToFlux(CustomerDTOGet.class)
                    .collectList()
                    .block();

            if (customers != null && !customers.isEmpty()) {
                customerGrid.setItems(customers);
                Notification.show("Customers loaded successfully");
            } else {
                Notification.show("No customers found");
            }
        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving customers: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving customers: " + e.getMessage());
        }
    }
}