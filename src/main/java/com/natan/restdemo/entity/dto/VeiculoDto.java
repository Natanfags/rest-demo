package com.natan.restdemo.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Veiculo", description = "Representa um veiculo")
public class VeiculoDto {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(value = "nome do veiculo", example = "Carro")
    private String nome;

    @ApiModelProperty(value = "Codigo do veiculo", example = "555")
    private long codigo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
