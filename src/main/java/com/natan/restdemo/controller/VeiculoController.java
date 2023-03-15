package com.natan.restdemo.controller;

import com.natan.restdemo.entity.Veiculo;
import com.natan.restdemo.entity.dto.VeiculoDto;
import com.natan.restdemo.service.VeiculoService;
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

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    private final VeiculoService veiculoService;

    private final ModelMapper modelMapper;

    public VeiculoController(VeiculoService veiculoService, ModelMapper modelMapper) {
        this.veiculoService = veiculoService;
        this.modelMapper = modelMapper;
    }

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<VeiculoDto> buscarPorId(@PathVariable("id") Long id) {
        Veiculo veiculo = veiculoService.findById(id);
        return ResponseEntity.ok(convertToDto(veiculo));
    }

    @PostMapping
    public ResponseEntity<VeiculoDto> novo(@RequestBody VeiculoDto dto) {

        Veiculo novo = veiculoService.novo(convertToEntity(dto));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(novo.getCodigo())
                .toUri();

        return ResponseEntity.created(uri).body(convertToDto(novo));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualiza(@PathVariable("id") Long id, @RequestBody VeiculoDto dto) {

        if (!Objects.equals(id, dto.getId())) {
            throw new IllegalArgumentException("Ids diferentes");
        }

        veiculoService.update(id, convertToEntity(dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id, @RequestBody VeiculoDto dto) {
        veiculoService.delete(id);
        return ResponseEntity.ok("Deletado");
    }

    private VeiculoDto convertToDto(Veiculo veiculo) throws ParseException {
        return modelMapper.map(veiculo, VeiculoDto.class);
    }

    private Veiculo convertToEntity(VeiculoDto dto) {
        return modelMapper.map(dto, Veiculo.class);
    }
}

