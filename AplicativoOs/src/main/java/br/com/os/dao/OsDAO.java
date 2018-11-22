package br.com.os.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.os.domain.ItemOs;
import br.com.os.domain.ItensServico;
import br.com.os.domain.Os;
import br.com.os.domain.Produto;
import br.com.os.util.HibernateUtil;

public class OsDAO extends GenericDAO<Os> {

	public void salvar(Os os, List<ItensServico> itensServicos) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		// trasaction garante que o itens não se perca
		// e a sessao é cancelada

		try {
			transacao = sessao.beginTransaction();
			sessao.save(os);
			// metodo para salvar os itens da venda

			for (int posicao = 0; posicao < itensServicos.size(); posicao++) {
				ItensServico itemServico = itensServicos.get(posicao);
				itemServico.setOs(os);

				sessao.save(itemServico);
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

	public void salvarOs(Os os, List<ItemOs> itensOs) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		// trasaction garante que o itens não se perca
		// e a sessao é cancelada

		try {
			transacao = sessao.beginTransaction();
			sessao.save(os);
			// metodo para salvar os itens da venda

			for (int posicao = 0; posicao < itensOs.size(); posicao++) {
				ItemOs item = itensOs.get(posicao);
				item.setOs(os);

				sessao.save(item);

				Produto produto = item.getProduto();
				int quantidade = produto.getQuantidade() - item.getQuantidade();
				if (quantidade >= 0) {
					produto.setQuantidade(new Short(quantidade + ""));
					sessao.update(produto);
				} else {
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
