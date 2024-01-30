package com.example.travelplannervaadinfrontend.traveler.get.currency;

import com.example.travelplannervaadinfrontend.destination.enums.CurrencyEnum;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@Route("convert")
public class Convert extends VerticalLayout {
    private ComboBox<CurrencyEnum> fromComboBox;
    private ComboBox<CurrencyEnum> toComboBox;
    private BigDecimalField amount;
    private TextField resultText;
    private RestTemplate restTemplate;

    public Convert() {
        restTemplate = new RestTemplate();
        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        Button saveButton = new Button("Convert", event -> convert());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        fromComboBox = new ComboBox<>("From");
        fromComboBox.setItems(getCurrencyOptions());
        toComboBox = new ComboBox<>("To");
        toComboBox.setItems(getCurrencyOptions());
        amount = new BigDecimalField("Amount");
        resultText = new TextField("Converted amount");

        add(fromComboBox, toComboBox, amount, saveButton, resultText);
    }

    private void convert() {
        CurrencyEnum fromCurrency = fromComboBox.getValue();
        CurrencyEnum toCurrency = toComboBox.getValue();
        BigDecimal amountValue = amount.getValue();

        if (fromCurrency == null || toCurrency == null || amountValue == null) {
            Notification.show("Please fill in all fields.");
            return;
        }
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/currency/conversion")
                    .queryParam("from", fromCurrency)
                    .queryParam("to", toCurrency)
                    .queryParam("amount", amountValue);
            ResponseEntity<CurrencyDto> responseEntity = restTemplate.getForEntity(
                    builder.toUriString(),
                    CurrencyDto.class
            );
            CurrencyDto currencyDto = responseEntity.getBody();
            resultText.setValue(String.valueOf(currencyDto.result()));
        } catch (HttpClientErrorException e) {
            Notification.show(e.getResponseBodyAsString());
        }
    }

    private List<CurrencyEnum> getCurrencyOptions() {
        return List.of(CurrencyEnum.values());
    }
}
