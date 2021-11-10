package food.manager.foodmanager.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Product {

    private String name;
    private String type;
    private Calendar expiration;
    private int id, quantity;

    public Product(){
        this.name = "default";
    }

    public Product(String name, String type, int quantity, Calendar calendar) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.expiration = calendar;
    }

    @Override
    public String toString() {
        return type+" : "+name+" "+expiration+" "+quantity;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getExpiration() {
        return expiration;
    }

    public void setExpiration(Calendar expiration) {
        this.expiration = expiration;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpirationToString(){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(this.expiration.getTime());

        return formatted;
    }
}
