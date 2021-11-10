package food.manager.foodmanager.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String name;
    private int time;
    private int difficulty;
    int id;

    public Recipe(){
        this.name =null;
        this.time = 0;
        this.difficulty=0;
        this.id=0;
    }
    public Recipe(String name, int time, int difficulty, int id, List<Product> ingredientsList) {
        this.name = name;
        this.time = time;
        this.difficulty = difficulty;
        this.id = id;
        this.ingredientsList = ingredientsList;
    }


    private List<Product> ingredientsList = new ArrayList<Product>();

    public void addIngredient(Product product){
        ingredientsList.add(product);
    }
    public void removeIngredient(Product product){
        ingredientsList.remove(product);
    }
    public List<Product> getIngredientsList(int recipeID){return this.ingredientsList;}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

    public int getDifficulty(){
        return this.difficulty;
    }
    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }

    public int getID() {
        return this.id;
    }
    public void setID(int id) {
        this.id = id;
    }

    //Permet d'afficher en quelle unité la quantité est
    //Exemple: pour la viande on utilise les kg et par défaut on utilise "x"
    public String getQuantityToString(int i){
        String str = ""+this.ingredientsList.get(i).getQuantity();

        if(this.ingredientsList.get(i).getType().equals("meat")){
            str = ""+str+" g";
        } else if(this.ingredientsList.get(i).getType().equals("vegetable")){
            str = ""+str+"x";
        }

        return str;
    }

    public String getIngredientsToString(){
        String str  = "";

        for( int i=0 ; i<this.ingredientsList.size() ; i++){
            str = str+""+this.ingredientsList.get(i).getName()+" "+getQuantityToString(i)+"\n";
        }

        return str;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                name + '\'' +
                ", time=" + time +
                ", difficulty='" + difficulty + '\'' +
                ", ingredientsList=" + ingredientsList +
                '}';
    }
}
