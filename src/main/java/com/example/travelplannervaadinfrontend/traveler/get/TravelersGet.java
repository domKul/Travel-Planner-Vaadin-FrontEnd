package com.example.travelplannervaadinfrontend.traveler.get;

import com.example.travelplannervaadinfrontend.traveler.save.TravelerSave;
import com.example.travelplannervaadinfrontend.traveler.save.TravelerUpdate;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;
@Route("customer-list")
public class TravelersGet extends VerticalLayout {
    private Grid<TravelerDTOGet> travelerGrid;

    public void navigateBack() {

        getUI().ifPresent(ui -> ui.navigate("GetStart"));
    }

    public TravelersGet() {
        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        HorizontalLayout buttonLayout = new HorizontalLayout();
        travelerGrid = new Grid<>();
        travelerGrid.addColumn(TravelerDTOGet::getCustomerId).setHeader("Customer Id");
        travelerGrid.addColumn(TravelerDTOGet::getFirstName).setHeader("First Name");
        travelerGrid.addColumn(TravelerDTOGet::getLastName).setHeader("Last Name");
        travelerGrid.addColumn(TravelerDTOGet::getBirthdate).setHeader("Birthdate");
        travelerGrid.addColumn(TravelerDTOGet::getCountry).setHeader("Country");
        travelerGrid.addColumn(TravelerDTOGet::getCity).setHeader("City");
        travelerGrid.addColumn(TravelerDTOGet::getStreetName).setHeader("Street Name");
        travelerGrid.addColumn(TravelerDTOGet::getPostalCode).setHeader("Postal Code");
        travelerGrid.addColumn(TravelerDTOGet::getEmail).setHeader("Email");
        travelerGrid.addColumn(TravelerDTOGet::getPhoneNumber).setHeader("Phone Number");

        Button addCustomer = new Button("Create traveler", e -> UI.getCurrent().navigate(TravelerSave.class));
        Button showCustomersButton = new Button("Show travelers list",event -> showTravelers());
        showCustomersButton.getElement().getStyle().set("right", "45%");

        Button deleteCustomerButton = new Button("DELETE traveler", event -> deleteSelectedTraveler());
        Button editCustomerButton = new Button("EDIT traveler", event -> editSelectedTraveler());

        Button bkButton = new Button("Back", event -> navigateBack());
        bkButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        bkButton.getElement().getStyle().set("left", "45%");

        buttonLayout.add(showCustomersButton,addCustomer,editCustomerButton,deleteCustomerButton);

        add(bkButton, travelerGrid,buttonLayout);
    }

    private void editSelectedTraveler() {
        TravelerDTOGet selectedCustomer = travelerGrid.asSingleSelect().getValue();
        if (selectedCustomer != null) {
            TravelerUpdate editForm = new TravelerUpdate(selectedCustomer);
            editForm.open();
            editForm.addDialogCloseActionListener(event -> {
                refreshTravelerList();
            });
        } else {
            Notification.show("Please select a traveler to edit");
        }
    }

    private void deleteSelectedTraveler() {
        TravelerDTOGet selectedCustomer = travelerGrid.asSingleSelect().getValue();
        if (selectedCustomer != null) {
            try {
                WebClient webClient = WebClient.create("http://localhost:8080/v1/customers");
                webClient.delete()
                        .uri("/{customerId}", selectedCustomer.getCustomerId())
                        .retrieve()
                        .toBodilessEntity()
                        .block();
                refreshTravelerList();
                Notification.show("Customer Deleted !");
            } catch (WebClientResponseException e) {
                Notification.show("Traveler deletion error: " + e.getResponseBodyAsString());
            } catch (Exception e) {
                Notification.show("Traveler deletion error: " + e.getMessage());
            }
        } else {
            Notification.show("Please select a traveler to delete");
        }
    }
    public void refreshTravelerList() {
        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/customers");
            List<TravelerDTOGet> customers = webClient.get()
                    .uri("/getCustomers")
                    .retrieve()
                    .bodyToFlux(TravelerDTOGet.class)
                    .collectList()
                    .block();

            if (customers != null && !customers.isEmpty()) {
                travelerGrid.setItems(customers);
            } else {
                travelerGrid.setItems(Collections.emptyList());
            }
        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving traveler: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving traveler: " + e.getMessage());
        }
    }

    private void showTravelers() {
        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/customers");

            List<TravelerDTOGet> customers = webClient.get()
                    .uri("/getCustomers")
                    .retrieve()
                    .bodyToFlux(TravelerDTOGet.class)
                    .collectList()
                    .block();

            if (customers != null && !customers.isEmpty()) {
                travelerGrid.setItems(customers);
                Notification.show("Travelers loaded successfully");
            } else {
                refreshTravelerList();
                Notification.show("List are empty");
            }
        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving travelers: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving travelers: " + e.getMessage());
        }
    }
}
