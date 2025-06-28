package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import br.com.ufv.inf311.solicitaedu.model.Contact;
import br.com.ufv.inf311.solicitaedu.model.ContactDTO;
import br.com.ufv.inf311.solicitaedu.network.ApiClient;
import br.com.ufv.inf311.solicitaedu.network.RubeusEndpointsAPI;
import br.com.ufv.inf311.solicitaedu.utils.DataBaseSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    EditText tEmail;
    EditText tPassword;
    boolean saveLogin = false;

    DataBaseSingleton db;

    private static final String API_TOKEN = BuildConfig.API_KEY;
    private static final String API_ORIGIN = BuildConfig.API_ORIGIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tEmail = findViewById(R.id.tEmail);
        tPassword = findViewById(R.id.tPassword);

        db = DataBaseSingleton.getInstance(this);

        Cursor c = db.search("Login", new String[] {"email", "senha", "lastLogin"}, "", "");
        if(c.getCount() != 0 && c.moveToFirst()){
            do {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inputDate = LocalDate.parse(c.getString(c.getColumnIndexOrThrow("lastLogin")), formatter);
                LocalDate today = LocalDate.now();
                long daysBetween = ChronoUnit.DAYS.between(inputDate, today);

                if(daysBetween <= 7) {
                    tEmail.setText(c.getString(c.getColumnIndexOrThrow("email")));
                    tPassword.setText(c.getString(c.getColumnIndexOrThrow("senha")));
                    Log.i("LOGIN", "Último Login há " + daysBetween + " dias");
                } else {
                    saveLogin = true;
                }

            } while (c.moveToNext());
        }

        Button loginBtn = findViewById(R.id.loginBtn);
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
                                if(password.replace("/", "").equals(birthDate)){
                                    contact.setName(info.getNome());
                                    contact.setId(Integer.parseInt(info.getId()));

                                    if(saveLogin) {
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                                        ContentValues values = new ContentValues();
                                        values.put("email", tEmail.getText().toString());
                                        values.put("senha", tPassword.getText().toString());
                                        values.put("lastLogin", LocalDate.now().format(formatter));
                                        db.update("Login", values, "id = 1");
                                    }

                                    Intent it = new Intent(getContext(), MainActivity.class);
                                    it.putExtra("contact",contact);
                                    startActivity(it);
                                }
                            } else {
                                //TODO: handle response error
                                Log.i("CONTATO","Response Body: "+response.body());
                                try {
                                    Log.i("CONTATO", "RESPONSE ERROR"+ RequestActivity.requestBodyToString(call.request().body()));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ContactDTO> call, Throwable t) {
                            //TODO: handle request error
                            Log.i("CONTATO", "REQUEST ERROR" + t.toString()+"error: "+t);
                        }
                    });
                }else{
                    Toast.makeText(getContext(),"Email e Data de Aniversário não devem ser nulos",Toast.LENGTH_LONG).show();
                }
            }
        };
    }
    private Context getContext(){
        return this;
    }
}