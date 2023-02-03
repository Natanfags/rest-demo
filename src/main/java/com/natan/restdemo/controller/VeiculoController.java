package com.natan.restdemo.controller;

import com.natan.restdemo.entity.Veiculo;
import com.natan.restdemo.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

import java.util.List;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

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

        return ResponseEntity.ok(veiculoService.novo(veiculo));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus
    public ResponseEntity<Veiculo> atualiza(@PathVariable("id") Long id, @RequestBody Veiculo veiculo) {
        return ResponseEntity.ok(veiculoService.update(id, veiculo));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {

        try {
            veiculoService.delete(id);
        } catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity<>("não encontrado veiculo com id: " + id, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("veiuculo id: " + id + " deletado com sucesso ", HttpStatus.OK);
    }

}

