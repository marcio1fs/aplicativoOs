package br.com.os.bean;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.os.dao.UsuarioDAO;
import br.com.os.domain.Pessoa;
import br.com.os.domain.Usuario;

@ManagedBean
@SessionScoped
public class AutenticacaoBean {

	private Usuario usuario;
	private Usuario usuarioLogado;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
	}

	public void autenticar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioLogado = usuarioDAO.autenticar(usuario.getPessoa().getCpf(), usuario.getSenha());
			
			if(usuarioLogado == null){
				Messages.addGlobalError("CPF e/ou senha incorretos");
				return;
			}
			
			Faces.redirect("./paginas/os.xhtml");
		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError(erro.getMessage());
		}
	}
	

	public boolean temPermissoes(List<String> permissoes){		
		for(String permissao : permissoes){
			//if(usuario.getAtivoFormatado().compareToIgnoreCase(permissao) == permissao.charAt(0)){
			
			if(usuarioLogado.getTipoUsuario().compareTo(null) == permissao.charAt(0)){
		//	if(usuarioLogado.getAtivo().compare(false, true) == permissao.charAt(0)){
				return true;
			}
		}
		
		return true;
	}
}
