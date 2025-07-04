package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.Arrays;

import br.com.ufv.inf311.solicitaedu.model.Contact;
import br.com.ufv.inf311.solicitaedu.model.Request;
import br.com.ufv.inf311.solicitaedu.model.RequestDTO;
import br.com.ufv.inf311.solicitaedu.model.RequestType;
import br.com.ufv.inf311.solicitaedu.network.ApiClient;
import br.com.ufv.inf311.solicitaedu.network.RubeusEndpointsAPI;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestActivity extends Activity {
    Button btnType;
    EditText description;
    RequestType requestType;
    RequestType[] requestTypes;
    Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ActivityBuilder.build(this, "Nova Solicitação");
        requestTypes = RequestType.values();
        Intent it = getIntent();
        contact = (Contact) it.getSerializableExtra("contact");
        btnType = findViewById(R.id.btnType);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        description = findViewById(R.id.tDescription);

        btnType.setOnClickListener(selectTitle());
        btnSubmit.setOnClickListener(submit());
    }
    public View.OnClickListener selectTitle(){
        String[] types = Arrays.stream(requestTypes).map(RequestType::getDescription).toArray(String[]::new);
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RequestActivity.this);
                builder.setTitle("Selecione o Tipo");
                builder.setItems(types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        btnType.setText(types[i]);
                        requestType = requestTypes[i];
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
    }
    public static String requestBodyToString(RequestBody requestBody) throws IOException {
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }
    public View.OnClickListener submit(){
        RubeusEndpointsAPI api = ApiClient.getClientRx().create(RubeusEndpointsAPI.class);
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgDescription  = description.getText().toString();
                if(requestType != null && !msgDescription.isEmpty()){
                    int processId= requestType.getId();
                    Request request = new Request(processId,contact,msgDescription);
                    Call<RequestDTO> requestCallBack = api.createRequest(request, BuildConfig.API_ORIGIN, BuildConfig.API_KEY);
                    requestCallBack.enqueue(new Callback<RequestDTO>() {
                        @Override
                        public void onResponse(Call<RequestDTO> call, Response<RequestDTO> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.putExtra("contact",contact);
                                startActivity(intent);
                            }else{
                                Log.i("EVENTO","Response Error");
                            }
                        }

                        @Override
                        public void onFailure(Call<RequestDTO> call, Throwable t) {
                            Log.i("EVENTO","Request Error");
                        }
                    });

                }else{
                    Log.i("EVENTO","Empty values");
                }
            }
        };
    }
    // Go to Home (MainActivity).
    public void openHomeActivity(View v) {
        Intent it = new Intent(this, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        it.putExtra("contact", contact);
        startActivity(it);
    }

    // Go to History Activity.
    public void openHistoryActivity(View v) {
        Intent it = new Intent(this, HistoryActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        it.putExtra("contact", contact);
        startActivity(it);
    }

    // Do nothing, since it's the current Activity.
    public void openRequestActivity(View v) {

    }
    public Context getContext(){
        return this;
    }
}