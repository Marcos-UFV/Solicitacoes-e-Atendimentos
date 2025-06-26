package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.com.ufv.inf311.solicitaedu.model.RegisterDTO;
import br.com.ufv.inf311.solicitaedu.model.RegisterInfo;
import br.com.ufv.inf311.solicitaedu.network.ApiClient;
import br.com.ufv.inf311.solicitaedu.network.RubeusEndpointsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryActivity extends Activity {

    LinearLayout cardContainer;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ActivityBuilder.build(this, "Histórico");

        cardContainer = findViewById(R.id.history_card_container);
        inflater = LayoutInflater.from(this);
//        for (int i = 10; i > 0; i--) {
//            View card;
//            if (i % 3 == 0) {
//                card = inflater.inflate(R.layout.concluded_history_card, cardContainer, false);
//                TextView title = card.findViewById(R.id.concluded_history_card_title);
//                title.setText("Solicitação #" + i);
//                TextView date = card.findViewById(R.id.concluded_history_card_date);
//                date.setText("03/06/2025");
//                // Card click
//                int finalI = i;
//                card.setOnClickListener(view -> onCardClick(finalI));
//            } else {
//                card = inflater.inflate(R.layout.waiting_history_card, cardContainer, false);
//                TextView title = card.findViewById(R.id.waiting_history_card_title);
//                title.setText("Solicitação #" + i);
//                TextView date = card.findViewById(R.id.waiting_history_card_date);
//                date.setText("03/06/2025");
//            }
//
//            cardContainer.addView(card);
//        }

        String[] titles = {
                "Histórico Escolar Graduação",
                "Certidão Atestado de Matrícula",
                "Certidão Atestado de que é aluno",
                "Alteração de Pré(co)-Requisito",
                "Trancamento de Matrícula",
                "Exame de suficiência"
        };

        for (int i = 0; i < titles.length; i++) {
            View card;
            String titleText = titles[i];
            int day = 20 - i;

            if ("Exame de suficiência".equals(titleText)) {
                card = inflater.inflate(R.layout.concluded_history_card, cardContainer, false);
                TextView title = card.findViewById(R.id.concluded_history_card_title);
                title.setText(titleText);
                TextView date = card.findViewById(R.id.concluded_history_card_date);
                date.setText("" + day + "/05/2025");
                int finalI = i;
                card.setOnClickListener(view -> onCardClick(finalI));
            } else {
                card = inflater.inflate(R.layout.waiting_history_card, cardContainer, false);
                TextView title = card.findViewById(R.id.waiting_history_card_title);
                title.setText(titleText);
                TextView date = card.findViewById(R.id.waiting_history_card_date);
                date.setText("" + day + "/05/2025");
            }

            cardContainer.addView(card);
        }

        fetchCards();
    }

    // Go to Home (MainActivity).
    public void openHomeActivity(View v) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    // Do nothing, since it's the current Activity.
    public void openHistoryActivity(View v) { }

    // Go to History Activity.
    public void openRequestActivity(View v) {
        Intent it = new Intent(this, RequestActivity.class);
        startActivity(it);
    }

    private void onCardClick(int idx) {
        Intent it = new Intent(this, HistoryDetail.class);
        startActivity(it);
    }

    private void fetchCards() {
        RubeusEndpointsAPI api = ApiClient.getClientRx().create(RubeusEndpointsAPI.class);
        Call<RegisterDTO> callGetRegisters = api.getRegisters(BuildConfig.API_ORIGIN, BuildConfig.API_KEY, "29");
        callGetRegisters.enqueue(new Callback<RegisterDTO>() {
            @Override
            public void onResponse(Call<RegisterDTO> call, Response<RegisterDTO> response) {
                if (response.isSuccessful()) {
                    RegisterInfo info = !response.body().getDados().isEmpty() ? response.body().getDados().get(0) : null;

                    if (info != null) {
                        Toast.makeText(getApplicationContext(), "processo: " + info.getProcessoNome(), Toast.LENGTH_LONG).show();
                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<RegisterDTO> call, Throwable t) {

            }
        });
    }
}