package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.ufv.inf311.solicitaedu.model.Contact;
import br.com.ufv.inf311.solicitaedu.model.RegisterDTO;
import br.com.ufv.inf311.solicitaedu.model.RegisterInfo;
import br.com.ufv.inf311.solicitaedu.network.ApiClient;
import br.com.ufv.inf311.solicitaedu.network.RubeusEndpointsAPI;
import br.com.ufv.inf311.solicitaedu.utils.DataBaseSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryActivity extends Activity {
    private List<RegisterInfo> registers;
    DataBaseSingleton db;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ActivityBuilder.build(this, "Histórico");

        db = DataBaseSingleton.getInstance(this);

        contact = (Contact) getIntent().getSerializableExtra("contact");

        fetchRegisters();
    }

    private void fetchRegisters() {
        RubeusEndpointsAPI api = ApiClient.getClientRx().create(RubeusEndpointsAPI.class);
        Call<RegisterDTO> callGetRegisters = api.getRegisters(BuildConfig.API_ORIGIN, BuildConfig.API_KEY, ""+contact.getId());
        callGetRegisters.enqueue(new Callback<RegisterDTO>() {
            @Override
            public void onResponse(Call<RegisterDTO> call, Response<RegisterDTO> response) {
                if (response.isSuccessful()) {
                    if (response.body().getDados() != null) {
                        registers = !response.body().getDados().isEmpty() ? response.body().getDados() : null;

                        int higherID = 0;
                        for(RegisterInfo reg : registers) {
                            if(reg.getEtapaNome().equals("Resolvida"))
                                higherID = Math.max(higherID, Integer.parseInt(reg.getId()));
                        }

                        ContentValues v = new ContentValues();
                        v.put("lastSeenID", higherID);
                        db.update("Notification", v, "id = 1");

                        createCards("", false);
                    } else {
                        setErrorMessageVisibility(true);
                    }
                } else {
                    setErrorMessageVisibility(true);
                }
            }

            @Override
            public void onFailure(Call<RegisterDTO> call, Throwable t) {
                setErrorMessageVisibility(true);
            }
        });
    }

    private void createCards(String startDate, boolean showOnlyResolved) {
        LinearLayout cardContainer = findViewById(R.id.history_card_container);
        LayoutInflater inflater = LayoutInflater.from(this);

        if (registers == null || registers.isEmpty()) {
            setErrorMessageVisibility(true);
            return;
        }

        int startDateVal = startDate.isEmpty() ? -1 : dateToInt(startDate);

        cardContainer.removeAllViews();
        setErrorMessageVisibility(false);

        for (int i = 0; i < registers.size(); i++) {
            View card;
            String dateStr = registers.get(i).getMomento().split(" ")[0].replace("-", "/");
            String yar = dateStr.split("/")[0];
            String mon = dateStr.split("/")[1];
            String day = dateStr.split("/")[2];
            dateStr = day + "/" + mon + "/" + yar;

            int dateStrInt = dateToInt(dateStr);
            Log.i("DATE_STR", ""+dateStrInt);
            Log.i("START_DATE", ""+startDateVal);

            if (dateToInt(dateStr) < startDateVal) continue;

            if (registers.get(i).getEtapaNome().equals("Resolvida")) {
                card = inflater.inflate(R.layout.concluded_history_card, cardContainer, false);
                TextView title = card.findViewById(R.id.concluded_history_card_title);
                title.setText(registers.get(i).getProcessoNome());
                TextView date = card.findViewById(R.id.concluded_history_card_date);
                date.setText(dateStr);

                // Card click
                int finalI = i;
                card.setOnClickListener(view -> onCardClick(finalI));
                cardContainer.addView(card);
            } else if (!showOnlyResolved) {
                card = inflater.inflate(R.layout.waiting_history_card, cardContainer, false);
                TextView title = card.findViewById(R.id.waiting_history_card_title);
                title.setText(registers.get(i).getProcessoNome());
                TextView date = card.findViewById(R.id.waiting_history_card_date);
                date.setText(dateStr);
                cardContainer.addView(card);
            }
        }

    }

    private void onCardClick(int idx) {
        Intent it = new Intent(this, HistoryDetail.class);
        it.putExtra("register", registers.get(idx));
        startActivity(it);
    }

    private void setErrorMessageVisibility(boolean visible) {
        TextView emptyMessage = findViewById(R.id.history_empty_message);
        emptyMessage.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    // Go to Home (MainActivity).
    public void openHomeActivity(View v) {
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("contact", contact);
        startActivity(it);
    }

    // Do nothing, since it's the current Activity.
    public void openHistoryActivity(View v) { }

    // Go to Request Activity.
    public void openRequestActivity(View v) {
        Intent it = new Intent(this, RequestActivity.class);
        it.putExtra("contact", contact);
        startActivity(it);
    }

    public void showFilterDialog(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_filters, null);

        EditText startDate = dialogView.findViewById(R.id.start_date);
        Switch statusSwitch = dialogView.findViewById(R.id.status_switch);

        startDate.setOnClickListener(v -> showDatePickerDialog(startDate));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filtrar registros")
                .setView(dialogView)
                .setPositiveButton("Aplicar", (dialog, which) -> {
                    String date = startDate.getText().toString();
                    boolean showOnlyResolved = statusSwitch.isChecked();
                    createCards(date, showOnlyResolved);

                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void showDatePickerDialog(EditText target) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    String date = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month1 + 1, year1);
                    target.setText(date);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private int dateToInt(String date) {
        int day = Integer.parseInt(date.split("/")[0]);
        int mon = Integer.parseInt(date.split("/")[1]);
        int yar = Integer.parseInt(date.split("/")[2]);

        return yar * 10000 + mon * 100 + day;
    }

}