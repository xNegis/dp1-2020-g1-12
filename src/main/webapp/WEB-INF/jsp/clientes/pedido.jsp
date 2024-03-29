<%@ page session="true" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="pedidos">
	    <h2><c:out value="Pedido con fecha ${pedido.fecha}"></c:out></h2>
		    <table style="text-align: center" id="pedidosTable" class="table table-striped">
		        <thead>
		        <tr>
		            <th style="width: 200px;text-align: center">Producto</th>
		            <th style="width: 100px;text-align: center">Estado</th>
		            <th style="width: 100px;text-align: center">Cantidad</th>
		            <th style="width: 100px;text-align: center">Precio unidad</th>
		        </tr>
		        </thead>
		        <tbody>
		      <c:forEach items="${lineas}" var="linea">
		            <tr>
		                <td>
							<spring:url value="/articulos/{articuloId}" var="articuloUrl">
								<spring:param name="articuloId" value="${linea.articulo.id}"/>
							</spring:url>
							<a href="${fn:escapeXml(articuloUrl)}"><c:out value="${linea.articulo.marca} ${linea.articulo.modelo}"></c:out></a>
		                </td>
		                <td>
		                	<c:if test="${linea.estado == 'EnReparto'}">
                    			<c:out value="En Reparto"/>
                    		</c:if>
                    		<c:if test="${linea.estado != 'EnReparto'}">
                    			<c:out value="${linea.estado}"/>
                    		</c:if>
		                </td>
		                <td>
							<c:out value="${linea.cantidad} unidades"></c:out>
		                </td>
						<td>
				           <fmt:formatNumber type="number" maxFractionDigits="2" value="${linea.precioUnitario}"/> €
						</td>
		            </tr>
		        </c:forEach>
		    <tr>
	        	<td></td>
	        	<td></td>
	        	<td>Gastos de envío:</td>
	        	<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${gastos}"/> €</td>
	        </tr> 
	        <tr>
	        	<td></td>
	        	<td></td>
	        	<td>Importe total:</td>
	        	<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${pedido.precioTotal}"/> €</td>
	        </tr> 
	        <tr>
	        	<td></td>
	        	<td></td>
	        	<td>Tarjeta de crédito utilizada:</td>
	        	<td>✱✱✱✱✱✱✱✱✱✱✱✱ ${pedido.tarjeta.numero.substring(12)}</td>
	        </tr> 
	        </tbody>
	    </table>
	<a href="/pedidos"><button class="btn btn-default" >Volver</button></a>
</dpc:layout>
