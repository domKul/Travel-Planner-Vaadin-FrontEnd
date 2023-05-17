package com.example.travelplannervaadinfrontend;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("maninView")
public class MainView extends VerticalLayout {




    public MainView() {

        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        getElement().getStyle().set("position", "relative");
        Image image = new Image("https://wakacjeamigos.pl/uploads/blog/retina_21a8a187-6fb9-49f1-94bc-ad04e43cd3c1.jpg", "Example image");
        add(image);

        Button button = new Button("Get Start", e -> UI.getCurrent().navigate(GetStart.class));
        button.getStyle().set("background-color", "white");
        button.getElement().getStyle().set("position", "absolute");
        button.getElement().getStyle().set("top", "10%");
        button.getElement().getStyle().set("left", "90%");
        add(button);

    }



}