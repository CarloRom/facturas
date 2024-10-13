package clabrado.facturas.service;

import clabrado.facturas.modelo.Invoice;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService implements IInvoiceService {

    private static final String INVOICE_LIST = "invoiceList";

    @Override
    public List<Invoice> listarFacturas(HttpSession session) {
        List<Invoice> invoices = (List<Invoice>) session.getAttribute(INVOICE_LIST);
        return invoices != null ? invoices : new ArrayList<>(); // Retorna una lista vacía si no hay facturas
    }
    @Override
    public Invoice editarFactura(Invoice invoice, HttpSession session) {
        List<Invoice> invoices = listarFacturas(session);

        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).getNumeroFactura() == invoice.getNumeroFactura()) {
                // Actualizar la factura encontrada con los nuevos datos
                invoices.set(i, invoice);
                break; // Salir del bucle una vez que la factura ha sido actualizada

            }
        }
        System.out.println("Lista de facturas antes de guardar: " + invoices);
        // Guardar la lista actualizada de facturas en la sesión
        session.setAttribute(INVOICE_LIST, invoices);
        System.out.println("Lista de facturas después de guardar: " + session.getAttribute(INVOICE_LIST));


        // Asegurarse de que la lista está actualizada correctamente
        return invoice; // Retornar la factura actualizada para mayor claridad
    }
    @Override
    public Invoice buscarFacturaPorNumero(Integer numeroFactura, HttpSession session) {
        List<Invoice> invoices = listarFacturas(session);
        return invoices.stream()
                .filter(invoice -> invoice.getNumeroFactura() == numeroFactura)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void guardarFactura(Invoice invoice, HttpSession session) {
        List<Invoice> invoices = listarFacturas(session);
        // Verificar si la factura ya existe en la lista
        boolean exists = invoices.stream().anyMatch(inv -> inv.getNumeroFactura() == invoice.getNumeroFactura());

        if (!exists) {
            invoices.add(invoice);
        } else {

            // Opcional: Manejar el caso en que la factura ya existe
            // Puedes lanzar una excepción o simplemente registrar un mensaje de error.
            System.out.println("Factura con el número " + invoice.getNumeroFactura() + " ya existe.");
        }
            session.setAttribute(INVOICE_LIST, invoices);
    }


    @Override
    public void eliminarFactura(Invoice invoice, HttpSession session) {
        List<Invoice> invoices = listarFacturas(session);
        invoices.removeIf(inv -> inv.getNumeroFactura() == (invoice.getNumeroFactura()));
        session.setAttribute(INVOICE_LIST, invoices); // Actualizar la lista en la sesión
    }
}
