package com.example.douglas.agendafirebase.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.douglas.agendafirebase.R;
import com.example.douglas.agendafirebase.models.Contato;

import java.util.ArrayList;

public class ContatosAdapter extends ArrayAdapter<Contato> {

    private ArrayList<Contato> contatos;
    private Context context;

    public ContatosAdapter(@NonNull Context c, @NonNull ArrayList<Contato> objects) {
        super(c,0, objects);
        this.contatos = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(contatos != null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_contatos, parent, false);

            TextView nomeContato = (TextView) view.findViewById(R.id.tv_nome);
            TextView telefoneContato = (TextView) view.findViewById(R.id.tv_telefone);

            Contato contato = contatos.get(position);
            nomeContato.setText(contato.getNome());
            telefoneContato.setText("Fone: "+contato.getTelefone());

        }

        return view;

    }

}
