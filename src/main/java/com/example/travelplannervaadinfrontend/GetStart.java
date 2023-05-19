package com.example.travelplannervaadinfrontend;

import com.example.travelplannervaadinfrontend.customer.get.CustomersGet;
import com.example.travelplannervaadinfrontend.customer.save.CustomerSave;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("GetStart")
public class GetStart extends VerticalLayout {
    public void navigateBack() {

        getUI().ifPresent(ui -> ui.navigate("maninView"));
    }

    public GetStart() {
        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);

        Button button = new Button("Create User", e -> UI.getCurrent().navigate(CustomerSave.class));
        Button showCustomersButton = new Button("Show Customers", e ->UI.getCurrent().navigate(CustomersGet.class));
        Button backButton = new Button("Back", event -> navigateBack());
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(button,showCustomersButton,backButton);


    }
}
