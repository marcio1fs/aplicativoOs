package br.com.os.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import br.com.os.dao.MontadoraDAO;
import br.com.os.domain.Montadora;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MontadoraBean implements Serializable {
	// atributos privados
	private Montadora montadora;
	private List<Montadora> montadoras;

	public Montadora getMontadora() {
		return montadora;
	}

	public void setMontadora(Montadora montadora) {
		this.montadora = montadora;
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
			MontadoraDAO montadoraDAO = new MontadoraDAO();

			montadoras = montadoraDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Montadoras");
			erro.printStackTrace();
		}
	}

	public void novo() {
		
		montadora = new Montadora();
	}

	public void salvar() {

		try {
			MontadoraDAO montadoraDAO = new MontadoraDAO();
			montadoraDAO.merge(montadora);

			// controla a listagem dos objetos selecionados na tela
			montadora = new Montadora();
			montadoras = montadoraDAO.listar();

			Messages.addGlobalInfo("Montadora salva com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar Montadora");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			montadora = (Montadora) evento.getComponent().getAttributes().get("montadoraSelecionada");
			MontadoraDAO montadoraDAO = new MontadoraDAO();
			montadoraDAO.excluir(montadora);

			montadoras = montadoraDAO.listar();

			Messages.addGlobalInfo("Montadora removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a Montadora");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		montadora = (Montadora) evento.getComponent().getAttributes().get("montadoraSelecionada");

	}

}
