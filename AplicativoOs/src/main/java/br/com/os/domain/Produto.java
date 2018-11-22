package br.com.os.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Produto extends GenericDomain {

	@Column(length = 80, nullable = false)
	private String descricao;

	@Column(length = 20, nullable = false)
	private String referencia;

	@Column(length = 250, nullable = false)
	private String aplicacao;

	@Column(nullable = false)
	private Short quantidade;

	@Column(nullable = false, precision = 6, scale = 2)
	private BigDecimal preco;

	@ManyToOne // chave strangeira
	@JoinColumn(nullable = false) // coluna de relacionamento das tabelas
	private Fabricante fabricante;

	@Transient
	private Fornecedor fornecedor;

	// @Column(nullable = false, length = 100)
	// private String aplicacao;

	@Transient
	private String caminho;

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

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(String aplicacao) {
		this.aplicacao = aplicacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((aplicacao == null) ? 0 : aplicacao.hashCode());
		result = prime * result + ((caminho == null) ? 0 : caminho.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((fabricante == null) ? 0 : fabricante.hashCode());
		result = prime * result + ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result + ((preco == null) ? 0 : preco.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((referencia == null) ? 0 : referencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (aplicacao == null) {
			if (other.aplicacao != null)
				return false;
		} else if (!aplicacao.equals(other.aplicacao))
			return false;
		if (caminho == null) {
			if (other.caminho != null)
				return false;
		} else if (!caminho.equals(other.caminho))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (fabricante == null) {
			if (other.fabricante != null)
				return false;
		} else if (!fabricante.equals(other.fabricante))
			return false;
		if (fornecedor == null) {
			if (other.fornecedor != null)
				return false;
		} else if (!fornecedor.equals(other.fornecedor))
			return false;
		if (preco == null) {
			if (other.preco != null)
				return false;
		} else if (!preco.equals(other.preco))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		if (referencia == null) {
			if (other.referencia != null)
				return false;
		} else if (!referencia.equals(other.referencia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [descricao=" + descricao + ", referencia=" + referencia + ", aplicacao=" + aplicacao
				+ ", quantidade=" + quantidade + ", preco=" + preco + ", fabricante=" + fabricante + ", fornecedor="
				+ fornecedor + ", caminho=" + caminho + ", getDescricao()=" + getDescricao() + ", getQuantidade()="
				+ getQuantidade() + ", getPreco()=" + getPreco() + ", getFabricante()=" + getFabricante()
				+ ", getCaminho()=" + getCaminho() + ", getFornecedor()=" + getFornecedor() + ", getReferencia()="
				+ getReferencia() + ", getAplicacao()=" + getAplicacao() + ", hashCode()=" + hashCode()
				+ ", getCodigo()=" + getCodigo() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ "]";
	}
	
	

}
