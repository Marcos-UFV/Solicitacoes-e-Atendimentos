package br.com.ufv.inf311.solicitaedu;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityBuilder.build(this, "Home");
    }

    // Do nothing, since it's the current Activity.
    public void openHomeActivity(View v) {

    }

    // Go to History Activity.
    public void openHistoryActivity(View v) {
        Intent it = new Intent(this, HistoryActivity.class);
        startActivity(it);
    }

    // Go to History Activity.
    public void openRequestActivity(View v) {
        Intent it = new Intent(this, RequestActivity.class);
        startActivity(it);
    }
}