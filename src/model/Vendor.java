package model;

public class Vendor {
    private String accepted_invitations;
    private String description;
    private String product;

    // Constructor
    public Vendor(String description, String product) {
        this.description = description;
        this.product = product;
    }

    // Getter dan Setter
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
