package dto;

public class Admin {
    private String id;
    private String password;

    public Admin() {
        id = "admin";
        password = "123456";
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    // public static void main(String[] args) {
    //     Admin x = new Admin();
    //     System.out.println(x.getId());
    //     System.out.println(x.getPassword());
    // }
}
