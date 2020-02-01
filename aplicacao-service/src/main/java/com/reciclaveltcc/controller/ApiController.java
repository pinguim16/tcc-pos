package com.reciclaveltcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaunt.NotFound;
import com.reciclaveltcc.CrawlingReciclaveis;
import com.reciclaveltcc.models.Cidade;
import com.reciclaveltcc.models.Empresa;
import com.reciclaveltcc.models.Estado;
import com.reciclaveltcc.repository.CidadeRepository;
import com.reciclaveltcc.repository.EmpresaRepository;
import com.reciclaveltcc.repository.EstadoRepository;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private CrawlingReciclaveis crawlingReciclaveis;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@GetMapping("/banco/coop/{estado}")
	public void crawCoop(@PathVariable Integer estado) throws NotFound {
		this.crawlingReciclaveis.crawCoop(estado);
	}
	
	@GetMapping("/banco/suc/{estado}")
	public void crawSuc(@PathVariable Integer estado) throws NotFound {
		this.crawlingReciclaveis.crawSuc(estado);
	}
	
	@GetMapping("/banco/rec/{estado}")
	public void crawRec(@PathVariable Integer estado) throws NotFound {
		this.crawlingReciclaveis.crawRec(estado);
	}
	
	@GetMapping("/estados")
	public List<Estado> findAllEstados(){
		return this.estadoRepository.findAll();
	}
	
	@GetMapping("/cidades/{idEstado}")
	public List<Cidade> findByEstado(@PathVariable Long idEstado){
		Estado estado = this.estadoRepository.findById(idEstado).get();
		return this.cidadeRepository.findByEstado(estado);
	}
	
	@GetMapping("/empresas/{idCidade}")
	public List<Empresa> findAllEmpresas(@PathVariable Long idCidade){
		Cidade cidade = this.cidadeRepository.findById(idCidade).get();
		return this.empresaRepository.findByCidade(cidade);
	}
}
