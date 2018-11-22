package br.com.os.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Servico extends GenericDomain {

	@Column(nullable = false, length = 100)
	private String descricao;

	@Column(nullable = false, precision = 7, scale = 2)
	private BigDecimal valor;

	@Column(nullable = false)
	private Short quantidade;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Short getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
