package com.natan.restdemo.controller.openapi;

import com.natan.restdemo.entity.dto.VeiculoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Api(tags = {"Veiculo"})
public interface VeiculoControllerOpenApi {

    @ApiOperation("Listar Veiculos")
    @GetMapping
    ResponseEntity<List<VeiculoDto>> buscarTodos(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("sortDir") String sortDir,
            @PathVariable("sort") String sort);

    @ApiOperation("Busca veiculo por ID")
    @GetMapping(value = "/{id}")
    ResponseEntity<VeiculoDto> buscarPorId(
            @ApiParam(value = "ID do veiculo", example = "1")
            @PathVariable("id") Long id);

    @ApiOperation("Cria um novo Veiculo")
    @PostMapping
    ResponseEntity<VeiculoDto> novo(
            @ApiParam(name = "corpo", value = "representação de um novo Veiculo")
            @RequestBody VeiculoDto dto);

    @ApiOperation("Atualiza veiculo por ID")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    void atualiza(
            @ApiParam(value = "ID do veiculo", example = "1")
            @PathVariable("id") Long id,
            @ApiParam(name = "corpo", value = "representação de um veiculo com novos dados")
            @RequestBody VeiculoDto dto);

    @ApiOperation("Exclui veiculo por ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @ApiParam(value = "ID do veiculo", example = "1")
            @PathVariable("id") Long id);
}
