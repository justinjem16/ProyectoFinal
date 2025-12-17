
package LogicaNegocio;
// Importa la entidad Nómina que contiene todos los datos salariales
import Entidades.Nomina;

// Librerías de iText para la generación de archivos PDF
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

// Manejo de archivos de salida
import java.io.FileOutputStream;

// Formato para mostrar montos en colones
import java.text.DecimalFormat;

/**
 * Clase responsable de generar los reportes en formato PDF
 * correspondientes a la nómina.
 * 
 * Genera dos tipos de documentos:
 * - Comprobante de pago para el empleado
 * - Reporte de cargas sociales para el patrono
 */

public class GeneradorPDF {
  /**
     * Formato estándar de moneda en colones costarricenses.
     * Ejemplo: ₡350,000.00
     */
    private static final DecimalFormat formatoMoneda =
            new DecimalFormat("₡#,##0.00");
    
    /**
     * Genera el comprobante de pago en PDF para el empleado.
     * 
     * El documento incluye:
     * - Datos del empleado
     * - Detalle de salario bruto
     * - Deducciones aplicadas
     * - Total de salario neto a pagar
     * 
     * @param nomina Objeto {@link Nomina} con toda la información salarial.
     * @return Nombre del archivo PDF generado.
     * @throws Exception Si ocurre algún error al crear el documento.
     */
    public String generarReporteEmpleado(Nomina nomina) throws Exception {

        // Construye el nombre del archivo usando cédula y período
        String nombreArchivo = "Nomina_" +
                nomina.getEmpleado().getCedula() + "_" +
                nomina.getPeriodo() + ".pdf";
        
        // Crea el documento PDF con tamaño carta
        Document document = new Document(PageSize.LETTER);

        // Asocia el documento con el archivo de salida
        PdfWriter.getInstance(
                document,
                new FileOutputStream(nombreArchivo)
        ); 

        // Abre el documento para comenzar a escribir
        document.open();
        
        /* =======================
         * TÍTULO DEL DOCUMENTO
         * ======================= */
        
        Font fontTitulo = new Font(
                Font.FontFamily.HELVETICA, 18, Font.BOLD
        );

        Paragraph titulo = new Paragraph(
                "COMPROBANTE DE PAGO DE SALARIO",
                fontTitulo
        );

        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);
        
        /* =======================
         * INFORMACIÓN DEL EMPLEADO
         * ======================= */
        
        Font fontNegrita = new Font(
                Font.FontFamily.HELVETICA, 10, Font.BOLD
        );

        Paragraph info = new Paragraph();

        info.add(new Chunk("Empleado: ", fontNegrita));
        info.add(nomina.getEmpleado().getNombreCompleto() + "\n");

        info.add(new Chunk("Cédula: ", fontNegrita));
        info.add(nomina.getEmpleado().getCedula() + "\n");

        info.add(new Chunk("Puesto: ", fontNegrita));
        info.add(nomina.getEmpleado().getPuesto() + "\n");

        info.add(new Chunk("Periodo: ", fontNegrita));
        info.add(nomina.getPeriodo() + "\n");

        info.setSpacingAfter(15);
        document.add(info);
        
        /* =======================
         * TABLA DE DETALLE
         * ======================= */
        
        // Crea una tabla de dos columnas
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        /* ---------- Encabezados ---------- */
        
        Font fontHeader = new Font(
                Font.FontFamily.HELVETICA,
                10,
                Font.BOLD,
                BaseColor.WHITE
        );

        PdfPCell headerConcepto =
                new PdfPCell(new Phrase("CONCEPTO", fontHeader));

        PdfPCell headerMonto =
                new PdfPCell(new Phrase("MONTO", fontHeader));

        headerConcepto.setBackgroundColor(BaseColor.DARK_GRAY);
        headerMonto.setBackgroundColor(BaseColor.DARK_GRAY);
        headerConcepto.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerMonto.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(headerConcepto);
        table.addCell(headerMonto);
        
        /* ---------- Salario Bruto ---------- */
        
        agregarFila(table, "Salario Bruto",
                nomina.getSalarioBruto(), true);
        
        /* ---------- Deducciones ---------- */
        
        PdfPCell subtitulo =
                new PdfPCell(new Phrase("DEDUCCIONES", fontNegrita));

        subtitulo.setBackgroundColor(BaseColor.LIGHT_GRAY);
        subtitulo.setPadding(5);

        table.addCell(subtitulo);
        table.addCell("");
        
        agregarFila(table,
                "  CCSS - IVM (4.17%)",
                nomina.getDeduccionIVM(), false);

        agregarFila(table,
                "  CCSS - SEM (6.50%)",
                nomina.getDeduccionSEM(), false);

        agregarFila(table,
                "  Banco Popular (1%)",
                nomina.getDeduccionBancoPop(), false);

        agregarFila(table,
                "  Impuesto Renta",
                nomina.getDeduccionImpuestoRenta(), false);

        agregarFila(table,
                "TOTAL DEDUCCIONES",
                nomina.getTotalDeducciones(), true);
        
        // Agrega la tabla al documento
        document.add(table);
        
        /* =======================
         * SALARIO NETO
         * ======================= */
        
