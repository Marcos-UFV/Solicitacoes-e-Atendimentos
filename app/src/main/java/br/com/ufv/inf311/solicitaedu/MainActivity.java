package br.com.ufv.inf311.solicitaedu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import java.util.Calendar;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityBuilder.build(this, "Home");
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

    // === FOOTER BUTTONS ===
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