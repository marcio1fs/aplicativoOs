package br.com.os.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.os.dao.FabricanteDAO;
import br.com.os.dao.FornecedorDAO;
import br.com.os.dao.ProdutoDAO;
import br.com.os.domain.Fabricante;
import br.com.os.domain.FornecedorProduto;
import br.com.os.domain.Fornecedor;
import br.com.os.domain.Produto;
import br.com.os.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {
	private Produto produto;

	private List<Produto> produtos;

	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;
	private List<FornecedorProduto> fornProdutos;

	private StreamedContent foto;

	public List<FornecedorProduto> getFornProdutos() {
		return fornProdutos;
	}

	public void setFornProdutos(List<FornecedorProduto> fornProdutos) {
		this.fornProdutos = fornProdutos;
	}

	private List<Fabricante> fabricantes;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public StreamedContent getFoto() {
		return foto;
	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}

	@PostConstruct
	public void listar() {
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar("descricao");

			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar("nome");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os produtos");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			produto = new Produto();

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar("descricao");

			 FornecedorDAO fornecedorDAO = new FornecedorDAO();
		     fornecedores = fornecedorDAO.listar("codigo");

			fornProdutos = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um novo produto");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			produto.setCaminho("C:/Users/marcio/Desktop/Upload/" + produto.getCodigo() + ".png");

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();

			 FornecedorDAO fornecedorDAO = new FornecedorDAO();
			 fornecedores = fornecedorDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um produto");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (produto.getCaminho() == null) {
				Messages.addGlobalError("O campo foto é obrigatório");
				return;
			}

			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto produtoRetorno = produtoDAO.merge(produto);

			Path origem = Paths.get(produto.getCaminho());
			Path destino = Paths.get("C:/Users/marcio/Desktop/Upload/" + produtoRetorno.getCodigo() + ".png");
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			produto = new Produto();

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();

			 FornecedorDAO fornecedorDAO = new FornecedorDAO();
			 fornecedores = fornecedorDAO.listar();

			produtos = produtoDAO.listar();
			fornProdutos = new ArrayList<>();

			Messages.addGlobalInfo("Produto salvo com sucesso");
		} catch (RuntimeException | IOException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar o produto");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.excluir(produto);

			Path arquivo = Paths.get("C:/Users/marcio/Desktop/Upload/" + produto.getCodigo() + ".png");
			Files.deleteIfExists(arquivo);

			produtos = produtoDAO.listar();

			Messages.addGlobalInfo("Produto removido com sucesso");
		} catch (RuntimeException | IOException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o produto");
			erro.printStackTrace();
		}
	}

	public void upload(FileUploadEvent evento) {
		try {
			UploadedFile arquivoUpload = evento.getFile();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			produto.setCaminho(arquivoTemp.toString());

			Messages.addGlobalInfo("Upload realizado com sucesso");
		} catch (IOException erro) {
			Messages.addGlobalInfo("Ocorreu um erro ao tentar realizar o upload de arquivo");
			erro.printStackTrace();
		}
	}

	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String proDescricao = (String) filtros.get("descricao");
			String fabDescricao = (String) filtros.get("fabricante.descricao");
			String proReferencia = (String) filtros.get("produto.referencia");

			String caminho = Faces.getRealPath("/reports/produto.jasper");

			Map<String, Object> parametros = new HashMap<>();
			if (proDescricao == null) {
				parametros.put("PRODUTO_DESCRICAO", "%%");
			} else {
				parametros.put("PRODUTO_DESCRICAO", "%" + proDescricao + "%");
			}
			if (fabDescricao == null) {
				parametros.put("FABRICANTE_DESCRICAO", "%%");
			} else {
				parametros.put("FABRICANTE_DESCRICAO", "%" + fabDescricao + "%");
			}
			
			if (proReferencia == null) {
				parametros.put("PRODUTO_REFERENCIA", "%%");
			} else {
				parametros.put("PRODUTO_REFERENCIA", "%" + proDescricao + "%");
			}

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);

			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar o relatório");
			erro.printStackTrace();
		}
	}

	public void download(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			InputStream stream = new FileInputStream("C:/Users/marcio/Desktop/Upload/" + produto.getCodigo() + ".png");
			foto = new DefaultStreamedContent(stream, "image/png", produto.getCodigo() + ".png");
		} catch (FileNotFoundException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar efetuar o download da foto");
			erro.printStackTrace();
		}
	}

}
