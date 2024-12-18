package view;

import controller.VendorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VendorView {
    private VendorController vendorController = new VendorController();

    public void start(Stage stage, String vendorEmail) {
        stage.setTitle("Vendor Dashboard");

        // Buttons
        Button viewInvitationsButton = new Button("View Invitations");
        Button viewAcceptedEventsButton = new Button("View Accepted Events");
        Button manageVendorButton = new Button("Manage Vendor Info");
        Button backButton = new Button("Back to Home");

        Label statusLabel = new Label("Welcome, Vendor!");

        // Event Handling
        viewInvitationsButton.setOnAction(e -> showInvitations(stage, vendorEmail));
        viewAcceptedEventsButton.setOnAction(e -> showAcceptedEvents(stage, vendorEmail));
        manageVendorButton.setOnAction(e -> showManageVendorPage(stage, vendorEmail));
        backButton.setOnAction(e -> stage.close());

        // Layout
        VBox layout = new VBox(10, statusLabel, viewInvitationsButton, viewAcceptedEventsButton, manageVendorButton, backButton);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    // View Invitations
    private void showInvitations(Stage stage, String vendorEmail) {
        stage.setTitle("View Invitations");

        ListView<String> invitationsList = new ListView<>();
        ObservableList<String> invitations = FXCollections.observableArrayList();

        try (ResultSet rs = vendorController.viewInvitations()) {
            while (rs != null && rs.next()) {
                invitations.add("Event ID: " + rs.getInt("event_id") + " | Status: " + rs.getString("invitation_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        invitationsList.setItems(invitations);

        Button acceptButton = new Button("Accept Invitation");
        Label statusLabel = new Label();

        acceptButton.setOnAction(e -> {
            String selectedItem = invitationsList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int eventID = Integer.parseInt(selectedItem.split(": ")[1].split(" ")[0]);
                if (vendorController.acceptInvitation(eventID, vendorEmail)) {
                    statusLabel.setText("Invitation accepted successfully!");
                } else {
                    statusLabel.setText("Failed to accept invitation.");
                }
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> start(stage, vendorEmail));

        VBox layout = new VBox(10, invitationsList, acceptButton, backButton, statusLabel);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 500, 400));
    }

    // View Accepted Events
    private void showAcceptedEvents(Stage stage, String vendorEmail) {
        stage.setTitle("Accepted Events");

        ListView<String> eventsList = new ListView<>();
        ObservableList<String> events = FXCollections.observableArrayList();

        try (ResultSet rs = vendorController.viewAcceptedEvents(vendorEmail)) {
            while (rs != null && rs.next()) {
                events.add(rs.getString("event_name") + " | " + rs.getString("event_date") + " | " + rs.getString("event_location"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        eventsList.setItems(events);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> start(stage, vendorEmail));

        VBox layout = new VBox(10, eventsList, backButton);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 500, 400));
    }

    // Manage Vendor Info
    private void showManageVendorPage(Stage stage, String vendorEmail) {
        stage.setTitle("Manage Vendor Info");

        TextField productNameField = new TextField();
        productNameField.setPromptText("Enter Product Name");

        TextArea productDescriptionField = new TextArea();
        productDescriptionField.setPromptText("Enter Product Description");

        Button saveButton = new Button("Save");
        Label statusLabel = new Label();

        saveButton.setOnAction(e -> {
            String productName = productNameField.getText();
            String productDescription = productDescriptionField.getText();
            if (vendorController.updateVendorInfo(vendorEmail, productName, productDescription)) {
                statusLabel.setText("Vendor info updated successfully!");
            } else {
                statusLabel.setText("Failed to update vendor info.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> start(stage, vendorEmail));

        VBox layout = new VBox(10, productNameField, productDescriptionField, saveButton, backButton, statusLabel);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 500, 400));
    }
}
