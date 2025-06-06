package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import android.content.res.ColorStateList;


public class ActivityBuilder {
    public static void build(Activity activity, String activityName) {
        // Sets activity name on header.
        TextView text = (TextView) activity.findViewById(R.id.header_text);
        text.setText(activityName);

        switch(activity.getClass().getName()) {
            case "br.com.ufv.inf311.solicitaedu.MainActivity":
                MaterialButton homeBttn = (MaterialButton) activity.findViewById(R.id.homeFooterBttn);
                homeBttn.setBackgroundColor(homeBttn.getResources().getColor(R.color.white));
                homeBttn.setIconTint(ColorStateList.valueOf(homeBttn.getResources().getColor(R.color.SOLICITA_blue)));
                break;

            case "br.com.ufv.inf311.solicitaedu.HistoryActivity":
                MaterialButton histBttn = (MaterialButton) activity.findViewById(R.id.historyFooterBttn);
                histBttn.setBackgroundColor(histBttn.getResources().getColor(R.color.white));
                histBttn.setIconTint(ColorStateList.valueOf(histBttn.getResources().getColor(R.color.SOLICITA_blue)));
                break;

            case "br.com.ufv.inf311.solicitaedu.RequestActivity":
                MaterialButton reqBttn = (MaterialButton) activity.findViewById(R.id.newRequestFooterBttn);
                reqBttn.setBackgroundColor(reqBttn.getResources().getColor(R.color.white));
                reqBttn.setIconTint(ColorStateList.valueOf(reqBttn.getResources().getColor(R.color.SOLICITA_blue)));
                break;
        }
    }
}
