package food.manager.foodmanager.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import food.manager.foodmanager.R;


public class MainActivity extends AppCompatActivity {

    private Button myProductButton;
    private Button shoppingListButton;
    private Button recipeButton;
    private Button disconnectButton;

    private int userID;

    public static final int LOGIN_ACTIVITY_REQUEST_CODE=1;
    public static final int MY_PRODUCT_ACTIVITY_REQUEST_CODE=2;
    public static final int RECIPE_ACTIVITY_REQUEST_CODE=3;
    public static final int SHOPPING_LIST_ACTIVITY_REQUEST_CODE=4;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //On récupère l'id de l'utilisateur connecté
        if (LOGIN_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            this.userID = data.getIntExtra(getString(R.string.user_id), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myProductButton = (Button) findViewById(R.id.activity_main_myproduct_btn);
        shoppingListButton = (Button) findViewById(R.id.activity_main_shoppinglist_btn);
        recipeButton = (Button) findViewById(R.id.activity_main_recipe_btn);
        disconnectButton = (Button) findViewById(R.id.activity_main_disconnect_btn);

        myProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.user_id),userID);
                Intent myProductActivity = new Intent(MainActivity.this, MyProductActivity.class);
                myProductActivity.putExtras(bundle);
                startActivity(myProductActivity);
            }
        });

        recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.user_id),userID);
                Intent recipeActivity = new Intent(MainActivity.this, RecipeActivity.class);
                recipeActivity.putExtras(bundle);
                startActivity(recipeActivity);
            }
        });

        shoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.user_id),userID);
                Intent shoppingListActivity = new Intent(MainActivity.this, ShoppingListActivity.class);
                shoppingListActivity.putExtras(bundle);
                startActivity(shoppingListActivity);
            }
        });

        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginListActivity = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginListActivity);
            }
        });

        //On démarre direct LoginActivity pour se connecter
        Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(loginActivity, LOGIN_ACTIVITY_REQUEST_CODE);
    }

}