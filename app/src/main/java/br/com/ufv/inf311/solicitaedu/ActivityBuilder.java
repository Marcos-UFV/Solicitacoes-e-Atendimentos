package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.widget.TextView;

public class ActivityBuilder {
    public static void build(Activity activity, String activityName) {
        // Sets activity name on header.
        TextView text = (TextView) activity.findViewById(R.id.header_text);
        text.setText(activityName);
    }
}
