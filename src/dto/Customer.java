package dto;

public class Customer implements Comparable<Customer> {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;

    public Customer() {
    }

    public Customer(String id, String name, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void showProfile() {
        System.out.printf("|%-6s|%-25s|%-25s|%-16s|%n", id, name, address, phoneNumber);
    }

    
    @Override
    public String toString() {
        return String.format("|%-6s|%-25s|%-25s|%-16s|", id, name, address, phoneNumber);
    }

    @Override
    public int compareTo(Customer that) {
        return this.name.compareToIgnoreCase(that.getName());
    }
}
