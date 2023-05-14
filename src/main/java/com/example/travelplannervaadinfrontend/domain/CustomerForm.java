package com.example.travelplannervaadinfrontend.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Route("customer")
public class CustomerForm extends VerticalLayout {
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final DatePicker birthdate = new DatePicker("Birthdate");
    private final TextField country = new TextField("Country");
    private final TextField city = new TextField("City");
    private final TextField streetName = new TextField("Street name");
    private final TextField postalCode = new TextField("Postal code");
    private final TextField email = new TextField("Email");
    private final TextField phoneNumber = new TextField("Phone number");
    private final Button saveButton = new Button("Save", this::saveCustomer);


    public CustomerForm() {
        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(
                firstName,
                lastName,
                birthdate,
                country,
                city,
                streetName,
                postalCode,
                email,
                phoneNumber,
                saveButton
        );
    }

    public Date getBirthdate() {
        LocalDate localDate = birthdate.getValue();
        if (localDate == null) {
            return null;
        }
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    private CustomerDTO saveCustomer(ClickEvent<Button> event) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(firstName.getValue());
        customerDTO.setLastName(lastName.getValue());
        customerDTO.setBirthdate(getBirthdate());
        customerDTO.setCountry(country.getValue());
        customerDTO.setCity(city.getValue());
        customerDTO.setStreetName(streetName.getValue());
        customerDTO.setPostalCode(postalCode.getValue());
        customerDTO.setEmail(email.getValue());
        customerDTO.setPhoneNumber(Integer.parseInt(phoneNumber.getValue()));

        try {
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(customerDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/v1/customers";
            ResponseEntity<CustomerDTO> responseEntity = restTemplate.postForEntity(url, requestEntity, CustomerDTO.class);

            Notification.show("Customer saved");
            return responseEntity.getBody();
        } catch (JsonProcessingException e) {
            Notification.show("Error saving customer");
            return null;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            Notification.show("Error saving customer: " + e.getResponseBodyAsString());
            return null;
        } catch (RestClientException e) {
            Notification.show("Error saving customer: " + e.getMessage());
            return null;
        }
    }

}
