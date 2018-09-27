package com.example.douglas.agendafirebase.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.douglas.agendafirebase.R;
import com.example.douglas.agendafirebase.config.ConfiguracaoFirebase;
import com.example.douglas.agendafirebase.helper.Base64Custom;
import com.example.douglas.agendafirebase.models.Contato;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ContatoActivity extends AppCompatActivity {

    private EditText campoNome;
    private EditText campoEmail;
    private EditText campoTelefone;
    private Button botaoApagar;
    private Button botaoAlterar;
    private String nomeContato;
    private String telefoneContato;
    private String emailContato;
    private DatabaseReference reference;
    private FirebaseAuth usuarioFirebase;
    private AlertDialog alerta;
    private Contato contato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        reference = ConfiguracaoFirebase.getFirebase();
        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        campoEmail = findViewById(R.id.campoEmailContatoExibir);
        campoNome = findViewById(R.id.campoNomeContatoExibir);
        campoTelefone = findViewById(R.id.campoTelefoneContatoExibir);
        botaoAlterar = findViewById(R.id.botaoAlterarContato);
        botaoApagar = findViewById(R.id.botaoApagarContato);

        setTitle("Dados do contato");

        Bundle extra = getIntent().getExtras();

        if(extra != null){
            nomeContato = extra.getString("nome");
            telefoneContato = extra.getString("telefone");
            emailContato = extra.getString("email");
        }

        campoNome.setText(nomeContato);
        campoTelefone.setText(telefoneContato);
        campoEmail.setText(emailContato);

        botaoAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ContatoActivity.this);
                builder.setTitle("Confirmação");
                builder.setMessage("Deseja mesmo alterar esse contato?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        String pacienteAtual = Base64Custom.codificarBase64(emailContato);
                        String usuarioAtual = Base64Custom.codificarBase64(usuarioFirebase.getCurrentUser().getEmail());
                        reference.child("contatos").child(usuarioAtual).child(pacienteAtual).removeValue();

                        contato = new Contato();
                        contato.setNome(campoNome.getText().toString());
                        contato.setEmail(campoEmail.getText().toString());
                        contato.setTelefone(campoTelefone.getText().toString());
                        contato.setId(usuarioAtual);
                        contato.salvarContato();
                        finish();
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

                alerta = builder.create();
                alerta.show();

            }
        });

        botaoApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ContatoActivity.this);
                builder.setTitle("Confirmação");
                builder.setMessage("Deseja mesmo apagar esse contato?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        String pacienteAtual = Base64Custom.codificarBase64(emailContato);
                        String usuarioAtual = Base64Custom.codificarBase64(usuarioFirebase.getCurrentUser().getEmail());
                        reference.child("contatos").child(usuarioAtual).child(pacienteAtual).removeValue();
                        finish();
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

                alerta = builder.create();
                alerta.show();

            }
        });
    }
}
