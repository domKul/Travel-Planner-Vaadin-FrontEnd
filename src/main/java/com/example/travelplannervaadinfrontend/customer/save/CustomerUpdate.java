package com.example.travelplannervaadinfrontend.customer.save;

import com.example.travelplannervaadinfrontend.customer.get.CustomerDTOGet;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Route("customerUpdate")
public class CustomerUpdate extends Dialog {
    private final CustomerDTOGet customer;
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private  DatePicker birthdate = new DatePicker("Birthdate");
    private  TextField country = new TextField("Country");
    private  TextField city = new TextField("City");
    private  TextField streetName = new TextField("Street name");
    private  TextField postalCode = new TextField("Postal code");
    private  TextField email = new TextField("Email");
    private  TextField phoneNumber = new TextField("Phone number");

    public CustomerUpdate(CustomerDTOGet customer) {
        this.customer = customer;
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);


        firstName.setValue(customer.getFirstName());
        lastName.setValue(customer.getFirstName());
        birthdate.setValue(birthdate.getValue());
        country.setValue(customer.getCountry());
        city.setValue(customer.getCity());
        streetName.setValue(customer.getStreetName());
        postalCode.setValue(customer.getPostalCode());
        email.setValue(customer.getEmail());
        phoneNumber.setValue(customer.getPhoneNumber());

        Button saveButton = new Button("Save", event -> saveCustomerWithUpdate());
        Button cancelButton = new Button("Cancel", event -> close());

        VerticalLayout content = new VerticalLayout(firstName, lastName,birthdate,country,city
                ,streetName,postalCode,email,phoneNumber,saveButton, cancelButton);
        add(content);
    }
    public Date getBirthdate() {
        LocalDate localDate = birthdate.getValue();
        if (localDate == null) {
            return null;
        }
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    private void saveCustomerWithUpdate() {
        String newFirstName = firstName.getValue();
        String newLastName = lastName.getValue();
        String newCountry = country.getValue();
        String newCity = city.getValue();
        String newStreetName = streetName.getValue();
        String newPostalCode = postalCode.getValue();
        String newEmail = email.getValue();
        String newPhoneNumber = phoneNumber.getValue();


        CustomerDTO updatedCustomer = new CustomerDTO();
        updatedCustomer.setFirstName(newLastName);
        updatedCustomer.setLastName(newFirstName);
        updatedCustomer.setBirthdate(getBirthdate());
        updatedCustomer.setCountry(newCountry);
        updatedCustomer.setCity(newCity);
        updatedCustomer.setStreetName(newStreetName);
        updatedCustomer.setPostalCode(newPostalCode);
        updatedCustomer.setEmail(newEmail);
        updatedCustomer.setPhoneNumber(Integer.parseInt(newPhoneNumber));

        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/customers");
            CustomerDTO response = webClient.put()
                    .uri("/{customerId}", customer.getCustomerId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(updatedCustomer)
                    .retrieve()
                    .bodyToMono(CustomerDTO.class)
                    .block();

            if (response != null) {
                Notification.show("Klient został zaktualizowany");
                close();
            } else {
                Notification.show("Wystąpił błąd podczas zapisywania klienta");
            }
        } catch (WebClientResponseException e) {
            Notification.show("Błąd podczas komunikacji z serwerem: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Wystąpił nieznany błąd: " + e.getMessage());
        }
    }
}
