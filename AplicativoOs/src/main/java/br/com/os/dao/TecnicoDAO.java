package br.com.os.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.os.domain.Tecnico;
import br.com.os.util.HibernateUtil;

public class TecnicoDAO extends GenericDAO<Tecnico> {
	
	//metodo para selecionar funcionario na tela de vendas 
		@SuppressWarnings("unchecked")
		public List<Tecnico> listarOrdenado() {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			try {
				Criteria consulta = sessao.createCriteria(Tecnico.class);
				consulta.createAlias("funcionario", "f");
				//alias consulta classe pessoa e captura o nome funcionario
				consulta.addOrder(Order.asc("f.nome"));
				List<Tecnico> resultado = consulta.list();
				return resultado;
			} catch (RuntimeException erro) {
				throw erro;
			} finally {
				sessao.close();
			}
		}

}
