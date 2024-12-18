package controller;

import model.DBConnection;
import java.sql.*;
import java.util.ArrayList;

public class EventController {
    // Create Event
    public boolean createEvent(String name, String date, String location, String description) {
        try (Connection con = DBConnection.connect()) {
            String query = "INSERT INTO events (event_name, event_date, event_location, event_description) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, location);
            ps.setString(4, description);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // View All Events
    public ArrayList<String> getAllEvents() {
        ArrayList<String> events = new ArrayList<>();
        try (Connection con = DBConnection.connect()) {
            String query = "SELECT * FROM events";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String event = "ID: " + rs.getInt("event_id") + ", Name: " + rs.getString("event_name");
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    // Edit Event Name
    public boolean editEventName(int eventID, String newName) {
        try (Connection con = DBConnection.connect()) {
            String query = "UPDATE events SET event_name = ? WHERE event_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, newName);
            ps.setInt(2, eventID);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
