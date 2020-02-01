package com.reciclaveltcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reciclaveltcc.models.Cidade;
import com.reciclaveltcc.models.Estado;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>  {
	
	Cidade findByNome(String nome);
	
	List<Cidade> findByEstado(Estado estado);

}
