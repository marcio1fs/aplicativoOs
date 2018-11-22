package br.com.os.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.os.domain.FornecedorProduto;
import br.com.os.domain.Produto;
import br.com.os.util.HibernateUtil;

public class ProdutoDAO extends GenericDAO<Produto> {
	// funcionalidade especifica para popular o combo dependente de pessoas

	@SuppressWarnings("unchecked")
	public List<Produto> buscarPorProduto(long fornecedorCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Produto.class);
			consulta.add(Restrictions.eq("fornecedor.codigo", fornecedorCodigo));
			// restrictions.eq compara chave estrangeira
			consulta.addOrder(Order.asc("nome"));
			List<Produto> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	//metodo especifico para salvar venda 
		public void salvar(Produto produto, List<FornecedorProduto> fornProdutos){
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			Transaction transacao = null;
			//trasaction garante que o itens não se perca
			//e a sessao é cancelada

			try {
				transacao = sessao.beginTransaction();
			
				sessao.save(produto);
				//metodo para salvar os itens da venda 
				
				for(int posicao = 0; posicao < fornProdutos.size(); posicao++){
					FornecedorProduto fornProduto = fornProdutos.get(posicao);
					fornProduto.setProduto(produto);
				
					
					sessao.save(fornProduto);
				}
				
				transacao.commit();
			} catch (RuntimeException erro) {
				if (transacao != null) {
					transacao.rollback();
				}
				throw erro;
			} finally {
				sessao.close();
			}
		}

		public void excluir(Produto produto, List<FornecedorProduto> fornProdutos){
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			Transaction transacao = null;
			//trasaction garante que o itens não se perca
			//e a sessao é cancelada

			try {
				transacao = sessao.beginTransaction();
			
				sessao.delete(produto);
				//metodo para salvar os itens da venda 
				
				for(int posicao = 0; posicao < fornProdutos.size(); posicao++){
					FornecedorProduto fornProduto = fornProdutos.get(posicao);
					fornProduto.setProduto(produto);
				
					
					sessao.delete(fornProduto);
				}
				
				transacao.commit();
			} catch (RuntimeException erro) {
				if (transacao != null) {
					transacao.rollback();
				}
				throw erro;
			} finally {
				sessao.close();
			}
		}
	
}