package com.example.travelplannervaadinfrontend;


import com.example.travelplannervaadinfrontend.domain.CustomerForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("maninView")
public class MainView extends VerticalLayout {


    CustomerForm customerForm = new CustomerForm();

    public MainView() {

        add(customerForm);
    }



}