package data_cabinet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import data.Customer;
import interfaces.CustomerDao;
import util.Tools;

public class CustomerDaoImpl implements CustomerDao {

    private List<Customer> customers = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private String fileName = "Customers.txt";

    private int searchACustomer(String id) {
        if (customers.isEmpty())
            return -1;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equalsIgnoreCase(id))
                return i;
        }
        return -1;
    }

    private Customer searchACustomerObject(String id) {
        if (customers.isEmpty())
            return null;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equalsIgnoreCase(id))
                return customers.get(i);
        }
        return null;
    }

    private List<Customer> getCustomersFromFile(String fName) {
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

    public CustomerDaoImpl() {
        customers = getCustomersFromFile(fileName);
    }

    @Override
    public void addACustomer() {
        //Delcaration
        String id, name;
        String address, phoneNumber, response;
        int pos;
        List<String> tmp = new ArrayList<>();

        do {
            do {
                id = Tools.getStringFormat("Enter the id[Cxxx]: ",
                        "The format of the id is Cxxx (X stands for a digit)", "^C\\d{3}$");
                pos = searchACustomer(id);
                if (pos >= 0)
                    System.out.println("The id has already existed! Please enter the another id!");
            } while (pos != -1);

            name = Tools.getString("Enter the name: ", "This field is required!").toUpperCase();
            address = Tools.getString("Enter the address: ", "This field is required!").toUpperCase();
            phoneNumber = Tools.getStringFormat("Enter the phone number: ",
                    "The length of phone number is in 10-12 digits", "\\d{10,12}$");

            customers.add(new Customer(id, name, address, phoneNumber));
            System.out.println("A new customer is added successfully!");
            System.out.print("Continue to create a new customer[Y/N]: ");
            response = sc.nextLine().toUpperCase();
        } while (response.startsWith("Y"));
        // Save to file
        for (Customer x : customers) {
            tmp.add(x.getId() + "," + x.getName() + "," + x.getAddress() + "," + x.getPhoneNumber());
        }
        Tools.writeListToFile(fileName, tmp);
    }

    @Override
    public void searchACustomerByID() {
        String id;
        int pos;
        Customer x;

        id = Tools.getStringFormat("Enter the customer's id[Cxxx]: ",
                "The format of the id is Cxxx (X stands for a digit)", "^C\\d{3}$");
        pos = searchACustomer(id);
        x = searchACustomerObject(id);

        if (pos == -1)
            System.out.println("The customer is not found!");
        else {
            System.out.println("Here is the customer that you have searched");
            x.showProfile();
        }
    }

    @Override
    public void updateACustomer() {
        String id;
        Customer x;
        int pos;
        List<String> tmp = new ArrayList<>();

        id = Tools.getStringFormat("Enter the customer's id[Cxxx]: ",
                "The format of the id is Cxxx (X stands for a digit)", "^C\\d{3}$");
        pos = searchACustomer(id);
        x = searchACustomerObject(id);

        if (pos == -1)
            System.out.println("The customer does not exist!");
        else {
            System.out.println("Here is the customer before updated");
            x.showProfile();
            System.out.print("Continue to update[Y/N]: ");
            String response = sc.nextLine().toUpperCase();
            if (response.isEmpty()) {
                System.out.println("The customer's information is not updated!");
                x.showProfile();
            } else if (response.startsWith("Y")) {
                String newName, newAddress, newPhoneNumber;

                newName = Tools.getString("Enter the name: ", "This field is required!").toUpperCase();
                newAddress = Tools.getString("Enter the address: ", "This field is required!").toUpperCase();
                newPhoneNumber = Tools.getString("Enter the phone number: ", "This field is required!");
                //update the customer's information
                x.setName(newName);
                x.setAddress(newAddress);
                x.setPhoneNumber(newPhoneNumber);

                System.out.println("The customer's information is updated successfully!");
            } else
                System.out.println("The customer's information is not updated!");
        }
        //Save to file
        for (Customer cus : customers) {
            tmp.add(cus.getId() + "," + cus.getName() + "," + cus.getAddress() + "," + cus.getPhoneNumber());
        }
        Tools.writeListToFile(fileName, tmp);

    }

    @Override
    public void displayCustomers() {
        if (customers.isEmpty())
            System.out.println("The list is empty!");

        for (Customer x : customers) {
            x.showProfile();
        }
    }

    @Override
    public void removeACustomer() {
        String id;
        int pos;
        Customer x;
        List<String> tmp = new ArrayList<>();

        id = Tools.getStringFormat("Enter the customer's id[Cxxx]: ",
                "The format of the id is Cxxx (X stands for a digit)", "^C\\d{3}$");
        pos = searchACustomer(id);
        x = searchACustomerObject(id);

        if (pos == -1)
            System.out.println("The customer does not exist!");
        else {
            System.out.println("Here is the customer before removed");
            x.showProfile();
            System.out.print("Continue to remove[Y/N]: ");
            String response = sc.nextLine().toUpperCase();
            if (response.startsWith("Y")) {
                customers.remove(pos);
                System.out.println("The customer is removed successfully!");
            } else
                System.out.println("The customer is not removed!");
        }
        //Save to file
        for (Customer cus : customers) {
            tmp.add(cus.getId() + "," + cus.getName() + "," + cus.getAddress() + "," + cus.getPhoneNumber());
        }
        Tools.writeListToFile(fileName, tmp);
    }

    // public static void main(String[] args) {
    //     CustomerDaoImpl tc = new CustomerDaoImpl();
    //     tc.displayCustomers();
    //     tc.addACustomer();
    //     tc.removeACustomer();
    //     tc.searchACustomerByID();
    //     tc.removeACustomer();
    // }

}
