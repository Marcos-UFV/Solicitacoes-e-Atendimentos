package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


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
        for (int i = 10; i > 0; i--) {
            View card;
            if (i % 3 == 0) {
                card = inflater.inflate(R.layout.concluded_history_card, cardContainer, false);
                TextView title = card.findViewById(R.id.concluded_history_card_title);
                title.setText("Solicitação #" + i);
                TextView date = card.findViewById(R.id.concluded_history_card_date);
                date.setText("03/06/2025");
            } else {
                card = inflater.inflate(R.layout.waiting_history_card, cardContainer, false);
                TextView title = card.findViewById(R.id.waiting_history_card_title);
                title.setText("Solicitação #" + i);
                TextView date = card.findViewById(R.id.waiting_history_card_date);
                date.setText("03/06/2025");
            }
            cardContainer.addView(card);
        }
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
}