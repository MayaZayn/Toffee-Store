package gui;

import actors.Attachtments.Order;
import actors.User;
import control.InputOutput;
import control.PaymentMethod;
import control.shop_items.Cart;
import control.shop_items.Catalog;
import control.shop_items.Item;
import model.DataBaseQueries;

import java.sql.SQLException;
import java.util.Map;

public class UserInterface {
    private InputOutput inputOutput = new InputOutput();
    private User user = new User();

    private Catalog catalog = new Catalog();

    public User register() throws SQLException {
        user = inputOutput.takeUserInput();
        return user;
    }

    public User logIn(){
        return user;
    }

    public void forgotPassword(){}

    public void checkOut(){
        user.getCart();
        Integer choice = inputOutput.checkOutOptions();
        if(choice == 1) {
           user.setAddress(inputOutput.takeAddressInput());
        }
        Order order = new Order(user);
        /*
        order.adjustTotalPrice(cart);
        order.adjustLoyaltyPoint(cart);
        PaymentMethod paymentMethod = new Delivery();
        order.showOrderDetails();
       */
        choice = inputOutput.orderOptions();
        if(choice == 1) {
            pay();
        }
        else{
            showCatalog();
        }
    }

//    public PaymentMethod pay(){}
    public void showCatalog(){
        /*
        String catalogStr = " ".repeat(7)+"<<<Toffee Catalog>>>\n";
        for(int i = 0; i < catalog.getItemList().size(); ++i){
            catalogStr +=  Integer.toString(i+1) + "." + catalog.getItemList().get(i).getName();
            catalogStr += "\n";
        }
        inputOutput.showCatalogInfo(catalogStr,catalog.getItemList().size());
        System.out.println("Enter Option Number: ");
        */

    }
    public void showCart(Cart cart){
        String cartStr = " ".repeat(7)+"<<< User Cart >>>\n";
        int cnt = 1;
        for(Map.Entry<Item,Integer> crt: cart.getItemsList().entrySet()){
            cartStr += Integer.toString(cnt) + "." + crt.getKey();
            cartStr += " ".repeat(2) + "Quantity: " + crt.getValue() + ".\n";
            cnt++;
        }
        Integer choice = inputOutput.cartOptions(cartStr);
        switch (choice){
            case 1:{
                checkOut();
                break;
            }
            case 2:{
                showCatalog();
                break;
            }
        }
    }

    public void pay(){};

    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface();
        DataBaseQueries dataBaseQueries = new DataBaseQueries();
        try {
            User user = dataBaseQueries.getUser("MRM");
            System.out.println(user.getUserName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        userInterface.checkOut();
        try {
            userInterface.register();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        InputOutput inputOutput1 = new InputOutput();
//        inputOutput1.takeAddressInput();
    }

}
