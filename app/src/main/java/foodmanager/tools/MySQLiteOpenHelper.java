package food.manager.foodmanager.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import food.manager.foodmanager.model.User;
import food.manager.foodmanager.model.Product;
import food.manager.foodmanager.model.Recipe;

//Cette classe va permettre de créer et d'intéragir avec la base de donnée (bdd)
//L'extension SQLiteOpenHelper aide à créer et gérer la bdd
//Nous allons expliquer en détail quelques méthodes seulement, car elles utilisent toutes le même principe
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    // On définit des attributs qui serviront à la création de la bdd
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbFoodManager.db";

    //Ces "bloques" servent à initialiser les noms des attributs et des colonnes de la bdd
    //On définit aussi des commandes en SQL
    /////////////////////////////////////////////////////////////////////////////////////// USER
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ID = "id_user";

    public static final String USER_TABLE_NAME = "User";
    public String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + "("
                    + USER_USERNAME + " TEXT NOT NULL,"
                    + USER_PASSWORD + " TEXT NOT NULL,"
                    + USER_ID + " INTEGER PRIMARY KEY);";
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
    ///////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////// PRODUCT
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_TYPE = "type";
    public static final String PRODUCT_ID = "id_product";

    public static final String PRODUCT_TABLE_NAME = "Product";
    public String PRODUCT_TABLE_CREATE =
            "CREATE TABLE " + PRODUCT_TABLE_NAME + "("
                    + PRODUCT_NAME + " TEXT NOT NULL,"
                    + PRODUCT_TYPE + " TEXT NOT NULL,"
                    + PRODUCT_ID + " INTEGER PRIMARY KEY);";
    private String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME;
    ///////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////// RECIPE
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_TIME = "time";
    public static final String RECIPE_DIFFICULTY = "difficulty";
    public static final String RECIPE_ID = "id_recipe";

    public static final String RECIPE_TABLE_NAME = "Recipe";
    public String RECIPE_TABLE_CREATE =
            "CREATE TABLE " + RECIPE_TABLE_NAME + "("
                    + RECIPE_NAME + " TEXT NOT NULL,"
                    + RECIPE_TIME + " INTEGER NOT NULL,"
                    + RECIPE_DIFFICULTY + " INTEGER NOT NULL,"
                    + RECIPE_ID + " INTEGER PRIMARY KEY);";
    private String DROP_RECIPE_TABLE = "DROP TABLE IF EXISTS " + RECIPE_TABLE_NAME;
    ///////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////// COURSE LIST
    public static final String LIST_USER_ID = "id_user";
    public static final String LIST_PRODUCT_ID = "id_product";
    public static final String LIST_PRODUCT_QUANTITY = "product_quantity";

    public static final String LIST_TABLE_NAME = "List";
    public String LIST_TABLE_CREATE =
            "CREATE TABLE " + LIST_TABLE_NAME + "("
                    + LIST_USER_ID + " INTEGER NOT NULL,"
                    + LIST_PRODUCT_ID + " INTEGER NOT NULL,"
                    + LIST_PRODUCT_QUANTITY + " REAL NOT NULL,"
                    + "FOREIGN KEY(" + LIST_USER_ID + ") REFERENCES " + USER_ID + ","
                    + "FOREIGN KEY(" + LIST_PRODUCT_ID + ") REFERENCES " + PRODUCT_ID + ");";
    private String DROP_LIST_TABLE = "DROP TABLE IF EXISTS " + LIST_TABLE_NAME;
    ///////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////// INVENTORY
    public static final String INVENTORY_USER_ID = "id_user";
    public static final String INVENTORY_PRODUCT_ID = "id_product";
    public static final String INVENTORY_PRODUCT_QUANTITY = "product_quantity";
    public static final String INVENTORY_PRODUCT_EXPIRATION = "product_expiration";

    public static final String INVENTORY_TABLE_NAME = "Inventory";
    public String INVENTORY_TABLE_CREATE =
            "CREATE TABLE " + INVENTORY_TABLE_NAME + "("
                    + INVENTORY_USER_ID + " INTEGER NOT NULL,"
                    + INVENTORY_PRODUCT_ID + " INTEGER NOT NULL,"
                    + INVENTORY_PRODUCT_QUANTITY + " REAL NOT NULL,"
                    + INVENTORY_PRODUCT_EXPIRATION + " TEXT NOT NULL,"
                    + "FOREIGN KEY(" + INVENTORY_USER_ID + ") REFERENCES " + USER_ID + ","
                    + "FOREIGN KEY(" + INVENTORY_PRODUCT_ID + ") REFERENCES " + PRODUCT_ID + ");";
    private String DROP_INVENTORY_TABLE = "DROP TABLE IF EXISTS " + INVENTORY_TABLE_NAME;
    ///////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////// RECIPE - PRODUCT

    public static final String RECIPE_PRODUCT_RECIPE_ID = "id_recipe";
    public static final String RECIPE_PRODUCT_PRODUCT_ID = "id_product";
    public static final String RECIPE_PRODUCT_PRODUCT_QUANTITY = "product_quantity";

    public static final String RECIPE_PRODUCT_TABLE_NAME = "Recipe_Product";
    public String RECIPE_PRODUCT_TABLE_CREATE =
            "CREATE TABLE " + RECIPE_PRODUCT_TABLE_NAME + "("
                    + RECIPE_PRODUCT_RECIPE_ID + " INTEGER NOT NULL,"
                    + RECIPE_PRODUCT_PRODUCT_ID + " INTEGER NOT NULL,"
                    + RECIPE_PRODUCT_PRODUCT_QUANTITY + " REAL NOT NULL,"
                    + "FOREIGN KEY(" + RECIPE_PRODUCT_RECIPE_ID + ") REFERENCES " + RECIPE_ID + ","
                    + "FOREIGN KEY(" + RECIPE_PRODUCT_PRODUCT_ID + ") REFERENCES " + PRODUCT_ID + ");";
    private String DROP_RECIPE_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + RECIPE_PRODUCT_TABLE_NAME;

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructor
     *
     * @param context
     */
    public MySQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * If change of DB
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PRODUCT_TABLE_CREATE);
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(RECIPE_TABLE_CREATE);
        db.execSQL(LIST_TABLE_CREATE);
        db.execSQL(INVENTORY_TABLE_CREATE);
        db.execSQL(RECIPE_PRODUCT_TABLE_CREATE);
    }

    /**
     * If change of version
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PRODUCT_TABLE);
        db.execSQL(DROP_RECIPE_TABLE);
        db.execSQL(DROP_LIST_TABLE);
        db.execSQL(DROP_INVENTORY_TABLE);
        db.execSQL(DROP_RECIPE_PRODUCT_TABLE);
        onCreate(db);
    }


    //Dans ces "bloques" on implémente des fonctions permettant plusieurs actions avec la bdd
    //On range ces bloques en fonction d'avec quelle partie de la bdd elles intéragissent
    /////////////////////////////////////////////////////////////////////////////////////// USER

    /**
     * Add an user in the data base
     *
     * @param user - the user to add
     */
    // Toutes les méthodes qui auront comme but d'ajouter quelque chose à la bdd auront cette forme
    public void addUser(User user) {
        //On ouvre la bdd de façon à pouvoir la modifier
        SQLiteDatabase db = this.getWritableDatabase();

        //ContentValues permet de stocker des valeurs que le ContentResolver peut utiliser
        ContentValues values = new ContentValues();
        //On ajoute les valeurs que l'on veut ajouter dans
        values.put(USER_USERNAME, user.getUsername());
        values.put(USER_PASSWORD, user.getPassword());

        //On ajoute ces valeurs dans la bdd
        db.insert(USER_TABLE_NAME, null, values);
        db.close();//On ferme la bdd
    }

    /**
     * Get the ID of an user
     *
     * @param username - the name of the user researched
     * @return the ID of the user associate to the username
     */
    // Toutes les méthodes qui auront comme but de récupérer quelque chose de la bdd auront cette forme
    public int getUserID(String username) {
        //Les colonnes qu'on souhaite récupérer
        String[] columns = {
                USER_ID
        };

        //On ouvre la bdd pour pouvoir lire ce qu'il y a dedans
        SQLiteDatabase db = this.getReadableDatabase();

        //La condition de la requête SQL
        String selection = USER_USERNAME + " = ?";
        //Ce qui remplacera le "?" de la requête
        String[] selectionArgs = {username};

        //On utilise un curseur car la recherche dans une bdd ressemble à un tableau
        Cursor cursor = db.query(USER_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int user_id = -1;

        //On replace le curseur à la fin (même si il n'y a en théorie qu'un utilsiateur avec cet id)
        cursor.moveToLast();
        if (!cursor.isAfterLast()) {//On vérifie que le curseur a récupéré quelque chose
            user_id = cursor.getInt(cursor.getColumnIndex(USER_ID));//On récupère la valeur de la colonne USER_ID
        }
        cursor.close();//On ferme le curseur
        db.close();//On ferme la bdd

        return user_id;
    }

    public User getUser(int userID) {
        String[] columns = {
                USER_USERNAME,
                USER_PASSWORD
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = USER_ID + " = ?";
        String[] selectionArgs = {"" + userID};

        Cursor cursor = db.query(USER_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        cursor.moveToFirst();
        String username = cursor.getString(cursor.getColumnIndex(USER_USERNAME));
        String password = cursor.getString(cursor.getColumnIndex(USER_PASSWORD));

        User user = new User(username, password);

        user.setId(userID);
        cursor.close();
        db.close();

        return user;
    }

    /**
     * Check if the account exists
     *
     * @param username - the name of the researched user
     * @param password - the password of the research user
     * @return true if the account exists, false if not
     */
    public boolean checkUser(String username, String password) {
        String[] columns = {
                USER_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = USER_USERNAME + " = ?" + " AND " + USER_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(USER_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    /**
     * Check if the username is already used
     *
     * @param username - the username to check
     * @return true if the username is already used, false if not
     */
    public boolean isUsernameUsed(String username) {
        String[] columns = {
                USER_USERNAME
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = USER_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(USER_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////// PRODUCT

    /**
     * Add a product to the data base
     * @param product - the product to add
     */
    public void addProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_TYPE, product.getType());

        db.insert(PRODUCT_TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Get a product from its ID
     * @param productID - the ID to research
     * @return the product associates to the ID
     */
    public Product getProduct(int productID){
        String[] columns = {
                PRODUCT_NAME,
                PRODUCT_TYPE,
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PRODUCT_ID + " = ?";
        String[] selectionArgs = {""+productID};

        Cursor cursor = db.query(PRODUCT_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        cursor.moveToLast();
        Product product = new Product();
        if(!cursor.isAfterLast()){
            product.setName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
            product.setType(cursor.getString(cursor.getColumnIndex(PRODUCT_TYPE)));
            product.setID(productID);
            cursor.close();
            db.close();
            return product;
        }
        cursor.close();
        db.close();

        return null;
    }

    public List<Product> getAllProduct(){
        String[] columns = {
                PRODUCT_ID,
                PRODUCT_NAME,
                PRODUCT_TYPE
        };

        String sortOrder =
                PRODUCT_ID + " ASC";
        List<Product> allProducts = new ArrayList<Product>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PRODUCT_TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
                product.setType(cursor.getString(cursor.getColumnIndex(PRODUCT_TYPE)));
                product.setID(cursor.getInt(cursor.getColumnIndex(PRODUCT_ID)));

                allProducts.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return allProducts;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////// RECIPE



    /**
     * Add a recipe to the data base
     * @param recipe - the recipe to add
     */
    public void addRecipe(Recipe recipe){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPE_NAME, recipe.getName());
        values.put(RECIPE_TIME, recipe.getTime());
        values.put(RECIPE_DIFFICULTY, recipe.getDifficulty());

        db.insert(RECIPE_TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Remove a recipe from the data base
     * @param recipeID - the recipe's ID to remove
     */
    public void removeRecipe(int recipeID){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(RECIPE_TABLE_NAME, RECIPE_ID + " = ?",
                new String[]{""+recipeID});
        db.close();


    }

    /**
     * Get the name, the time and the difficulty of a recipe from the data base
     * @param recipeID - the ID of the recipe to research
     * @return the recipe with the attributes research
     */
    public Recipe getRecipe(int recipeID){
        String[] columns = {
                RECIPE_NAME,
                RECIPE_TIME,
                RECIPE_DIFFICULTY
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = RECIPE_ID + " = ? ";
        String[] selectionArgs = {""+recipeID};

        Cursor cursor = db.query(RECIPE_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        cursor.moveToFirst();
        Recipe recipe = new Recipe();

        recipe.setName(cursor.getString(cursor.getColumnIndex(RECIPE_NAME)));
        recipe.setTime(cursor.getInt(cursor.getColumnIndex(RECIPE_TIME)));
        recipe.setDifficulty(cursor.getInt(cursor.getColumnIndex(RECIPE_DIFFICULTY)));
        recipe.setID(recipeID);

        List<Product> productsList = new ArrayList<>();
        productsList.addAll(this.getProductsOfRecipe(recipeID));
        int nbOfProducts = productsList.size();

        for( int i=0 ; i<nbOfProducts ; i++){
            recipe.addIngredient(productsList.get(i));
        }

        cursor.close();
        db.close();

        return recipe;

    }

    public List<Recipe> getAllRecipes(){
        String[] columns = {
                RECIPE_ID,
                RECIPE_NAME,
                RECIPE_DIFFICULTY,
                RECIPE_TIME
        };

        String sortOrder =
                RECIPE_ID + " ASC";
        List<Recipe> allRecipes = new ArrayList<Recipe>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(RECIPE_TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.setName(cursor.getString(cursor.getColumnIndex(RECIPE_NAME)));
                recipe.setDifficulty(cursor.getInt(cursor.getColumnIndex(RECIPE_DIFFICULTY)));
                recipe.setTime(cursor.getInt(cursor.getColumnIndex(RECIPE_TIME)));
                recipe.setID(cursor.getInt(cursor.getColumnIndex(RECIPE_ID)));

                List<Product> productsList = new ArrayList<>();
                productsList.addAll(this.getProductsOfRecipe(recipe.getID()));

                for( int i=0 ; i<productsList.size() ; i++){
                    recipe.addIngredient(productsList.get(i));
                }

                allRecipes.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return allRecipes;
    }

    public List<Recipe> researchRecipe(String recipeName){

        List<Recipe> recipeList = this.getAllRecipes();

        for(int i=0 ; i<recipeList.size() ; i++){
            if(!recipeList.get(i).getName().contains(recipeName)){
                recipeList.remove(i);
            }
        }
        return recipeList;
    }

    public List<Recipe> getDoableRecipes(int userID){

        User user = this.getUser(userID);
        List<Product> inventory = new ArrayList<>();
        inventory.addAll(this.getProductsOfInventory(userID));

        List<Recipe> recipeList = new ArrayList<>();
        recipeList.addAll(this.getAllRecipes());
        List<Recipe> doableRecipeList = new ArrayList<>();
        List<Product> recipeProducts = new ArrayList<>();
        int count;

        for(int i=0 ; i<recipeList.size() ; i++){
            recipeProducts.clear();
            recipeProducts.addAll(this.getProductsOfRecipe(recipeList.get(i).getID()));
            count = 0;

            for(int h=0 ; h<recipeProducts.size() ; h++){

                for(int j=0 ; j<inventory.size() ; j++){

                    if(recipeProducts.get(h).getID()==inventory.get(j).getID() && recipeProducts.get(h).getQuantity() <= inventory.get(j).getQuantity()){
                        count++;
                    }
                }
                if(count == recipeProducts.size()){
                    doableRecipeList.add(recipeList.get(i));
                }

            }
        }
        return doableRecipeList;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////RECIPE - PRODUCTS

    /**
     * Add a product and its quantity to a recipe
     * @param recipeID
     * @param productID
     * @param quantity
     */
    public void addProductToRecipe(int recipeID, int productID, int quantity){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPE_PRODUCT_RECIPE_ID, recipeID);
        values.put(RECIPE_PRODUCT_PRODUCT_ID, productID);
        values.put(RECIPE_PRODUCT_PRODUCT_QUANTITY, quantity);

        db.insert(RECIPE_PRODUCT_TABLE_NAME, null, values);
        db.close();

    }

    public void removeProductFromRecipe(int recipeID, int productID){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(RECIPE_PRODUCT_TABLE_NAME, RECIPE_PRODUCT_RECIPE_ID + " = ? AND " + RECIPE_PRODUCT_PRODUCT_ID + " = ? ",
                new String[]{""+recipeID, ""+productID});
        db.close();

    }

    /**
     * Get the ingredients of a recipe (without the quantities)
     * @param recipeID
     * @return an array of products representing the ingredients
     */
    public List<Product> getProductsOfRecipe(int recipeID){

        String[] columns = {
                RECIPE_PRODUCT_PRODUCT_ID,
                RECIPE_PRODUCT_PRODUCT_QUANTITY
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = RECIPE_PRODUCT_RECIPE_ID + " = ? ";
        String[] selectionArgs = {""+recipeID};
        String sortOrder =
                RECIPE_PRODUCT_PRODUCT_ID + " ASC";

        Cursor cursor = db.query(RECIPE_PRODUCT_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        int nbOfProducts = cursor.getCount();
        List<Product> productsList = new ArrayList<>(nbOfProducts);

        if(cursor.moveToFirst()){
            int i=0;
            int productID;
            do{
                productID = cursor.getInt(cursor.getColumnIndex(RECIPE_PRODUCT_PRODUCT_ID));
                Product product = this.getProduct(productID);
                product.setQuantity(cursor.getInt(cursor.getColumnIndex(RECIPE_PRODUCT_PRODUCT_QUANTITY)));
                productsList.add(product);
                i++;
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productsList;

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////// COURSE LIST

    public void addProductToList(int userID, int productID, int quantity){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LIST_USER_ID, userID);
        values.put(LIST_PRODUCT_ID, productID);
        values.put(LIST_PRODUCT_QUANTITY, quantity);

        db.insert(LIST_TABLE_NAME, null, values);
        db.close();

    }

    public void removeProductFromList(int userID, int productID){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(LIST_TABLE_NAME, LIST_USER_ID + " = ? AND " + LIST_PRODUCT_ID + " = ? ",
                new String[]{""+userID, ""+productID});
        db.close();

    }

    public void getList(User user){

        Product[] productsList = this.getProductsOfList(user.getId());
        int nbOfProducts = productsList.length;

        for( int i=0 ; i<nbOfProducts ; i++){
            user.addProductToList(productsList[i]);
        }

    }

    public Product[] getProductsOfList(int userID){

        String[] columns = {
                LIST_PRODUCT_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = LIST_USER_ID + " = ? ";
        String[] selectionArgs = {""+userID};
        String sortOrder =
                LIST_PRODUCT_ID + " ASC";

        Cursor cursor = db.query(LIST_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        int nbOfProducts = cursor.getCount();
        Product[] productsList = new Product[nbOfProducts];

        if(cursor.moveToFirst()){
            int i=0;
            int productID;
            do{
                productID = cursor.getInt(cursor.getColumnIndex(LIST_PRODUCT_ID));
                productsList[i] = this.getProduct(productID);
                productsList[i].setQuantity(cursor.getInt(cursor.getColumnIndex(LIST_PRODUCT_QUANTITY)));
                i++;
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productsList;

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////// INVENTORY

    public void addProductToInventory(int user_id, int productID, int quantity, Calendar expiration){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(INVENTORY_USER_ID, user_id);
        values.put(INVENTORY_PRODUCT_ID, productID);
        values.put(INVENTORY_PRODUCT_QUANTITY, quantity);
        values.put(INVENTORY_PRODUCT_EXPIRATION, expiration.toString());

        db.insert(INVENTORY_TABLE_NAME, null, values);
        db.close();

    }

    public void removeProductFromInventory(int user_id, int productID){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(INVENTORY_TABLE_NAME, INVENTORY_USER_ID + " = ? AND "+ INVENTORY_PRODUCT_ID +" = ?",
                new String[]{""+user_id, ""+productID});

        db.close();

    }

    public void getInventory(int userID){

        User user = this.getUser(userID);
        List<Product> productsList = new ArrayList<>();
        productsList.addAll(this.getProductsOfInventory(user.getId()));
        int nbOfProducts = productsList.size();

        for( int i=0 ; i<nbOfProducts ; i++){
            user.addProductToInventory(productsList.get(i));
        }

    }

    public List<Product> getProductsOfInventory(int userID){

        String[] columns = {
                INVENTORY_PRODUCT_ID,
                INVENTORY_PRODUCT_EXPIRATION,
                INVENTORY_PRODUCT_QUANTITY
        };

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = INVENTORY_USER_ID + " = ? ";
        String[] selectionArgs = {""+userID};
        String sortOrder =
                INVENTORY_PRODUCT_ID + " ASC";

        Cursor cursor = db.query(INVENTORY_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null/*sortOrder*/);

        int nbOfProducts = cursor.getCount();
        List<Product> productsList = new ArrayList<>(nbOfProducts);

        if(cursor.moveToFirst()){
            do{
                Product product = this.getProduct(cursor.getInt(cursor.getColumnIndex(INVENTORY_PRODUCT_ID)));
                product.setQuantity(cursor.getInt(cursor.getColumnIndex(INVENTORY_PRODUCT_QUANTITY)));
                try {
                    String str_date = cursor.getString(cursor.getColumnIndex(INVENTORY_PRODUCT_EXPIRATION));
                    DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
                    Date date = (Date)formatter.parse(str_date);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    product.setExpiration(cal);
                } catch (ParseException e){
                    product.setExpiration(null);
                }

                productsList.add(product);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productsList;

    }

}