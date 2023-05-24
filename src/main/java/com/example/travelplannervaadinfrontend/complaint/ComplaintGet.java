package com.example.travelplannervaadinfrontend.complaint;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;

@Route("getComplaints")
public class ComplaintGet extends VerticalLayout {

    private Grid<ComplaintGetDTO>complaintGetDTOGrid;

  public ComplaintGet(){
      setMargin(true);
      setSpacing(false);
      setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
      setAlignItems(FlexComponent.Alignment.CENTER);
      complaintGetDTOGrid = new Grid<>();
      complaintGetDTOGrid.addColumn(ComplaintGetDTO::getComplaintId).setHeader("complaintId");
      complaintGetDTOGrid.addColumn(ComplaintGetDTO::getTitle).setHeader("Title");
      complaintGetDTOGrid.addColumn(ComplaintGetDTO::getDescription).setHeader("Description");
      complaintGetDTOGrid.addColumn(ComplaintGetDTO::getComplaintDate).setHeader("Date of Creation");
      complaintGetDTOGrid.addColumn(ComplaintGetDTO::getStatus).setHeader("Status");
      complaintGetDTOGrid.addColumn(ComplaintGetDTO::getCustomerId).setHeader("customer Id");

      Button refreshComplaints = new Button("Refresh List",e->refreshComplaits());

      add(complaintGetDTOGrid,refreshComplaints);
  }

    private void refreshComplaits() {
        try {
            WebClient webClient = WebClient.create("http://localhost:8080/v1/complaints");
            List<ComplaintGetDTO> bookings = webClient.get()
                    .retrieve()
                    .bodyToFlux(ComplaintGetDTO.class)
                    .collectList()
                    .block();

            if (bookings != null && !bookings.isEmpty()) {
                complaintGetDTOGrid.setItems(bookings);

            } else {
                complaintGetDTOGrid.setItems(Collections.emptyList());
            }
        } catch (WebClientResponseException e) {
            Notification.show("Error retrieving complaints: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            Notification.show("Error retrieving complaints: " + e.getMessage());
        }
    }
}
