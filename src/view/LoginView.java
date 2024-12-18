package view;

import controller.UserController;
import java.util.Scanner;

public class LoginView {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserController userController = new UserController();

        System.out.print("Masukkan email: ");
        String email = sc.next();
        System.out.print("Masukkan password: ");
        String password = sc.next();

        if (userController.login(email, password)) {
            System.out.println("Login berhasil!");
        } else {
            System.out.println("Login gagal. Periksa email dan password.");
        }
    }
}
