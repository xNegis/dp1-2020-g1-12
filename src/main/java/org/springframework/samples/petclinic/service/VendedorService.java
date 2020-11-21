package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Articulo;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.repository.VendedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendedorService {

	@Autowired
	private VendedorRepository vendedorRepository;

	@Transactional
	public int vendedorCount() {
		return (int) vendedorRepository.count();
	}

	@Transactional
	public Optional<Vendedor> datosPerfil(Integer vendedorId) {
		Optional<Vendedor> result = vendedorRepository.findById(vendedorId);
		return result;
	}

	@Transactional
	public void guardar(Vendedor vendedor) {
		vendedorRepository.save(vendedor);
	}
	
	@Transactional
	public void editar(Vendedor vendedor, Integer id) {
		Vendedor vendedorGuardado = findSellerById(id);
		vendedorGuardado.setApellido(vendedor.getApellido());
		vendedorGuardado.setDireccion(vendedor.getDireccion());
		vendedorGuardado.setDni(vendedor.getDni());
		vendedorGuardado.setEmail(vendedor.getEmail());
		vendedorGuardado.setNombre(vendedor.getNombre());
		vendedorGuardado.setTelefono(vendedor.getTelefono());
	}

	@Transactional(readOnly = true)
	public Vendedor findSellerById(int id) throws DataAccessException {
		return vendedorRepository.findById(id).get();
	}

	public Iterable<Vendedor> findAllSeller() {
		Iterable<Vendedor> result = vendedorRepository.findAll();
		return result;
	}
	
	@Transactional(readOnly = true)
	public Iterable<Articulo> findArticlesByProvider(Integer id) {
		return findSellerById(id).getArticulos();
	}

}
