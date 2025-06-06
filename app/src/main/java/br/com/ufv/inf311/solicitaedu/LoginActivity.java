package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
    EditText email;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn = findViewById(R.id.loginBtn);
        email = findViewById(R.id.tEmail);
        password = findViewById(R.id.tPassword);
        loginBtn.setOnClickListener(login());
    }
    private View.OnClickListener login(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement logic to perform login

                Intent it = new Intent(getContext(),MainActivity.class);
                startActivity(it);
            }
        };
    }
    private Context getContext(){
        return this;
    }
}