package org.springframework.samples.dpc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.model.LineaCesta;
import org.springframework.samples.dpc.repository.CestaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CestaService {

	private CestaRepository cestaRepository;
	private ClienteService clienteService;
	private UserService userService;
	private LineaCestaService lineaCestaService;

	@Autowired
	public CestaService(CestaRepository cestaRepository, ClienteService clienteService,
			LineaCestaService lineaCestaService, UserService userService) {
		this.cestaRepository = cestaRepository;
		this.clienteService = clienteService;
		this.lineaCestaService = lineaCestaService;
		this.userService = userService;
	}

	@Transactional
	public void crearCesta(Cesta cesta) {
		cestaRepository.save(cesta);
	}

	@Transactional
	public Cesta findCestaById(Integer cestaId) {
		Optional<Cesta> c = cestaRepository.findById(cestaId);
		return c.isPresent() ? c.get() : null;
	}

	@Transactional
	public Cesta obtenerCestaCliente() {
		return clienteService.getClienteDeSesion().getCesta();
	}

	@Transactional
	public void eliminarLineasCesta(List<LineaCesta> lineas) {
		int l = lineas.size();
		for (int i = 0; i < l; i++) {
			eliminarLineaCesta(lineas.get(0).getId());
		}
	}

	@Transactional
	public void anyadirLineaCesta(Integer articuloId) {
		lineaCestaService.crearLinea(articuloId, obtenerCestaCliente());
	}

	@Transactional
	public void eliminarLineaCesta(Integer lineaId) {
		LineaCesta lineaCesta = lineaCestaService.findLineaById(lineaId);
		if (obtenerCestaCliente().getLineas().contains(lineaCesta)) {
			obtenerCestaCliente().getLineas().remove(lineaCesta);
			lineaCestaService.eliminarLinea(lineaCesta);
		}
	}

	@Transactional(readOnly = true)
	public Boolean articuloEnCesta(Integer articuloId) {
		return userService.getAuthority().equals("cliente")
				? !obtenerCestaCliente().getLineas().stream().filter(x -> x.getArticulo().getId().equals(articuloId))
						.findFirst().isPresent()
				: false;
	}

	@Transactional
	public void actualizarCesta(Cesta cesta) {
		Cesta cestaSesion = obtenerCestaCliente();

		for (int i = 0; i <= cestaSesion.getLineas().size() - 1; i++) {
			if (cestaSesion.getLineas().get(i) != cesta.getLineas().get(i)) {
				lineaCestaService.findLineaById(cesta.getLineas().get(i).getId())
						.setCantidad(cesta.getLineas().get(i).getCantidad());
				cestaSesion.getLineas().get(i).setCantidad(cesta.getLineas().get(i).getCantidad());
			}
		}
	}
}
