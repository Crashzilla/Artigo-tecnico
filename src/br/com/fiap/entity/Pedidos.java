package br.com.fiap.entity;

import java.io.Serializable;
import java.sql.Date;

public class Pedidos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id, idProduto;
	private Date data;
	private String descricao;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
