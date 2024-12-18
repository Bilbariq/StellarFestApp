package controller;

import model.DBConnection;
import java.sql.*;

public class VendorController {

    // Accept Invitation
    public boolean acceptInvitation(int eventID, String vendorEmail) {
        try (Connection con = DBConnection.connect()) {
            String query = "INSERT INTO event_vendors (event_id, vendor_email) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, eventID);
            ps.setString(2, vendorEmail);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // View Accepted Events
    public ResultSet viewAcceptedEvents(String vendorEmail) {
        try (Connection con = DBConnection.connect()) {
            String query = "SELECT e.event_name, e.event_date, e.event_location FROM events e " +
                           "JOIN event_vendors ev ON e.event_id = ev.event_id " +
                           "WHERE ev.vendor_email = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, vendorEmail);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // View Invitations
    public ResultSet viewInvitations() {
        try (Connection con = DBConnection.connect()) {
            String query = "SELECT * FROM invitations WHERE invitation_status = 'Pending'";
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Manage Vendor Info
    public boolean updateVendorInfo(String email, String productName, String productDescription) {
        try (Connection con = DBConnection.connect()) {
            String query = "UPDATE vendors SET product_name = ?, product_description = ? WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, productName);
            ps.setString(2, productDescription);
            ps.setString(3, email);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
