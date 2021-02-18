package com.guterres.people.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String Nome;
    private String Endereco;
    private String Telefone;


    @ManyToMany
    private Set<Profissional> Profissional;

    public String getEndereco() {
        return this.Endereco;
    }

    public String getNome() {
        return this.Nome;
    }

    public Integer getId() {
        return this.Id == null ? 0: this.Id;
    }

    public String getTelefone() {
        return this.Telefone;
    }


    @JsonIgnoreProperties("estabelecimento")
    public Set<Profissional> getProfissional() {
        return this.Profissional;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setTelefone(String telefone) {
        this.Telefone = telefone;
    }

    public void setProfissional(Set<Profissional> profissional) {
        this.Profissional = profissional;
    }
}
