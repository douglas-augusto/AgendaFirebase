package com.example.douglas.agendafirebase.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.douglas.agendafirebase.R;
import com.example.douglas.agendafirebase.adapter.ContatosAdapter;
import com.example.douglas.agendafirebase.config.ConfiguracaoFirebase;
import com.example.douglas.agendafirebase.fragment.AgendaFragment;
import com.example.douglas.agendafirebase.helper.Preferencias;
import com.example.douglas.agendafirebase.models.Contato;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth usuarioFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new AgendaFragment())
                .commit();

    }

    private void deslogarUsuario(){
        usuarioFirebase.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.item_sair :
                deslogarUsuario();
                return true;
            case R.id.item_add :
                Intent intent = new Intent(MainActivity.this, NovoContatoActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
