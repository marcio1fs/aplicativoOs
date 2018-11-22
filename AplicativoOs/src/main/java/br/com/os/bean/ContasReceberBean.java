package br.com.os.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;

import br.com.os.dao.ContasReceberDAO;
import br.com.os.dao.OsDAO;
import br.com.os.domain.ContasReceber;
import br.com.os.domain.Os;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ContasReceberBean implements Serializable {

	private ContasReceber contasReceber;
	private List<ContasReceber> contasRebes;
	private List<Os> os;

	public ContasReceber getContasReceber() {
		return contasReceber;
	}

	public void setContasReceber(ContasReceber contasReceber) {
		this.contasReceber = contasReceber;
	}

	public List<ContasReceber> getContasRebes() {
		return contasRebes;
	}

	public void setContasRebes(List<ContasReceber> contasRebes) {
		this.contasRebes = contasRebes;
	}

	public List<Os> getOs() {
		return os;
	}

	public void setOs(List<Os> os) {
		this.os = os;
	}

	public void listar() {
		try {
			ContasReceberDAO contasReceberDAO = new ContasReceberDAO();
			contasRebes = contasReceberDAO.listar("codigo");

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as Contas a Receber");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			contasReceber = new ContasReceber();

			OsDAO osDAO = new OsDAO();
			os = osDAO.listar("codigo");

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar uma nova Conta");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ContasReceberDAO contasReceberDAO = new ContasReceberDAO();
			contasReceberDAO.merge(contasReceber);

			contasReceber = new ContasReceber();

			OsDAO osDAO = new OsDAO();
			os = osDAO.listar();

			contasRebes = contasReceberDAO.listar();

			Messages.addGlobalInfo("Contas salva com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar uma nova Conta");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			contasReceber = (ContasReceber) evento.getComponent().getAttributes().get("contaSelecionada");
			ContasReceberDAO contasReceberDAO = new ContasReceberDAO();
			contasReceberDAO.excluir(contasReceber);

			contasRebes = contasReceberDAO.listar();

			Messages.addGlobalInfo("Conta removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a conta");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			contasReceber = (ContasReceber) evento.getComponent().getAttributes().get("contaSelecionada");

			OsDAO osDAO = new OsDAO();
			os = osDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar editar a conta");
			erro.printStackTrace();
		}
	}

}
