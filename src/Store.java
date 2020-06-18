import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.jar.JarOutputStream;

public class Store implements Serializable , ActionListener {
    private JFrame frame=new JFrame("E-Commerce Store");
    private JButton buyerButton=new JButton("BUYER");
    private JButton sellersButton=new JButton("SELLER");
    private JButton saveAndExit=new JButton("SAVE AND EXIT");


    private String[] categories = new String[7];

    private ArrayList<Person> sellers = new ArrayList<>();  // I'll add Seller Objects in this ArrayList using Polymorphism

    public Store() {
//        Seller seller=new Seller("Muhammad Awais","01388727319","123");
//        accountLogin(seller);
        loadFrame();
        loadData();
        loadCategories();
    }

    private void loadFrame(){

        JLabel welcomeText=new JLabel("WELCOME");
        JLabel selectCat=new JLabel("SELECT YOUR CATEGORY");

        welcomeText.setBounds(220,17,150,20);
        selectCat.setBounds(180,50,150,20);

        buyerButton.setBounds(100,90,100,20);
        buyerButton.addActionListener(this::actionPerformed);

        sellersButton.setBounds(300,90,100,20);
        sellersButton.addActionListener(this::actionPerformed);


        saveAndExit.setBounds(170,170,150,20);
        saveAndExit.addActionListener(this::actionPerformed);

        Container container=frame.getContentPane();
        frame.setLocation(600,400);
        frame.setResizable(false);
        container.add(selectCat);
        container.add(welcomeText);
        container.add(buyerButton);
        container.add(sellersButton);
        container.add(saveAndExit);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,250);

