package com.example.douglas.agendafirebase.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.douglas.agendafirebase.R;
import com.example.douglas.agendafirebase.config.ConfiguracaoFirebase;
import com.example.douglas.agendafirebase.helper.Base64Custom;
import com.example.douglas.agendafirebase.models.Contato;
import com.google.firebase.auth.FirebaseAuth;

public class NovoContatoActivity extends AppCompatActivity {

    private EditText campoNomeContato;
    private EditText campoEmailContato;
    private EditText campoTelefoneContato;
    private Button botaoAdicionarContato;
    private FirebaseAuth usuarioFirebase;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_contato);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        setTitle("Novo contato");

        campoNomeContato = findViewById(R.id.campoNomeContato);
        campoEmailContato = findViewById(R.id.campoEmailContato);
        campoTelefoneContato = findViewById(R.id.campoTelefoneContato);
        botaoAdicionarContato = findViewById(R.id.botaoAdicionarContato);

        botaoAdicionarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contato = new Contato();
                contato.setNome(campoNomeContato.getText().toString());
                contato.setEmail(campoEmailContato.getText().toString());
                contato.setTelefone(campoTelefoneContato.getText().toString());

                String usuarioAtual = Base64Custom.codificarBase64(usuarioFirebase.getCurrentUser().getEmail());
                contato.setId(usuarioAtual);
                contato.salvarContato();

                Toast.makeText(NovoContatoActivity.this, "Cadastrando!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NovoContatoActivity.this, MainActivity.class );
                startActivity(intent);
                finish();

            }
        });
    }
}
