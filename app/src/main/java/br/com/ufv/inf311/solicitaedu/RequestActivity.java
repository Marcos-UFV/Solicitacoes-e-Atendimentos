package br.com.ufv.inf311.solicitaedu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RequestActivity extends Activity {
    EditText type;
    EditText description;
    String[] types = {"Histórico Escolar Graduação","Certidão de Matrícula","Certidão de que é aluno","Solicitação de Exame de Suficiência","Alteração de Pré(co)-Requisito","Trancamento de Matrícula"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ActivityBuilder.build(this, "Nova Solicitação");

        Button btnType = findViewById(R.id.btnType);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        type = findViewById(R.id.tType);
        description = findViewById(R.id.tDescription);

        btnType.setOnClickListener(selectTitle());
        btnSubmit.setOnClickListener(submit());
    }
    public View.OnClickListener selectTitle(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RequestActivity.this);
                builder.setTitle("Selecione o Tipo");
                builder.setItems(types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        type.setText(types[i]);
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
                String msgTitle = type.getText().toString();
                String msgDescription  = description.getText().toString();
                String msg = String.format("Title: %s\nDescription: %s",msgTitle,msgDescription);
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