        frame.setLayout(null);
        frame.setVisible(true);


    }
    public void sellersList(){
        String record="Name   Phone\n";
        for (int i=0; i<sellers.size() ;i++ ){
            String temp=sellers.get(i).getName()+"  "+sellers.get(i).getPhoneNumber()+"\n";
            record+=temp;
        }
        if (sellers.isEmpty()){
            JOptionPane.showMessageDialog(null,"No seller record available");
        }
        else {
            JOptionPane.showMessageDialog(null,record);
        }
    }
    private void loadData() {
        try {
             FileInputStream file=new FileInputStream(new File("SellerRecord.txt"));
             ObjectInputStream reader=new ObjectInputStream(file);

             while (true){

                 try {
                     Seller seller=(Seller) reader.readObject();
                     sellers.add(seller);
                 }catch (EOFException e){
                     break;
                 }
             }
             file.close();
             reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            FileOutputStream file = new FileOutputStream(new File("SellerRecord.txt"));
            ObjectOutputStream writer = new ObjectOutputStream(file);
            for (int i = 0; i < sellers.size(); i++) {
                Seller seller = (Seller) sellers.get(i);
                writer.writeObject(seller);
            }
            file.close();
            writer.close();
            JOptionPane.showMessageDialog(null, "Records Saved");
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Error in saving your records");
        } catch (IOException o) {
            JOptionPane.showMessageDialog(null,"Error in saving your records");
        }
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


    private int showCategories() {
        String list = "Select Category\n";
        for (int i = 0; i < categories.length; i++) {
            String temp = (i + 1) + "." + categories[i] + "\n";
            list += temp;
        }
        int option = Integer.parseInt(JOptionPane.showInputDialog(list));
        if (option > 0) {
            return option - 1;
        } else {
            return -1;
        }
    }

    public void buyer() {
        boolean productAvailable = false;
        int option = showCategories();
        showAvailableProducts(option);
    }

    private void showAvailableProducts(int option) {
        String productList = "Title   Quantity   Price   Seller-Name   Seller-Contact#\n";
        boolean productAvailable = false;
        for (int i = 0; i < sellers.size(); i++) {
            Seller seller = (Seller) sellers.get(i);
            for (int j = 0; j < seller.getProductsList().size(); j++) {
                ArrayList<Product> products = seller.getProductsList();
                if (products.get(j).getCategory().equals(categories[option])) {
                    productAvailable = true;
                    String temp = products.get(j).getTitle() +
                            "   " + products.get(j).getQuantity() +
                            "   Rs." + products.get(j).getPrice() +
                            "   " + seller.getName() +
                            "   " + seller.getPhoneNumber() + "\n";
                    productList += temp;
                }
            }
        }
        if (productAvailable) {
            JOptionPane.showMessageDialog(null, "Available Products:\n" + productList);
        } else {
            JOptionPane.showMessageDialog(null, "No Products Available");
        }
    }


    public void seller() {
        JFrame sellerFrame=new JFrame("Seller");
        sellerFrame.setSize(300,300);
        sellerFrame.setLocation(650,300);

        JLabel email_label=new JLabel("Email");
        email_label.setBounds(40,70,50,20);
        JTextField username=new JTextField();
        username.setBounds(80,70,150,20);

        JLabel password_label=new JLabel("Password");
        password_label.setBounds(40,100,70,20);
        JPasswordField password=new JPasswordField();
        password.setBounds(105,100,125,20);

        JLabel welcomeSeller=new JLabel("SELLER LOGIN");
        welcomeSeller.setBounds(100,30,120,20);

        JButton login=new JButton("LOGIN");
        login.setBounds(100,130,80,20);
        login.addActionListener((ActionEvent e) -> {
            if (username.getText()!=null&& password.getText()!=null){
                boolean accountFound=false;
                String givenname=username.getText();
                String givenPass=password.getText();
                for (int i=0 ;i<sellers.size() ;i++){
                    Seller seller=(Seller) sellers.get(i);
                    if (seller.getName().equals(givenname)&&seller.getPassword().equals(givenPass)){
                        accountLogin(seller);
                        accountFound=true;
                        break;
                    }
                }
                if (!accountFound){
                    JOptionPane.showMessageDialog(sellerFrame,"ACCOUNT NOT FOUND!");
                }

            }



        });


        JLabel signupLabel=new JLabel("If you don't have account, Click on Sign Up");
        signupLabel.setBounds(20,160,240,20);
        JButton signUp=new JButton("SIGN UP");
        signUp.setBounds(100,190,80,20);
        signUp.addActionListener((ActionEvent e) -> {
            createSellerAccount();
        });

        Container c=sellerFrame.getContentPane();
        c.add(welcomeSeller);
        c.add(email_label);
        c.add(username);
        c.add(password_label);
        c.add(password);
        c.add(login);
        c.add(signupLabel);
        c.add(signUp);
        c.setLayout(null);
        sellerFrame.setResizable(false);
        sellerFrame.setVisible(true);



//
//        int option = Integer.parseInt(JOptionPane.showInputDialog("Select Option:\n" +
//                "1.Create Seller Account\n" +
//                "2.Check Account Info\n" +
//                "3.Add Product\n" +
//                "4.Check Products List\n" +
//                "5.Modify Product Info.\n" +
//                "6.Delete Product from List\n" +
//                "7.Modify Account Info\n" +
//                "8.Delete Account"));
//        switch (option) {
//            case 1:
//                createSellerAccount();
//                break;
//            case 2:
//                checkAccountInfo();
//                break;
//            case 3:
//                addProduct();
//                break;
//            case 4:
//                checkProductList();
//                break;
//            case 5:
//                modifyProduct();
//                break;
//            case 6:
//                deleteProduct();
//                break;
//            case 7:
//                modifyAccount();
//                break;
//            case 8:
//                deleteAccount();
//                break;
//        }
    }

    private void accountLogin(Seller seller){
        JFrame frame=new JFrame("Seller Account Menu");
        Container container=frame.getContentPane();
        frame.setSize(500,500);
        JLabel welcomeLabel=new JLabel("Welcome Dear "+seller.getName()+", Please Select Options Given Below");
        welcomeLabel.setBounds(30,20,450,20);


        JButton checkAccountInfo=new JButton("CHECK ACCOUNT DETAILS");
        checkAccountInfo.setBounds(30,50,200,20);
        checkAccountInfo.addActionListener((ActionEvent e) -> {
            checkAccountInfo(seller);
        });


        JButton addProduct=new JButton("ADD PRODUCT");
        addProduct.setBounds(30,80,200,20);
        addProduct.addActionListener((ActionEvent e) -> {
            addProduct(seller);
                });

        JButton checkItemsList=new JButton("CHECK PRODUCTS LIST");
        checkItemsList.setBounds(30,110,200,20);
        checkItemsList.addActionListener((ActionEvent e) -> {
            checkProductList(seller);
        });


        JButton modifyProduct=new JButton("MODIFY PRODUCT");
        modifyProduct.setBounds(30,140,200,20);
        modifyProduct.addActionListener((ActionEvent e) -> {
            modifyProduct(seller);
        });


        JButton deleteProduct=new JButton("DELETE PRODUCT");
        deleteProduct.setBounds(30,170,200,20);
        deleteProduct.addActionListener((ActionEvent e) -> {
            deleteProduct(seller);
        });


        JButton modifyAccount=new JButton("MODIFY ACCOUNT");
        modifyAccount.setBounds(30,200,200,20);
        modifyAccount.addActionListener((ActionEvent e) -> {
            modifyAccount(seller);
        });

        JButton deleteAccount=new JButton("DELETE ACCOUNT");
        deleteAccount.setBounds(30,230,200,20);
        deleteAccount.addActionListener((ActionEvent e) -> {
            deleteAccount(seller);
        });

        JButton logout=new JButton("LOGOUT");
        logout.setBounds(30,260,200,20);
        logout.addActionListener((ActionEvent e) -> {loadFrame();
        });

        container.add(welcomeLabel);
        container.add(checkAccountInfo);
        container.add(addProduct);
        container.add(checkItemsList);
        container.add(modifyProduct);
        container.add(deleteProduct);
        container.add(modifyAccount);
        container.add(deleteAccount);
        container.add(logout);
        container.setLayout(null);
        frame.setLocation(300,200);
        frame.setVisible(true);
    }


    private void modifyAccount(Seller s) {
        boolean userFound = false;
        String name = s.getName();
        String password = s.getPassword();
        for (int i = 0; i < sellers.size(); i++) {
            Seller seller = (Seller) sellers.get(i);        //Downcasting Instances of Seller class...
            if (seller.getName().equals(name) && seller.getPassword().equals(password)) {
                String givenInfo = "Select\n1.  Name:" + seller.getName() + "\n2.  Phone Number:" + seller.getPhoneNumber() + "\n3.  Password:" + seller.getPassword();
                int option = Integer.parseInt(JOptionPane.showInputDialog(givenInfo));
                boolean infoUpdated = false;
                switch (option) {
                    case 1:
                        String updatedName = JOptionPane.showInputDialog("Enter New Name:");
                        seller.setName(updatedName);
                        infoUpdated = true;
                        break;
                    case 2:
                        String updatedPhone = JOptionPane.showInputDialog("Enter New Phone Number:");
                        seller.setPhoneNumber(updatedPhone);
                        infoUpdated = true;
                        break;
                    case 3:
                        String newPassword = JOptionPane.showInputDialog("Enter New Password:");
                        String pass2 = JOptionPane.showInputDialog("Enter Password Again:");
                        if (newPassword.equals(pass2)) {
                            seller.setPassword(newPassword);
                            infoUpdated = true;
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid Option");
                }
                if (infoUpdated) {
                    Person person = (Person) seller;
                    sellers.add(i, person);
                    JOptionPane.showMessageDialog(null, "Profile Updated");
                    break;
                }
            }
        }
    }

    private void deleteAccount(Seller s) {
        boolean userFound = false;
        String name = s.getName();
        String password = s.getPassword();
        for (Person user : sellers) {
            Seller seller = (Seller) user;     //DownCasting
            if (seller.getName().equals(name) && seller.getPassword().equals(password)) {
                userFound = true;
                int option = Integer.parseInt(JOptionPane.showInputDialog("Are your sure?\n1.Yes\n2.No"));
                if (option == 1) {
                    sellers.remove(seller);
                    JOptionPane.showMessageDialog(null, "Account Deleted");
                    break;
                }
            }
        }
        if (!userFound) {
            JOptionPane.showMessageDialog(null, "Account not found");
        }
    }

    private void deleteProduct(Seller s) {
        boolean userFound = false;
        String name = s.getName();
        String password = s.getPassword();
        for (Person user : sellers) {
            Seller seller = (Seller) user;     //DownCasting
            if (seller.getName().equals(name) && seller.getPassword().equals(password)) {
                seller.deleteProduct();
                userFound = true;
                break;
            }
        }
    }

    private void modifyProduct(Seller s) {
        boolean userFound = false;
        String name = s.getName();
        String password = s.getPassword();
        for (Person user : sellers) {
            Seller seller = (Seller) user;     //DownCasting
            if (seller.getName().equals(name) && seller.getPassword().equals(password)) {
                seller.modifyProduct();
                userFound = true;
                break;
            }
        }
        if (!userFound) {
            JOptionPane.showMessageDialog(null, "Account not found");
        }
    }

    private void addProduct(Seller s) {
        boolean userFound = false;
        String name = s.getName();
        String password = s.getPassword();
        for (Person user : sellers) {
            Seller seller = (Seller) user;     //DownCasting
            if (seller.getName().equals(name) && seller.getPassword().equals(password)) {
                seller.addProduct();
                userFound = true;
                JOptionPane.showMessageDialog(null, "Product " +
                        "added");
                break;
            }
        }
        if (!userFound) {
            JOptionPane.showMessageDialog(null, "Account not found");
        }
    }

    private void checkProductList(Seller s) {
        boolean userFound = false;
        String name = s.getName();
        String password = s.getPassword();
        for (Person user : sellers) {
            Seller seller = (Seller) user;     //DownCasting
            if (seller.getName().equals(name) && seller.getPassword().equals(password)) {
                seller.showProductList();
                userFound = true;
                break;
            }
        }
        if (!userFound) {
            JOptionPane.showMessageDialog(null, "Account not found");
        }
    }

    private void createSellerAccount() {
        String name = JOptionPane.showInputDialog("Enter New Name:");
        String password = JOptionPane.showInputDialog("Enter New Password:");
        String phoneNumber = JOptionPane.showInputDialog("Enter your contact number");
        Seller seller = new Seller(name, phoneNumber, password);
        sellers.add(seller);
        JOptionPane.showMessageDialog(null, "Account Created");
        int option = Integer.parseInt(JOptionPane.showInputDialog("Do you want to add products?\n1.Yes\n2.No"));
        if (option == 1) {
            addProduct(seller);
        }
    }

    private void checkAccountInfo(Seller s) {
        boolean userFound = false;
        try {
            String name = s.getName();
            String password = s.getPassword();
            if (!sellers.isEmpty()) {
                for (Person user : sellers) {
                    Seller seller = (Seller) user;
                    if (seller.getName().equals(name) && seller.getPassword().equals(password)) {
                        JOptionPane.showMessageDialog(null, "Username: " + seller.getName() + "\nPhone Number:" + seller.getPhoneNumber() + "\nPassword:" + seller.getPassword());
                        userFound = true;
                    }
                }
                if (!userFound) {
                    JOptionPane.showMessageDialog(null, "Account Not Found");
                }
            }
        } catch (NullPointerException ex) {
            ex.getMessage();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action=e.getActionCommand();
        if (action.equals("BUYER")){
            buyer();
        }
        else if (action.equals("SELLER")){
            seller();
        }
        else if (action.equals("SAVE AND EXIT")){
            saveData();
            System.exit(0);
        }
    }
}