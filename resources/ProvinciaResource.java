package com.edson.shop.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edson.shop.domain.Cidade;
import com.edson.shop.domain.Provincia;
import com.edson.shop.dto.CidadeDTO;
import com.edson.shop.dto.ProvinciaDTO;
import com.edson.shop.service.CidadeService;
import com.edson.shop.service.ProvinciaService;

@RestController
@RequestMapping(value="/provincias")
public class ProvinciaResource {
	
	@Autowired
	private ProvinciaService service;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ProvinciaDTO>> findAll() {
		List<Provincia> list = service.findAll();
		List<ProvinciaDTO> listDto = list.stream().map(obj -> new ProvinciaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/{ProvinciaId}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer ProvinciaId) {
		List<Cidade> list = cidadeService.findByProvincia(ProvinciaId);
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

}
