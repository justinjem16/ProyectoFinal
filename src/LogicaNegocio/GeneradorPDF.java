/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaNegocio;

import Entidades.Nomina;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List; // NUEVO IMPORT

/**
 * Generador de reportes PDF para nóminas.
 * 
 * @author Rachell Mora Reyes
 */
public class GeneradorPDF {
    
    private static final DecimalFormat formatoMoneda = new DecimalFormat("₡#,##0.00");
    
    /**
     * Genera el reporte PDF para el empleado.
     */
    public String generarReporteEmpleado(Nomina nomina) throws Exception {
        String nombreArchivo = "Nomina_" + nomina.getEmpleado().getCedula() + 
                               "_" + nomina.getPeriodo() + ".pdf";
        
        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo)); 
        document.open();
        
        // Título
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph("COMPROBANTE DE PAGO DE SALARIO", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);
        
        // Información empleado
        Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
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
        
        // Tabla de detalle
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        // Encabezados
        Font fontHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
        PdfPCell headerConcepto = new PdfPCell(new Phrase("CONCEPTO", fontHeader));
        PdfPCell headerMonto = new PdfPCell(new Phrase("MONTO", fontHeader));
        headerConcepto.setBackgroundColor(BaseColor.DARK_GRAY);
        headerMonto.setBackgroundColor(BaseColor.DARK_GRAY);
        headerConcepto.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerMonto.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerConcepto);
        table.addCell(headerMonto);
        
        // Salario bruto
        agregarFila(table, "Salario Bruto", nomina.getSalarioBruto(), true);
        
        // Deducciones
        PdfPCell subtitulo = new PdfPCell(new Phrase("DEDUCCIONES", fontNegrita));
        subtitulo.setBackgroundColor(BaseColor.LIGHT_GRAY);
        subtitulo.setPadding(5);
        table.addCell(subtitulo);
        table.addCell("");
        
        agregarFila(table, "  CCSS - IVM (4.17%)", nomina.getDeduccionIVM(), false);
        agregarFila(table, "  CCSS - SEM (6.50%)", nomina.getDeduccionSEM(), false);
        agregarFila(table, "  Banco Popular (1%)", nomina.getDeduccionBancoPop(), false);
        agregarFila(table, "  Impuesto Renta", nomina.getDeduccionImpuestoRenta(), false);
        agregarFila(table, "TOTAL DEDUCCIONES", nomina.getTotalDeducciones(), true);
        
        document.add(table);
        
        // Total
        Font fontTotal = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph total = new Paragraph();
        total.setSpacingBefore(15);
        total.add(new Chunk("SALARIO NETO A PAGAR: ", fontTotal));
        total.add(new Chunk(formatoMoneda.format(nomina.getSalarioNeto()), fontTotal));
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);
        
        document.close();
        return nombreArchivo;
    }
    
    /**
     * Genera el reporte PDF para el patrono (INDIVIDUAL - mantiene funcionalidad original).
     * Este método se mantiene para compatibilidad con código existente.
     */
    public String generarReportePatrono(Nomina nomina) throws Exception {
        String nombreArchivo = "Patronal_" + nomina.getEmpleado().getCedula() + 
                               "_" + nomina.getPeriodo() + ".pdf";
        
        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
        document.open();
        
        // Título
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph("REPORTE DE CARGAS SOCIALES PATRONALES", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);
        
        // Información empleado
        Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Paragraph info = new Paragraph();
        info.add(new Chunk("Empleado: ", fontNegrita));
        info.add(nomina.getEmpleado().getNombreCompleto() + "\n");
        info.add(new Chunk("Periodo: ", fontNegrita));
        info.add(nomina.getPeriodo() + "\n");
        info.setSpacingAfter(15);
        document.add(info);
        
        // Tabla de aportes
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        // Encabezados
        Font fontHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
        PdfPCell headerConcepto = new PdfPCell(new Phrase("CONCEPTO", fontHeader));
        PdfPCell headerMonto = new PdfPCell(new Phrase("MONTO", fontHeader));
        headerConcepto.setBackgroundColor(BaseColor.DARK_GRAY);
        headerMonto.setBackgroundColor(BaseColor.DARK_GRAY);
        table.addCell(headerConcepto);
        table.addCell(headerMonto);
        
        agregarFila(table, "Salario Base", nomina.getSalarioBruto(), true);
        
        PdfPCell subtitulo = new PdfPCell(new Phrase("APORTES PATRONALES", fontNegrita));
        subtitulo.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(subtitulo);
        table.addCell("");
        
        agregarFila(table, "  CCSS - IVM (7.08%)", nomina.getAporteIVM(), false);
        agregarFila(table, "  CCSS - SEM (10.59%)", nomina.getAporteSEM(), false);
        agregarFila(table, "  INA (1.5%)", nomina.getAporteINA(), false);
        agregarFila(table, "  FCL (3%)", nomina.getAporteFCL(), false);
        agregarFila(table, "  Asignaciones (5%)", nomina.getAporteAsignaciones(), false);
        agregarFila(table, "TOTAL APORTES PATRONALES", nomina.getTotalAportesPatronales(), true);
        
        document.add(table);
        
        document.close();
        return nombreArchivo;
    }
    
    /**
     * NUEVO MÉTODO: Genera el reporte PDF consolidado mensual para el patrono.
     * Este reporte incluye TODOS los empleados de la planilla y suma los totales.
     * 
     * @param listaNominas Lista de todas las nóminas calculadas del periodo
     * @param periodo Periodo de la nómina (ej: "Enero 2024")
     * @return Nombre del archivo PDF generado
     */
    public String generarReportePatronoMensual(List<Nomina> listaNominas, String periodo) throws Exception {
        String nombreArchivo = "Patronal_Mensual_" + periodo.replace(" ", "_") + ".pdf";
        
        Document document = new Document(PageSize.LETTER, 36, 36, 54, 54); // Márgenes ajustados
        PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
        document.open();
        
        // ===== TÍTULO PRINCIPAL =====
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Paragraph titulo = new Paragraph("REPORTE CONSOLIDADO DE CARGAS SOCIALES PATRONALES", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(10);
        document.add(titulo);
        
        // ===== INFORMACIÓN DEL PERIODO =====
        Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph subtitulo = new Paragraph("Periodo: " + periodo, fontSubtitulo);
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setSpacingAfter(20);
        document.add(subtitulo);
        
        // ===== TABLA DE DETALLE POR EMPLEADO =====
        PdfPTable tableDetalle = new PdfPTable(7);
        tableDetalle.setWidthPercentage(100);
        tableDetalle.setWidths(new float[]{2.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f});
        
        // Encabezados de la tabla
        Font fontHeader = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
        agregarCeldaHeader(tableDetalle, "EMPLEADO", fontHeader);
        agregarCeldaHeader(tableDetalle, "SALARIO", fontHeader);
        agregarCeldaHeader(tableDetalle, "CCSS", fontHeader);
        agregarCeldaHeader(tableDetalle, "INA", fontHeader);
        agregarCeldaHeader(tableDetalle, "FCL", fontHeader);
        agregarCeldaHeader(tableDetalle, "ASIGNACIONES", fontHeader);
        agregarCeldaHeader(tableDetalle, "TOTAL APORTES", fontHeader);
        
        // Variables para acumular totales
        double totalSalarios = 0;
        double totalCCSS = 0;
        double totalINA = 0;
        double totalFCL = 0;
        double totalAsignaciones = 0;
        double totalAportes = 0;
        
        // Agregar fila por cada empleado
        Font fontDatos = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
        for (Nomina nomina : listaNominas) {
            double aportesCCSS = nomina.getAporteIVM() + nomina.getAporteSEM();
            double totalAportesEmpleado = nomina.getTotalAportesPatronales();
            
            // Acumular totales
            totalSalarios += nomina.getSalarioBruto();
            totalCCSS += aportesCCSS;
            totalINA += nomina.getAporteINA();
            totalFCL += nomina.getAporteFCL();
            totalAsignaciones += nomina.getAporteAsignaciones();
            totalAportes += totalAportesEmpleado;
            
            // Agregar fila
            agregarCeldaDatos(tableDetalle, nomina.getEmpleado().getNombreCompleto(), fontDatos, Element.ALIGN_LEFT);
            agregarCeldaDatos(tableDetalle, formatoMoneda.format(nomina.getSalarioBruto()), fontDatos, Element.ALIGN_RIGHT);
            agregarCeldaDatos(tableDetalle, formatoMoneda.format(aportesCCSS), fontDatos, Element.ALIGN_RIGHT);
            agregarCeldaDatos(tableDetalle, formatoMoneda.format(nomina.getAporteINA()), fontDatos, Element.ALIGN_RIGHT);
            agregarCeldaDatos(tableDetalle, formatoMoneda.format(nomina.getAporteFCL()), fontDatos, Element.ALIGN_RIGHT);
            agregarCeldaDatos(tableDetalle, formatoMoneda.format(nomina.getAporteAsignaciones()), fontDatos, Element.ALIGN_RIGHT);
            agregarCeldaDatos(tableDetalle, formatoMoneda.format(totalAportesEmpleado), fontDatos, Element.ALIGN_RIGHT);
        }
        
        // Fila de TOTALES
        Font fontTotal = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE);
        agregarCeldaTotal(tableDetalle, "TOTALES", fontTotal);
        agregarCeldaTotal(tableDetalle, formatoMoneda.format(totalSalarios), fontTotal);
        agregarCeldaTotal(tableDetalle, formatoMoneda.format(totalCCSS), fontTotal);
        agregarCeldaTotal(tableDetalle, formatoMoneda.format(totalINA), fontTotal);
        agregarCeldaTotal(tableDetalle, formatoMoneda.format(totalFCL), fontTotal);
        agregarCeldaTotal(tableDetalle, formatoMoneda.format(totalAsignaciones), fontTotal);
        agregarCeldaTotal(tableDetalle, formatoMoneda.format(totalAportes), fontTotal);
        
        document.add(tableDetalle);
        
        // ===== RESUMEN DE TOTALES =====
        document.add(new Paragraph("\n"));
        
        PdfPTable tableResumen = new PdfPTable(2);
        tableResumen.setWidthPercentage(60);
        tableResumen.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        Font fontResumen = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font fontResumenValor = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        
        agregarFilaResumen(tableResumen, "Total Empleados:", String.valueOf(listaNominas.size()), fontResumen, fontResumenValor);
        agregarFilaResumen(tableResumen, "Total Salarios:", formatoMoneda.format(totalSalarios), fontResumen, fontResumenValor);
        agregarFilaResumen(tableResumen, "Total CCSS (IVM + SEM):", formatoMoneda.format(totalCCSS), fontResumen, fontResumenValor);
        agregarFilaResumen(tableResumen, "Total INA:", formatoMoneda.format(totalINA), fontResumen, fontResumenValor);
        agregarFilaResumen(tableResumen, "Total FCL:", formatoMoneda.format(totalFCL), fontResumen, fontResumenValor);
        agregarFilaResumen(tableResumen, "Total Asignaciones:", formatoMoneda.format(totalAsignaciones), fontResumen, fontResumenValor);
        
        // Línea separadora
        PdfPCell separador1 = new PdfPCell();
        separador1.setColspan(2);
        separador1.setBorder(Rectangle.TOP);
        separador1.setPadding(3);
        tableResumen.addCell(separador1);
        
        // TOTAL GENERAL con mayor énfasis
        Font fontTotalGeneral = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        agregarFilaResumen(tableResumen, "TOTAL A PAGAR:", formatoMoneda.format(totalAportes), fontTotalGeneral, fontTotalGeneral);
        
        document.add(tableResumen);
        
        // ===== PIE DE PÁGINA CON DESGLOSE DE PORCENTAJES =====
        document.add(new Paragraph("\n"));
        
        Font fontNota = new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC);
        Paragraph nota = new Paragraph(
            "Nota: Los aportes patronales incluyen CCSS (IVM 7.08% + SEM 10.59%), INA (1.5%), FCL (3%) y Asignaciones Familiares (5%)",
            fontNota
        );
        nota.setAlignment(Element.ALIGN_CENTER);
        document.add(nota);
        
        document.close();
        return nombreArchivo;
    }
    
    // ===== MÉTODOS AUXILIARES PARA FORMATO DE TABLAS =====
    
    /**
     * Agrega una celda de encabezado a la tabla.
     */
    private void agregarCeldaHeader(PdfPTable table, String texto, Font font) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, font));
        celda.setBackgroundColor(BaseColor.DARK_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(5);
        table.addCell(celda);
    }
    
    /**
     * Agrega una celda de datos a la tabla.
     */
    private void agregarCeldaDatos(PdfPTable table, String texto, Font font, int alineacion) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, font));
        celda.setHorizontalAlignment(alineacion);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(4);
        table.addCell(celda);
    }
    
    /**
     * Agrega una celda de totales a la tabla.
     */
    private void agregarCeldaTotal(PdfPTable table, String texto, Font font) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, font));
        celda.setBackgroundColor(new BaseColor(70, 70, 70));
        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(5);
        table.addCell(celda);
    }
    
    /**
     * Agrega una fila al resumen de totales.
     */
    private void agregarFilaResumen(PdfPTable table, String concepto, String valor, Font fontConcepto, Font fontValor) {
        PdfPCell celdaConcepto = new PdfPCell(new Phrase(concepto, fontConcepto));
        celdaConcepto.setBorder(Rectangle.NO_BORDER);
        celdaConcepto.setHorizontalAlignment(Element.ALIGN_LEFT);
        celdaConcepto.setPadding(3);
        
        PdfPCell celdaValor = new PdfPCell(new Phrase(valor, fontValor));
        celdaValor.setBorder(Rectangle.NO_BORDER);
        celdaValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celdaValor.setPadding(3);
        
        table.addCell(celdaConcepto);
        table.addCell(celdaValor);
    }
    
    /**
     * Agrega una fila a la tabla.
     */
    private void agregarFila(PdfPTable table, String concepto, double monto, boolean negrita) {
        Font font = negrita ? 
            new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD) :
            new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        
        PdfPCell cellConcepto = new PdfPCell(new Phrase(concepto, font));
        PdfPCell cellMonto = new PdfPCell(new Phrase(formatoMoneda.format(monto), font));
        
        cellConcepto.setPadding(5);
        cellMonto.setPadding(5);
        cellMonto.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        if (negrita) {
            cellConcepto.setBackgroundColor(new BaseColor(240, 240, 240));
            cellMonto.setBackgroundColor(new BaseColor(240, 240, 240));
        }
        
        table.addCell(cellConcepto);
        table.addCell(cellMonto);
    }
} 