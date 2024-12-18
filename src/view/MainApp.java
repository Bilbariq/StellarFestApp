package view;

import controller.UserController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage mainStage; // Stage utama aplikasi
    private UserController userController = new UserController(); // Controller untuk login
    private String currentUserRole = null; // Role pengguna yang login

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        showHomePage();
    }

    // Halaman Utama
    private void showHomePage() {
        mainStage.setTitle("StellarFest - Home Page");

        Label welcomeLabel = new Label("=== Selamat Datang di StellarFest ===");
        Button registerButton = new Button("Register");
        Button loginButton = new Button("Login");
        Button editProfileButton = new Button("Edit Profile");
        Button exitButton = new Button("Exit");

        // Event Handling
        registerButton.setOnAction(e -> showRegisterPage());
        loginButton.setOnAction(e -> showLoginPage());
        editProfileButton.setOnAction(e -> showEditProfilePage());
        exitButton.setOnAction(e -> mainStage.close());

        // Layout
        VBox layout = new VBox(15, welcomeLabel, registerButton, loginButton, editProfileButton, exitButton);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 350, 250);

        mainStage.setScene(scene);
        mainStage.show();
    }

    // Halaman Register
    private void showRegisterPage() {
        Stage stage = new Stage();
        stage.setTitle("Register");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("EventOrganizer", "Admin", "Guest", "Vendor");
        roleBox.setPromptText("Pilih Role");

        Button registerButton = new Button("Register");
        Button backButton = new Button("Kembali");

        Label resultLabel = new Label();

        registerButton.setOnAction(e -> {
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleBox.getValue();

            if (!email.isEmpty() && !username.isEmpty() && !password.isEmpty() && role != null) {
                boolean success = userController.register(email, username, password, role);
                resultLabel.setText(success ? "Registrasi Berhasil!" : "Registrasi Gagal.");
            } else {
                resultLabel.setText("Semua field harus diisi!");
            }
        });

        backButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(10, new Label("Register User"), emailField, usernameField, passwordField, roleBox, registerButton, resultLabel, backButton);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 350, 300);

        stage.setScene(scene);
        stage.show();
    }

    // Halaman Login
    private void showLoginPage() {
        Stage stage = new Stage();
        stage.setTitle("Login");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        Label resultLabel = new Label();

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (userController.login(email, password)) {
                currentUserRole = userController.getCurrentRole();
                stage.close(); // Tutup halaman login

                if ("EventOrganizer".equals(currentUserRole)) {
                    showEventOrganizerDashboard();
                } else if ("Vendor".equals(currentUserRole)) {
                    showVendorView();
                } else if ("Guest".equals(currentUserRole)) {
                        showGuestView();
                } else {
                    showGeneralHomePage();
                }
            } else {
                resultLabel.setText("Login gagal. Periksa email dan password.");
            }
        });

        VBox layout = new VBox(10, new Label("Login"), emailField, passwordField, loginButton, resultLabel);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 350, 200);

        stage.setScene(scene);
        stage.show();
    }

    // Halaman Edit Profile
    private void showEditProfilePage() {
        Stage stage = new Stage();
        stage.setTitle("Edit Profile");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField newNameField = new TextField();
        newNameField.setPromptText("Nama Baru");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Password Baru");

        Button updateButton = new Button("Update");
        Label resultLabel = new Label();

        updateButton.setOnAction(e -> {
            String email = emailField.getText();
            String newName = newNameField.getText();
            String newPassword = newPasswordField.getText();

            boolean success = userController.updateProfile(email, newName, newPassword);
            resultLabel.setText(success ? "Profil berhasil diperbarui!" : "Gagal memperbarui profil.");
        });

        Button backButton = new Button("Kembali");
        backButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(10, emailField, newNameField, newPasswordField, updateButton, resultLabel, backButton);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 350, 250);

        stage.setScene(scene);
        stage.show();
    }

    // Halaman General Home Page
    private void showGeneralHomePage() {
        mainStage.setTitle("Home Page");

        Label label = new Label("Selamat Datang di Home Page!");
        Button backButton = new Button("Logout");

        backButton.setOnAction(e -> showHomePage());

        VBox layout = new VBox(10, label, backButton);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 350, 200);

        mainStage.setScene(scene);
    }

    // Dashboard untuk Event Organizer
    private void showEventOrganizerDashboard() {
        EventOrganizerView organizerView = new EventOrganizerView();
        Stage organizerStage = new Stage();
        organizerView.start(organizerStage);
    }

    // View untuk Vendor
    private void showVendorView() {
        VendorView vendorView = new VendorView();
        Stage vendorStage = new Stage();
        vendorView.start(vendorStage, "vendor@example.com"); // Kirim email vendor
    }
    
    private void showGuestView() {
        GuestView guestView = new GuestView();
        Stage guestStage = new Stage();
        guestView.start(guestStage, "guest@example.com"); // Kirim email guest
    }


    public static void main(String[] args) {
        launch(args);
    }
}
