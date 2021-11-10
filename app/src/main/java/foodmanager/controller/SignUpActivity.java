package food.manager.foodmanager.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import food.manager.foodmanager.R;
import food.manager.foodmanager.model.User;
import food.manager.foodmanager.tools.MySQLiteOpenHelper;

public class SignUpActivity extends AppCompatActivity {

    boolean  isUsernameValid, isPasswordValid, isPasswordConfirmationValid;
    private Button signUpButton, loginButton;
    private EditText username, password,passwordConfirmation;
    private TextInputLayout usernameError, passwordError, passwordConfirmationError;
    private TextView successfulMessage;
    public MySQLiteOpenHelper mySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        this.signUpButton = (Button) findViewById(R.id.activity_sign_up_sign_up_btn);
        this.loginButton = (Button) findViewById(R.id.activity_sign_up_login_btn);

        this.username = (EditText) findViewById(R.id.activity_sign_up_username_input);
        this.usernameError = (TextInputLayout) findViewById(R.id.activity_sign_up_username_error_input);

        this.password = (EditText) findViewById(R.id.activity_sign_up_password_input);
        this.passwordError = (TextInputLayout) findViewById(R.id.activity_sign_up_password_error_input);

        this.passwordConfirmation = (EditText) findViewById(R.id.activity_sign_up_password_confirmation_input);
        this.passwordConfirmationError = (TextInputLayout) findViewById(R.id.activity_sign_up_password_confirmation_error_input);

        this.successfulMessage = (TextView) findViewById(R.id.activity_sign_up_message_text);

        signUpButton.setOnClickListener(signUpButtonListener);
        loginButton.setOnClickListener(loginButtonListener);

        this.mySQLiteOpenHelper = new MySQLiteOpenHelper(SignUpActivity.this);

        //////////////
        mySQLiteOpenHelper.getReadableDatabase();
        //////////////

    }

    private View.OnClickListener signUpButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setValidation();
        }
    };

    private View.OnClickListener loginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gameActivity = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(gameActivity);
            finish();
        }
    };

    //Pour effacer ce qu'il y a marqué dans les text input
    public void clear(){
        username.setText(null);
        password.setText(null);
        passwordConfirmation.setText(null);
    }

    //Vérifie que toutes les informations entrées sont correctes
    public void setValidation(){

        checkUsername();
        checkPassword();
        checkPasswordConfirmation();

        if(isUsernameValid && isPasswordValid && isPasswordConfirmationValid){
            successfulMessage.setText(R.string.sign_up_succeed);
            mySQLiteOpenHelper.addUser(new User(username.getText().toString(), password.getText().toString()));
            clear();
        }

    }

    public void checkUsername(){
        if(username.getText().toString().isEmpty()){
            usernameError.setError(getResources().getString(R.string.username_error));
            isUsernameValid = false;
        } else if(mySQLiteOpenHelper.isUsernameUsed(username.getText().toString())){
            usernameError.setError(getResources().getString(R.string.username_error_invalid));
            isUsernameValid = false;
        } else {
            usernameError.setErrorEnabled(false);
            isUsernameValid = true;
        }
    }

    public void checkPassword(){
        if(password.getText().toString().isEmpty()){
            passwordError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        }else {
            passwordError.setErrorEnabled(false);
            isPasswordValid = true;
        }
    }

    public void checkPasswordConfirmation(){
        if(passwordConfirmation.getText().toString().isEmpty()){
            passwordConfirmationError.setError(getResources().getString(R.string.password_confirmation_error));
            isPasswordConfirmationValid = false;
        } else if(!passwordConfirmation.getText().toString().equals(password.getText().toString())){
            passwordConfirmationError.setError(getResources().getString(R.string.password_confirmation_error_invalid));
            isPasswordConfirmationValid = false;
        } else {
            passwordConfirmationError.setErrorEnabled(false);
            isPasswordConfirmationValid = true;
        }
    }

}