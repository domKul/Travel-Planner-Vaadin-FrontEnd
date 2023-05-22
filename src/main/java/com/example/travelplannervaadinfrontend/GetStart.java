package com.example.travelplannervaadinfrontend;

import com.example.travelplannervaadinfrontend.booking.get.BookingFinder;
import com.example.travelplannervaadinfrontend.hotel.get.HotelGet;
import com.example.travelplannervaadinfrontend.traveler.get.TravelersGet;
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

       // Button button = new Button("Create User", e -> UI.getCurrent().navigate(TravelerSave.class));
        Button showCustomersButton = new Button("Menage travelers", e ->UI.getCurrent().navigate(TravelersGet.class));

        Button showHotels = new Button("Show Hotels", e ->UI.getCurrent().navigate(HotelGet.class));
        Button createBooking = new Button(" Booking plan", e ->UI.getCurrent().navigate(BookingFinder.class));

        Button bkButton = new Button("Back", event -> navigateBack());
        bkButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        add(showCustomersButton,showHotels,createBooking, bkButton);


    }
}
