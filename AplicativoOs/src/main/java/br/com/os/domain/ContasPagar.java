package br.com.os.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class ContasPagar extends GenericDomain {

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)//serve para guarda a data
	private Date dataCadastro;
	
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)//serve para guarda a data
	private Date dataVencimento;
	
	@Column(nullable = false, precision = 6, scale = 2)
	private BigDecimal valor;
	
	
	@ManyToOne // chave strangeira
	@JoinColumn(nullable = false)
	private Fornecedor fornecedor;

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public Date getDataCadastro() {
		return dataCadastro;
	}
	
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public Date getDataVencimento() {
		return dataVencimento;
	}
	
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

}
