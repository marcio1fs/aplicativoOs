package br.com.os.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.os.dao.FornecedorDAO;
import br.com.os.domain.Fornecedor;

@Path("fornecedor")
public class FornecedorService {
	
	// http://127.0.0.1:8080/AplicativoOs/rest/fornecedor
	@GET//protocolo de chamada de informações do banco
	public String listar(){
		FornecedorDAO fornecedorDAO =  new FornecedorDAO();
		List<Fornecedor> fornecedores = fornecedorDAO.listar("descricao");
		
		Gson gson = new Gson();
		String json = gson.toJson(fornecedores);
		
		return json;
	}
	
	// http://127.0.0.1:8080/AplicativoOs/rest/fornecedor/{codigo}
	// http://127.0.0.1:8080/AplicativoOs/rest/fornecedor/10
	@GET //listar 
	@Path("{codigo}")//define o nome do serviço a ser chamado
	public String buscar(@PathParam("codigo") Long codigo) {
		FornecedorDAO fornecedorDAO =  new FornecedorDAO();
		Fornecedor fornecedor = fornecedorDAO.buscar(codigo);
       
		
		//conversor do googlo Gson
		//converte o bojeto em string
		Gson gson = new Gson();
		String json = gson.toJson(fornecedor);

		return json;
	}
	
	//http://localhost:8080/AplicativoOs/rest/fabricante
	@POST //salvar
	public String salvar(String json) {
		Gson gson = new Gson();
		Fornecedor fornecedor = gson.fromJson(json, Fornecedor.class);

		FornecedorDAO fornecedorDAO =  new FornecedorDAO();
		fornecedorDAO.merge(fornecedor);

		String jsonSaida = gson.toJson(fornecedor);
		return jsonSaida;
	}
	
	
	// http://127.0.0.1:8080/AplicativoOs/rest/fabricante
		@PUT //editar
		public String editar(String json) {
			Gson gson = new Gson();
			Fornecedor fornecedor = gson.fromJson(json, Fornecedor.class);

			FornecedorDAO fornecedorDAO =  new FornecedorDAO();
			fornecedorDAO.editar(fornecedor);

			String jsonSaida = gson.toJson(fornecedor);
			return jsonSaida;
		}
	
		// http://127.0.0.1:8080/AplicativoOS/rest/fornecedor
		@DELETE
		public String excluir(String json){
			Gson gson = new Gson();
			Fornecedor fornecedor = gson.fromJson(json, Fornecedor.class);
			
			FornecedorDAO fornecedorDAO =  new FornecedorDAO();
			fornecedor = fornecedorDAO.buscar(fornecedor.getCodigo());
			fornecedorDAO.excluir(fornecedor);
			
			String saida = gson.toJson(fornecedor);
			return saida;
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
