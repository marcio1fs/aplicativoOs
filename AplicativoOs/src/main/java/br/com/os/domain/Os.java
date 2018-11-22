package br.com.os.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Os extends GenericDomain {

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP) // guarda data e hora
	private Date horario;
	
	@Column(nullable = false, precision = 7, scale = 2)
	private BigDecimal valor;

	@ManyToOne
	private Tecnico tecnico;

	@ManyToOne
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(nullable = false) // relaçao entre as tabelas
	private Funcionario funcionario;

	@OneToOne
	@JoinColumn(nullable = false)
	private Veiculo veiculo;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "os")
	private List<ItemOs> itensOs;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "os")
	private List<ItensServico> itensServicos;
	

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public void salvarC(Os os, List<ContasReceber> contas) {
		this.equals(contas);
		// TODO Auto-generated method stub
		
	}

	public List<ItemOs> getItensOs() {
		return itensOs;
	}

	public void setItensOs(List<ItemOs> itensOs) {
		this.itensOs = itensOs;
	}

	public List<ItensServico> getItensServicos() {
		return itensServicos;
	}

	public void setItensServicos(List<ItensServico> itensServicos) {
		this.itensServicos = itensServicos;
	}

	
	
}
