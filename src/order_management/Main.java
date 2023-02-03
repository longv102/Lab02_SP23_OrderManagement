package order_management;

import data_cabinet.CustomerDaoImpl;
import data_cabinet.OrderDaoImpl;
import data_cabinet.ProductDaoImpl;
import interfaces.CustomerDao;
import interfaces.OrderDao;
import interfaces.ProductDao;
import ui.Menu;

public class Main {
    public static void main(String[] args) {
        //Delcaration
        CustomerDao customer = new CustomerDaoImpl();
        OrderDao order = new OrderDaoImpl();
        ProductDao product = new ProductDaoImpl();
        int choice;

        Menu menu = new Menu();
        menu.addNewOption("|1. List all Products                                                                                            |");
        menu.addNewOption("|2. List all Customers                                                                                           |");
        menu.addNewOption("|3. Search a customer based on his/her ID                                                                        |");
        menu.addNewOption("|4. Add a customer                                                                                               |");
        menu.addNewOption("|5. Update a customer                                                                                            |");
        menu.addNewOption("|6. List all orders in ascending order of customer name                                                          |");
        menu.addNewOption("|7. List all pending orders                                                                                      |");
        menu.addNewOption("|8. Add an order                                                                                                 |");
        menu.addNewOption("|9. Update an order                                                                                              |");
        menu.addNewOption("|10. Quit                                                                                                        |");

        do {
            menu.printMenu();
            choice = menu.getUserChoice();

            switch (choice) {
                case 1:
                    System.out.println("Here is the list of products");
                    System.out.println();
                    product.displayProducts();
                    break;
                case 2:
                    System.out.println("Here is the list of customers");
                    System.out.println();
                    customer.displayCustomers();
                    break;
                case 3:
                    System.out.println("You are preparing to search a customer");
                    customer.searchACustomerByID();
                    break;
                case 4:
                    System.out.println("You are preparing to add a new customer");
                    customer.addACustomer();
                    break;
                case 5:
                    System.out.println("You are preparing to update a customer");
                    customer.updateACustomer();
                    break;
                case 6:
                    System.out.println("Here is the list of order(s) after sorted");
                    order.displayOrdersAscendingByCusName();
                    break;
                case 7:
                    System.out.println("Here is the list of pending order(s)");
                    order.displayPendingOrders();
                    break;
                case 8:
                    System.out.println("You are preparing to add a new order");
                    order.addAOrder();
                    break;
                case 9:
                    System.out.println("You are preparing to update an order");
                    order.updateOrder();
                    break;
                case 10:
                    System.out.println("Bye bye, see you next time!");
                    break;
            }
        } while (choice != 10);
    }
}
