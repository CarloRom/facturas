package clabrado.facturas.controller;

import clabrado.facturas.modelo.Invoice;
import clabrado.facturas.service.IInvoiceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class InvoiceController {

    @Autowired
    private IInvoiceService invoiceService;

    @GetMapping("/")
    public String showInvoiceForm(Model model) {




        model.addAttribute("invoice", new Invoice());
        return "index"; // Devuelve la vista del formulario
    }

    @PostMapping("guardar") // Nueva acción para guardar la factura
    public String guardarFactura(@ModelAttribute("invoice")  Invoice invoice, BindingResult result, Model model, HttpSession session) {

        // Validar la factura
        if (!validarFactura(invoice, result, model, "index")) {
            return "index"; // Retorna al formulario si hay error
        }

        try {
            // Guardar la factura en la sesión
            invoiceService.guardarFactura(invoice, session);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage()); // Añadir el mensaje de error al modelo
            return "index"; // Retorna al formulario con el error
        }
        //Cálculos
        double retencion = (invoice.getCantidad() * invoice.getPorcentajeRetencion()) / 100;
        double totalConRetencion = invoice.getCantidad() - retencion;

        // Añadir datos a la vista
        model.addAttribute("numeroFactura", invoice.getNumeroFactura());
        model.addAttribute("concepto", invoice.getConcepto());
        model.addAttribute("cantidad", invoice.getCantidad());
        model.addAttribute("porcentajeRetencion", invoice.getPorcentajeRetencion());
        model.addAttribute("totalConRetencion", totalConRetencion);

        return mostrarResumen(model, session); // Redirige a resumen
    }
    @GetMapping("/editar")
    public String showEditForm(@RequestParam Integer numeroFactura, BindingResult result, Model model, HttpSession session) {
        Invoice invoice  = invoiceService.buscarFacturaPorNumero(numeroFactura, session);
        if (invoice != null || result.hasErrors()) {
            model.addAttribute("invoice", invoice);
            return "editar"; // Devuelve la vista de edición
        }
        model.addAttribute("mensaje", "Factura no encontrada");
        return "buscar"; // Vuelve a la vista de búsqueda si no se encuentra la factura
    }

    @PostMapping("/editar")
    public String editarFactura(@ModelAttribute("invoice") Invoice invoice, BindingResult result, Model model, HttpSession session) {

        // Buscar la factura y actualizarla con los datos enviados desde el formulario
        invoiceService.editarFactura(invoice, session);
        // Validar la factura
        if (!validarFactura(invoice, result, model, "index")) {
            return "editar"; // Retorna al formulario si hay error
        }
        model.addAttribute("mensaje", "Factura actualizada con éxito");
        return "editar"; // Redirige a resumen
    }



    @PostMapping("/resumir")
    private String mostrarActualResumen(@ModelAttribute("invoice") Invoice invoice, Model model, HttpSession session) {
       invoiceService.editarFactura(invoice, session);
        //List<Invoice> invoices = invoiceService.listarFacturas(session);
        //model.addAttribute("facturas", invoices);
        double retencion = (invoice.getCantidad() * invoice.getPorcentajeRetencion()) / 100;
        double totalConRetencion = invoice.getCantidad() - retencion;
        // Añadir datos a la vista
        model.addAttribute("numeroFactura", invoice.getNumeroFactura());
        model.addAttribute("concepto", invoice.getConcepto());
        model.addAttribute("cantidad", invoice.getCantidad());
        model.addAttribute("porcentajeRetencion", invoice.getPorcentajeRetencion());
        model.addAttribute("totalConRetencion", totalConRetencion);

        return mostrarResumen(model, session); // Redirige a resumen

    }

    private String mostrarResumen(Model model, HttpSession session) {
        List<Invoice> invoices = invoiceService.listarFacturas(session);
        model.addAttribute("facturas", invoices);
        return "resumen"; // Regresa a la vista de resumen
    }

    @GetMapping("/buscar")
    public String showSearchForm(Model model) {
        model.addAttribute("numeroFactura"); // Asegurarse de que esté en el modelo
        return "buscar"; // Vista para buscar factura
    }

    @PostMapping("/buscar")
    public String searchInvoice(@RequestParam Integer numeroFactura, Model model, HttpSession session) {
        Invoice invoice = invoiceService.buscarFacturaPorNumero(numeroFactura, session);
        if (invoice != null) {
            double retencion = (invoice.getCantidad() * invoice.getPorcentajeRetencion()) / 100;
            double totalConRetencion = invoice.getCantidad() - retencion;

            // Pasar datos a la vista
            model.addAttribute("numeroFactura", invoice.getNumeroFactura());
            model.addAttribute("concepto", invoice.getConcepto());
            model.addAttribute("cantidad", invoice.getCantidad());
            model.addAttribute("porcentajeRetencion", invoice.getPorcentajeRetencion());
            model.addAttribute("totalConRetencion", totalConRetencion);
            return mostrarResumen(model, session); //
            //return "resumen"; // Vista para mostrar resumen de factura
        }
        model.addAttribute("mensaje", "Factura no encontrada");
        return "buscar"; // Volver a la vista de búsqueda
    }

    @PostMapping("/eliminar")
    public String eliminarFactura(@RequestParam("numeroFactura") int numeroFactura, HttpSession session, Model model) {
        // Buscar la factura en la lista y eliminarla
        Invoice invoice = invoiceService.buscarFacturaPorNumero(numeroFactura, session);

        if (invoice != null) {
            invoiceService.eliminarFactura(invoice, session);
            model.addAttribute("mensaje", "Factura eliminada con éxito.");
        } else {
            model.addAttribute("error", "La factura no fue encontrada.");
        }

        // Redirigir al resumen después de eliminar
        return mostrarResumen(model, session);
    }

    private boolean validarFactura(Invoice invoice, BindingResult result, Model model, String pagina) {
        // Verificar errores de validación iniciales
        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor, completa todos los campos correctamente.");
            return false; // Retorna false si hay errores
        }

        // Validaciones adicionales
        if (invoice.getNumeroFactura() == null || invoice.getNumeroFactura() <= 0) {
            model.addAttribute("error", "El número de factura debe ser un número mayor que 0.");
            return false; // Retorna false si hay errores
        }
        if (invoice.getConcepto() == null || invoice.getConcepto().trim().isEmpty()) {
            model.addAttribute("error", "El concepto es obligatorio.");
            return false; // Retorna false si hay errores
        }
        if (invoice.getCantidad() == null || invoice.getCantidad() <= 0) {
            model.addAttribute("error", "La cantidad debe ser un número mayor que 0.");
            return false; // Retorna false si hay errores
        }
        if (invoice.getPorcentajeRetencion() == null || invoice.getPorcentajeRetencion() < 0) {
            model.addAttribute("error", "El porcentaje de retención no puede ser negativo.");
            return false; // Retorna false si hay errores
        }

        return true; // Retorna true si todas las validaciones son exitosas
    }

}
