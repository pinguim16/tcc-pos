package com.reciclaveltcc.models;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "empresa")
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "endereco")
	private String endereco;
	
	@Column(name = "bairro")
	private String bairro;
	
	@ManyToOne
	@JoinColumn(name = "id_cidade")
	private Cidade cidade;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "cep")
	private String cep;
	
	@Column(name = "tipo_empresa")
	private String tipoEmpresa;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="empresa_categoria_material", joinColumns=
    {@JoinColumn(name="empresa_id")}, inverseJoinColumns=
      {@JoinColumn(name="categoria_material_id")})
	private List<CategoriaMaterial> categoriaMaterials;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="empresa_material", joinColumns=
    {@JoinColumn(name="empresa_id")}, inverseJoinColumns=
      {@JoinColumn(name="material_id")})
	private List<Material> materials;

	@Transient
	private String categoriaMateriaisStr;

	@Transient
	private String materiaisStr;
	
	public Empresa() {}

	public Empresa(String nome, String endereco, String bairro, Cidade cidade,String telefone, String cep, String tipoEmpresa) {
		this.nome = nome;
		this.endereco = endereco;
		this.bairro = bairro;
		this.cidade = cidade;
		this.telefone = telefone;
		this.cep = cep;
		this.tipoEmpresa = tipoEmpresa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<CategoriaMaterial> getCategoriaMaterials() {
		return categoriaMaterials;
	}

	public void setCategoriaMaterials(List<CategoriaMaterial> categoriaMaterials) {
		this.categoriaMaterials = categoriaMaterials;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public String getCategoriaMateriaisStr() {
		return categoriaMateriaisStr;
	}

	public void setCategoriaMateriaisStr(String categoriaMateriaisStr) {
		this.categoriaMateriaisStr = categoriaMateriaisStr;
	}

	public String getMateriaisStr() {
		return materiaisStr;
	}

	public void setMateriaisStr(String materiaisStr) {
		this.materiaisStr = materiaisStr;
	}
}
