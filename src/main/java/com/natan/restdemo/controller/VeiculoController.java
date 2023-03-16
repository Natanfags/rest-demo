package com.natan.restdemo.controller;

import com.natan.restdemo.entity.Veiculo;
import com.natan.restdemo.entity.dto.VeiculoDto;
import com.natan.restdemo.service.VeiculoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Api(tags = {"Veiculo"})
@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    private final VeiculoService veiculoService;

    private final ModelMapper modelMapper;

    public VeiculoController(VeiculoService veiculoService, ModelMapper modelMapper) {
        this.veiculoService = veiculoService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation("Listar Veiculos")
    @GetMapping
    public ResponseEntity<List<VeiculoDto>> buscarTodos(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("sortDir") String sortDir,
            @PathVariable("sort") String sort) {
        List<Veiculo> todos = veiculoService.findAllPaginado(page, size, sortDir, sort);

        return ResponseEntity.ok(
                todos.stream()
                        .map(this::convertToDto)
                        .toList());
    }

    @ApiOperation("Busca veiculo por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<VeiculoDto> buscarPorId(
            @ApiParam(value = "ID do veiculo", example = "1")
            @PathVariable("id") Long id) {
        Veiculo veiculo = veiculoService.findById(id);
        return ResponseEntity.ok(convertToDto(veiculo));
    }

    @ApiOperation("Cria um novo Veiculo")
    @PostMapping
    public ResponseEntity<VeiculoDto> novo(
            @ApiParam(name = "corpo", value = "representação de um novo Veiculo")
            @RequestBody VeiculoDto dto) {

        Veiculo novo = veiculoService.novo(convertToEntity(dto));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(novo.getCodigo())
                .toUri();

        return ResponseEntity.created(uri).body(convertToDto(novo));
    }

    @ApiOperation("Atualiza veiculo por ID")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualiza(
            @ApiParam(value = "ID do veiculo", example = "1")
            @PathVariable("id") Long id,
            @ApiParam(name = "corpo", value = "representação de um veiculo com novos dados")
            @RequestBody VeiculoDto dto) {

        if (!Objects.equals(id, dto.getId())) {
            throw new IllegalArgumentException("Ids diferentes");
        }

        veiculoService.update(id, convertToEntity(dto));
    }

    @ApiOperation("Exclui veiculo por ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @ApiParam(value = "ID do veiculo", example = "1")
            @PathVariable("id") Long id) {
        veiculoService.delete(id);
    }

    private VeiculoDto convertToDto(Veiculo veiculo) throws ParseException {
        return modelMapper.map(veiculo, VeiculoDto.class);
    }

    private Veiculo convertToEntity(VeiculoDto dto) {
        return modelMapper.map(dto, Veiculo.class);
    }
}

