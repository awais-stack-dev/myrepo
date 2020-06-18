import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.jar.JarOutputStream;

public class Seller extends Person implements Serializable {
    private String[] categories = new String[7];
    private ArrayList<Product> products = new ArrayList<Product>();  //Adding Product instances in ArrayList.. Using concept of Collection and Composition
    private String password;

    public Seller(String name, String phoneNumber, String password) {
        super(name, phoneNumber);
        this.password = password;
        loadCategories();
    }
    public Seller(String name, String phoneNumber, String password,ArrayList<Product> products) {
        super(name, phoneNumber);
        this.password = password;
        this.products=products;
        loadCategories();
    }
    public ArrayList<Product> getProductsList() {
        return products;
    }

    private void loadCategories() {
        categories[0] = "Foods";
        categories[1] = "Garments";
        categories[2] = "Grocery";
        categories[3] = "Toys";
        categories[4] = "Electronics";
        categories[5] = "Vehicles";
        categories[6] = "Furniture";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    public void updateProductList(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        super.setPhoneNumber(phoneNumber);
    }
    public void modifyProduct(){
        String productList="Select Product\n Category   Name   Quantity   Price\n";
        for (int i=0; i<products.size() ;i++){
            Product product=products.get(i);
            String temp=(i+1)+"."+product.getCategory()+"   "+product.getTitle()+"   "+product.getQuantity()+"   "+product.getPrice()+"\n";
            productList+=temp;
        }
        boolean productModified=false;
        int option=Integer.parseInt(JOptionPane.showInputDialog(productList));
        if (option>0 && option<=products.size()) {
            int modificationType = Integer.parseInt(JOptionPane.showInputDialog("Select\n1.Change Title\n2.Update Quantity\n3.Change Price\n4.Change Category"));
            switch (modificationType) {
                case 1:
                    String updatedTitle = JOptionPane.showInputDialog("Enter New Title:");
                    products.get(option - 1).setTitle(updatedTitle);
                    productModified = true;
                    break;
                case 2:
                    String updatedQuantity = JOptionPane.showInputDialog("Enter New Quantity:");
                    products.get(option - 1).setQuantity(Integer.parseInt(updatedQuantity));
                    productModified = true;
                    break;
                case 3:
                    String updatedPrice = JOptionPane.showInputDialog("Enter New Price:");
                    products.get(option - 1).setPrice(Integer.parseInt(updatedPrice));
                    productModified = true;
                    break;
                case 4:
                    int updatedCategory = showCategories();
                    products.get(option - 1).setCategory(categories[updatedCategory]);
                    productModified = true;
                    break;
            }
            if (productModified) {
                JOptionPane.showMessageDialog(null, "Product Information Updated");
            }
        }
    }
    public void deleteProduct() {
        String productList = "Select Product\n Category   Name   Quantity   Price\n";
        if (!products.isEmpty()) {
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                String temp = (i + 1) + "." + product.getCategory() + "   " + product.getTitle() + "   " + product.getQuantity() + "   " + product.getPrice() + "\n";
                productList += temp;
            }
            int productIndex = Integer.parseInt(JOptionPane.showInputDialog(productList));
            if (productIndex > 0 && productIndex <= products.size()) {
                products.remove(productIndex - 1);
                JOptionPane.showMessageDialog(null, "Item Removed from List");
            } else {
                JOptionPane.showMessageDialog(null, "Error in deleting product");
            }
        }
        else {
            JOptionPane.showMessageDialog(null,"Products List is Empty");
        }
    }

    public void addProduct() {
        int category = showCategories();
        if (category>=0) {
            String title = JOptionPane.showInputDialog("Enter Product Title:");
            int price = Integer.parseInt(JOptionPane.showInputDialog("Enter Product Price:"));
            int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter Available Quantity:"));
            Product product = new Product(title, price, quantity, categories[category]);
            products.add(product);
        }
        else {
            JOptionPane.showMessageDialog(null,"Category not found");
        }
    }

    private int showCategories() {
        String list = "Select Category\n";
        for (int i = 0; i < categories.length; i++) {
            String temp = (i + 1) + "." + categories[i]+"\n";
            list += temp;
        }
        int option = Integer.parseInt(JOptionPane.showInputDialog(list));
        if (option > 0) {
            return option-1;
        } else {
            return -1;
        }
    }

    public void showProductList() {
        if (!products.isEmpty()) {
            String productList = "Title   Quantity   Price\n";
            String temp;
            for (int i = 0; i < products.size(); i++) {
                temp = "";
                temp = products.get(i).getTitle() + "  " + products.get(i).getQuantity() + "   Rs." + products.get(i).getPrice() + "\n";
                productList += temp;
            }

            JOptionPane.showMessageDialog(null, productList);
        } else {
            JOptionPane.showMessageDialog(null, "Products list is empty");
        }
    }
}
