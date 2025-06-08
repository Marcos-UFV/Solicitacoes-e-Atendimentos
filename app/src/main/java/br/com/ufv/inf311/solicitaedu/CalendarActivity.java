package br.com.ufv.inf311.solicitaedu;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;


public class CalendarActivity extends Activity {

    LinearLayout cardContainer;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ActivityBuilder.build(this, "Agenda");

        String[] eventos = {"Reunião com Conselho de Ética",
                            "Uso do Cluster para Pesquisa",
                            "Reunião com Departamento de Relações Internacionais",
                            "Exame de Suficiência em IN311"};
        String[] days = {"06", "07", "09", "10"};
        String[] begs = {"10", "00", "14", "08"};
        String[] ends = {"11", "23", "15", "10"};

        cardContainer = findViewById(R.id.calendar_card_container);
        inflater = LayoutInflater.from(this);
        for (int i = 0, j = 0; i < 4; i++, j += 2) {
            View card;

            card = inflater.inflate(R.layout.calendar_card, cardContainer, false);
            TextView title = card.findViewById(R.id.calendar_card_title);
            title.setText(eventos[i]);
            TextView date = card.findViewById(R.id.calendar_card_date);
            TextView loc = card.findViewById(R.id.calendar_card_loc);

            int day = (i + 9 + j);
            date.setText(days[i] + "/06/2025 - de " + begs[i] + ":00 até " +  ends[i] + ":00");

            loc.setText("Departamento de Informática - CCE 403B ");

            cardContainer.addView(card);
        }
    }

    public void saveOnGoogleCalendar(View v) {
        MaterialButton bttn = (MaterialButton) v;
        ViewParent card = bttn.getParent();

        TextView date = ((RelativeLayout) card).findViewById(R.id.calendar_card_date);
        TextView title = ((RelativeLayout) card).findViewById(R.id.calendar_card_title);
        TextView loc = ((RelativeLayout) card).findViewById(R.id.calendar_card_loc);
        CalendarEvent e = parseDate(date.getText().toString());

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(e.year, e.month, e.day, e.startHour, e.startMin);
        long startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.set(e.year, e.month, e.day, e.endHour, e.endMin);
        long endMillis = endTime.getTimeInMillis();

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, loc.getText().toString())
                .putExtra(CalendarContract.Events.TITLE, title.getText().toString())
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

        startActivity(intent);
    }

    private CalendarEvent parseDate(String date) {
        String[] parts = date.split(" - de ");
        CalendarEvent calendarEvent = null;

        if (parts.length == 2) {
            String datePart = parts[0]; // "06/06/2025"
            String timeRangePart = parts[1]; // "14:00 até 18:30"

            // Split the time range
            String[] times = timeRangePart.split(" até ");
            if (times.length == 2) {
                String startTimeStr = times[0]; // "14:00"
                String endTimeStr = times[1];   // "18:30"

                calendarEvent = new CalendarEvent(datePart, startTimeStr, endTimeStr);
            }
        }

        return calendarEvent;
    }

    public void returnToLastActivity(View v) {
        Intent it = new Intent(this, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(it);
    }
}