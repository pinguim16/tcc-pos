package com.reciclaveltcc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import com.reciclaveltcc.models.CategoriaMaterial;
import com.reciclaveltcc.models.Cidade;
import com.reciclaveltcc.models.Empresa;
import com.reciclaveltcc.models.Estado;
import com.reciclaveltcc.models.Material;
import com.reciclaveltcc.repository.CategoriaMaterialRepository;
import com.reciclaveltcc.repository.CidadeRepository;
import com.reciclaveltcc.repository.EmpresaRepository;
import com.reciclaveltcc.repository.EstadoRepository;
import com.reciclaveltcc.repository.MaterialRepository;

@Controller
public class CrawlingReciclaveis {
	
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CategoriaMaterialRepository categoriaMaterialRepository;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;

	private static String URL_COOPERATIVAS = "http://cempre.org.br/servico/pesquisa/lista/v4/C/v1/";
	private static String URL_SUCATEIROS = "http://cempre.org.br/servico/pesquisa/lista/v4/S/v1/";
	private static String URL_RECICLADORES = "http://cempre.org.br/servico/pesquisa/lista/v4/R/v1/";

	private static String URL_PAGINAS_COOP = "http://cempre.org.br/servico/pesquisa/lista/v1/idEstado/v4/C/pg/pagina";
	private static String URL_PAGINAS_SUC = "http://cempre.org.br/servico/pesquisa/lista/v1/idEstado/v4/S/pg/pagina";
	private static String URL_PAGINAS_R = "http://cempre.org.br/servico/pesquisa/lista/v1/idEstado/v4/R/pg/pagina";

	public void crawCoop(Integer est) throws NotFound {
		this.crawlingSiteReciclaveis(URL_COOPERATIVAS, URL_PAGINAS_COOP, est);
	}
	
	public void crawSuc(Integer est) throws NotFound {
		this.crawlingSiteReciclaveis(URL_SUCATEIROS, URL_PAGINAS_SUC, est);
	}
	
	public void crawRec(Integer est) throws NotFound {
		this.crawlingSiteReciclaveis(URL_RECICLADORES, URL_PAGINAS_R, est);
	}

