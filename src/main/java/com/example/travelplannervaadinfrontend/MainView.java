package com.example.travelplannervaadinfrontend;


import com.example.travelplannervaadinfrontend.domain.CustomerForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.awt.print.Book;

@Route
public class MainView extends VerticalLayout {



    private Grid<Book> grid = new Grid<>(Book.class);
    private TextField filter = new TextField();

    CustomerForm customerForm = new CustomerForm();

    public MainView() {
        add(customerForm);
    }



}