package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RequestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        ActivityBuilder.build(this, "Nova Solicitação");

    }

    // Go to Home (MainActivity).
    public void openHomeActivity(View v) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    // Go to History Activity.
    public void openHistoryActivity(View v) {
        Intent it = new Intent(this, HistoryActivity.class);
        startActivity(it);
    }

    // Do nothing, since it's the current Activity.
    public void openRequestActivity(View v) {

    }
}