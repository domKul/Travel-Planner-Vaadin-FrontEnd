package com.example.travelplannervaadinfrontend.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    public Date getBirthdate() {
        LocalDate localDate = birthdate.getValue();
        if (localDate == null) {
            return null;
        }
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
    public CustomerForm() {
        //setSizeFull();
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

        HttpEntity<CustomerDTO> requestEntity = new HttpEntity<>(customerDTO, headers);
        String url = "http://localhost:8080/v1/customers";
        CustomerDTO cus= restTemplate.postForEntity(url, requestEntity, CustomerDTO.class).getBody();
        Notification.show("Customer saved");
        return cus;
    }

}
