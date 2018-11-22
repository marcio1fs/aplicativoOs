package br.com.os.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class ItensServico extends GenericDomain{
	
	
	
	@Column(nullable = false, precision = 7, scale = 2)
	private BigDecimal precoParcial;
	
	@Column(nullable = false)
	private Short quantidade;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Os os;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Servico servico;

	public BigDecimal getPrecoParcial() {
		return precoParcial;
	}

	public void setPrecoParcial(BigDecimal precoParcial) {
		this.precoParcial = precoParcial;
	}

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	public Os getOs() {
		return os;
	}

	public void setOs(Os os) {
		this.os = os;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}
	

	

}
