<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Buscar Factura</title>
</head>
<body>
<h2>Buscar Factura</h2>
<form action="buscar" method="post">
    <label>Número de Factura:</label>
    <input type="number" name="numeroFactura" required="true"/>
    <input type="submit" value="Buscar"/>
</form>

<c:if test="${not empty mensaje}">
    <p>${mensaje}</p>
</c:if>

<a href="/facturas">Crear nueva factura</a>
</body>
</html>
