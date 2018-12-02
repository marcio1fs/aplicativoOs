package br.com.os.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.os.dao.ContasPagarDAO;
import br.com.os.dao.FornecedorDAO;
import br.com.os.domain.ContasPagar;
import br.com.os.domain.Fornecedor;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ContasPagarBean implements Serializable {

	private ContasPagar contasPagar;
	private List<ContasPagar> contasPagas;

	private List<Fornecedor> fornecedores;

	public ContasPagar getContasPagar() {
		return contasPagar;
	}

	public void setContasPagar(ContasPagar contasPagar) {
		this.contasPagar = contasPagar;
	}

	public List<ContasPagar> getContasPagas() {
		return contasPagas;
	}

	public void setContasPagas(List<ContasPagar> contasPagas) {
		this.contasPagas = contasPagas;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	@PostConstruct
	public void listar() {
		try {
			ContasPagarDAO contasPagarDAO = new ContasPagarDAO();
			contasPagas = contasPagarDAO.listar("codigo");

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as Contas a pagar");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			contasPagar = new ContasPagar();

			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar Contas a pagar");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ContasPagarDAO contasPagarDAO = new ContasPagarDAO();
			contasPagarDAO.merge(contasPagar);

			contasPagar = new ContasPagar();

			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar("codigo");

			Messages.addGlobalInfo("Contas salva com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar uma nova Conta");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			contasPagar = (ContasPagar) evento.getComponent().getAttributes().get("contasSelecionada");

			ContasPagarDAO contasPagarDAO = new ContasPagarDAO();
			contasPagarDAO.excluir(contasPagar);

			contasPagas = contasPagarDAO.listar("codigo");

			Messages.addGlobalInfo("Contas removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover uma nova conta");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			contasPagar = (ContasPagar) evento.getComponent().getAttributes().get("contasSelecionada");

			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar uma Conta");
			erro.printStackTrace();
		}
	}

}
