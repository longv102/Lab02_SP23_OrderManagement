package data_cabinet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import data.Product;
import interfaces.ProductDao;
import util.Tools;

public class ProductDaoImpl implements ProductDao {

    private List<Product> products = new ArrayList<>();
    private String fileName = "Products.txt";
    private Scanner sc = new Scanner(System.in);

    public ProductDaoImpl() {
        products = getProductsFromFile(fileName);
    }

    @Override
    public void addAProduct() {
        //Declaration
        String id, name, unit, origin;
        double price;
        int pos;
        List<String> tmp = new ArrayList<>();

        do {
            id = Tools.getStringFormat("Enter the id[Pxxx]: ",
                    "The format of the id is Pxxx (x stands for a digit)", "^C\\d{3}$");
            pos = searchABookByID(id);
            if (pos >= 0) {
                System.out.println("The id has already existed! Please enter the another id!");
            }
        } while (pos != -1);

        name = Tools.getString("Enter the name: ", "This field is required!");
        unit = Tools.getString("Enter the unit: ", "This field is required!");
        origin = Tools.getString("Enter the origin: ", "This field is required!");
        price = Tools.getADouble("Enter the price: ", "This field is required!");

        products.add(new Product(id, name, unit, origin, price));
        System.out.println("A new product is added successfully!");
        //Save to file
        for (Product pro : products) {
            tmp.add(pro.getId() + "," + pro.getName() + "," + pro.getUnit() + "," + pro.getOrigin() + ","
                    + pro.getPrice());
        }
        Tools.writeListToFile(fileName, tmp);
    }

    @Override
    public void searchAProduct() {
        String id;
        Product x;
        int pos;

        id = Tools.getStringFormat("Enter the id[Pxxx]: ",
                "The format of the id is Pxxx (x stands for a digit)", "^C\\d{3}$");
        pos = searchABookByID(id);
        x = searchABookObjectByID(id);

        if (pos == -1)
            System.out.println("The product is not found!");
        else {
            System.out.println("This is the product that you have searched");
            x.showProfile();
        }
    }

    @Override
    public void removeAProduct() {
        String id;
        Product x;
        int pos;
        List<String> tmp = new ArrayList<>();

        id = Tools.getStringFormat("Enter the id[Pxxx]: ",
                "The format of the id is Pxxx (x stands for a digit)", "^C\\d{3}$");
        x = searchABookObjectByID(id);
        pos = searchABookByID(id);

        if (pos == -1)
            System.out.println("The product does not exist!");
        else {
            System.out.println("Here is the product before removed");
            x.showProfile();

            System.out.println("Do you want to remove[Y/N]: ");
            String response = sc.nextLine().toUpperCase();
            if (response.startsWith("Y")) {
                products.remove(pos);
                System.out.println("The product is removed successfully!");
            } else
                System.out.println("The product is not removed!");
        }
        //Save to file
        for (Product pro : products) {
            tmp.add(pro.getId() + "," + pro.getName() + "," + pro.getUnit() + "," + pro.getOrigin() + ","
                    + pro.getPrice());
        }
    }

    @Override
    public void displayProducts() {
        for (Product x : products) {
            x.showProfile();
        }
    }

    private int searchABookByID(String id) {
        if (products.isEmpty())
            return -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(id))
                return i;
        }
        return -1;
    }

    private Product searchABookObjectByID(String id) {
        if (products.isEmpty())
            return null;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(id))
                return products.get(i);
        }
        return null;
    }

    private List<Product> getProductsFromFile(String fName) {
        List<Product> productList = new ArrayList<>();
        List<String> tmp = Tools.readLineFromFile(fName);

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

}
