package com.reciclaveltcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reciclaveltcc.models.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
	
	public Material findByNome(String nome);

}
