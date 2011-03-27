package br.com.pixel3.pixel3list.modelo;

public class Aluno {

	private Long id ;
	private String nome ;
	private String email ;
	private String sexo ;
	private String periodos ;
	
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

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getSexo() {
		return sexo;
	}

	public void setPeriodos(String periodos) {
		this.periodos = periodos;
	}

	public String getPeriodos() {
		return periodos;
	}

}
