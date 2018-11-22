package br.com.os.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.os.dao.ClienteDAO;
import br.com.os.dao.MontadoraDAO;
import br.com.os.dao.VeiculoDAO;
import br.com.os.domain.Cliente;
import br.com.os.domain.Montadora;
import br.com.os.domain.Veiculo;

@SuppressWarnings("serial")
@ManagedBean // trata do controle dentro da aplicação web
@ViewScoped
public class VeiculoBean implements Serializable {

	private Veiculo veiculo;
	private List<Veiculo> veiculos;
	private List<Montadora> montadoras;
	private List<Cliente> clientes;
	

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public List<Montadora> getMontadoras() {
		return montadoras;
	}

	public void setMontadoras(List<Montadora> montadoras) {
		this.montadoras = montadoras;
	}
	

	@PostConstruct
	public void listar() {
		try {
			VeiculoDAO veiculoDAO = new VeiculoDAO();
			veiculos = veiculoDAO.listar();
			
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarOrdenado();
			
		  
			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os veiculos");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			veiculo = new Veiculo();

			MontadoraDAO montadoraDAO = new MontadoraDAO();
			montadoras = montadoraDAO.listar("descricao");
			
	
		
				
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar("codigo");
			
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gravar um novo Veiculos");
			erro.printStackTrace();
		}
		
		
	}

	public void salvar() {
		try {
			VeiculoDAO veiculoDAO = new VeiculoDAO();
			veiculoDAO.merge(veiculo);

			veiculo = new Veiculo();

			veiculos = veiculoDAO.listar("descricao");

			MontadoraDAO montadoraDAO = new MontadoraDAO();
			montadoras = montadoraDAO.listar("descricao");
			
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar("codigo");
			
			
		
		
			Messages.addGlobalInfo("veiculo salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o veiculo");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			veiculo = (Veiculo) evento.getComponent().getAttributes().get("veiculoSelecionado");

			VeiculoDAO veiculoDAO = new VeiculoDAO();
			veiculoDAO.excluir(veiculo);

			veiculos = veiculoDAO.listar();

			Messages.addGlobalInfo("Veiculo removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o Veiculo");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			veiculo = (Veiculo) evento.getComponent().getAttributes().get("veiculoSelecionado");

			VeiculoDAO veiculoDAO = new VeiculoDAO();
			veiculos = veiculoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um Veiculo");
			erro.printStackTrace();
		}
	}

}
