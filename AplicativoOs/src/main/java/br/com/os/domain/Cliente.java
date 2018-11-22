package br.com.os.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Cliente extends GenericDomain {
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)//serve para guarda a data
	private Date dataCadastro;
	
	
	@Column(nullable = false)
	private Boolean liberado;//guarda um tipo
	
	@OneToOne //relacionamemto de 1 pra 1
	@JoinColumn(nullable = false)//coluna de relacionamento das tabelas
	private Pessoa pessoa;

	@Transient
	private String nome;
	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getLiberado() {
		return liberado;
	}

	public void setLiberado(Boolean liberado) {
		this.liberado = liberado;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
}
