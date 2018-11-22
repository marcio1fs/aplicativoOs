package br.com.os.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.os.dao.PessoaDAO;
import br.com.os.dao.TecnicoDAO;
import br.com.os.domain.Pessoa;
import br.com.os.domain.Tecnico;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TecnicoBean implements Serializable {

	private Tecnico tecnico;
	
	private List<Pessoa> pessoas;
	private List<Tecnico> tecnicos;
	
	

	
	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Tecnico> getTecnicos() {
		return tecnicos;
	}

	public void setTecnicos(List<Tecnico> tecnicos) {
		this.tecnicos = tecnicos;
	}

	@PostConstruct
	public void listar(){
		try {
			TecnicoDAO tecnicoDAO = new TecnicoDAO();
			
			tecnicos = tecnicoDAO.listar("dataAdmissao");
			
		}catch(RuntimeException erro){
			Messages.addGlobalError("Ocorreu um erro ao tentar listar o Tecnico");
			erro.printStackTrace();
		}
	}
	
	public void novo(){
		try {
			tecnico = new Tecnico();
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar um novo Tecnico");
			erro.printStackTrace();
		}
	}
	
	public void salvar(){
		try {
			TecnicoDAO tecnicoDAO = new TecnicoDAO();
			tecnicoDAO.merge(tecnico);
			
			tecnico = new Tecnico();
			tecnicos = tecnicoDAO.listar();
			
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();
			
			Messages.addGlobalInfo("Tecnico salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Tecnico");
			erro.printStackTrace();
		}
	}
	
	public void excluir(ActionEvent evento) {
		try {
			tecnico = (Tecnico) evento.getComponent().getAttributes().get("tecnicoSelecionado");

			TecnicoDAO tecnicoDAO = new TecnicoDAO();
			tecnicoDAO.excluir(tecnico);

			tecnicos = tecnicoDAO.listar();

			Messages.addGlobalInfo("Tecnico com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o Tecnico");
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento) {
	try {
		tecnico = (Tecnico) evento.getComponent().getAttributes().get("tecnicoSelecionado");

		TecnicoDAO tecnicoDAO = new TecnicoDAO();
		tecnicoDAO.excluir(tecnico);
		
	} catch (RuntimeException erro) {
		Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um Tecnico");
		erro.printStackTrace();
	}	
			

	}
}
