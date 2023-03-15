package com.natan.restdemo.repository;

import com.natan.restdemo.entity.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends CrudRepository<Veiculo, Long>, PagingAndSortingRepository<Veiculo, Long> {

    @Override
    Page<Veiculo> findAll(Pageable pageReq);

}
