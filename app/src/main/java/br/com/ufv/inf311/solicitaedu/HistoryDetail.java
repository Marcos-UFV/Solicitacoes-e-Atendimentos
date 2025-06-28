package br.com.ufv.inf311.solicitaedu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.List;

import br.com.ufv.inf311.solicitaedu.model.RegisterInfo;

public class HistoryDetail extends AppCompatActivity {

    RegisterInfo register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        Intent it = getIntent();
        register = (RegisterInfo) it.getSerializableExtra("register");

        TextView title = (TextView) findViewById(R.id.detail_header_text);
        title.setText(register.getProcessoNome());

        buildDetails();
        buildAttachments();
    }

    private void buildDetails() {
        TextView detailsTxt = (TextView) findViewById(R.id.history_detail_details);
        if(register.getDetalhes() != null)
            detailsTxt.setText(register.getDetalhes());
    }

    private void buildAttachments() {
        LinearLayout attachmentsContainer = findViewById(R.id.attachments_container);
        LayoutInflater inflater = LayoutInflater.from(this);

        attachmentsContainer.removeAllViews();

        List<String> files = register.getArquivos();
        if(register.getArquivos() != null) {
            for (int i = 0; i < files.size(); i++) {
                View itemView = inflater.inflate(R.layout.item_attachment, attachmentsContainer, false);

                TextView name = itemView.findViewById(R.id.attachment_name);
                name.setText("Arquivo_" + (i + 1));

                int finalI = i;
                name.setOnClickListener(v -> {
                    Uri fileUri = Uri.parse(files.get(finalI));
                    Intent it = new Intent(Intent.ACTION_VIEW, fileUri);
                    startActivity(it);
                });

                attachmentsContainer.addView(itemView);
            }
        }
    }

    public void goBack(View view) {
        finish();
    }
}