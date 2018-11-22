package br.com.os.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.os.domain.Cidade;
import br.com.os.util.HibernateUtil;



public class CidadeDAO extends GenericDAO<Cidade> {
	//funcionalidade especifica para popular o combo dependente de pessoas
	
	@SuppressWarnings("unchecked")
	//metodo recebe chave strangeira  retorna lista de cidades 
	public List<Cidade> buscarPorEstado(Long estadoCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.add(Restrictions.eq("estado.codigo", estadoCodigo));
			//restrictions.eq compara chave estrangeira 
			consulta.addOrder(Order.asc("nome"));
			List<Cidade> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}