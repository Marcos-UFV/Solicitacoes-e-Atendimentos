package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RequestActivity extends Activity {
    EditText title;
    EditText document;
    EditText description;
    String[] titles = {"Título 1","Título 2","Título 3","Título 4","Título 5"};
    String[] documents = {"Documento 1","Documento 2","Documento 3","Documento 4","Documento 5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ActivityBuilder.build(this, "Nova Solicitação");

        Button btnTitle = findViewById(R.id.btnTitle);
        Button btnDocument = findViewById(R.id.btnDocument);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        title = findViewById(R.id.tTitle);
        document = findViewById(R.id.tDocument);
        description = findViewById(R.id.tDescription);

        btnTitle.setOnClickListener(selectTitle());
        btnDocument.setOnClickListener(selectDocument());
        btnSubmit.setOnClickListener(submit());
    }
    public View.OnClickListener selectTitle(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RequestActivity.this);
                builder.setTitle("Selecione um Título");
                builder.setItems(titles, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        title.setText(titles[i]);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
    }

    public View.OnClickListener selectDocument(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RequestActivity.this);
                builder.setTitle("Selecione um documento");
                builder.setItems(documents, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        document.setText(documents[i]);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
    }

    public View.OnClickListener submit(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgTitle = title.getText().toString();
                String msgDocument = document.getText().toString();
                String msgDescription  = document.getText().toString();
                String msg = String.format("Title: %s\nDocument: %s\nDescription: %s",msgTitle,msgDocument,msgDescription);
                Toast.makeText(RequestActivity.this,msg,Toast.LENGTH_LONG).show();
            }
        };
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