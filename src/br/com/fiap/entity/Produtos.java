package br.com.fiap.entity;

import java.io.Serializable;

public class Produtos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id, vendas;
	private String nome;
	private double preco;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVendas() {
		return vendas;
	}
	public void setVendas(int vendas) {
		this.vendas = vendas;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
}
