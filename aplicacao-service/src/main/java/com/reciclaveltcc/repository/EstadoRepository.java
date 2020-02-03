package com.reciclaveltcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reciclaveltcc.models.Estado;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

	Estado findByNome(String nome);

	List<Estado> findAllByOrderByNome();

}
