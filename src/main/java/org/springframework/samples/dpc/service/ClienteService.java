package org.springframework.samples.dpc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.dpc.model.Bloqueo;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.repository.ClienteRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	private ClienteRepository clienteRepository;
	private UserService userService;
	private ArticuloService articuloService;
	private BloqueoService bloqueoService;

	@Autowired
	public ClienteService(ClienteRepository clienteRepository, UserService userService, 
			ArticuloService articuloService,@Lazy BloqueoService bloqueoService) {
		this.clienteRepository = clienteRepository;
		this.userService = userService;
		this.articuloService = articuloService;
		this.bloqueoService = bloqueoService;
	}

	@Transactional
	public Integer obtenerIdSesion() {
		return clienteRepository.clienteId(userService.obtenerUsername());
	}

	@Transactional
	public void guardar(Cliente cliente) {
		clienteRepository.save(cliente);
	}
	@Transactional
	public void registroCliente(Cliente cliente) {
		String cifrado = new BCryptPasswordEncoder().encode(cliente.getUser().getPassword());
		cliente.getUser().setPassword(cifrado);
		Cesta cesta = new Cesta();
		Bloqueo b = new Bloqueo();
		b.setBloqueado(false);
		bloqueoService.guardar(b);
		cliente.setBloqueo(b);
		cliente.getUser().setEnabled(true);
		cliente.setCesta(cesta);
	}

	@Transactional
	public void editar(Cliente cliente, Integer id) {
		Cliente clienteGuardado = findClientById(id);
		clienteGuardado.setApellido(cliente.getApellido());
		clienteGuardado.setDireccion(cliente.getDireccion());
		clienteGuardado.setDni(cliente.getDni());
		clienteGuardado.setEmail(cliente.getEmail());
		clienteGuardado.setNombre(cliente.getNombre());
		clienteGuardado.setTelefono(cliente.getTelefono());
	}

	@Transactional(readOnly = true)
	public Cliente findClientById(int id) throws DataAccessException {
		return (clienteRepository.findById(id).isPresent()) ? clienteRepository.findById(id).get() : null;
	}

	@Transactional(readOnly = true)
	public Page<Cliente> findAllClient(Integer page, Integer size, String orden) {
		Pageable pageable = articuloService.obtenerFiltros(page, size, orden, "clientes");
		return clienteRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Cliente findClientByDni(String dni) throws DataAccessException {
		return clienteRepository.findByDni(dni);
	}

	@Transactional(readOnly = true)
	public Cliente getClienteDeSesion() throws DataAccessException {
		return findClientById(obtenerIdSesion());
	}
	
	@Transactional(readOnly = true)
	public Bloqueo getBloqueoCliente(String username) throws DataAccessException {
		return clienteRepository.clienteBloqueo(username);
	}
}
