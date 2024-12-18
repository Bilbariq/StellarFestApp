package model;

public class Invitation {
    private int invitationId;
    private String eventName;
    private String eventDate;

    public Invitation(int id, String name, String date) {
        this.invitationId = id;
        this.eventName = name;
        this.eventDate = date;
    }

    public int getInvitationId() { return invitationId; }
    public String getEventName() { return eventName; }
    public String getEventDate() { return eventDate; }
}
