package model;

public class Guest {
    private int guestID;
    private String guestName;
    private String guestEmail;
    private String phoneNumber;

    // Constructor
    public Guest(int guestID, String guestName, String guestEmail, String phoneNumber) {
        this.guestID = guestID;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public int getGuestID() {
        return guestID;
    }

    public void setGuestID(int guestID) {
        this.guestID = guestID;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
