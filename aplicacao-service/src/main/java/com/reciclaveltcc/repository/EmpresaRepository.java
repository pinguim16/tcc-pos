package com.reciclaveltcc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reciclaveltcc.models.Cidade;
import com.reciclaveltcc.models.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	
	List<Empresa> findByCidade(Cidade cidade);

}
