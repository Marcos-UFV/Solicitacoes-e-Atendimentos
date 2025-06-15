package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.ufv.inf311.solicitaedu.model.Contact;
import br.com.ufv.inf311.solicitaedu.model.ContactDTO;
import br.com.ufv.inf311.solicitaedu.network.ApiClient;
import br.com.ufv.inf311.solicitaedu.network.RubeusEndpointsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    EditText tEmail;
    EditText password;
    private static final String API_TOKEN = BuildConfig.API_KEY;
    private static final String API_ORIGIN = BuildConfig.API_ORIGIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn = findViewById(R.id.loginBtn);
        tEmail = findViewById(R.id.tEmail);
        password = findViewById(R.id.tPassword);
        loginBtn.setOnClickListener(login());
    }
    private View.OnClickListener login(){
        RubeusEndpointsAPI api = ApiClient.getClientRx().create(RubeusEndpointsAPI.class);
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement logic to perform login
                String email = tEmail.getText().toString();
                Contact contact = new Contact(null, email, null);
                Call<ContactDTO> contactCallBack = api.getContact(contact,API_ORIGIN,API_TOKEN);
                contactCallBack.enqueue(new Callback<ContactDTO>() {
                    @Override
                    public void onResponse(Call<ContactDTO> call, Response<ContactDTO> response) {
                        if(response.isSuccessful()){
                            Log.i("CONTATO",response.body().toString());
                            Intent it = new Intent(getContext(),MainActivity.class);
                            startActivity(it);
                        }else{
                            Log.i("CONTATO","RESPONSE ERROR");
                        }
                    }

                    @Override
                    public void onFailure(Call<ContactDTO> call, Throwable t) {
                        Log.i("CONTATO","REQUEST ERROR"+t.toString());
                    }
                });
            }
        };
    }
    private Context getContext(){
        return this;
    }
}