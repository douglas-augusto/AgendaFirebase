package com.example.douglas.agendafirebase.models;

import com.example.douglas.agendafirebase.config.ConfiguracaoFirebase;
import com.example.douglas.agendafirebase.helper.Base64Custom;
import com.google.firebase.database.DatabaseReference;

public class Contato {

    private String id;
    private String nome;
    private String telefone;
    private String email;

    public Contato() {
    }

    public void salvarContato(){

        String emailCodificado = Base64Custom.codificarBase64(getEmail());

        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("contatos").child(getId()).child(emailCodificado).setValue(this);

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
