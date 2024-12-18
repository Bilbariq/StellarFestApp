package model;

public class User {
    private int userID;
    private String email;
    private String username;
    private String password;
    private String role;

    // Constructor
    public User(String email, String username, String password, String role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getPassword() { return password; }
}
