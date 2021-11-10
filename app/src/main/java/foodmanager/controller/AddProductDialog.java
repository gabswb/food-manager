package food.manager.foodmanager.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;

import food.manager.foodmanager.R;

public class AddProductDialog extends AppCompatDialogFragment {

    private EditText typeInput;
    private EditText nameInput;
    private EditText quantityInput;
    private DatePicker dateInput;

    private AddProductDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();//permet de récupérer les données du fichier XML
        View view = inflater.inflate(R.layout.layout_addproduct_dialog, null);

        typeInput = view.findViewById(R.id.addproduct_dialog_inptu1);
        nameInput = view.findViewById(R.id.addproduct_dialog_inptu2);
        quantityInput = view.findViewById(R.id.addproduct_dialog_inptu3);
        dateInput = view.findViewById(R.id.addproduct_dialog_input4);
        dateInput.setMinDate(System.currentTimeMillis());


        builder.setView(view)
                .setTitle("Add a product")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })


                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String type = typeInput.getText().toString();//on récupère touts les inputs
                        String name = nameInput.getText().toString();
                        int quantity = Integer.parseInt(quantityInput.getText().toString());//On convert la quantité rentré dans l'EditText (String)  en integer

                        int   day  = dateInput.getDayOfMonth();
                        int   month= dateInput.getMonth();
                        int   year = dateInput.getYear();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);


                        listener.addProduct(type, name, quantity, calendar);

                    }
                });


        return builder.create();
    }


    //Finalement il y'a qu'une méthode à implémenter (le rapport disait deux mais après vérification ce n'est pas nécessaire
    public interface AddProductDialogListener{
        void addProduct(String type, String name, int quantity, Calendar calendar);
    }

    // au cas où on oublit d'implémenter l'interface AddProductDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddProductDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+
                    "must implement ExampleDialogListener");
        }
    }
}
