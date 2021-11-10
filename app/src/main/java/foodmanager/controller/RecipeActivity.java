package food.manager.foodmanager.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import food.manager.foodmanager.R;
import food.manager.foodmanager.model.Product;
import food.manager.foodmanager.model.Recipe;
import food.manager.foodmanager.model.RecipeAdapter;
import food.manager.foodmanager.tools.MySQLiteOpenHelper;

public class RecipeActivity extends AppCompatActivity {

    //for the exemple
    private List<Product> ingredientsListCrepe;

    public static final String CHANNEL_ID = "id1";
    private List<Recipe> recipeList;
    private RecipeAdapter recipeAdapter;
    private Context context;
    private SearchView searchView;
    private int userID = -1;
    private CheckBox checkBox;

    public MySQLiteOpenHelper mySQLiteOpenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //On récupère l'id de l'utilisateur connecté
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            this.userID = bundle.getInt(getString(R.string.user_id));
        }

        context=this;
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);

        this.searchView = (SearchView) findViewById(R.id.recipe_search_view);
        this.checkBox = (CheckBox) findViewById(R.id.recipe_checkBox);

        recipeList= new ArrayList();
        getRecipeFromDataBase();

        /*
        //For the exemple------------------------------------------------------------------------------------------
        ingredientsListCrepe = new ArrayList();

        ingredientsListCrepe.add( new Product("flour", "", 250, createCalendar(2022, 11, 23)));
        ingredientsListCrepe.add( new Product("eggs", "proteins", 4, createCalendar(2021, 8, 11)));
        ingredientsListCrepe.add( new Product("milk", "dairy product", 500, createCalendar(2021, 7, 21)));
        ingredientsListCrepe.add( new Product("melted butter", "dairy product", 50, createCalendar(2021, 8, 21)));
        ingredientsListCrepe.add( new Product("sugar", "dairy product", 3, createCalendar(2022, 7, 21)));

        recipeList.add(new Recipe("crepe", 25, 1, 1, ingredientsListCrepe));
        //---------------------------------------------------------------------------------------------------------
        */

        recipeAdapter = new RecipeAdapter(context, recipeList);

        ListView recipeListView = findViewById(R.id.recipe_list_view);
        recipeListView.setAdapter(recipeAdapter);

        //Actualise la liste lorsqu'on tape quelque chose dans la barre de recherche
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recipeList = mySQLiteOpenHelper.researchRecipe(newText);
                recipeAdapter = new RecipeAdapter(context,recipeList);
                recipeListView.setAdapter(recipeAdapter);
                return false;
            }
        });




    }

    //L'utilisateur choisit le type d'affichage
    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        if(checked){
            this.getDoableRecipeFromDataBase();
        }else{
            this.getRecipeFromDataBase();
        }
        recipeAdapter.notifyDataSetChanged();
    }

    //Récupère toutes les recettes
    public void getRecipeFromDataBase(){
        this.recipeList.clear();
        this.recipeList.addAll(mySQLiteOpenHelper.getAllRecipes());
    }

    //Récupère les recettes que l'utilisateur peut faire avec son inventaire
    public void getDoableRecipeFromDataBase(){
        this.recipeList.clear();
        this.recipeList.addAll(mySQLiteOpenHelper.getDoableRecipes(userID));
    }

    //For the exemple
    private Calendar createCalendar(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        return calendar;
    }
}