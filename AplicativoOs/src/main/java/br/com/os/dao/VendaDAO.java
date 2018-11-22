package br.com.os.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.os.domain.ItemVenda;
import br.com.os.domain.Produto;
import br.com.os.domain.Venda;
import br.com.os.util.HibernateUtil;

public class VendaDAO extends GenericDAO<Venda> {
	// metodo especifico para salvar venda
	public void salvar(Venda venda, List<ItemVenda> itensVenda) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		// trasaction garante que o itens não se perca
		// e a sessao é cancelada

		try {
			transacao = sessao.beginTransaction();

			sessao.save(venda);
			// metodo para salvar os itens da venda

			for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
				ItemVenda itemVenda = itensVenda.get(posicao);
				itemVenda.setVenda(venda);

				sessao.save(itemVenda);

				Produto produto = itemVenda.getProduto();
				int quantidade = produto.getQuantidade() - itemVenda.getQuantidade();
				if (quantidade >= 0) {
					produto.setQuantidade(new Short(quantidade + ""));
					sessao.update(produto);
				}else{
					throw new RuntimeException("Quantidade insuficiente em estoque");
				}

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
	
	
	
	// metodo especifico para salvar venda
		public void excluir(Venda venda, List<ItemVenda> itensVenda) {
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			Transaction transacao = null;
			// trasaction garante que o itens não se perca
			// e a sessao é cancelada

			try {
				transacao = sessao.beginTransaction();

				sessao.delete(venda);
				// metodo para salvar os itens da venda

				for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
					ItemVenda itemVenda = itensVenda.get(posicao);
					itemVenda.setVenda(venda);

					sessao.delete(itemVenda);

					Produto produto = itemVenda.getProduto();
					int quantidade = produto.getQuantidade() - itemVenda.getQuantidade();
					if (quantidade >= 0) {
						produto.setQuantidade(new Short(quantidade + ""));
						sessao.update(produto);
					}else{
						throw new RuntimeException("Quantidade insuficiente em estoque");
					}

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
