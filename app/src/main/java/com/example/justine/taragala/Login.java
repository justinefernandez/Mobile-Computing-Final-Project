package com.example.justine.taragala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;

public class Login extends AppCompatActivity {
    LoginCRUD logInCRUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final TextView signin = (TextView) findViewById(R.id.signin);
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        Button show = (Button) findViewById(R.id.showbutton);
        Button login = (Button) findViewById(R.id.loginbutton);
        // Button for Show
        show.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motion){
                int cursor = password.getSelectionStart();
                switch ( motion.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        password.setTransformationMethod(null);
                        Log.d("Classname", "ACTION_DOWN");
                        password.setSelection(cursor);
                        break;
                    case MotionEvent.ACTION_UP:
                        password.setTransformationMethod(new PasswordTransformationMethod());
                        Log.d("Classname", "ACTION_UP");
                        password.setSelection(cursor);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        password.setTransformationMethod(new PasswordTransformationMethod());
                        password.setSelection(cursor);;
                        break;
                }
                return true;
                /*int EventAct = motion.getAction();

                if (EventAct == MotionEvent.ACTION_UP) {

                    password.setTransformationMethod(new PasswordTransformationMethod());
                } else if (EventAct == MotionEvent.ACTION_CANCEL) {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                } else if (EventAct == MotionEvent.ACTION_DOWN) {
                    password.setTransformationMethod(null);
               }
                return true;*/
            }
        });
        logInCRUD = new LoginCRUD(this);
        logInCRUD = logInCRUD.open();
        // Button for Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    if(username.getText().toString().isEmpty()){
                        username.setError("The Item username cannot be empty.");
                    }
                    if(password.getText().toString().isEmpty()){
                        password.setError("The Item password cannot be empty.");
                    }
                } else {
                    String pas = password.getText().toString();
                    String uns = username.getText().toString();
                    String storedPassword = logInCRUD.getSinlgeEntry(uns);
                    // check if the Stored password matches with  Password entered by user
                    if(pas.equals(storedPassword))
                    {
                        Toast.makeText(Login.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivityForResult(intent, 0);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Login.this, "Username or Password does not match", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        //Sign In Link
        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), SignIn.class);
                startActivityForResult(intent, 0);
                finish();
            }
        });
    }
}
