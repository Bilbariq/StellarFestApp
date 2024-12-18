package view;

import controller.EventController;
import controller.GuestVendorController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EventOrganizerView extends Application {
    EventController eventController = new EventController();
    GuestVendorController guestVendorController = new GuestVendorController();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Event Organizer Dashboard");

        // Tombol Menu Utama
        Button createEventButton = new Button("Create Event");
        Button viewEventsButton = new Button("View Events");
        Button editEventButton = new Button("Edit Event Name");
        Button addGuestButton = new Button("Add Guests");
        Button addVendorButton = new Button("Add Vendors");
        Button exitButton = new Button("Exit");

        // Event Handling untuk setiap tombol
        createEventButton.setOnAction(e -> createEvent());
        viewEventsButton.setOnAction(e -> viewEvents());
        editEventButton.setOnAction(e -> editEventName());
        addGuestButton.setOnAction(e -> addGuestToEvent());
        addVendorButton.setOnAction(e -> addVendorToEvent());
        exitButton.setOnAction(e -> stage.close());

        // Layout Utama
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Event Organizer Menu"),
                createEventButton,
                viewEventsButton,
                editEventButton,
                addGuestButton,
                addVendorButton,
                exitButton
        );

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    // 1. Create Event
    private void createEvent() {
        Stage stage = new Stage();
        stage.setTitle("Create Event");

        // Input fields
        TextField nameField = new TextField();
        nameField.setPromptText("Event Name");

        TextField dateField = new TextField();
        dateField.setPromptText("Event Date (YYYY-MM-DD)");

        TextField locationField = new TextField();
        locationField.setPromptText("Event Location");

        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Event Description");

        Button submitButton = new Button("Create");
        Label resultLabel = new Label();

        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String date = dateField.getText();
            String location = locationField.getText();
            String description = descriptionField.getText();

            if (!name.isEmpty() && !date.isEmpty() && !location.isEmpty() && !description.isEmpty()) {
                boolean success = eventController.createEvent(name, date, location, description);
                resultLabel.setText(success ? "Event created successfully!" : "Failed to create event.");
            } else {
                resultLabel.setText("All fields must be filled.");
            }
        });

        VBox layout = new VBox(10, nameField, dateField, locationField, descriptionField, submitButton, resultLabel);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 400, 400);

        stage.setScene(scene);
        stage.show();
    }

    // 2. View Events
    private void viewEvents() {
        Stage stage = new Stage();
        stage.setTitle("View Events");

        ListView<String> eventList = new ListView<>();
        eventList.getItems().addAll(eventController.getAllEvents());

        VBox layout = new VBox(10, new Label("All Events:"), eventList);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 400, 400);

        stage.setScene(scene);
        stage.show();
    }

    // 3. Edit Event Name
    private void editEventName() {
        Stage stage = new Stage();
        stage.setTitle("Edit Event Name");

        TextField eventIDField = new TextField();
        eventIDField.setPromptText("Event ID");

        TextField newNameField = new TextField();
        newNameField.setPromptText("New Event Name");

        Button updateButton = new Button("Update");
        Label resultLabel = new Label();

        updateButton.setOnAction(e -> {
            int eventID = Integer.parseInt(eventIDField.getText());
            String newName = newNameField.getText();

            boolean success = eventController.editEventName(eventID, newName);
            resultLabel.setText(success ? "Event name updated successfully!" : "Failed to update event name.");
        });

        VBox layout = new VBox(10, eventIDField, newNameField, updateButton, resultLabel);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 400, 250);

        stage.setScene(scene);
        stage.show();
    }

    // 4. Add Guest to Event
    private void addGuestToEvent() {
        Stage stage = new Stage();
        stage.setTitle("Add Guest to Event");

        TextField eventIDField = new TextField();
        eventIDField.setPromptText("Event ID");

        ListView<String> guestList = new ListView<>();
        guestList.getItems().addAll(guestVendorController.getAllGuests());

        Button addButton = new Button("Add Guest");
        Label resultLabel = new Label();

        addButton.setOnAction(e -> {
            String selectedGuest = guestList.getSelectionModel().getSelectedItem();
            if (selectedGuest != null && !eventIDField.getText().isEmpty()) {
                int guestID = Integer.parseInt(selectedGuest.split(" ")[1]); // Extract guest ID
                int eventID = Integer.parseInt(eventIDField.getText());

                boolean success = guestVendorController.addGuestToEvent(eventID, guestID);
                resultLabel.setText(success ? "Guest added successfully!" : "Failed to add guest.");
            } else {
                resultLabel.setText("Please select a guest and input event ID.");
            }
        });

        VBox layout = new VBox(10, new Label("Event ID:"), eventIDField, guestList, addButton, resultLabel);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 400, 400);

        stage.setScene(scene);
        stage.show();
    }

    // 5. Add Vendor to Event
    private void addVendorToEvent() {
        Stage stage = new Stage();
        stage.setTitle("Add Vendor to Event");

        TextField eventIDField = new TextField();
        eventIDField.setPromptText("Event ID");

        ListView<String> vendorList = new ListView<>();
        vendorList.getItems().addAll(guestVendorController.getAllVendors());

        Button addButton = new Button("Add Vendor");
        Label resultLabel = new Label();

        addButton.setOnAction(e -> {
            String selectedVendor = vendorList.getSelectionModel().getSelectedItem();
            if (selectedVendor != null && !eventIDField.getText().isEmpty()) {
                int vendorID = Integer.parseInt(selectedVendor.split(" ")[1]); // Extract vendor ID
                int eventID = Integer.parseInt(eventIDField.getText());

                boolean success = guestVendorController.addVendorToEvent(eventID, vendorID);
                resultLabel.setText(success ? "Vendor added successfully!" : "Failed to add vendor.");
            } else {
                resultLabel.setText("Please select a vendor and input event ID.");
            }
        });

        VBox layout = new VBox(10, new Label("Event ID:"), eventIDField, vendorList, addButton, resultLabel);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 400, 400);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
