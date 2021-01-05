<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dpc" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<dpc:layout pageName="articulosVendidos">
    
	<form action="/vendedores/articulosVendidos?" method="get">
		<div style="float:right">
			<input type="hidden" name="page" value="${lineaPedido.getNumber()}"/>
			<input type="hidden" name="size" value="${lineaPedido.getSize()}"/>			
	 		<select onchange="this.form.submit();" name="orderBy">
	       		<option value="" disabled selected>Ordenar por:</option>
	 			<option value="-id">Más recientes</option>
	 			<option value="id">Más antiguos</option>
	 			<option value="marca">Nombre A-Z</option>
	 			<option value="-marca">Nombre Z-A</option>
	 		</select>
		</div>
	</form>
    <h2>Lista de artículos vendidos</h2>

    <table id="articulosVendidosTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 180px;">Artículo</th>
            <th style="width: 80px">Cantidad</th>
        </tr>
        </thead>
        <tbody>
      <c:forEach items="${lineaPedido.getContent()}" var="lp">
            <tr>
                <td>
					<c:out value="${lp.articulo.marca} ${lp.articulo.modelo}"/>
                </td>
                <td>
                    <c:out value="${lp.cantidad}"/>
                </td>
            </tr>
        </c:forEach> 
        </tbody>
    </table>
	<div class="container">
		<div class="row text-center">
			<form action="/vendedores/articulosVendidos?" method="get">
				<input type="hidden" name="page" value="${lineaPedido.getNumber()}"/>
				<input type="hidden" name="orderBy" value="${ordenacion}"/>
				<label for="size">Elementos por página: </label>
				<input onchange="this.form.submit();" style="border-radius: 5px; width:10%; text-align: center"
				 type="number" min="2" name="size" value="${lineaPedido.getSize()}">
			 </form>
			<br><br>
			<div class="col-12 text-center">
				<c:forEach begin="0" end="${lineaPedido.getTotalPages()-1}" varStatus="page">
					<c:if test="${page.index != lineaPedido.getNumber()}">
						<spring:url value="/vendedores/articulosVendidos?" var="siguienteUrl">
							<spring:param name="page" value="${page.index}"/>
							<spring:param name="size" value="${lineaPedido.getSize()}"/>
							<spring:param name="orderBy" value="${ordenacion}"/>									
						</spring:url>
						
						<a href="${fn:escapeXml(siguienteUrl)}">
							<button class="btn btn-default" type="submit">${page.index+1}</button>
						</a>
					</c:if> 
					<c:if test="${page.index == lineaPedido.getNumber()}">
						<button class="btn btn-default" disabled>${page.index+1}</button>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
</dpc:layout>
