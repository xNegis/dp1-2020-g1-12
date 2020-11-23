package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.service.OfertaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("vendedores/{vendedorId}/ofertas")
public class OfertaController {

	@Autowired
	private OfertaService ofertaService;

	@GetMapping(value = "/{ofertaId}")
	public String editar(@PathVariable("ofertaId") int ofertaId, Model model) {
		Oferta oferta = this.ofertaService.findOfertById(ofertaId);
		model.addAttribute(oferta);
		return "vendedores/editarOferta";
	}

	@PostMapping(value = "/{ofertaId}")
	public String procesoOfertar(@Valid Oferta oferta, BindingResult result, @PathVariable("vendedorId") int vendedorId,
			@PathVariable("ofertaId") int ofertaId) {
		if (result.hasErrors()) {
			return "vendedores/editarOferta";
		} else {
			this.ofertaService.editar(oferta, ofertaId, true);
			return "redirect:/vendedores/{vendedorId}/articulosEnVenta";
		}
	}
	
	  @GetMapping(value = "/desofertar/{ofertaId}") 
	  public String procesoDesofertar(@Valid Oferta oferta, BindingResult result,
			  @PathVariable("ofertaId") int ofertaId) {
		  this.ofertaService.editar(oferta, ofertaId, false); 
		  return "redirect:/vendedores/{vendedorId}/articulosEnVenta";
	  }
	 
}
