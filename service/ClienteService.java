package com.edson.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edson.shop.domain.Cidade;
import com.edson.shop.domain.Cliente;
import com.edson.shop.domain.Endereco;
import com.edson.shop.domain.enums.TipoCliente;
import com.edson.shop.dto.ClienteDTO;
import com.edson.shop.dto.ClienteNewDTO;
import com.edson.shop.repositories.ClienteRepository;
import com.edson.shop.repositories.EnderecoRepository;
import com.edson.shop.service.exceptions.DataIntegrityException;
import com.edson.shop.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//@Autowired
	//private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	public Cliente find(Integer id) {
			
		/*
		 * UserSS user = UserService.authenticated(); if (user==null ||
		 * !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) { throw new
		 * AuthorizationException("Acess negado!"); }
		 */
			
		Optional<Cliente> obj = repository.findById(id);
			
		return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto nao encontrado! id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente findByEmail(String email) {
		/*
		 * UserSS user = UserService.authenticated(); if(user==null ||
		 * !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) { throw new
		 * AuthorizationException("Acesso negado"); }
		 */
		Cliente obj = repository.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objecto nao encontrado! Id: Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Nao e possivel eliminar porque ha pedidos relacionadas.");
		}
	}
	
	public List<Cliente> findAll() {
		return repository.findAll();
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getNome());
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
		//return new Cliente(id, nome, email, tipo, senha);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) { 
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), TipoCliente.toEnum(objDto.getTipo()), objDto.getSenha());
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getBairro(), objDto.getReferencia(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	

}
