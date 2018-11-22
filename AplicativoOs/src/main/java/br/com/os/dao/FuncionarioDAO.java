package br.com.os.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.os.domain.Funcionario;
import br.com.os.util.HibernateUtil;

public class FuncionarioDAO extends GenericDAO<Funcionario>{
	//metodo para selecionar funcionario na tela de vendas 
	@SuppressWarnings("unchecked")
	public List<Funcionario> listarOrdenado() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Funcionario.class);
			consulta.createAlias("pessoa", "p");
			//alias consulta classe pessoa e captura o nome funcionario
			consulta.addOrder(Order.asc("p.nome"));
			List<Funcionario> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
