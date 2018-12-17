package br.com.os.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.os.dao.ClienteDAO;
import br.com.os.dao.FuncionarioDAO;
import br.com.os.dao.OsDAO;
import br.com.os.dao.ProdutoDAO;
import br.com.os.dao.ServicoDAO;
import br.com.os.dao.TecnicoDAO;
import br.com.os.dao.VeiculoDAO;
import br.com.os.domain.Cliente;
import br.com.os.domain.ContasReceber;
import br.com.os.domain.Funcionario;
import br.com.os.domain.ItemOs;
import br.com.os.domain.ItensServico;
import br.com.os.domain.Os;
import br.com.os.domain.Produto;
import br.com.os.domain.Servico;
import br.com.os.domain.Tecnico;
import br.com.os.domain.Veiculo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class OsBean implements Serializable {

	private Os os;

	private List<Os>  oss;
	private List<Produto> produtos;
	private List<ItemOs> itensOs;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;
	private List<Servico> servicos;
	private List<ItensServico> itensServicos;
	private List<Veiculo> veiculos;
	private List<Tecnico> tecnicos;
	private List<ContasReceber> contas;

	public Os getOs() {
		return os;
	}

	public void setOs(Os os) {
		this.os = os;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<ItensServico> getItensServicos() {
		return itensServicos;
	}

	public void setItensServicos(List<ItensServico> itensServicos) {
		this.itensServicos = itensServicos;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public List<Tecnico> getTecnicos() {
		return tecnicos;
	}

	public void setTecnicos(List<Tecnico> tecnicos) {
		this.tecnicos = tecnicos;
	}

	public List<ItemOs> getItensOs() {
		return itensOs;
	}

	public void setItensOs(List<ItemOs> itensOs) {
		this.itensOs = itensOs;
	}

	public List<ContasReceber> getContas() {
		return contas;
	}

	public void setContas(List<ContasReceber> contas) {
		this.contas = contas;
	}
	
	public List<Os> getOss() {
		return oss;
	}
	public void setOss(List<Os> oss) {
		this.oss = oss;
	}

	@PostConstruct
	public void novo() {
		try {
			os = new Os();

			os.setValor(new BigDecimal("0.00"));

			ServicoDAO servicoDAO = new ServicoDAO();
			servicos = servicoDAO.listar("descricao");

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar("descricao");

			itensOs = new ArrayList<>();

			itensServicos = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de Ordem de Serviço");
			erro.printStackTrace();
		}

	}
	
	
	
	public void listar(){
		
		try{
			
		OsDAO osDAO = new OsDAO();
		oss = osDAO.listar("horario"); 
		
		
		oss = new ArrayList<>();
		
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar Listar a Ordem de Serviço");
			erro.printStackTrace();
		}
	}
	

	public void adicionar1(ActionEvent evento) {
		Servico servico = (Servico) evento.getComponent().getAttributes().get("servicoSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensServicos.size(); posicao++) {
			if (itensServicos.get(posicao).getServico().equals(servico)) {
				achou = posicao;
			}
		}
		if (achou < 0) {
			ItensServico itemServico = new ItensServico();
			itemServico.setPrecoParcial(servico.getValor());
			itemServico.setServico(servico);
			itemServico.setQuantidade(new Short("1"));

			itensServicos.add(itemServico);
		} else {
			ItensServico itemServico = itensServicos.get(achou);
			itemServico.setQuantidade(new Short(itemServico.getQuantidade() + 1 + ""));
			itemServico.setPrecoParcial(servico.getValor().multiply(new BigDecimal(itemServico.getQuantidade())));

		}

		calcular();

	}

	public void adicionar(ActionEvent ev) {
		Produto produto = (Produto) ev.getComponent().getAttributes().get("produtoSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensOs.size(); posicao++) {
			if (itensOs.get(posicao).getProduto().equals(produto)) {
				achou = posicao;
			}
		}

		if (achou < 0) {
			ItemOs itens = new ItemOs();
			itens.setPrecoParcial(produto.getPreco());
			itens.setProduto(produto);
			itens.setQuantidade(new Short("1"));

			itensOs.add(itens);
		} else {
			ItemOs itens = itensOs.get(achou);
			itens.setQuantidade(new Short(itens.getQuantidade() + 1 + ""));
			itens.setPrecoParcial(produto.getPreco().multiply(new BigDecimal(itens.getQuantidade())));

		}

		calcular();

	}

	public void remover(ActionEvent evento) {
		ItensServico itemServico = (ItensServico) evento.getComponent().getAttributes().get("servicoSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensServicos.size(); posicao++) {
			if (itensServicos.get(posicao).getServico().equals(itemServico.getServico())) {

				achou = posicao;
			}
		}
		if (achou > -1) {
			itensServicos.remove(achou);
		}
		calcular();
	}

	public void finalizar() {
		try {
			os.setHorario(new Date());
			os.setCliente(null);
			os.setFuncionario(null);
			os.setTecnico(null);
			os.setVeiculo(null);

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarOrdenado();

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarOrdenado();

			TecnicoDAO tecnicoDAO = new TecnicoDAO();
			tecnicos = tecnicoDAO.listar();

			VeiculoDAO veiculoDAO = new VeiculoDAO();
			veiculos = veiculoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a Ordem de Serviço");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (os.getValor().signum() == 0) {
				Messages.addGlobalError("Informe pelo menos um serviço para a os");
				return;
			}
			OsDAO osDAO = new OsDAO();
			osDAO.salvar(os, itensServicos);
			osDAO.salvarOs(os, itensOs);
			os.salvarC(os, contas);

			os = new Os();
			os.setValor(new BigDecimal("0.00"));

			ServicoDAO servicoDAO = new ServicoDAO();
			servicos = servicoDAO.listar("descricao");

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar("descricao");

			contas = new ArrayList<>();

			itensServicos = new ArrayList<>();
			itensOs = new ArrayList<>();
			
			oss = osDAO.listar();

			Messages.addGlobalInfo("Os realizada com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Os");
			erro.printStackTrace();
		}
	}

	public void calcular() {
		os.setValor(new BigDecimal("0.00"));

		for (int posicao = 0; posicao < itensServicos.size(); posicao++) {
			ItensServico itemServico = itensServicos.get(posicao);
			os.setValor(os.getValor().add(itemServico.getPrecoParcial()));
		}

		for (int posicao = 0; posicao < itensOs.size(); posicao++) {
			ItemOs itemOs = itensOs.get(posicao);
			os.setValor(os.getValor().add(itemOs.getPrecoParcial()));

		}
	}

}
