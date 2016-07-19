package br.com.fiap.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;

import br.com.fiap.entity.Produtos;

public class ProdutosDao extends Dao {
	
	private String sql;
	private CachedRowSet crs;
	
	public void incluirProduto (Produtos produto) throws Exception {
		
		try {
			
			abrirConexao();
			cn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			cn.setAutoCommit(false);
			
			sql = "INSERT INTO PRODUTOS (NOME, VENDAS, PRECO) VALUES (?, 0, ?)";
			ps = cn.prepareStatement(sql);
			ps.setString(1, produto.getNome());
			ps.setDouble(2, produto.getPreco());
			ps.executeUpdate();
			
			cn.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			if (cn != null){
				try {
					JOptionPane.showMessageDialog(null, "Erro! Abortando transação.");
					cn.rollback();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Erro! Transação não pode ser abortada.");
					e1.printStackTrace();
				}
			}
			
		} finally {
			
			cn.setAutoCommit(true);
			fecharConexao();
			
		}
	}
	
	public Produtos buscarProduto (int id) throws Exception {
		
		Produtos produto =  null;
		try {
			
			abrirConexao();
			cn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			sql = "SELECT * FROM PRODUTOS WHERE IDPRODUTO = ?";
			ps = cn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);
			if (crs.next()) {
				
				produto = new Produtos();
				produto.setId(id);
				produto.setNome(crs.getString("NOME"));
				produto.setVendas(crs.getInt("VENDAS"));
				produto.setPreco(crs.getDouble("PRECO"));
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
			
		} finally {
			
			fecharConexao();
			
		}
		
		return produto;
		
	}
	public List<Produtos> listarProdutos() throws Exception {
		
		List<Produtos> produtos = new ArrayList<>();
		
		try {
			
			abrirConexao();
			cn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			sql = "SELECT * FROM PRODUTOS";
			ps = cn.prepareStatement(sql);
			rs = ps.executeQuery();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);
			while (crs.next()) {
				
				Produtos p = new Produtos();
				p.setId(crs.getInt("IDPRODUTO"));
				p.setNome(crs.getString("NOME"));
				p.setVendas(crs.getInt("VENDAS"));
				p.setPreco(crs.getDouble("PRECO"));
				
				produtos.add(p);
				
			}
			
		} catch (Exception e) {

			throw e;
			
		} finally {
			
			fecharConexao();
			
		}
		
		return produtos;
	}
}
