package br.com.os.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.os.dao.FabricanteDAO;
import br.com.os.domain.Fabricante;

@Path("fabricante")
public class FabricanteService {
	// http://127.0.0.1:8080/LojaVirtual/rest/fabricante
	@GET//protocolo de chamada de informações do banco
	public String listar() {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		List<Fabricante> fabricantes = fabricanteDAO.listar("descricao");

		Gson gson = new Gson();
		String json = gson.toJson(fabricantes);

		return json;
	}

	// http://127.0.0.1:8080/LojaVirtual/rest/fabricante/{codigo}
	// http://127.0.0.1:8080/LojaVirtual/rest/fabricante/10
	@GET //listar 
	@Path("{codigo}")//define o nome do serviço a ser chamado
	public String buscar(@PathParam("codigo") Long codigo) {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		Fabricante fabricante = fabricanteDAO.buscar(codigo);
       
		
		//conversor do googlo Gson
		//converte o bojeto em string
		Gson gson = new Gson();
		String json = gson.toJson(fabricante);

		return json;
	}

	// http://127.0.0.1:8080/LojaVirtual/rest/fabricante
	@POST //salvar
	public String salvar(String json) {
		Gson gson = new Gson();
		Fabricante fabricante = gson.fromJson(json, Fabricante.class);

		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		fabricanteDAO.merge(fabricante);

		String jsonSaida = gson.toJson(fabricante);
		return jsonSaida;
	}

	// http://127.0.0.1:8080/LojaVirtual/rest/fabricante
	@PUT //editar
	public String editar(String json) {
		Gson gson = new Gson();
		Fabricante fabricante = gson.fromJson(json, Fabricante.class);

		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		fabricanteDAO.editar(fabricante);

		String jsonSaida = gson.toJson(fabricante);
		return jsonSaida;
	}
	
	// http://127.0.0.1:8080/LojaVirtual/rest/fabricante
	@DELETE
	public String excluir(String json){
		Gson gson = new Gson();
		Fabricante fabricante = gson.fromJson(json, Fabricante.class);
		
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		fabricante = fabricanteDAO.buscar(fabricante.getCodigo());
		fabricanteDAO.excluir(fabricante);
		
		String saida = gson.toJson(fabricante);
		return saida;
	}
}
