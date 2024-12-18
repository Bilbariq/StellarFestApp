package view;

import controller.GuestController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestView {
    private GuestController guestController = new GuestController();

    // Metode start untuk Guest
    public void start(Stage stage, String guestEmail) {
        stage.setTitle("Guest Dashboard");

        // Buttons
        Button viewInvitationsButton = new Button("View Invitations");
        Button viewAcceptedEventsButton = new Button("View Accepted Events");
        Button backButton = new Button("Back to Home");

        Label statusLabel = new Label("Welcome, Guest!");

        // Event Handling
        viewInvitationsButton.setOnAction(e -> showInvitations(stage, guestEmail));
        viewAcceptedEventsButton.setOnAction(e -> showAcceptedEvents(stage, guestEmail));
        backButton.setOnAction(e -> stage.close());

        // Layout
        VBox layout = new VBox(10, statusLabel, viewInvitationsButton, viewAcceptedEventsButton, backButton);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    // View Invitations
    private void showInvitations(Stage stage, String guestEmail) {
        stage.setTitle("View Invitations");

        ListView<String> invitationsList = new ListView<>();
        ObservableList<String> invitations = FXCollections.observableArrayList();

        try (ResultSet rs = guestController.viewInvitations(guestEmail)) {
            while (rs != null && rs.next()) {
                invitations.add("Event ID: " + rs.getInt("event_id") +
                        " | Status: " + rs.getString("invitation_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        invitationsList.setItems(invitations);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> start(stage, guestEmail));

        VBox layout = new VBox(10, invitationsList, backButton);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 500, 400));
    }

    // View Accepted Events
    private void showAcceptedEvents(Stage stage, String guestEmail) {
        stage.setTitle("Accepted Events");

        ListView<String> eventsList = new ListView<>();
        ObservableList<String> events = FXCollections.observableArrayList();

        try (ResultSet rs = guestController.viewAcceptedEvents(guestEmail)) {
            while (rs != null && rs.next()) {
                events.add(rs.getString("event_name") + " | " +
                        rs.getString("event_date") + " | " +
                        rs.getString("event_location"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        eventsList.setItems(events);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> start(stage, guestEmail));

        VBox layout = new VBox(10, eventsList, backButton);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 500, 400));
    }
}
