package br.com.os.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;

import br.com.os.dao.ClienteDAO;
import br.com.os.dao.FuncionarioDAO;
import br.com.os.dao.ProdutoDAO;
import br.com.os.dao.VendaDAO;
import br.com.os.domain.Cliente;
import br.com.os.domain.Funcionario;
import br.com.os.domain.ItemVenda;
import br.com.os.domain.Produto;
import br.com.os.domain.Venda;
import br.com.os.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {
	
	private Venda venda;

	private List<Produto> produtos;
	private List<ItemVenda> itensVenda;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;
	private List<Venda> vendas;

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
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
	
	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	@PostConstruct
	public void novo() {
		try {
			venda = new Venda();
			venda.setPrecoTotal(new BigDecimal("0.00"));

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar("descricao");

			itensVenda = new ArrayList<>();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de vendas");
			erro.printStackTrace();
		}
	}
	
	
	public void listar(){
		
		try{
		VendaDAO vendaDAO = new VendaDAO();
		vendas = vendaDAO.listar("horario"); 
		
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar Listar o Venda");
			erro.printStackTrace();
		}
	}
	
	

	public void adicionar(ActionEvent evento) {
		Produto produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(produto)) {
				achou = posicao;
			}
		}

		if (achou < 0) {
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setPrecoParcial(produto.getPreco());
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(new Short("1"));

			itensVenda.add(itemVenda);
		} else {
			ItemVenda itemVenda = itensVenda.get(achou);
			itemVenda.setQuantidade(new Short(itemVenda.getQuantidade() + 1 + ""));
			itemVenda.setPrecoParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
		}

		calcular();
	}
	
	
	
	public void atualizarPrecoParcial(){
		for(ItemVenda itemVenda : this.itensVenda){
			itemVenda.setPrecoParcial(itemVenda.getProduto().getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
		}
		this.calcular();
	}
	
	
	

	public void remover(ActionEvent evento) {
		ItemVenda itemVenda = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(itemVenda.getProduto())) {
				achou = posicao;
			}
		}

		if (achou > -1) {
			itensVenda.remove(achou);
		}

		calcular();
	}

	public void calcular() {
		venda.setPrecoTotal(new BigDecimal("0.00"));

		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			ItemVenda itemVenda = itensVenda.get(posicao);
			venda.setPrecoTotal(venda.getPrecoTotal().add(itemVenda.getPrecoParcial()));
		}
	}

	public void finalizar() {
		try {
			venda.setHorario(new Date());
			venda.setCliente(null);
			venda.setFuncionario(null);
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarOrdenado();

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarOrdenado();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a venda");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if(venda.getPrecoTotal().signum() == 0){
				Messages.addGlobalError("Informe pelo menos um item para a venda");
				return;
			}
			
			VendaDAO vendaDAO = new VendaDAO();
			vendaDAO.salvar(venda, itensVenda);
			
			venda = new Venda();
			venda.setPrecoTotal(new BigDecimal("0.00"));

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar("descricao");

			itensVenda = new ArrayList<>();
			
			Messages.addGlobalInfo("Venda realizada com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a venda");
			erro.printStackTrace();
		}
	
	}
	public void excluir(ActionEvent evento) {
		try {
			venda = (Venda) evento.getComponent().getAttributes().get("vendaSelecionado");

			VendaDAO vendaDAO = new VendaDAO();
			vendaDAO.excluir(venda);
			
			ProdutoDAO produtoDAO = new ProdutoDAO();
			
			//verificar se o metodo de delite funcionou
	    	produtos = produtoDAO.listar();
			
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarOrdenado();

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarOrdenado();
			
			
			vendas = vendaDAO.listar();

			Messages.addGlobalInfo("venda removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a venda");
			erro.printStackTrace();
		}
	}
	
	
	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String venDescricao = (String) filtros.get("descricao");
			String venHorario = (String) filtros.get("venda.horario");
			String venReferencia = (String) filtros.get("venda.referencia");

			String caminho = Faces.getRealPath("/reports/venda.jasper");

			Map<String, Object> parametros = new HashMap<>();
			if (venDescricao == null) {
				parametros.put("VENDA_DESCRICAO", "%%");
			} else {
				parametros.put("VENDA_DESCRICAO", "%" + venDescricao + "%");
			}
			if (venHorario == null) {
				parametros.put("VENDA_HORARIO", "%%");
			} else {
				parametros.put("VENDA_HORARIO", "%" + venHorario + "%");
			}
			
			
			if (venReferencia == null) {
				parametros.put("VENDA_REFERENCIA", "%%");
			} else {
				parametros.put("VENDA_REFERENCIA", "%" + venReferencia + "%");
			}

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);

			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar o relatório");
			erro.printStackTrace();
		}
	}
	
	
	
	public void editar(ActionEvent evento) {
		try {
			venda = (Venda) evento.getComponent().getAttributes().get("vendaSelecionado");
		

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar();
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarOrdenado();

			venda = new Venda();
		
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um venda");
			erro.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
