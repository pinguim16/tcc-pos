package com.reciclaveltcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reciclaveltcc.models.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

	public Estado findByNome(String nome);

}
