package com.example.justine.taragala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity {
    LoginCRUD logInCRUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Button btnregister = (Button) findViewById(R.id.register);
        final EditText etfirstname = (EditText) findViewById(R.id.firstname);
        final EditText etlastname = (EditText) findViewById(R.id.lastname);
        final EditText etusername = (EditText) findViewById(R.id.username);
        final EditText etemail = (EditText) findViewById(R.id.email);
        final EditText etpassword = (EditText) findViewById(R.id.password);
        final EditText etconfirmpassword = (EditText) findViewById(R.id.confirmpassword);

        logInCRUD = new LoginCRUD(this);
        logInCRUD = logInCRUD.open();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etfirstname.getText().toString().isEmpty()||etlastname.getText().toString().isEmpty()||etusername.getText().toString().isEmpty()||etemail.getText().toString().isEmpty()||etpassword.getText().toString().isEmpty()||etconfirmpassword.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), "Please fill out all the remaining fields.", Toast.LENGTH_SHORT).show();
                }
                else if(!(Pattern.compile("[a-zA-Z]+").matcher(etfirstname.getText()).matches())){
                    etfirstname.setError("Special Characters and Numbers are not allowed.");
                }
                else if(!(Pattern.compile("[a-zA-Z]+").matcher(etlastname.getText()).matches())){
                    etlastname.setError("Special Characters and Numbers are not allowed.");
                }
                else if(Patterns.EMAIL_ADDRESS.matcher(etusername.getText()).matches()){
                    etusername.setError("Invalid Username.");
                }
                else if(logInCRUD.getUsernameEntry(etusername.getText().toString())){
                    etusername.setError("Username Already Exist.");
                }
                else if(logInCRUD.getEmailEntry(etemail.getText().toString())){
                    etemail.setError("Username Already Exist.");
                }
                else if(!(Patterns.EMAIL_ADDRESS.matcher(etemail.getText()).matches())){
                    etemail.setError("Please enter a valid emaill address.");
                }
                else if(etpassword.getText().length() < 8){
                    etpassword.setError("Password length atleast 8 characters");
                }
                else if(!(etconfirmpassword.getText().toString().equals(etpassword.getText().toString()))){
                    etconfirmpassword.setError("Password Does not match");
                }
                else{
                    logInCRUD.insertEntry(String.valueOf(etusername.getText()), String.valueOf(etpassword.getText()), String.valueOf(etemail.getText()), String.valueOf(etfirstname.getText()), String.valueOf(etlastname.getText()));

                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(SignIn.this, Login.class);
                    startActivity(a);
                    finish();
                }

            }
        });
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        logInCRUD.close();
    }

}
