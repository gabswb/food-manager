package food.manager.foodmanager.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import food.manager.foodmanager.R;
import food.manager.foodmanager.model.Product;
import food.manager.foodmanager.model.ProductAdapter;
import food.manager.foodmanager.tools.MySQLiteOpenHelper;

public class MyProductActivity extends AppCompatActivity implements AddProductDialog.AddProductDialogListener {


    public static final String CHANNEL_ID = "id1";
    private List<Product> productList;
    private ProductAdapter productAdapter = null;
    private Context context;
    private Button addButton;
    private int userID = -1;

    public MySQLiteOpenHelper mySQLiteOpenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product);

        //permet de récupérer l'id de l'User pour enregistrer et récupérer son inventaire
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            this.userID = bundle.getInt(getString(R.string.user_id));
        }


        productList= new ArrayList();
        //l'adapter permet de convertir la liste de produit en objet affichables par la listView
        productAdapter = new ProductAdapter(this, productList);
        context=this;
        addButton = findViewById(R.id.myproduct_add_btn);
        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);




        getProductsFromDataBase();//on récupère les produits de l'inventaire enregistrés dans la bdd

        ListView productListView = findViewById(R.id.myproduct_list_view);
        productListView.setAdapter(productAdapter);

        createNotificationChannel();//nécessaire pour envoyer des notification



        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override  //Listener qui est appelé lorqu'on clique sur un produit de la list view
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder infoProductDialog = new AlertDialog.Builder(context);


                infoProductDialog.setTitle("Product informations")
                        .setMessage("Type : "+productList.get(position).getType()
                                +"\nName : "+productList.get(position).getName()
                                +"\nQuantity : "+productList.get(position).getQuantity()
                                +"\nDate : "+productList.get(position).getExpirationToString())
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                productList.remove(position);
                                productAdapter.notifyDataSetChanged();//lorsqu'on supprime un produit de la liste, il faut mettre à jour la listView
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });




    }
    public void openDialog(){
        AddProductDialog addProductDialog = new AddProductDialog();
        addProductDialog.show(getSupportFragmentManager(), "addProductDialog");
    }



    @Override
    public void addProduct(String type, String name, int quantity, Calendar calendar) {

        //On récupère les inputs rentrés dans le dialog
        Product p = new Product(name, type, quantity,calendar);
        productList.add(p);
        mySQLiteOpenHelper.addProduct(p);//On enregistre le produit dans la bibliothèque de produits
        mySQLiteOpenHelper.addProductToInventory(userID, productList.size(),quantity,calendar);//On ajoute ce produit à l'inventaire

        productAdapter.notifyDataSetChanged();
        createAlarm(calendar);//On crée l'alarme
    }


    public void createAlarm(Calendar calendar){

        //On utilise initialise un intent et un pendinIntent qui va permettre à la notification d'ouvrir l'activité lorsqu'on clique dessus
        Intent intent = new Intent(MyProductActivity.this, AlertReceiver.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }






    private void createNotificationChannel() {
        //création d'un Notification Channel (copié de la documentation android)

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }




    public void getProductsFromDataBase(){

        //this.productList.addAll(mySQLiteOpenHelper.getProductsOfInventory(userID));
        // il y a un problème avec la gestion des calendar que nous n'avons pas réussi à régler dans la base de donnée
    }
}