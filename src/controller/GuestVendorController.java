package controller;

import model.DBConnection;
import java.sql.*;
import java.util.ArrayList;

public class GuestVendorController {

    // Ambil semua tamu dari database
    public ArrayList<String> getAllGuests() {
        ArrayList<String> guests = new ArrayList<>();
        try (Connection con = DBConnection.connect()) {
            String query = "SELECT guest_id, guest_name FROM guests";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                guests.add("ID: " + rs.getInt("guest_id") + " - " + rs.getString("guest_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guests;
    }

    // Tambahkan Guest ke Event
    public boolean addGuestToEvent(int eventID, int guestID) {
        try (Connection con = DBConnection.connect()) {
            String query = "INSERT INTO event_guests (event_id, guest_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, eventID);
            ps.setInt(2, guestID);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Ambil semua vendor dari database
    public ArrayList<String> getAllVendors() {
        ArrayList<String> vendors = new ArrayList<>();
        try (Connection con = DBConnection.connect()) {
            String query = "SELECT vendor_id, vendor_name FROM vendors";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                vendors.add("ID: " + rs.getInt("vendor_id") + " - " + rs.getString("vendor_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendors;
    }

    // Tambahkan Vendor ke Event
    public boolean addVendorToEvent(int eventID, int vendorID) {
        try (Connection con = DBConnection.connect()) {
            String query = "INSERT INTO event_vendors (event_id, vendor_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, eventID);
            ps.setInt(2, vendorID);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}