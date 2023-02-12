package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import crud.ProductDao;
import dto.Product;
import util.Tools;

public class ProductDaoImpl implements ProductDao {

    private List<Product> products = new ArrayList<>();
    private String fileName = "Products.txt";
    private final Scanner sc = new Scanner(System.in);
    //load the data from the Products.txt
    public ProductDaoImpl() {
        products = getProductsFromFile(fileName);
    }

    private void addAProduct() {
        //Declaration
        String id, name, unit, origin;
        double price;
        int pos;
        List<String> tmp = new ArrayList<>();
        //Input validation
        do {
            id = Tools.getStringFormat("Enter the id[Pxxx]: ",
                    "The format of the id is Pxxx (x stands for a digit)", "^C\\d{3}$");
            pos = searchAProductByID(id);
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
    public void displayProducts() {
        if (products.isEmpty())
            System.out.println("The list is empty");
        for (Product x : products) {
            x.showProfile();
        }
    }
    //Return the position of a product in the list
    private int searchAProductByID(String id) {
        if (products.isEmpty())
            return -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equalsIgnoreCase(id))
                return i;
        }
        return -1;
    }
    //Return the information of a product in the list
    private Product searchAProductObjectByID(String id) {
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
