<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Factura</title>
</head>
<body>
<h2>Crear Factura</h2>

<%-- Mostrar mensaje de error si existe --%>
<c:if test="${not empty error}">
    <div style="color: red;">${error}</div>
</c:if>

<form:form modelAttribute="invoice" action="guardar" method="post">
    <table>
        <tr>
            <td><label>Número de Factura:</label></td>
            <td><form:input path="numeroFactura" type="number" required="true"  min="1"/></td>
             <td><form:errors path="numeroFactura" cssClass="error"/></td>
        </tr>
        <tr>
            <td><label>Concepto:</label></td>
            <td><form:input path="concepto" required="true"/></td>
            <td><form:errors path="concepto" cssClass="error"/></td>
        </tr>
        <tr>
            <td><label>Cantidad:</label></td>
            <td><form:input path="cantidad" type="number" required="true" min="0" step="0.01"/></td>
            <td><form:errors path="cantidad" cssClass="error"/></td>
        </tr>
        <tr>
            <td><label>Porcentaje de Retención:</label></td>
            <td><form:input path="porcentajeRetencion"  type="number" required="true" min="0" step="0.01"/></td>
            <td><form:errors path="porcentajeRetencion" cssClass="error"/></td>
        </tr>
        <tr>
            <td colspan="2"> <input type="submit" value="Crear"/></td>
        </tr>
    </table>
</form:form>
<a href="buscar">Buscar Factura</a>
</body>
</html>
