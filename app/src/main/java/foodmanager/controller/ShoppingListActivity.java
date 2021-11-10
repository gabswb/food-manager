package food.manager.foodmanager.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import food.manager.foodmanager.R;
import food.manager.foodmanager.model.Product;
import food.manager.foodmanager.model.Recipe;
import food.manager.foodmanager.tools.MySQLiteOpenHelper;

public class ShoppingListActivity extends AppCompatActivity {

    private int userID=-1;

    private List<String> shoppingList;
    private Button addButton;
    private Button deleteButton;
    private EditText addEditText;
    private ListView listView;
    private ArrayAdapter adapter;

    private MySQLiteOpenHelper mySQLiteOpenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        //permet de récupérer l'id de l'User pour enregistrer et récupérer sa liste de la bdd
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            this.userID = bundle.getInt(getString(R.string.user_id));
        }

        shoppingList = new ArrayList();
        addButton = (Button) findViewById(R.id.shopping_list_add_btn);
        deleteButton = (Button) findViewById(R.id.shopping_list_delete_btn);
        addEditText = (EditText) findViewById(R.id.shopping_list_text_input);
        listView = (ListView) findViewById(R.id.shopping_list_listview);

        //getListFromDataBase(); Il y a un problème avec notre base de donnée

        //On instancie un un adapter à choix muliple
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, shoppingList);
        listView.setAdapter(adapter);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newProduct = addEditText.getText().toString();//On recupère la chaine de caractère de l'editText

                shoppingList.add(newProduct);
                addEditText.setText("");

                //l'architecture de la base de donnée ne permet pas son utilisation correctement avec l'aboutissement actuel de l'application
                //mySQLiteOpenHelper.addProduct(new Product(newProduct, null,0,null ));
                //mySQLiteOpenHelper.addProductToList(userID,shoppingList.size(),1);
                adapter.notifyDataSetChanged();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> trash = new ArrayList<>();

                int len = listView.getCount();
                SparseBooleanArray checked = listView.getCheckedItemPositions();//checked est alors un SparseBooleanArray,
                // c'est à dire qu'il contient un entier (correspondant à la position d'un élément de l'adapter)
                // et un boolean (vrai si il est coché)

                for (int i = 0; i < len; i++)  //On contrôle pour chaque élément de l'adapter s'il est coché
                    if (checked.get(i)) {
                        trash.add(shoppingList.get(i)); //s'il est coché on le stock dans une list temporaire
                    }
                for(int i=0; i<trash.size();i++){ //On supprime tous les éléments stockés
                    shoppingList.remove(trash.get(i));
                }

                adapter.notifyDataSetChanged();
            }
        });

    }

    public void getListFromDataBase(){

        Product[] p = mySQLiteOpenHelper.getProductsOfList(userID);//On récupère tout les éléments de la liste sous forme de tableau

        for(int i=0; i<p.length;i++){
            shoppingList.add(p[i].getName());
        }
    }


}