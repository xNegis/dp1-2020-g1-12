package org.springframework.samples.dpc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dpc.model.Articulo;
import org.springframework.samples.dpc.model.Comentario;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ComentarioServiceTest {
	
	private final Integer TEST_COMENTARIO_ID = 1;
	private final Integer TEST_ARTICULO_ID = 1;

	private final ComentarioService comentarioService;
	private final ArticuloService articuloService;
	private Comentario comentario = new Comentario();
	
	@Autowired
	public ComentarioServiceTest(ComentarioService comentarioService, ArticuloService articuloService) {
		this.comentarioService = comentarioService;
		this.articuloService = articuloService;
	}
	
	public void inicializa() {
		Articulo articulo = this.articuloService.findArticuloById(TEST_ARTICULO_ID);
		this.comentario.setArticulo(articulo);
		this.comentario.setId(TEST_COMENTARIO_ID);
		this.comentario.setDescripcion("ASDFGHJKLÑQWERTY");
		this.comentario.setValoracion(3);  
	}
	
//	@Test
//	void testGuardarComentarioModerador() throws ComentarioProhibidoException{
//		inicializa();
//		Comentario comentario = this.comentario;
//		Moderador moderador = this.moderadorService.findModeradorById(TEST_MODERADOR_ID);
//		comentario.setModerador(moderador);
//		this.comentarioService.guardarComentario(comentario, TEST_ARTICULO_ID);
//		assertThat(comentario.getValoracion()).isZero();
//	}
	
	@Test 
	void testObtenerComentariosArticulo() {
		List<Comentario> comentarios = this.comentarioService.getComentariosDeUnArticulo(TEST_ARTICULO_ID);
		assertThat(comentarios.size()).isEqualTo(4);
		assertThat(comentarios.get(0).getId()).isEqualTo(1);
	}
	
	@Test 
	void testObtenerValoracionMedia() {
		Double valoracion = this.comentarioService.getValoracionDeUnArticulo(TEST_ARTICULO_ID);
		assertThat(valoracion).isEqualTo((double) 3);
	}
	
	@Test
	void testEliminarComentario() {
		Comentario comentario = this.comentarioService.findCommentById(TEST_COMENTARIO_ID);
		this.comentarioService.eliminarComentario(comentario);
		List<Comentario> comentarios = this.comentarioService.getComentariosDeUnArticulo(TEST_ARTICULO_ID);
		assertThat(comentarios.size()).isEqualTo(3);
	}
}
