package food.manager.foodmanager.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import food.manager.foodmanager.R;
import food.manager.foodmanager.tools.MySQLiteOpenHelper;

public class LoginActivity extends AppCompatActivity {

    boolean isUsernameValid, isPasswordValid;
    private Button signUpButton, loginButton;
    private EditText username, password;
    private TextInputLayout usernameError, passwordError;
    public MySQLiteOpenHelper mySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.signUpButton = (Button) findViewById(R.id.activity_login_signup_btn);
        this.loginButton = (Button) findViewById(R.id.activity_login_login_btn);

        this.username = (EditText) findViewById(R.id.activity_login_username_input);
        this.usernameError = (TextInputLayout) findViewById(R.id.activity_login_username_error_input);

        this.password = (EditText) findViewById(R.id.activity_login_password_input);
        this.passwordError = (TextInputLayout) findViewById(R.id.activity_login_password_error_input);

        signUpButton.setOnClickListener(signUpButtonListener);
        loginButton.setOnClickListener(loginButtonListener);

        this.mySQLiteOpenHelper = new MySQLiteOpenHelper(LoginActivity.this);
        mySQLiteOpenHelper.getReadableDatabase();

    }

    private View.OnClickListener loginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validation();
        }
    };

    private View.OnClickListener signUpButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent gameActivity = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(gameActivity);
            finish();
        }
    };

    //Efface ce qu'il y a marqué dans les input text
    public void clear(){
        username.setText(null);
        password.setText(null);
    }

    //Vérifie que les conditions sont respectées
    public void validation(){

        checkUsername();
        checkPassword();

        if(isUsernameValid && isPasswordValid ){
            Intent intent = new Intent();
            intent.putExtra(getString(R.string.user_id), mySQLiteOpenHelper.getUserID(username.getText().toString()));
            setResult(RESULT_OK, intent);
            finish();
        }
        clear();

    }

    //Vérifie l'input de l'username
    public void checkUsername(){

        if(username.getText().toString().isEmpty()){
            usernameError.setError(getResources().getString(R.string.username_error));
            isUsernameValid = false;
        } else if(!mySQLiteOpenHelper.isUsernameUsed(username.getText().toString())){
            usernameError.setError(getResources().getString(R.string.username_error_invalid));
            isUsernameValid = false;
        } else {
            usernameError.setErrorEnabled(false);
            isUsernameValid = true;
        }
    }

    //Vérifie l'input du password
    public void checkPassword(){

        if(password.getText().toString().isEmpty()){
            passwordError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if(!mySQLiteOpenHelper.checkUser(username.getText().toString(), password.getText().toString())){
            passwordError.setError(getResources().getString(R.string.password_error_invalid));
            isPasswordValid = false;
        } else {
            passwordError.setErrorEnabled(false);
            isPasswordValid = true;
        }
    }

}