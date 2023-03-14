package com.natan.restdemo.controller;

import com.natan.restdemo.entity.Veiculo;
import com.natan.restdemo.service.VeiculoService;
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

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> buscarTodos() {
        List<Veiculo> todos = veiculoService.findAll();

        return ResponseEntity.ok(todos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable("id") Long id) {

        return ResponseEntity.ok(veiculoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Veiculo> novo(@RequestBody Veiculo veiculo) {

        Veiculo novo = veiculoService.novo(veiculo);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(novo.getCodigo())
                .toUri();

        return ResponseEntity.created(uri).body(novo);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus
    public ResponseEntity<Veiculo> atualiza(@PathVariable("id") Long id, @RequestBody Veiculo veiculo) {
        return ResponseEntity.ok(veiculoService.update(id, veiculo));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        veiculoService.delete(id);
        return ResponseEntity.ok("Deletado");
    }

}

