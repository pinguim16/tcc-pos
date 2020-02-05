package com.reciclaveltcc.controller;

import java.util.List;

import com.reciclaveltcc.models.*;
import com.reciclaveltcc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jaunt.NotFound;
import com.reciclaveltcc.CrawlingReciclaveis;
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

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/banco/coop/{estado}")
	public String crawCoop(@PathVariable Integer estado) throws NotFound {
		this.crawlingReciclaveis.crawCoop(estado);
		return "Feito";
	}
	
	@GetMapping("/banco/suc/{estado}")
	public String crawSuc(@PathVariable Integer estado) throws NotFound {
		this.crawlingReciclaveis.crawSuc(estado);
		return "Feito";
	}
	
	@GetMapping("/banco/rec/{estado}")
	public void crawRec(@PathVariable Integer estado) throws NotFound {
		this.crawlingReciclaveis.crawRec(estado);
	}
	
	@GetMapping("/estados")
	public List<Estado> findAllEstados(){
		return this.estadoRepository.findAllByOrderByNome();
	}
	
	@GetMapping("/cidades/{idEstado}")
	public List<Cidade> findByEstado(@PathVariable Long idEstado){
		Estado estado = this.estadoRepository.findById(idEstado).get();
		return this.cidadeRepository.findByEstadoOrderByNome(estado);
	}

	@PostMapping("/usuario/cadastrar")
	public Usuario cadastrarUsuario(@RequestBody Usuario usuario){
		return this.usuarioRepository.save(usuario);
	}

	@PostMapping("/usuario/validaUsuario")
	public Usuario validaSenhaUsuario(@RequestBody Usuario usuario){
		Usuario usuBd = this.usuarioRepository.findByLogin(usuario.getLogin());
		if(usuBd == null){
			usuario.setError(Boolean.TRUE);
			usuario.setMensagem("Usuário não existe");
			return usuario;
		}else if (usuBd != null && usuario != null
				&& usuBd.getSenha() != null && !usuBd.getSenha().isEmpty()
				&& usuario.getSenha() != null && !usuario.getSenha().isEmpty()
				&& usuario.getSenha().equals(usuBd.getSenha())){
			usuario.setError(Boolean.FALSE);
		}else{
			usuario.setError(Boolean.TRUE);
			usuario.setMensagem("Usuário ou Senha incorretas !");
		}
		return usuario;
	}
	
	@GetMapping("/empresas/{idCidade}")
	public List<Empresa> findAllEmpresas(@PathVariable Long idCidade){
		Cidade cidade = this.cidadeRepository.findById(idCidade).get();
		List<Empresa> empresas = this.empresaRepository.findByCidade(cidade);

		for (Empresa empresa : empresas){
			if (empresa.getCategoriaMaterials() != null && !empresa.getCategoriaMaterials().isEmpty()){
				StringBuilder categoria = new StringBuilder();
				for (CategoriaMaterial material : empresa.getCategoriaMaterials()){
					categoria.append(material.getNome());
					categoria.append(" , ");
				}
				empresa.setCategoriaMateriaisStr(categoria.toString().substring(0, categoria.toString().length() - 2));
			}

			if(empresa.getMaterials() != null && !empresa.getMaterials().isEmpty()){
				StringBuilder material = new StringBuilder();
				for (Material mat : empresa.getMaterials()){
					material.append(mat.getNome());
					material.append(" , ");
				}
				empresa.setMateriaisStr(material.toString().substring(0, material.toString().length() - 2));
			}
		}
		return empresas;
	}
}
