package view;

import controller.UserController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterView extends Application {
    UserController userController = new UserController();

    @Override
    public void start(Stage stage) {
        // Membuat grid layout untuk tampilan
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Label dan Input untuk Email
        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 0);
        TextField emailField = new TextField();
        emailField.setPromptText("Masukkan Email");
        GridPane.setConstraints(emailField, 1, 0);

        // Label dan Input untuk Username
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Masukkan Username");
        GridPane.setConstraints(usernameField, 1, 1);

        // Label dan Input untuk Password
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Masukkan Password");
        GridPane.setConstraints(passwordField, 1, 2);

        // Label dan ComboBox untuk Role
        Label roleLabel = new Label("Role:");
        GridPane.setConstraints(roleLabel, 0, 3);
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("Admin", "EventOrganizer", "Vendor", "Guest");
        roleBox.setPromptText("Pilih Role");
        GridPane.setConstraints(roleBox, 1, 3);

        // Tombol Register
        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1, 4);

        // Label untuk Hasil
        Label resultLabel = new Label();
        GridPane.setConstraints(resultLabel, 1, 5);

        // Event Handler untuk Tombol Register
        registerButton.setOnAction(e -> {
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleBox.getValue();

            // Validasi Input
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || role == null) {
                resultLabel.setText("Semua field harus diisi!");
            } else if (password.length() < 5) {
                resultLabel.setText("Password minimal 5 karakter!");
            } else {
                // Proses registrasi
                boolean success = userController.register(email, username, password, role);
                if (success) {
                    resultLabel.setText("Registrasi berhasil! Silakan login.");
                } else {
                    resultLabel.setText("Registrasi gagal. Email atau Username sudah digunakan.");
                }
            }
        });

        // Menambahkan komponen ke grid
        grid.getChildren().addAll(emailLabel, emailField, usernameLabel, usernameField,
                passwordLabel, passwordField, roleLabel, roleBox, registerButton, resultLabel);

        // Scene dan Stage
        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Register Pengguna");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
