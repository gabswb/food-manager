package food.manager.foodmanager.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SearchView;
import android.widget.TextView;

import food.manager.foodmanager.R;
import food.manager.foodmanager.model.Product;
import food.manager.foodmanager.model.Recipe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class RecipeAdapter extends BaseAdapter {

    private Context context;
    private List<Recipe> recipeList;
    private LayoutInflater inflater;

    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Recipe getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.adapter_recipe, null);

        //On récupère du produit de la liste à la position "position"
        Recipe currentRecipe = getItem(position);
        String recipeName = currentRecipe.getName();
        int recipeTime = currentRecipe.getTime();
        int recipeDifficulty = currentRecipe.getDifficulty();
        String recipeIngredients = currentRecipe.getIngredientsToString();


        TextView productInfoView = convertView.findViewById(R.id.recipe_description);
        productInfoView.setText(recipeName+"  "+recipeTime+" min  "+ recipeDifficulty+"/3 \n"+recipeIngredients);

        return convertView;
    }
}
