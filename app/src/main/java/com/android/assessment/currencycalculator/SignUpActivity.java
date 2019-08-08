package com.android.assessment.currencycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/*Signature: Uche Emmanuel
 * Developersunesis*/

/**
 * Please note, this activity is just a backend camouflage
 * The form doesn't really submit to any server or so
 * But that cant be easy to implement
 * Ofcourse you might have to do that (smiles)
 */

public class SignUpActivity extends AppCompatActivity {

    /*
    *   Declare all the views from layout
     */
    Button signup;
    EditText email, password, confirm_password;
    ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup = findViewById(R.id.signup);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        close =findViewById(R.id.close);

        //set close onClickListener
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //set signup onClickListener
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !confirm_password.getText().toString().isEmpty()){
                    if(password.getText().toString().equals(confirm_password.getText().toString())){
                        if(ValidateEmail.isValidEmail(email.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Registration Complete!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter a valid email address!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password do not match!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please complete the form!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
