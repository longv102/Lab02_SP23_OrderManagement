package data_cabinet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import data.Customer;
import data.Order;
import data.Product;
import interfaces.OrderDao;
import util.Tools;
import ui.Menu;

public class OrderDaoImpl implements OrderDao {
    // Declaration
    private List<Order> orders = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);
    private String fileName = "Orders.txt";
    private List<Customer> customers = getCustomersFromFile();
    private List<Product> products = getProductsFromFile();
    //Return the position of an order in the list
    private int searchAnOrderById(String orderId) {
        if (orders.isEmpty())
            return -1;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equalsIgnoreCase(orderId))
                return i;
        }
        return -1;
    }
    //Return the information of an order in the list
    private Order searchAnOrderObjectById(String orderId) {
        if (orders.isEmpty())
            return null;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equalsIgnoreCase(orderId))
                return orders.get(i);
        }
        return null;
    }

    private List<Customer> getCustomersFromFile() {
        String fName = "Customers.txt";
        List<String> tmp = Tools.readLineFromFile(fName);
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

    private List<Product> getProductsFromFile() {
        String fName = "Products.txt";
        List<String> tmp = Tools.readLineFromFile(fName);
        List<Product> productList = new ArrayList<>();

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

    private List<Order> getOrdersFromFile(String fName) {
        List<Order> orderList = new ArrayList<>();
        List<String> tmp = Tools.readLineFromFile(fName);

        for (String x : tmp) {
            StringTokenizer stk = new StringTokenizer(x, ",");
            String id = stk.nextToken();
            String customerId = stk.nextToken();
            String productId = stk.nextToken();
            int quantity = Integer.parseInt(stk.nextToken());
            String date = stk.nextToken();
            String status = stk.nextToken();

            orderList.add(new Order(id, customerId, productId, quantity, date, status));
        }
        return orderList;
    }

    public OrderDaoImpl() {
        orders = getOrdersFromFile(fileName);
    }

    @Override
    public void addAOrder() {
        //Declaration
        String orderId, productId, date, status, display;
        String customerId;
        String response;
        int quantity, pos;
        int choice01, choice02;
        List<String> tmp = new ArrayList<>();
        
        do {
            do {
                orderId = Tools.getStringFormat("Enter the id[Dxxx]: ",
                        "The format of id is Dxxx (X stands for a digit)", "^D\\d{3}$");
                pos = searchAnOrderById(orderId);
                if (pos >= 0)
                    System.out.println("This id has already existed! Please enter the another id");
            } while (pos != -1);
            System.out.println("Here is the list of customers");
            //submenu for choosing a customer's id
            Menu subMenu01 = new Menu();
            for (int i = 0; i < customers.size(); i++) {
                subMenu01.addNewOption(i + 1 + ". " + customers.get(i).toString());
            }
            //display the submenu and get user input to have a customer's id
            subMenu01.printMenu();
            System.out.println("Enter a number to choose a customer's id");
            choice01 = subMenu01.getUserChoice();
            customerId = customers.get(choice01 - 1).getId();
            //submenu for choosing a product's id
            System.out.println("Here is the list of products");
            Menu subMenu02 = new Menu();
            for (int i = 0; i < products.size(); i++) {
                subMenu02.addNewOption(i + 1 + ". " + products.get(i).toString());
            }
            //display the submenu and get user input to have a product's id
            subMenu02.printMenu();
            System.out.println("Enter a number to choose a product's id");
            choice02 = subMenu02.getUserChoice();
            productId = products.get(choice02 - 1).getId();
            date = Tools.getStringFormat("Enter the date: ", 
                    "The format of the date is m/d/yy AND mm/dd/yyyy",
                    "^(1[0-2]|0?[1-9])/(3[01]|[12][0-9]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$");
            
            do {
                quantity = Tools.getAnInteger("Enter the quantity: ", "The quantity must be a positive integer!");
            } while (quantity <= 0);

            do {
                status = Tools.getString("Enter the status[T/F]: ", "This field is required!");
                if (status.matches("T"))
                    display = "true";
                else
                    display = "false";
            } while (!(status.matches("^(T|F)$")));
            // Add a new order
            orders.add(new Order(orderId, customerId, productId, quantity, date, display));
            System.out.println("A new order is added sucessfully!");
            System.out.print("Continue to create a new order[Y/N]: ");
            response = sc.nextLine().toUpperCase();
        } while (response.startsWith("Y"));
        // Save to file
        for (Order x : orders) {
            tmp.add(x.getId() + "," + x.getCustomerId() + "," + x.getProductId() + "," + x.getQuantity() + ","
                    + x.getDate() + "," + x.getStatus());
        }
        Tools.writeListToFile(fileName, tmp);
    }

    @Override
    public void displayOrdersAscendingByCusName() {
        if (orders.isEmpty())
            System.out.println("The list is empty!");

        List<Customer> customerList = getCustomersFromFile();
        List<Order> sortedOrders = new ArrayList<>();
        // sort the customerList based on their names
        Collections.sort(customerList);
        // sort the orders in ascending orders
        for (int i = 0; i < customerList.size(); i++) {
            for (int j = 0; j < orders.size(); j++) {
                if (orders.get(j).getCustomerId().equalsIgnoreCase(customerList.get(i).getId()))
                    sortedOrders.add(orders.get(j));
            }
        }
        for (Order x : sortedOrders) {
            x.showProfile();
        }
    }

    @Override
    public void displayPendingOrders() {
        if (orders.isEmpty())
            System.out.println("The list is empty!");

        List<Order> tmp = getOrdersFromFile(fileName);
        for (Order x : tmp) {
            if (x.getStatus().equalsIgnoreCase("false"))
                x.showProfile();
        }
    }

    @Override
    public void updateOrder() {
        String orderId;
        Order x;
        int pos, choice;
        List<String> tmp = new ArrayList<>();

        orderId = Tools.getStringFormat("Enter the order's id[Dxxx]: ",
                "The format of id is Dxxx (X stands for a digit)", "^D\\d{3}$");
        pos = searchAnOrderById(orderId);
        x = searchAnOrderObjectById(orderId);

        if (pos == -1)
            System.out.println("The order does not exist!");
        else {
            System.out.println("Here is the order's information before updated");
            x.showProfile();
            //submenu for update the order
            Menu subMenu = new Menu();
            subMenu.addNewOption("  1. Update an order's information");
            subMenu.addNewOption("  2. Delete an order");
            subMenu.addNewOption("  3. Quit");

            do {
                subMenu.printMenu();
                choice = subMenu.getUserChoice();
                switch (choice) {
                    case 1:
                        System.out.println("Here is the order's information before updated");
                        x.showProfile();

                        System.out.print("Continue to update the order's information[Y/N]: ");
                        String response01 = sc.nextLine().toUpperCase();

                        if (response01.isEmpty()) {
                            System.out.println("The order's information is not updated!");
                            x.showProfile();
                        } else if (response01.startsWith("Y")) {
                            String display;
                            String newStatus;
                            do {
                                newStatus = Tools.getString("Enter the status[T/F]: ", "This field is required!");
                                if (newStatus.matches("T"))
                                    display = "true";
                                else
                                    display = "false";
                            } while (!(newStatus.matches("^(T|F)$")));
                            // update the order's status
                            x.setStatus(display);
                            System.out.println("The order's information is updated successfully!");
                        } else
                            System.out.println("The order's information is not updated!");
                        break;
                    case 2:
                        System.out.println("Here is the order's information before removed");
                        x.showProfile();

                        System.out.print("Continue to remove the order[Y/N]: ");
                        String response02 = sc.nextLine().toUpperCase();

                        if (response02.startsWith("Y")) {
                            orders.remove(pos);
                            System.out.println("The order is removed successfully!");
                        } else
                            System.out.println("The order is not removed!");
                        break;
                    case 3:
                        break;
                }
            } while (choice != 3);
            // Save to file
            for (Order ord : orders) {
                tmp.add(ord.getId() + "," + ord.getCustomerId() + "," + ord.getProductId() + "," + ord.getQuantity()
                        + "," + ord.getDate() + "," + ord.getStatus());
            }
            Tools.writeListToFile(fileName, tmp);
        }
    }

    // public static void main(String[] args) {
    //     OrderDaoImpl tc = new OrderDaoImpl();   
    //     tc.addAOrder();
    //     tc.displayOrdersAscendingByCusName();
    //     tc.displayPendingOrders();
    //     tc.updateOrder();
    // }
}
