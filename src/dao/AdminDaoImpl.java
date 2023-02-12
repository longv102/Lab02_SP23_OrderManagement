package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import crud.AdminDao;
import dto.Customer;
import dto.Product;
import util.Tools;

public class AdminDaoImpl implements AdminDao {

    private final Scanner sc = new Scanner(System.in);
    private List<Customer> customers = getCustomersFromFile();
    private List<Product> products = getProductsFromFile();
    // For using in inputted a new product, synchronize with list products
    private List<Product> productList = new ArrayList<>();
    private String CUSTOMERS_FILE = "Customers.txt";
    private String PRODUCTS_FILE = "Products.txt";

    @Override
    public void addAProduct() {
        // Declaration
        String id, name, unit, origin;
        double price;
        int pos;
        List<String> tmp = new ArrayList<>();
        // Input validation
        do {
            id = Tools.getStringFormat("Enter the id[Pxxx]: ", "The format of the id is Pxxx(X stands for a digit)",
                    "^P\\d{3}$");
            pos = searchAProductById(id);
            if (pos >= 0)
                System.out.println("This id has already existed. Please enter the another id!");
        } while (pos != -1);
        name = Tools.getString("Enter the name: ", "This field is required!");
        unit = Tools.getString("Enter the name: ", "This field is required!");
        origin = Tools.getString("Enter the origin: ", "This field is required!");
        do {
            price = Tools.getADouble("Enter the price: ", "The price MUST BE A POSITIVE NUMBER!");
        } while (price <= 0);
        productList.add(new Product(id, name, unit, origin, price));
        System.out.println("A new product is added successfully!");

        for (Product x : productList) {
            tmp.add(x.getId() + "," + x.getName() + "," + x.getUnit() + "," + x.getOrigin() + "," + x.getPrice());
        }
        Tools.writeListToFile(PRODUCTS_FILE, tmp);
    }

    @Override
    public void searchAProductById() {
        // Declaration
        String id;
        Product x;
        int pos;

        id = Tools.getStringFormat("Enter the id[Pxxx]: ", "The format of the id is Pxxx(X stands for a digit)",
                "^P\\d{3}$");
        pos = searchAProductById(id);
        x = searchAProductObjectById(id);

        if (pos == -1)
            System.out.println("The product is not found!");
        else {
            System.out.println("Here is a product that you have searched");
            x.showProfile();
        }
    }

    @Override
    public void removeAProduct() {
        // Declaration
        String id;
        Product t;
        int pos;
        List<String> tmp = new ArrayList<>();

        id = Tools.getStringFormat("Enter the id[Pxxx]: ", "The format of the id is Pxxx(X stands for a digit)",
                "^P\\d{3}$");
        pos = searchAProductById(id);
        t = searchAProductObjectById(id);

        if (pos == -1)
            System.out.println("The product is not found!");
        else {
            System.out.println("Here is the product before removed");
            t.showProfile();
            System.out.print("Do you want to remove this product[Y/N]: ");
            String response = sc.nextLine().toUpperCase();
            if (response.startsWith("Y")) {
                products.remove(pos);
                System.out.println("The product is removed successfully!");
            } else
                System.out.println("The product is not removed!");
        }
        saveProductsFromFile();
    }

    @Override
    public void removeACustomer() {
        String id;
        Customer t;
        int pos;

        id = Tools.getStringFormat("Enter the id[Cxxx]: ", "The format of the id is Cxxx(X stands for a digit)",
                "^C\\d{3}$");
        pos = searchACustomerById(id);
        t = searchACustomerObjectById(id);

        if (pos == -1)
            System.out.println("The product is not found!");
        else {
            System.out.println("Here is the customer before removed");
            t.showProfile();
            System.out.print("Do you want to remove this customer[Y/N]: ");
            String response = sc.nextLine().toUpperCase();
            if (response.startsWith("Y")) {
                customers.remove(pos);
                System.out.println("The customer is removed successfully!");
            } else
                System.out.println("The customer is not removed!");
        }
    }
    // Find the position of a product by product's id
    // -1: not found
    private int searchAProductById(String productId) {
        if (products.isEmpty())
            return -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(productId))
                return i;
        }
        return -1;
    }
    
    private Product searchAProductObjectById(String productId) {
        if (products.isEmpty())
            return null;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(productId))
                return products.get(i);
        }
        return null;
    }

    private int searchACustomerById(String customerId) {
        if (customers.isEmpty())
            return -1;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equalsIgnoreCase(customerId))
                return i;
        }
        return -1;
    }

    private Customer searchACustomerObjectById(String customerId) {
        if (customers.isEmpty())
            return null;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equalsIgnoreCase(customerId))
                return customers.get(i);
        }
        return null;
    }

    private List<Product> getProductsFromFile() {
        String fileName = "Products.txt";
        List<Product> productList = new ArrayList<>();
        List<String> tmp = Tools.readLineFromFile(fileName);

        for (String x : tmp) {
            StringTokenizer stk = new StringTokenizer(x, ",");
            String id = stk.nextToken();
            String name = stk.nextToken();
            String unit = stk.nextToken();
            String origin = stk.nextToken();
            double price = Double.parseDouble(stk.nextToken());

            productList.add(new Product(id, name, unit, origin, price));
        }
        return productList;
    }

    private List<Customer> getCustomersFromFile() {
        String fileName = "Customers.txt";
        List<String> tmp = Tools.readLineFromFile(fileName);
        List<Customer> customerList = new ArrayList<>();

        for (String x : tmp) {
            StringTokenizer stk = new StringTokenizer(x, ",");
            String id = stk.nextToken();
            String name = stk.nextToken();
            String address = stk.nextToken();
            String phoneNumber = stk.nextToken();

            customerList.add(new Customer(id, name, address, phoneNumber));
        }
        return customerList;
    }

    private void saveProductsFromFile() {
        List<String> tmp = new ArrayList<>();
        for (Product x : products) {
            tmp.add(x.getId() + "," + x.getName() + "," + x.getUnit() + "," + x.getOrigin() + "," + x.getPrice());
        }
        Tools.writeListToFile(PRODUCTS_FILE, tmp);
    }

    private void saveCustomersFromFile() {
        List<String> tmp = new ArrayList<>();
        for (Customer x : customers) {
            tmp.add(x.getId() + "," + x.getName() + "," + x.getAddress() + "," + x.getPhoneNumber());
        }
        Tools.writeListToFile(CUSTOMERS_FILE, tmp);
    }
}
