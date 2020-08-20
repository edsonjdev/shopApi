package com.edson.shop.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.edson.shop.domain.Cliente;
import com.edson.shop.domain.enums.TipoCliente;
import com.edson.shop.dto.ClienteNewDTO;
import com.edson.shop.repositories.ClienteRepository;
import com.edson.shop.resources.exception.FieldMessage;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
	
		List<FieldMessage> list = new ArrayList<>();
		// inclua os testes aqui, inserindo erros na lista
		//cpf
		/*
		 * if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) &&
		 * !BR.isValidCPF(objDto.getCpfOucnpj())) { list.add(new
		 * FieldMessage("cpfOuCnpj", "CPF invalido")); }
		 * 
		 * //cnpj if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) &&
		 * !BR.isValidCNPJ(objDto.getCpfOucnpj())) { list.add(new
		 * FieldMessage("cpfOuCnpj", "CNPJ invalido")); }
		 */
		
		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "E-mail ja existente"));
		}
	
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
			}
		return list.isEmpty();
	}

}