	public void crawlingSiteReciclaveis(String urlPrincipal, String urlPagina, Integer est) throws NotFound {
		UserAgent userAgent = new UserAgent();
		List<String> listUrls = new ArrayList<>();
		try {
			
			userAgent.visit(urlPrincipal + est);
			System.out.println(urlPrincipal + est);
			//System.out.println(userAgent.doc.innerHTML());
			String empresasHtml = userAgent.doc.findEach("<h3 class=\"tit-sub\">").outerHTML();
			Integer paginaPercorrer = 0;
			Integer empresasUltimaPagina = 0;
			Integer objetosPorPagina = 10;

			Integer empresasEncontradas = Integer.parseInt(empresasHtml
					.substring(48, empresasHtml.lastIndexOf("empresas com as chaves de busca informadas.</h3></#elements>"))
					.trim());
			System.out.println("Empresas Encontradas - " + empresasEncontradas);

			paginaPercorrer = empresasEncontradas / 10;
			if ((empresasEncontradas % 10) >= 1 && paginaPercorrer >= 1) { //4
				empresasUltimaPagina = empresasEncontradas % 10;
			}else if(paginaPercorrer == 0) {
				paginaPercorrer = 1;
				objetosPorPagina = empresasEncontradas;
			}
			
			String urlAcessada = new String(urlPagina);
			urlAcessada = urlAcessada.replaceAll("idEstado", String.valueOf(est));
			for (int j = 1; j <= paginaPercorrer; j++) {
				for (int i = 0; i < objetosPorPagina; i++) {
					
					urlAcessada = urlAcessada.substring(0, 58) + j;
					userAgent.visit(urlAcessada);
					
					String element = userAgent.doc.findEach("<div class=\"bt\">").getElement(i).getParent().outerHTML();
					String url = element.substring(9, element.lastIndexOf("\"><div class"));
					if(!listUrls.contains(url)) {
						listUrls.add(url);
					}
				}
			}
			
			if(empresasUltimaPagina > 0) {
				urlAcessada = urlAcessada.replace("pagina", String.valueOf(paginaPercorrer+1));
				userAgent.visit(urlAcessada);
				for (int i = 0; i < empresasUltimaPagina; i++) {
					
					String element = userAgent.doc.findEach("<div class=\"bt\">").getElement(i).getParent().outerHTML();
					String url = element.substring(9, element.lastIndexOf("\"><div class"));
					if(!listUrls.contains(url)) {
						listUrls.add(url);
					}
				}
			}
			
			for (String url : listUrls) {
				
				System.out.println(listUrls.size());
				System.out.println(url);
				
				List<CategoriaMaterial> categoriaMaterials = new ArrayList<CategoriaMaterial>();
				List<Material> materials = new ArrayList<Material>();
				
				userAgent.visit(url);
				String nomeEmpresa = userAgent.doc.findEach("<div class=\"tit\">").getElement(0).getTextContent().replace("Empresa: ", "").trim();
				String enderecoEmpresa = userAgent.doc.findEach("<div class=\"txt\" style=\"border-bottom: 0;\">").findEvery("<div>Endereço:").getElement(0).getTextContent().replace("Endereço: ", "").trim();
				String bairroEmpresa = userAgent.doc.findEach("<div class=\"txt\" style=\"border-bottom: 0;\">").findEvery("<div>Bairro: ").getElement(0).getTextContent().replace("Bairro: ", "").trim();
				
				String cidadeEstado = userAgent.doc.findEach("<div class=\"txt\" style=\"border-bottom: 0;\">").findEvery("<div>Cidade: ").getElement(0).getTextContent().replace("Cidade: ", "").trim();
				String[] cidadeEstadoSplit = cidadeEstado.split("/");
				String cidadeString = cidadeEstadoSplit[0];
				String estadoString = cidadeEstadoSplit[1].trim();
				
				String cep = userAgent.doc.findEach("<div class=\"txt\" style=\"border-bottom: 0;\">").findEvery("<div>CEP: ").getElement(0).getTextContent().replace("CEP: ", "").trim();
				
				String telefone1;
				try {
					telefone1 = userAgent.doc.findEach("<div class=\"txt\" style=\"border-bottom: 0;\">").findEvery("<div>Telefone 1: ").getElement(0).getTextContent().replace("Telefone 1: ", "").trim();
				}catch (Exception e) {
					telefone1 = "";
				}
				
				String tipoEmpresa = userAgent.doc.findEach("<div class=\"txt\" style=\"border-bottom: 0;\">").findEvery("<div class=\"cor\"><br>").getElement(0).getTextContent().replace("Tipo: ", "").trim();
				
				//Categoria material 
				String categoriaMateriaisString = userAgent.doc.findEach("<div class=\"txt\" style=\"border-bottom: 0;\">").findEvery("<div class=\"cor\"><br>").getElement(1).getTextContent().replace("Categoria de Materiais: ", "").trim();
				CategoriaMaterial categoriaMaterial = null;
				if(!StringUtils.isAnyBlank(categoriaMateriaisString)) {
					String[] categoriaMateriaisSplit = categoriaMateriaisString.split(",");
					for (int i = 0;  i < categoriaMateriaisSplit.length; i++) {
						String nomeCategoriaMaterial = categoriaMateriaisSplit[i].trim();
						if(!StringUtils.isAnyBlank(nomeCategoriaMaterial)) {
							categoriaMaterial = this.categoriaMaterialRepository.findByNome(nomeCategoriaMaterial);
							if(categoriaMaterial == null) {
								categoriaMaterial = new CategoriaMaterial(nomeCategoriaMaterial);
								categoriaMaterial = this.categoriaMaterialRepository.save(categoriaMaterial);
							}
							categoriaMaterials.add(categoriaMaterial);
						}
						
					}
				}
				
				//Material
				String materiaisString = userAgent.doc.findEach("<div class=\"txt\" style=\"border-bottom: 0;\">").findEvery("<div class=\"cor\"><br>").getElement(2).getTextContent().replace("Materiais: ", "").trim();
				Material material = null;
				if(!StringUtils.isAnyBlank(materiaisString)) {
					String[] materiaisSplit = materiaisString.split(",");
					for (int i = 0;  i < materiaisSplit.length; i++) {
						String nomeMaterial = materiaisSplit[i].trim();
						if(!StringUtils.isAnyBlank(nomeMaterial)) {
							material = this.materialRepository.findByNome(nomeMaterial);
							if(material == null) {
								material = new Material(nomeMaterial);
								material = this.materialRepository.save(material);
							}
							materials.add(material);
						}
					}
				}
				
				Estado estado = null;
				if(!estadoString.isEmpty()) {
					estado = this.estadoRepository.findByNome(estadoString);
					if(estado == null) {
						estado = new Estado(estadoString);
						estado = this.estadoRepository.save(estado);
					}
				}
				
				Cidade cidade = null;
				if(!cidadeString.isEmpty()) {
					cidade = this.cidadeRepository.findByNome(cidadeString);
					if(cidade == null) {
						cidade = new Cidade(cidadeString);
						cidade.setEstado(estado);
						cidade = this.cidadeRepository.save(cidade);
					}
				}
				
				Empresa empresa = new Empresa(nomeEmpresa, enderecoEmpresa, bairroEmpresa, cidade,telefone1, cep, tipoEmpresa);
				empresa.setCategoriaMaterials(categoriaMaterials);
				empresa.setMaterials(materials);
				this.empresaRepository.save(empresa);
				
			}

		} catch (ResponseException e) {
			e.printStackTrace();
		}

	}

}
