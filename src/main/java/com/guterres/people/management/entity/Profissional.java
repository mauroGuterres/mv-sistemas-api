package com.guterres.people.management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String Nome;
    private String Celular;
    private String TelefoneResidencial;
    private String Endereco;
    private String Funcao;


    @ManyToMany
    private Set<Estabelecimento> Estabelecimento;

    public Integer getId() {
        return this.Id;
    }

    public String getNome() {
        return this.Nome;
    }

    public String getCelular() {
        return this.Celular;
    }

    public String getEndereco(){
        return this.Endereco;
    }

    public String getFuncao(){
        return this.Funcao;
    }

    public String getTelefoneResidencial() {
        return this.TelefoneResidencial;
    }

    public void setEndereco(String Endereco){
        this.Endereco = Endereco;
    }

    public void setFuncao(String funcao) {
        this.Funcao = funcao;
    }

    @JsonIgnoreProperties("profissional")
    public Set<Estabelecimento> getEstabelecimento() {
        return this.Estabelecimento;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public void setCelular(String celular) {
        this.Celular = celular;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public void setTelefoneResidencial(String TelefoneResidencial) {
        this.TelefoneResidencial = TelefoneResidencial;
    }

    public void setEstabelecimento(Set<Estabelecimento> estabelecimento) {
        this.Estabelecimento = estabelecimento;
    }
}
