package org.springframework.samples.dpc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dpc.model.Cesta;
import org.springframework.samples.dpc.service.CestaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cesta")
public class CestaController {
	
	
	private final CestaService cestaService;
	
	@Autowired
	public CestaController(CestaService cestaService) {
		this.cestaService = cestaService;
	}
	
	@GetMapping()
	public String listadoCesta(ModelMap modelMap) {
		Cesta cesta = cestaService.obtenerCestaCliente();
		modelMap.addAttribute("cesta",cesta);
		return "clientes/cesta";
	}
	
	@GetMapping("/añadirArticulo/{articuloId}")
	public String anyadirArticuloCesta(ModelMap modelMap,@PathVariable("articuloId") int articuloId) {
		cestaService.anyadirLineaCesta(articuloId);
		return "redirect:/articulos/{articuloId}";
	}
	
	@GetMapping("/eliminar/{lineaId}")
	public String eliminarArticuloCesta(ModelMap modelMap,@PathVariable("lineaId") int lineaId) {
		cestaService.eliminarLineaCesta(lineaId);
		return "redirect:/cesta";
	}


}