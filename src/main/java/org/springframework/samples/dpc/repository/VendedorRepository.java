package org.springframework.samples.dpc.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.dpc.model.Vendedor;

public interface VendedorRepository extends CrudRepository<Vendedor, Integer> {

	@Query("select u.id from Vendedor u where u.user.username = :username")
	Integer vendedorId(@Param("username") String username) throws DataAccessException;

	@Query("SELECT DISTINCT u FROM Vendedor u WHERE u.dni LIKE :dni%")
	Vendedor findByDni(@Param("dni") String dni) throws DataAccessException;
	
	@Query("SELECT u.vendedor FROM Solicitud u WHERE u.articulo.id = :articuloId")
	Vendedor vendedorDeArticulo(@Param("articuloId") Integer articuloId) throws DataAccessException;
}