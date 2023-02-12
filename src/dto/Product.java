package dto;

public class Product {
   private String id;
   private String name;
   private String unit;
   private String origin;
   private double price;
   
    public Product() {
    }

    public Product(String id, String name, String unit, String origin, double price) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.origin = origin;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("|%-6s|%-25s|%-25s|%-25s|%-9.3f|", id, name, unit, origin, price);
    }

    public void showProfile() {
        System.out.printf("|%-6s|%-25s|%-25s|%-25s|%-9.3f|%n", id, name, unit, origin, price);
    }
   
}
