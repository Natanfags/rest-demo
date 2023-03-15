package com.natan.restdemo.service;

import com.natan.restdemo.entity.Veiculo;
import com.natan.restdemo.repository.VeiculoRepository;
import org.assertj.core.util.Lists;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository repository) {
        veiculoRepository = repository;
    }

    public List<Veiculo> findAllPaginado(int page, int size, String sortDir, String sort) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);

        Page<Veiculo> all = veiculoRepository.findAll(pageRequest);
        return all.getContent();
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
            throw new EmptyResultDataAccessException("Veiculo não encontrado", 1);
        }
    }

}
