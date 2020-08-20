package com.edson.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edson.shop.domain.Cidade;
import com.edson.shop.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repository;
	
	public List<Cidade> findByProvincia(Integer provinciaId) {
		return repository.findCidades(provinciaId);
	}

}
