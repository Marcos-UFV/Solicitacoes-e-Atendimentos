package br.com.ufv.inf311.solicitaedu;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class CalendarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ActivityBuilder.build(this, "Agenda");
    }

    public void returnToLastActivity(View v) {
        Intent it = new Intent(this, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(it);
    }
}