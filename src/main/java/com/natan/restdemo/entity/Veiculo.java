package com.natan.restdemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Veiculo {

    @Id
    private Long id;

    private String nome;

    private long codigo;
    private String test;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
