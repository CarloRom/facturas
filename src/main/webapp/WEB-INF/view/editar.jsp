<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Factura</title>
</head>
<body>
<h2>Editar Factura</h2>
<form:form modelAttribute="invoice" action="resumir" method="post">
<form:hidden path="numeroFactura"/>
    <table>

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
            <td colspan="2"> <input type="submit" value="Actualizar"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>
