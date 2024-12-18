package controller;

import model.DBConnection;
import java.sql.*;

public class UserController {
    private String currentRole; // Menyimpan role pengguna saat ini setelah login
    private String currentUsername; // Menyimpan username pengguna

    // Method untuk Login pengguna
    public boolean login(String email, String password) {
        try (Connection con = DBConnection.connect()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                currentRole = rs.getString("role"); // Simpan role pengguna
                currentUsername = rs.getString("username");
                System.out.println("Login berhasil! Selamat datang, " + currentUsername);
                return true;
            } else {
                System.out.println("Email atau Password salah.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method untuk Register pengguna
    public boolean register(String email, String username, String password, String role) {
        try (Connection con = DBConnection.connect()) {
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                System.out.println("Semua field harus diisi.");
                return false;
            }

            if (password.length() < 5) {
                System.out.println("Password harus minimal 5 karakter.");
                return false;
            }

            // Validasi jika email atau username sudah digunakan
            String checkQuery = "SELECT * FROM users WHERE email = ? OR username = ?";
            PreparedStatement checkPs = con.prepareStatement(checkQuery);
            checkPs.setString(1, email);
            checkPs.setString(2, username);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                System.out.println("Email atau Username sudah digunakan.");
                return false;
            }

            // Insert user baru jika valid
            String insertQuery = "INSERT INTO users (email, username, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setString(1, email);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, role);

            ps.executeUpdate();
            System.out.println("Registrasi berhasil! Silakan login.");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method untuk Update Profil pengguna
    public boolean updateProfile(String email, String newName, String newPassword) {
        try (Connection con = DBConnection.connect()) {
            if (newPassword.length() < 5) {
                System.out.println("Password baru harus minimal 5 karakter.");
                return false;
            }

            String updateQuery = "UPDATE users SET username = ?, password = ? WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(updateQuery);
            ps.setString(1, newName);
            ps.setString(2, newPassword);
            ps.setString(3, email);

            int updatedRows = ps.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Profil berhasil diperbarui.");
                return true;
            } else {
                System.out.println("Email tidak ditemukan.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method untuk mendapatkan role pengguna setelah login
    public String getCurrentRole() {
        return currentRole;
    }

    // Method untuk mendapatkan username pengguna yang login
    public String getCurrentUsername() {
        return currentUsername;
    }
}
