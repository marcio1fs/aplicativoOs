package br.com.os.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.os.dao.ProdutoDAO;
import br.com.os.domain.Produto;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean2 implements Serializable {
	
	private Produto produto;
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@PostConstruct
	public void novo(){
		produto = new Produto();
	}
	
	public void buscar(){
		
		try {
			
			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto resultado = produtoDAO.buscar(produto.getCodigo());
			
			if(resultado == null){
				Messages.addGlobalWarn("Não existe produto cadastrado no estoque");
			}
			else{
				produto = resultado;
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar o produto");
			erro.printStackTrace();
			
		}
	}
}
