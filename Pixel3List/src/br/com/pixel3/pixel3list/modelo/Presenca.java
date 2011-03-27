package br.com.pixel3.pixel3list.modelo;


public class Presenca {

	private Long id ;
	private Long alunoId ;
	private String periodo ;
	private long data ;
	private boolean presenca ;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getAlunoId() {
		return alunoId;
	}
	
	public void setAlunoId(Long alunoId) {
		this.alunoId = alunoId;
	}
	
	public String getPeriodo() {
		return periodo;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public long getData() {
		return data;
	}
	
	public void setData(long data) {
		this.data = data;
	}
	
	public boolean isPresenca() {
		return presenca;
	}

	public void setPresenca(boolean presenca) {
		this.presenca = presenca;
	}
	
}
