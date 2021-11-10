package food.manager.foodmanager.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username, password;
    private int id;

    private List<Product> productsList = new ArrayList<>();
    private List<Product> inventory = new ArrayList<>();

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }


    public void addProductToList(Product product){
        this.productsList.add(product);
    }

    public void removeProductFromList(Product product){
        this.productsList.remove(product);
    }

    public void addProductToInventory(Product product){
        this.inventory.add(product);
    }

    public void removeProductFromInventory(Product product){
        this.productsList.remove(product);
    }

    public List<Product> getInventory(){
        return this.inventory;
    }


    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public int getId(){
        return this.id;
    }

    public void setUsername(String name){
        this.username = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setId(int id){
        this.id = id;
    }

}
