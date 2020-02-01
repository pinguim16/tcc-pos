package com.reciclaveltcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reciclaveltcc.models.CategoriaMaterial;

@Repository
public interface CategoriaMaterialRepository extends JpaRepository<CategoriaMaterial, Long> {
	
	public CategoriaMaterial findByNome(String nome);

}
