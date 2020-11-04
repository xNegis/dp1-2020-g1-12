package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Transactional
	public int clienteCount() {
		return (int)clienteRepository.count();
	}
	
	@Transactional
	public Optional<Cliente> datosPerfil(Integer clienteId){
		Optional<Cliente> result = clienteRepository.findById(clienteId);
		return result;
	}
	
	public void guardar(Cliente cliente) {
		clienteRepository.save(cliente);		
	}
	
	@Transactional(readOnly = true)
	public Cliente findClientById(int id) throws DataAccessException {
		return clienteRepository.findById(id).get();
	}
	
	
}
