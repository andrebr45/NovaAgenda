package com.example.novaagenda.model;

import java.io.Serializable;
//implementar Serializable para tranferir dados para byte e depois para objeto
public class Pessoa implements Serializable {
    String nome;
    String telefone;
    String email;
    //construtor vazio
    public Pessoa() {

    }
    //getter e setters
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


}
