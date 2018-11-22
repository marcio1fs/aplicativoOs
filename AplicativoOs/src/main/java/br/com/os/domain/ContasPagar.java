package br.com.os.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class ContasPagar extends GenericDomain {

	@ManyToOne // chave strangeira
	@JoinColumn(nullable = false)
	private Fornecedor fornecedor;

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	

}
