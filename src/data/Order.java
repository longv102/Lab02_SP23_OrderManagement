package data;

public class Order {
    private String id;
    private String customerId;
    private String productId;
    private int quantity;
    private String date;
    private String status;

    public Order() {
    }

    public Order(String id, String customerId, String productId, int quantity, String date, String status) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void showProfile() {
        System.out.printf("|%-6s|%-6s|%-6s|%-3d|%-20s|%-8s|%n", id, customerId, productId, quantity, date, status);
    }
    
}
