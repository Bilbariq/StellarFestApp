package controller;

import model.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GuestController {

    // Melihat semua undangan untuk Guest
    public ResultSet viewInvitations(String email) {
        String query = "SELECT * FROM Invitation WHERE user_id = (SELECT user_id FROM User WHERE user_email = ?)";
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            return stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Melihat semua event yang diterima
    public ResultSet viewAcceptedEvents(String email) {
        String query = "SELECT e.event_name, e.event_date, e.event_location " +
                       "FROM Event e JOIN Invitation i ON e.event_id = i.event_id " +
                       "WHERE i.user_id = (SELECT user_id FROM User WHERE user_email = ?) AND i.invitation_status = 'Accepted'";
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            return stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