        Font fontTotal = new Font(
                Font.FontFamily.HELVETICA, 14, Font.BOLD
        );

        Paragraph total = new Paragraph();
        total.setSpacingBefore(15);

        total.add(new Chunk(
                "SALARIO NETO A PAGAR: ",
                fontTotal
        ));

        total.add(new Chunk(
                formatoMoneda.format(nomina.getSalarioNeto()),
                fontTotal
        ));

        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);
        
        // Cierra el documento
        document.close();

        // Retorna el nombre del archivo generado
        return nombreArchivo;
    }
    
    /**
     * Genera el reporte PDF de cargas sociales patronales.
     * 
     * Este documento está orientado al empleador y muestra
     * todos los aportes patronales obligatorios.
     * 
     * @param nomina Objeto {@link Nomina} con los aportes calculados.
     * @return Nombre del archivo PDF generado.
     * @throws Exception Si ocurre algún error al generar el archivo.
     */
    public String generarReportePatrono(Nomina nomina) throws Exception {

        // Nombre del archivo patronal
        String nombreArchivo = "Patronal_" +
                nomina.getEmpleado().getCedula() + "_" +
                nomina.getPeriodo() + ".pdf";
        
        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(
                document,
                new FileOutputStream(nombreArchivo)
        );

        document.open();
        
        /* =======================
         * TÍTULO
         * ======================= */
        
        Font fontTitulo = new Font(
                Font.FontFamily.HELVETICA, 18, Font.BOLD
        );

        Paragraph titulo = new Paragraph(
                "REPORTE DE CARGAS SOCIALES PATRONALES",
                fontTitulo
        );

        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);
        
        /* =======================
         * INFORMACIÓN GENERAL
         * ======================= */
        
        Font fontNegrita = new Font(
                Font.FontFamily.HELVETICA, 10, Font.BOLD
        );

        Paragraph info = new Paragraph();

        info.add(new Chunk("Empleado: ", fontNegrita));
        info.add(nomina.getEmpleado().getNombreCompleto() + "\n");

        info.add(new Chunk("Periodo: ", fontNegrita));
        info.add(nomina.getPeriodo() + "\n");

        info.setSpacingAfter(15);
        document.add(info);
        
        /* =======================
         * TABLA DE APORTES
         * ======================= */
        
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        Font fontHeader = new Font(
                Font.FontFamily.HELVETICA,
                10,
                Font.BOLD,
                BaseColor.WHITE
        );

        PdfPCell headerConcepto =
                new PdfPCell(new Phrase("CONCEPTO", fontHeader));

        PdfPCell headerMonto =
                new PdfPCell(new Phrase("MONTO", fontHeader));

        headerConcepto.setBackgroundColor(BaseColor.DARK_GRAY);
        headerMonto.setBackgroundColor(BaseColor.DARK_GRAY);

        table.addCell(headerConcepto);
        table.addCell(headerMonto);
        
        agregarFila(table,
                "Salario Base",
                nomina.getSalarioBruto(), true);
        
        PdfPCell subtitulo =
                new PdfPCell(new Phrase("APORTES PATRONALES", fontNegrita));

        subtitulo.setBackgroundColor(BaseColor.LIGHT_GRAY);

        table.addCell(subtitulo);
        table.addCell("");
        
        agregarFila(table,
                "  CCSS - IVM (7.08%)",
                nomina.getAporteIVM(), false);

        agregarFila(table,
                "  CCSS - SEM (10.59%)",
                nomina.getAporteSEM(), false);

        agregarFila(table,
                "  INA (1.5%)",
                nomina.getAporteINA(), false);

        agregarFila(table,
                "  FCL (3%)",
                nomina.getAporteFCL(), false);

        agregarFila(table,
                "  Asignaciones (5%)",
                nomina.getAporteAsignaciones(), false);

        agregarFila(table,
                "TOTAL APORTES PATRONALES",
                nomina.getTotalAportesPatronales(), true);
        
        document.add(table);
        document.close();

        return nombreArchivo;
    }
    
    /**
     * Agrega una fila a una tabla PDF con formato estándar.
     * 
     * @param table Tabla a la que se agregará la fila.
     * @param concepto Descripción del rubro.
     * @param monto Valor monetario del rubro.
     * @param negrita Indica si la fila debe mostrarse en negrita.
     */
    private void agregarFila(
            PdfPTable table,
            String concepto,
            double monto,
            boolean negrita) {

        // Selecciona el tipo de fuente según el parámetro negrita
        Font font = negrita
                ? new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)
                : new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        
        PdfPCell cellConcepto =
                new PdfPCell(new Phrase(concepto, font));

        PdfPCell cellMonto =
                new PdfPCell(new Phrase(
                        formatoMoneda.format(monto), font
                ));
        
        cellConcepto.setPadding(5);
        cellMonto.setPadding(5);
        cellMonto.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        // Aplica color de fondo si la fila es destacada
        if (negrita) {
            cellConcepto.setBackgroundColor(
                    new BaseColor(240, 240, 240)
            );
            cellMonto.setBackgroundColor(
                    new BaseColor(240, 240, 240)
            );
        }
        
        table.addCell(cellConcepto);
        table.addCell(cellMonto);
    }  
}
