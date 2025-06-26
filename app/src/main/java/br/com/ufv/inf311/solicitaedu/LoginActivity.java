package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import br.com.ufv.inf311.solicitaedu.model.Contact;
import br.com.ufv.inf311.solicitaedu.model.ContactDTO;
import br.com.ufv.inf311.solicitaedu.network.ApiClient;
import br.com.ufv.inf311.solicitaedu.network.RubeusEndpointsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    EditText tEmail;
    EditText tPassword;
    private static final String API_TOKEN = BuildConfig.API_KEY;
    private static final String API_ORIGIN = BuildConfig.API_ORIGIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn = findViewById(R.id.loginBtn);
        tEmail = findViewById(R.id.tEmail);
        tPassword = findViewById(R.id.tPassword);
        loginBtn.setOnClickListener(login());
    }
    private View.OnClickListener login(){
        RubeusEndpointsAPI api = ApiClient.getClientRx().create(RubeusEndpointsAPI.class);
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement logic to perform login
                String email = tEmail.getText().toString();
                String password = tPassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()) {
                    Contact contact = new Contact(null, email, password);
                    Call<ContactDTO> contactCallBack = api.getContact(contact, API_ORIGIN, API_TOKEN);
                    contactCallBack.enqueue(new Callback<ContactDTO>() {
                        @Override
                        public void onResponse(Call<ContactDTO> call, Response<ContactDTO> response) {
                            if (response.isSuccessful()) {
                                Log.i("CONTATO", response.body().toString());
                                ContactDTO.Data info = !response.body().getDados().isEmpty() ?response.body().getDados().get(0):null;
                                String birthDate = info.getDatanascimento();
                                Log.i("CONTATO", "Typed password: "+password+" correct password: "+birthDate);
                                if(password.equals(birthDate)){
                                    contact.setName(info.getNome());
                                    contact.setId(Integer.parseInt(info.getId()));
                                    Intent it = new Intent(getContext(), MainActivity.class);
                                    it.putExtra("contact",contact);
                                    startActivity(it);
                                }
                            } else {
                                //TODO: handle response error
                                Log.i("CONTATO", "RESPONSE ERROR");
                            }
                        }

                        @Override
                        public void onFailure(Call<ContactDTO> call, Throwable t) {
                            //TODO: handle request error
                            Log.i("CONTATO", "REQUEST ERROR" + t.toString());
                        }
                    });
                }else{
                    //TODO: handle empty values
                }
            }
        };
    }
    private Context getContext(){
        return this;
    }
}