<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resumen de Factura</title>
</head>
<body>
<h2>Resumen de Factura</h2>

<!-- Tabla de Resumen -->
<table>
        <tr><th>Número de Factura</th><td>${numeroFactura}</td></tr>
        <tr><th>Concepto</th> <td>${concepto}</td></tr>
        <tr><th>Cantidad</th> <td>${cantidad}</td></tr>
        <tr><th>Porcentaje de Retención</th><td>${porcentajeRetencion}</td></tr>
        <tr><th>Total con Retención</th><td>${totalConRetencion}</td></tr>
</table>
 <h3>Listado de Todas las Facturas</h3>
 <c:if test="${empty facturas}">
     <p>No hay facturas disponibles.</p>
 </c:if>
  <c:if test="${!empty facturas}">
    <table border="1">
        <thead>
            <tr>
                <th>Número de Factura</th>
                <th>Concepto</th>
                <th>Cantidad</th>
                <th>Porcentaje de Retención</th>
                <th>Total con Retención</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="factu" items="${facturas}">
                <tr>
                    <td>${factu.numeroFactura}</td>
                    <td>${factu.concepto}</td>
                    <td>${factu.cantidad}</td>
                    <td>${factu.porcentajeRetencion}</td>
                    <td>
                        <c:set var="retencion" value="${factu.cantidad * factu.porcentajeRetencion / 100}"/>
                        <c:set var="total" value="${factu.cantidad - retencion}"/>
                        ${total}
                    </td>
                    <td>
                        <form action="editar" method="post" style="display:inline;">
                             <input type="hidden" name="numeroFactura" value="${factu.numeroFactura}"/>
                                <input type="hidden" name="concepto" value="${factu.concepto}"/>
                                <input type="hidden" name="cantidad" value="${factu.cantidad}"/>
                                <input type="hidden" name="porcentajeRetencion" value="${factu.porcentajeRetencion}"/>
                            <input type="submit" value="Editar"/>
                        </form>
                        <form action="eliminar" method="post" style="display:inline;">
                            <input type="hidden" name="numeroFactura" value="${factu.numeroFactura}"/>
                            <input type="submit" value="Eliminar"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
 </c:if>
    <a href="/facturas">Crear factura</a>
    <a href="buscar">Buscar facturas</a>
</body>
</html>
