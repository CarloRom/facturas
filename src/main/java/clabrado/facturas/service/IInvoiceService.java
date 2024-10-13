package clabrado.facturas.service;

import clabrado.facturas.modelo.Invoice;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IInvoiceService {
    List<Invoice> listarFacturas(HttpSession session);
    Invoice buscarFacturaPorNumero(Integer numeroFactura, HttpSession session);
    public void guardarFactura(Invoice invoice, HttpSession session);
    public Invoice editarFactura(Invoice invoice, HttpSession session);
    public void eliminarFactura(Invoice invoice, HttpSession session);


}
