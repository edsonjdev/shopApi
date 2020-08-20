package com.edson.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edson.shop.domain.Provincia;
import com.edson.shop.repositories.ProvinciaRepository;

@Service
public class ProvinciaService {
	
	@Autowired
	private ProvinciaRepository repo;
	
	public List<Provincia> findAll() {
		return repo.findAllByOrderByNome();
	}

}
