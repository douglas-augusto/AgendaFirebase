package com.example.douglas.agendafirebase.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.douglas.agendafirebase.R;
import com.example.douglas.agendafirebase.adapter.ContatosAdapter;
import com.example.douglas.agendafirebase.config.ConfiguracaoFirebase;
import com.example.douglas.agendafirebase.helper.Preferencias;
import com.example.douglas.agendafirebase.models.Contato;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AgendaFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerContatos;

    public AgendaFragment(){}

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerContatos);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerContatos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contatos = new ArrayList<>();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_agenda, container, false);

        listView = (ListView) view.findViewById(R.id.lv_contatos);

        /*adapter = new ArrayAdapter(
                getActivity(),
                R.layout.lista_locais,
                locais
        );*/


        adapter = new ContatosAdapter(getActivity(), contatos);
        listView.setAdapter(adapter);

        Preferencias preferencias = new Preferencias(getActivity());
        String usuarioLogado = preferencias.getIdentificador();
        firebase = ConfiguracaoFirebase.getFirebase()
                .child("contatos")
                .child(usuarioLogado);

        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar a lista
                contatos.clear();

                //Listar pacientes
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        return view;

    }


}
