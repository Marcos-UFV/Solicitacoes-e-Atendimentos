package br.com.ufv.inf311.solicitaedu;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.ufv.inf311.solicitaedu.model.RegisterDTO;
import br.com.ufv.inf311.solicitaedu.model.RegisterInfo;
import br.com.ufv.inf311.solicitaedu.network.ApiClient;
import br.com.ufv.inf311.solicitaedu.network.RubeusEndpointsAPI;
import br.com.ufv.inf311.solicitaedu.utils.DataBaseSingleton;

import br.com.ufv.inf311.solicitaedu.model.Contact;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends Activity {

    DataBaseSingleton db;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = DataBaseSingleton.getInstance(this);

        Intent it = getIntent();
        contact = (Contact) it.getSerializableExtra("contact");

        ActivityBuilder.build(this, "Home");
        initNotifications();
        // fetch();
    }

    // === HOME BUTTONS ===
    // Opens university website.
    public void openUniversityWebsite(View v) {
        Uri uri = Uri.parse("https://www.ufv.br/");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);
    }

    // Calls university support telephone.
    public void callUniversity(View v) {
        Uri uri = Uri.parse("tel:+55 (31) 3612-1081");
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(it);
    }

    // Open Calendar Activity.
    public void openCalendarActivity(View v) {
        Intent it = new Intent(this, CalendarActivity.class);
        startActivity(it);
    }

    public void initNotifications() {
        MaterialButton bttn = (MaterialButton) findViewById(R.id.notifsButton);
        final int[] total = {0}; // Necessário para poder modificar dentro do callback
        String rest = " Nova(s) Respostas(s)";

        int lastSeenId = 0;

        Cursor c = db.search("Notification", new String[]{"lastSeenID"}, "", "");
        if(c.getCount() != 0 && c.moveToFirst()) {
            do {
                lastSeenId = c.getInt(c.getColumnIndexOrThrow("lastSeenID"));
            } while (c.moveToNext());
        } else {
            bttn.setVisibility(View.GONE);
            findViewById(R.id.ultimaAtt).setVisibility(View.GONE);
        }

        RubeusEndpointsAPI api = ApiClient.getClientRx().create(RubeusEndpointsAPI.class);
        Call<RegisterDTO> callGetRegisters = api.getRegisters(BuildConfig.API_ORIGIN, BuildConfig.API_KEY, ""+contact.getId());
        int finalLastSeenId = lastSeenId; // Essa cópia é necessária para usar a variável dentro do callback
        callGetRegisters.enqueue(new Callback<RegisterDTO>() {
            @Override
            public void onResponse(Call<RegisterDTO> call, Response<RegisterDTO> response) {
                int maiorId = 0;
                String momento = "";

                if (response.isSuccessful()) {
                    List<RegisterInfo> info = !response.body().getDados().isEmpty() ? response.body().getDados() : null;
                    if (info != null) {
                        for (int i = 0; i < info.size(); i++) {
                            if (Integer.parseInt(info.get(i).getId()) > finalLastSeenId &&
                                    info.get(i).getEtapaNome().equals("Resolvida")) {
                                total[0]++;
                            }

                            if(Integer.parseInt(info.get(i).getId()) > maiorId) {
                                maiorId = Integer.parseInt(info.get(i).getId());
                                momento = info.get(i).getMomento();
                            }
                        }
                    }

                    bttn.setText(total[0] + rest);

                    SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
                    Date data = null;
                    try {
                        data = formatoEntrada.parse(momento);
                        ((TextView) findViewById(R.id.ultimaAtt)).setText("Última atualização: " + formatoSaida.format(data));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }


                    if(total[0] == 0) {
                        bttn.setVisibility(View.GONE);
                        findViewById(R.id.ultimaAtt).setVisibility(View.GONE);
                    } else {
                        bttn.setVisibility(View.VISIBLE);
                        findViewById(R.id.ultimaAtt).setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterDTO> call, Throwable t) {}
        });
    }

    // === FOOTER BUTTONS ===
    // Do nothing, since it's the current Activity.
    public void openHomeActivity(View v) {

    }

    // Go to History Activity.
    public void openHistoryActivity(View v) {
        Intent it = new Intent(this, HistoryActivity.class);
        it.putExtra("contact", contact);
        startActivity(it);
    }

    // Go to History Activity.
    public void openRequestActivity(View v) {
        Intent it = new Intent(this, RequestActivity.class);
        it.putExtra("contact",contact);
        startActivity(it);
    }
}