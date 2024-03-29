package org.springframework.samples.dpc.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.samples.dpc.model.Cliente;
import org.springframework.samples.dpc.model.Vendedor;
import org.springframework.samples.dpc.service.ClienteService;
import org.springframework.samples.dpc.service.VendedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	private final ClienteService clienteService;
	private final VendedorService vendedorService;
	
	@Autowired
	public ClienteController(ClienteService clienteService, VendedorService vendedorService) {
		this.clienteService = clienteService;
		this.vendedorService = vendedorService;
	}

	@GetMapping(value="/perfil")
	public String mostrarPerfil(ModelMap modelMap){
		log.info("Entrando en la función Mostrar Perfil del controlador de Cliente.");

		String  perfil="clientes/perfil";
		Cliente optperfil = clienteService.findClientById(clienteService.obtenerIdSesion());		
		modelMap.addAttribute("cliente", optperfil);
		return perfil;
	}
	
	@GetMapping(value = "/editar")
	public String editar(Model model) {
		log.info("Entrando en la función Editar Perfil del controlador de Cliente.");

		Cliente cliente = this.clienteService.findClientById(clienteService.obtenerIdSesion());
		model.addAttribute(cliente);
		return "clientes/editarPerfil";
	}

	@PostMapping(value = "/editar")
	public String procesoEditar(@Valid Cliente cliente, BindingResult result) {
		log.info("Entrando en la función Proceso Editar Perfil del controlador de Cliente.");
		
		if (result.hasErrors()) {
			return "clientes/editarPerfil";
		} else {
			this.clienteService.editar(cliente, clienteService.obtenerIdSesion());
			return "redirect:/clientes/perfil";
		}
	}
	
	@GetMapping()
	public String listadoCliente(@RequestParam(name = "clientPage", defaultValue = "0", required = false) Integer clientPage,
				@RequestParam(name = "clientSize", defaultValue = "10", required = false) Integer clientSize,
				@RequestParam(name = "orderClientBy", defaultValue = "nombre", required = false) String ordenCliente,
				@RequestParam(name = "sellerPage", defaultValue = "0", required = false) Integer sellerPage,
				@RequestParam(name = "sellerSize", defaultValue = "10", required = false) Integer sellerSize,
				@RequestParam(name = "orderSellerBy", defaultValue = "nombre", required = false) String ordenVendedor, ModelMap modelMap) {

		log.info("Entrando en la función Listado de Clientes del controlador de Cliente.");
		
		Page<Cliente> clientes = clienteService.findAllClient(clientPage, clientSize, ordenCliente);
		Page<Vendedor> vendedores = vendedorService.findAllSeller(sellerPage, sellerSize, ordenVendedor);
		String signoCliente = clientes.getSort().get().findAny().get().isAscending() ? "" : "-";		//Guardo el parámetro de ordenación para que al cambiar
		String ordenacionCliente = signoCliente + clientes.getSort().get().findAny().get().getProperty();	//de página se siga usando el filtro seleccionado
		String signoVendedor = vendedores.getSort().get().findAny().get().isAscending() ? "" : "-";		//Guardo el parámetro de ordenación para que al cambiar
		String ordenacionVendedor = signoVendedor + vendedores.getSort().get().findAny().get().getProperty();	//de página se siga usando el filtro seleccionado
		
		modelMap.addAttribute("clientes",clientes);
		modelMap.addAttribute("vendedores",vendedores);
		modelMap.addAttribute("ordenacionCliente", ordenacionCliente);
		modelMap.addAttribute("ordenacionVendedor", ordenacionVendedor);
		return "moderadores/listadoClientes";
	}
}
