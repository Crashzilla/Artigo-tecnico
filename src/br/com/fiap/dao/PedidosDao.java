package br.com.fiap.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;

import br.com.fiap.entity.Pedidos;

public class PedidosDao extends Dao {
	
	private String sql;
	private CachedRowSet crs;
	
	//Inclusão de Pedidos
	public void incluirPedido (Pedidos pedido) throws Exception {
		
		String insercao = "INSERT INTO PEDIDOS (IDPRODUTO, DATA, DESCRICAO) VALUES (?, ?, ?)";
		String atualizacao = "UPDATE PRODUTOS SET VENDAS = VENDAS + 1 WHERE IDPRODUTO = ?";
		
		try {
			abrirConexao();
			
			cn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			cn.setAutoCommit(false);
			
			PreparedStatement inserirPedidos = cn.prepareStatement(insercao);
			PreparedStatement atualizarVendas = cn.prepareStatement(atualizacao);
			
			inserirPedidos.setInt(1, pedido.getIdProduto());
			inserirPedidos.setDate(2, pedido.getData());
			inserirPedidos.setString(3, pedido.getDescricao());
			inserirPedidos.executeUpdate();
			
			atualizarVendas.setInt(1, pedido.getIdProduto());
			atualizarVendas.executeUpdate();
			
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
	
	//Busca de Pedidos pelo ID do Pedido
	public Pedidos buscarPedidosPorIdPed (int idPedido) throws Exception {
		
		Pedidos pedido = null;
		try {
			
			abrirConexao();
			cn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			sql = "SELECT * FROM PEDIDOS WHERE IDPEDIDO = ?";
			ps = cn.prepareStatement(sql);
			ps.setInt(1, idPedido);
			rs = ps.executeQuery();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);
			if (crs.next()) {
				
				pedido = new Pedidos();
				pedido.setId(idPedido);
				pedido.setIdProduto(crs.getInt("IDPRODUTO"));
				pedido.setData(crs.getDate("DATA"));
				pedido.setDescricao(crs.getString("DESCRICAO"));
				
			}
			
		} catch (Exception e) {
			
		} finally {
			
			fecharConexao();
			
		}
		
		return pedido;
		
	}
	
	//Busca de Pedidos pelo ID do Produto
	public List<Pedidos> buscarPedidosPorIdProd (int idProduto) throws Exception {
		
		List<Pedidos> pedidos = new ArrayList<>();
		
		try {
			
			abrirConexao();
			cn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			sql = "SELECT * FROM PEDIDOS WHERE IDPRODUTO = ?";
			ps = cn.prepareStatement(sql);
			ps.setInt(1, idProduto);
			rs = ps.executeQuery();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);
			while (crs.next()) {
				
				Pedidos p = new Pedidos();
				p.setId(crs.getInt("IDPEDIDO"));
				p.setIdProduto(idProduto);
				p.setData(crs.getDate("DATA"));
				p.setDescricao(crs.getString("DESCRICAO"));
				
				pedidos.add(p);
			}
			
		} catch (Exception e) {
			
		} finally {
			
			fecharConexao();
			
		}
		
		return pedidos;
		
	}
	
	//Lista de Todos os Pedidos
	public List<Pedidos> listarPedidos () throws Exception {
		
		List<Pedidos> pedidos = new ArrayList<>();
		
		try {
			
			abrirConexao();
			cn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			sql = "SELECT * FROM PEDIDOS";
			ps = cn.prepareStatement(sql);
			rs = ps.executeQuery();
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(rs);
			while (crs.next()) {
				
				Pedidos p = new Pedidos();
				p.setId(crs.getInt("IDPEDIDO"));
				p.setIdProduto(crs.getInt("IDPRODUTO"));
				p.setData(crs.getDate("DATA"));
				p.setDescricao(crs.getString("DESCRICAO"));
				
				pedidos.add(p);
			}
			
		} catch (Exception e) {
			
		} finally {
			
			fecharConexao();
			
		}
		
		return pedidos;
		
	}
}
