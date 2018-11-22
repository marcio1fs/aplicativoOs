package br.com.os.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.os.dao.FornecedorDAO;
import br.com.os.domain.Fornecedor;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FornecedorBean implements Serializable {

	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	// post contruct construção dos metodos
@PostConstruct
	public void listar() {
		try {
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar("nome");

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar o Fornecedor");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			fornecedor = new Fornecedor();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar um novo Fornecedor");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedorDAO.merge(fornecedor);

			fornecedor = new Fornecedor();

			fornecedores = fornecedorDAO.listar();

			Messages.addGlobalInfo("Fornecedor salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar um novo Fornecedor");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			fornecedor = (Fornecedor) evento.getComponent().getAttributes().get("fornecedorSelecionado");

			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedorDAO.excluir(fornecedor);

			fornecedores = fornecedorDAO.listar();

			Messages.addGlobalInfo("Fornecedor removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o Fornecedor");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			fornecedor = (Fornecedor) evento.getComponent().getAttributes().get("fornecedorSelecionado");

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar alterar o Fornecedor");
			erro.printStackTrace();
		}
	}

}
