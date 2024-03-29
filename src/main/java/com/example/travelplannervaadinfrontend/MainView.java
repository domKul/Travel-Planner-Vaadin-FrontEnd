package com.example.travelplannervaadinfrontend;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

@Route("maninView")
@PreserveOnRefresh
public class MainView extends VerticalLayout {


    public MainView() {

        setMargin(true);
        setSpacing(false);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        getElement().getStyle().set("position", "relative");
        Image image = new Image("https://www.radio.bialystok.pl/src/461/34afd5082c02ab7d6d0488ef1648c0f4", "Example image");
        add(image);

        Button buttonGetStart = new Button("Get Start", e -> UI.getCurrent().navigate(GetStart.class));
        buttonGetStart.getStyle().set("background-color", "white");
        buttonGetStart.getElement().getStyle().set("position", "absolute");
        buttonGetStart.getElement().getStyle().set("top", "10%");
        buttonGetStart.getElement().getStyle().set("left", "90%");

        Button buttonAPI = new Button("BackEnd-API");
        buttonAPI.getStyle().set("background-color", "white");
        buttonAPI.getElement().getStyle().set("position", "absolute");
        buttonAPI.getElement().getStyle().set("top", "10%");
        buttonAPI.getElement().getStyle().set("left", "82%");
        buttonAPI.addClickListener(e -> getUI().ifPresent(ui -> ui.getPage().executeJs("window.open('https://github.com/domKul/Travel-Planner', '_blank')")));

        add(buttonGetStart,buttonAPI);

    }
}