package com.edson.shop.dto;

import java.io.Serializable;

import com.edson.shop.domain.Provincia;

public class ProvinciaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	public ProvinciaDTO() {
	}

	public ProvinciaDTO(Provincia obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
		

}
