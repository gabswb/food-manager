package food.manager.foodmanager.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import food.manager.foodmanager.R;
import food.manager.foodmanager.model.Product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> productList;
    private LayoutInflater inflater;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.adapter_product, null);

        //On récupère du produit de la liste à la position "position"
        Product currentProduct = getItem(position);
        String productName = currentProduct.getName();
        String productType = currentProduct.getType();
        int productQuantity = currentProduct.getQuantity();
        String productDate = currentProduct.getExpirationToString();

        
        TextView productInfoView = convertView.findViewById(R.id.product_description);

        String str = ""+productQuantity;
        if(productType.equals("meat")){
            str = ""+str+" g";
        } else if(productType.equals("vegetable")){
            str = ""+str+"x";
        }


        productInfoView.setText(str+" "+productType+" : "+ productName+", "+productDate);

        return convertView;
    }
}
