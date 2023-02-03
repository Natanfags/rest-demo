package com.natan.restdemo.service;

import com.natan.restdemo.entity.Veiculo;
import com.natan.restdemo.repository.VeiculoRepository;
import org.assertj.core.util.Lists;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository repository) {
        veiculoRepository = repository;
    }

    public List<Veiculo> findAll() {
        return Lists.newArrayList(veiculoRepository.findAll());
    }

    public Veiculo findById(Long id) {
        return veiculoRepository.findById(id).orElseThrow();
    }

    public Veiculo novo(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    public Veiculo update(Long id, Veiculo veiculo) {

        Optional<Veiculo> veiculoToUpdate = veiculoRepository.findById(id);

        if (veiculoToUpdate.isEmpty()) {
            throw new IllegalArgumentException("Veiculo com id: " + id + " não existe");
        }

        return veiculoRepository.save(veiculo);
    }

    public void delete(Long id) {
        try {

            veiculoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EmptyResultDataAccessException(1);
        }
    }

}
