package order_management;

import java.util.Scanner;

import crud.AdminDao;
import crud.CustomerDao;
import crud.OrderDao;
import crud.ProductDao;
import dao.AdminDaoImpl;
import dao.CustomerDaoImpl;
import dao.OrderDaoImpl;
import dao.ProductDaoImpl;
import dto.Admin;
import ui.Menu;

public class Main {
    public static void main(String[] args) {
        // Declaration
        Scanner sc = new Scanner(System.in);
        CustomerDao customer = new CustomerDaoImpl();
        OrderDao order = new OrderDaoImpl();
        ProductDao product = new ProductDaoImpl();
        AdminDao admin = new AdminDaoImpl();
        int choice = 0;
        Menu menu = new Menu();
        menu.addNewOption("1. Administration");
        menu.addNewOption("2. Customer");
        menu.addNewOption("3. Quit");

        do {
            System.out.println("========================================WELCOME TO THE ORDER MANAGEMENT APP========================================");
            Admin x = new Admin();
            menu.printMenu();
            choice = menu.getUserChoice();
            switch (choice) {
                case 1:
                    System.out.print("\033[H\033[2J");
                    System.out.print("Enter the username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter the password: ");
                    String password = sc.nextLine();
                    if (username.matches(x.getId()) && password.matches(x.getPassword())) {
                        int choice01 = 0;
                        Menu subMenu01 = new Menu();
                        subMenu01.addNewOption("1. Add a product");
                        subMenu01.addNewOption("2. Search a product by product's id");
                        subMenu01.addNewOption("3. Remove a product by product's id");
                        subMenu01.addNewOption("4. Remove a customer by customer's id");
                        subMenu01.addNewOption("5. Return to the main menu");
                        do {
                            System.out.println("===================================================ADMINISTRATION==================================================");
                            subMenu01.printMenu();
                            choice01 = subMenu01.getUserChoice();
                            System.out.print("\033[H\033[2J");
                            switch (choice01) {
                                case 1:
                                    System.out.println("You are preparing to add a new product");
                                    admin.addAProduct();
                                    System.out.println();
                                    break;
                                case 2:
                                    System.out.println("You are preparing to search a product");
                                    admin.searchAProductById();
                                    System.out.println();
                                    break;
                                case 3:
                                    System.out.println("You are preparing to remove a product");
                                    admin.removeAProduct();
                                    System.out.println();
                                    break;
                                case 4:
                                    System.out.println("You are preparing to remove a customer");
                                    admin.removeACustomer();
                                    System.out.println();
                                    break;
                                case 5:
                            }
                        } while (choice01 != 5);
                    } else 
                        System.out.println("The username or password is incorrect!");
                    break;
                case 2:
                    System.out.print("\033[H\033[2J");
                    Menu subMenu02 = new Menu();
                    subMenu02.addNewOption("1. List all products");
                    subMenu02.addNewOption("2. List all customers");
                    subMenu02.addNewOption("3. Search a customer based on customer's id");
                    subMenu02.addNewOption("4. Add a customer");
                    subMenu02.addNewOption("5. Update a customer");
                    subMenu02.addNewOption("6. List all orders in ascending order of customer name");
                    subMenu02.addNewOption("7. List all pending orders");
                    subMenu02.addNewOption("8. Add an order");
                    subMenu02.addNewOption("9. Update an order");
                    subMenu02.addNewOption("10. Quit");
                    int choice02 = 0;
                    do {
                        System.out.println("======================================================CUSTOMER=====================================================");
                        subMenu02.printMenu();
                        choice02 = subMenu02.getUserChoice();
                        System.out.print("\033[H\033[2J");
                        switch (choice02) {
                            case 1:
                                System.out.println("Here is the list of product(s)");
                                product.displayProducts();
                                System.out.println();
                                break;
                            case 2:
                                System.out.println("Here is the list of customer(s)");
                                customer.displayCustomers();
                                System.out.println();
                                break;
                            case 3:
                                System.out.println("You are preparing to search a customer");
                                customer.searchACustomerByID();
                                System.out.println();
                                break;
                            case 4:
                                System.out.println("You are preparing to add a new customer");
                                customer.addACustomer();
                                System.out.println();
                                break;
                            case 5:
                                System.out.println("You are preparing to update a customer");
                                customer.updateACustomer();
                                System.out.println();
                                break;
                            case 6:
                                System.out.println("Here is the list of orders after sorted");
                                order.displayOrdersAscendingByCusName();
                                System.out.println();
                                break;
                            case 7:
                                System.out.println("Here is the list of pending orders");
                                order.displayPendingOrders();
                                System.out.println();
                                break;
                            case 8:
                                System.out.println("You are preparing to add a new order");
                                order.addAOrder();
                                System.out.println();
                                break;
                            case 9:
                                System.out.println("You are preparing to update an order");
                                order.updateOrder();
                                System.out.println();
                                break;
                            case 10:
                        }
                    } while (choice02 != 10);
                    break;
                case 3:
                    System.out.println("Bye bye, see you next time!");
            }
        } while (choice != 3);
    }
}
