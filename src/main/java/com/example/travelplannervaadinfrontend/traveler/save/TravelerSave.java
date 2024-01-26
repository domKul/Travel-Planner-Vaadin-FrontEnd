package com.example.travelplannervaadinfrontend.traveler.save;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.ClickEvent;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Route("traveler")
public class TravelerSave extends VerticalLayout {
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final DatePicker birthdate = new DatePicker("Birthdate");
    private final TextField country = new TextField("Country");
    private final TextField city = new TextField("City");
    private final TextField streetName = new TextField("Street name");
    private final TextField postalCode = new TextField("Postal code");
    private final TextField email = new TextField("Email");
    private final TextField phoneNumber = new TextField("Phone number");
    private final Button saveButton = new Button("Save", this::saveTraveler);
    Button backButton = new Button("Back", event -> navigateBack());




    public TravelerSave() {
        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        backButton.getElement().getStyle().set("top", "20%");
        backButton.getElement().getStyle().set("left", "45%");
        add(
                backButton,
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
    public void navigateBack() {

        getUI().ifPresent(ui -> ui.navigate("travelers-list"));
    }

    public Date getBirthdate() {
        LocalDate localDate = birthdate.getValue();
        if (localDate == null) {
            return null;
        }
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    private TravelerDTO saveTraveler(ClickEvent<Button> event) {
        TravelerDTO travelerDTO = new TravelerDTO();
        travelerDTO.setFirstName(firstName.getValue());
        travelerDTO.setLastName(lastName.getValue());
        travelerDTO.setBirthdate(getBirthdate());
        travelerDTO.setCountry(country.getValue());
        travelerDTO.setCity(city.getValue());
        travelerDTO.setStreetName(streetName.getValue());
        travelerDTO.setPostalCode(postalCode.getValue());
        travelerDTO.setEmail(email.getValue());
        if (!phoneNumber.getValue().isEmpty()) {
            travelerDTO.setPhoneNumber(Integer.parseInt(phoneNumber.getValue()));
        } else {
            Notification.show("Phone number is required");
            return null;
        }

        if (travelerDTO.getFirstName().isEmpty() || travelerDTO.getLastName().isEmpty() ||
                travelerDTO.getBirthdate() == null || travelerDTO.getCountry().isEmpty() ||
                travelerDTO.getCity().isEmpty() || travelerDTO.getStreetName().isEmpty() ||
                travelerDTO.getPostalCode().isEmpty() || travelerDTO.getEmail().isEmpty()) {
            Notification.show("Please fill in all fields");
            return null;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(travelerDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://vps-7c561477.vps.ovh.net:8080/v1/customers";
            ResponseEntity<TravelerDTO> responseEntity = restTemplate.postForEntity(url, requestEntity, TravelerDTO.class);

            Notification.show("Customer saved");
            return responseEntity.getBody();
        } catch (JsonProcessingException e) {
            Notification.show("Error saving traveler");
            return null;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            Notification.show("Error saving traveler: " + e.getResponseBodyAsString());
            return null;
        } catch (RestClientException e) {
            Notification.show("Error saving traveler: " + e.getMessage());
            return null;
        }
    }

}
