package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "vendedores")
public class Vendedor extends Persona {
	
	@Column(name = "email")
	@Email
	private String email;
	
	@OneToOne(optional=false)
	private Bloqueo bloqueo;
	
	public String getEmail() {
		return email;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendedor", fetch = FetchType.EAGER)
	private Collection<Articulo> articulos;

	public void setEmail(String email) {
		this.email = email;
	}

	public Bloqueo getBloqueo() {
		return bloqueo;
	}

	public void setBloqueo(Bloqueo bloqueo) {
		this.bloqueo = bloqueo;
	}

	public Collection<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(Collection<Articulo> articulos) {
		this.articulos = articulos;
	}
	
	public void añadirArticulo(Articulo articulo) {
		getArticulos().add(articulo);
	}
}
