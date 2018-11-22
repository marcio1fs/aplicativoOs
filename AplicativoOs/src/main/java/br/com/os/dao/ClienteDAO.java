package br.com.os.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.os.domain.Cliente;
import br.com.os.util.HibernateUtil;

public class ClienteDAO extends GenericDAO<Cliente> {
	//metodo para selecionar cliente na tela de vendas 
	@SuppressWarnings("unchecked")
	public List<Cliente> listarOrdenado() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cliente.class);
			consulta.createAlias("pessoa", "p");
			//alias consulta classe pessoa e captura o nome cliente 
			consulta.addOrder(Order.asc("p.nome"));
			List<Cliente> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
