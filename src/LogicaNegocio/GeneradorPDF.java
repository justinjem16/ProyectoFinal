package LogicaNegocio;

/**
 * Clase Nomina del paquete Entidades que representa una planilla de pago.
 * 
 * <p>Se utiliza en esta clase para obtener los datos calculados de la nómina
 * (deducciones, aportes patronales, salarios) y generar los reportes PDF
 * correspondientes para empleados y patronos.
 */
import Entidades.Nomina;

/**
 * Clases del paquete com.itextpdf.text para creación de documentos PDF.
 * 
 * <p>Se utilizan para construir los documentos PDF con formato profesional,
 * incluyendo manejo de texto, párrafos, fuentes, colores y elementos
 * estructurales del documento.
 */
import com.itextpdf.text.*;

/**
 * Clases del paquete com.itextpdf.text.pdf para elementos PDF específicos.
 * 
 * <p>Se utilizan para crear tablas PDF (PdfPTable), celdas (PdfPCell),
 * y manejar la escritura del documento PDF (PdfWriter). Estas clases
 * permiten un control preciso sobre el formato y presentación de los reportes.
 */
import com.itextpdf.text.pdf.*;

/**
 * Clase FileOutputStream del paquete java.io para escritura de archivos.
 * 
 * <p>Se utiliza para crear y escribir el contenido del documento PDF en
 * el sistema de archivos, permitiendo guardar los reportes generados.
 */
import java.io.FileOutputStream;

/**
 * Clase DecimalFormat del paquete java.text para formateo de números.
 * 
 * <p>Se utiliza para formatear valores monetarios con el símbolo de colón
 * costarricense (₡), separadores de miles y dos decimales, mejorando la
 * presentación de los montos en los reportes.
 */
import java.text.DecimalFormat;

/**
 * Interfaz List del paquete java.util para colecciones ordenadas.
 * 
 * <p>Se utiliza para recibir la lista de nóminas que serán incluidas en
 * el reporte consolidado mensual del patrono, permitiendo procesar
 * múltiples empleados en un solo documento.
 */
import java.util.List;

/**
 * Clase que genera documentos PDF para reportes de nómina.
 * 
 * <p>Esta clase implementa la lógica de negocio para generar reportes
 * profesionales en formato PDF utilizando la librería iText. Proporciona
 * funcionalidad para crear tres tipos diferentes de reportes:
 * <ul>
 *   <li><strong>Reporte de Empleado:</strong> Comprobante de pago individual
 *       que muestra el desglose completo de salario bruto, deducciones
 *       detalladas y salario neto a recibir</li>
 *   <li><strong>Reporte Individual de Patrono:</strong> Detalle de cargas
 *       sociales patronales para un empleado específico</li>
 *   <li><strong>Reporte Mensual Consolidado de Patrono:</strong> Resumen
 *       completo de todos los empleados con totales acumulados de aportes
 *       patronales para facilitar el pago a las instituciones</li>
 * </ul>
 * 
 * <p>Todos los reportes se generan con formato profesional, incluyendo:
 * <ul>
 *   <li>Encabezados y títulos formateados</li>
 *   <li>Tablas organizadas con información clara y detallada</li>
 *   <li>Formato monetario en colones costarricenses (₡)</li>
 *   <li>Uso de colores y estilos para mejorar la legibilidad</li>
 *   <li>Totales y subtotales destacados visualmente</li>
 * </ul>
 * 
 * <h3>Formato Monetario:</h3>
 * <p>Todos los montos se formatean con el patrón "₡#,##0.00", que incluye:
 * <ul>
 *   <li>Símbolo del colón costarricense (₡)</li>
 *   <li>Separadores de miles (comas)</li>
 *   <li>Dos decimales fijos</li>
 * </ul>
 * 
 * <h3>Ejemplo de uso:</h3>
 * <pre>
 * GeneradorPDF generador = new GeneradorPDF();
 * 
 * // Generar comprobante para empleado
 * String archivoEmpleado = generador.generarReporteEmpleado(nomina);
 * 
 * // Generar reporte individual patronal
 * String archivoPatronal = generador.generarReportePatrono(nomina);
 * 
 * // Generar reporte mensual consolidado
 * List&lt;Nomina&gt; todasLasNominas = obtenerNominasDelMes();
 * String archivoConsolidado = generador.generarReportePatronoMensual(
 *     todasLasNominas, "Diciembre 2024"
 * );
 * </pre>
 * 
 * @author Rachell Mora Reyes
 * @version 1.0
 */
public class GeneradorPDF {
    
    // ========================================================================
    // CONSTANTES DE CLASE
    // ========================================================================
    
    /**
     * Formato estándar para presentación de valores monetarios.
     * 
     * <p>Define el patrón de formato para todos los montos que aparecen
     * en los reportes PDF. El formato incluye:
     * <ul>
     *   <li>₡ - Símbolo de colón costarricense</li>
     *   <li># - Dígitos sin ceros a la izquierda</li>
     *   <li>, - Separador de miles</li>
     *   <li>##0 - Al menos un dígito entero</li>
     *   <li>.00 - Dos decimales obligatorios</li>
     * </ul>
     * 
     * <p>Ejemplo de salida: ₡1,250,500.00
     */
    private static final DecimalFormat formatoMoneda = new DecimalFormat("₡#,##0.00");
    
    // ========================================================================
    // MÉTODOS PÚBLICOS - GENERACIÓN DE REPORTES PARA EMPLEADOS
    // ========================================================================
    
    /**
     * Genera el comprobante de pago en PDF para un empleado.
     * 
     * <p>Este método crea un documento PDF profesional que el empleado
     * puede utilizar como comprobante de su pago. El reporte incluye:
     * <ul>
     *   <li>Información personal del empleado (nombre, cédula, puesto)</li>
     *   <li>Periodo de pago correspondiente</li>
     *   <li>Salario bruto antes de deducciones</li>
     *   <li>Desglose detallado de todas las deducciones:
     *       <ul>
     *         <li>CCSS - IVM (4.17%)</li>
     *         <li>CCSS - SEM (6.50%)</li>
     *         <li>Banco Popular (1%)</li>
     *         <li>Impuesto sobre la Renta (variable por tramos)</li>
     *       </ul>
     *   </li>
     *   <li>Total de deducciones aplicadas</li>
     *   <li>Salario neto final a pagar (destacado visualmente)</li>
     * </ul>
     * 
     * <p>El documento se genera en tamaño carta (PageSize.LETTER) con
     * formato profesional, utilizando fuentes Helvetica en diferentes
     * tamaños y estilos, tablas organizadas con encabezados en gris
     * oscuro y totales resaltados con fondo gris claro.
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * GeneradorPDF generador = new GeneradorPDF();
     * Nomina nomina = new Nomina(1, empleado, "Primera Quincena Diciembre 2024");
     * // Asegurarse de que la nómina esté calculada
     * calculadora.calcularNominaCompleta(nomina);
     * 
     * try {
     *     String nombreArchivo = generador.generarReporteEmpleado(nomina);
     *     System.out.println("Comprobante generado: " + nombreArchivo);
     * } catch (Exception e) {
     *     System.err.println("Error al generar comprobante: " + e.getMessage());
     * }
     * </pre>
     * 
     * @param nomina el objeto Nomina con todos los cálculos ya realizados
     *               (deducciones y salario neto)
     * @return el nombre del archivo PDF generado, en formato:
     *         "Nomina_[cedula]_[periodo].pdf"
     * @throws Exception si ocurre algún error durante la creación del PDF
     *                   (por ejemplo, problemas de permisos de escritura,
     *                   espacio en disco insuficiente, o errores de formato)
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
    
    // ========================================================================
    // MÉTODOS PÚBLICOS - GENERACIÓN DE REPORTES INDIVIDUALES PARA PATRONO
    // ========================================================================
    
    /**
     * Genera el reporte individual de cargas sociales patronales en PDF.
     * 
     * <p>Este método crea un documento PDF que detalla las obligaciones
     * patronales (cargas sociales) que el empleador debe pagar a las
     * diferentes instituciones del Estado por un empleado específico.
     * Se mantiene para compatibilidad con código existente que necesita
     * reportes individuales por empleado.
     * 
     * <p>El reporte incluye:
     * <ul>
     *   <li>Información del empleado (nombre completo)</li>
     *   <li>Periodo de pago correspondiente</li>
     *   <li>Salario base utilizado para el cálculo</li>
     *   <li>Desglose detallado de todos los aportes patronales:
     *       <ul>
     *         <li>CCSS - IVM (7.08%): Cuota patronal Invalidez, Vejez y Muerte</li>
     *         <li>CCSS - SEM (10.59%): Cuota patronal Seguro de Enfermedad y Maternidad</li>
     *         <li>INA (1.5%): Instituto Nacional de Aprendizaje</li>
     *         <li>FCL (3%): Fondo de Capitalización Laboral</li>
     *         <li>Asignaciones (5%): Asignaciones Familiares (FODESAF)</li>
     *       </ul>
     *   </li>
     *   <li>Total de aportes patronales (suma de todos los conceptos)</li>
     * </ul>
     * 
     * <p>Estos montos son adicionales al salario del empleado y representan
     * el costo laboral total que la empresa debe pagar a las instituciones
     * correspondientes (CCSS, INA, MTSS).
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * GeneradorPDF generador = new GeneradorPDF();
     * Nomina nomina = new Nomina(1, empleado, "Primera Quincena Diciembre 2024");
     * calculadora.calcularNominaCompleta(nomina);
     * 
     * try {
     *     String nombreArchivo = generador.generarReportePatrono(nomina);
     *     System.out.println("Reporte patronal generado: " + nombreArchivo);
     * } catch (Exception e) {
     *     System.err.println("Error al generar reporte: " + e.getMessage());
     * }
     * </pre>
     * 
     * @param nomina el objeto Nomina con los aportes patronales ya calculados
     * @return el nombre del archivo PDF generado, en formato:
     *         "Patronal_[cedula]_[periodo].pdf"
     * @throws Exception si ocurre algún error durante la creación del PDF
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
    
    // ========================================================================
    // MÉTODOS PÚBLICOS - GENERACIÓN DE REPORTES CONSOLIDADOS MENSUALES
    // ========================================================================
    
    /**
     * Genera el reporte consolidado mensual de cargas sociales patronales en PDF.
     * 
     * <p>Este método crea un documento PDF completo que incluye TODOS los
     * empleados de la planilla del periodo especificado, con sus respectivos
     * aportes patronales individuales y los totales acumulados. Es el reporte
     * principal que el departamento de contabilidad o recursos humanos utiliza
     * para realizar los pagos mensuales a las instituciones (CCSS, INA, MTSS).
     * 
     * <p>El reporte consolidado incluye:
     * <ul>
     *   <li><strong>Encabezado:</strong> Título del reporte y periodo correspondiente</li>
     *   <li><strong>Tabla detallada por empleado:</strong> Para cada trabajador muestra:
     *       <ul>
     *         <li>Nombre completo del empleado</li>
     *         <li>Salario bruto base</li>
     *         <li>CCSS (IVM + SEM combinados)</li>
     *         <li>INA (Instituto Nacional de Aprendizaje)</li>
     *         <li>FCL (Fondo de Capitalización Laboral)</li>
     *         <li>Asignaciones Familiares</li>
     *         <li>Total de aportes por empleado</li>
     *       </ul>
     *   </li>
     *   <li><strong>Fila de totales:</strong> Suma de cada columna con formato destacado
     *       (fondo gris oscuro, texto blanco)</li>
     *   <li><strong>Resumen ejecutivo:</strong> Tabla lateral con:
     *       <ul>
     *         <li>Cantidad total de empleados</li>
     *         <li>Total de salarios pagados</li>
     *         <li>Desglose de totales por institución</li>
     *         <li>Total general a pagar (destacado con mayor énfasis)</li>
     *       </ul>
     *   </li>
     *   <li><strong>Nota aclaratoria:</strong> Pie de página con los porcentajes
     *       aplicados para cada concepto</li>
     * </ul>
     * 
     * <p>El documento se genera con márgenes optimizados (36 puntos) y tabla
     * de 7 columnas con anchos proporcionales para mejor presentación de la
     * información. La fuente se reduce a 8 puntos para permitir más datos
     * por página sin sacrificar legibilidad.
     * 
     * <h3>Diferencias con el reporte individual:</h3>
     * <ul>
     *   <li>Incluye múltiples empleados en un solo documento</li>
     *   <li>Proporciona totales consolidados por institución</li>
     *   <li>Formato optimizado para impresión en una página cuando es posible</li>
     *   <li>Incluye resumen ejecutivo para toma de decisiones rápida</li>
     * </ul>
     * 
     * <h3>Ejemplo de uso:</h3>
     * <pre>
     * GeneradorPDF generador = new GeneradorPDF();
     * CalculadoraNomina calculadora = new CalculadoraNomina();
     * 
     * // Obtener todas las nóminas del mes
     * List&lt;Nomina&gt; nominasDelMes = new ArrayList&lt;&gt;();
     * for (Empleado emp : listaEmpleados) {
     *     Nomina nomina = new Nomina(generarId(), emp, "Diciembre 2024");
     *     calculadora.calcularNominaCompleta(nomina);
     *     nominasDelMes.add(nomina);
     * }
     * 
     * try {
     *     String archivo = generador.generarReportePatronoMensual(
     *         nominasDelMes, "Diciembre 2024"
     *     );
     *     System.out.println("Reporte consolidado generado: " + archivo);
     *     System.out.println("Total de empleados procesados: " + nominasDelMes.size());
     * } catch (Exception e) {
     *     System.err.println("Error al generar reporte consolidado: " + e.getMessage());
     * }
     * </pre>
     * 
     * @param listaNominas lista de todas las nóminas calculadas del periodo.
     *                     Cada nómina debe tener los aportes patronales ya
     *                     calculados mediante
     *                     {@link CalculadoraNomina#calcularAportesPatronales(Nomina)}
     * @param periodo descripción del periodo del reporte, por ejemplo:
     *                "Diciembre 2024", "Enero 2025", "Primera Quincena Noviembre 2024"
     * @return el nombre del archivo PDF generado, en formato:
     *         "Patronal_Mensual_[periodo_sin_espacios].pdf"
     * @throws Exception si ocurre algún error durante la creación del PDF,
     *                   como problemas de permisos, espacio en disco insuficiente,
     *                   o si la lista de nóminas está vacía o es null
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
    
    // ========================================================================
    // MÉTODOS PRIVADOS AUXILIARES - FORMATO DE CELDAS Y TABLAS
    // ========================================================================
    
    /**
     * Agrega una celda de encabezado a una tabla PDF.
     * 
     * <p>Este método auxiliar crea y agrega celdas formateadas como encabezados
     * de tabla con estilo consistente: fondo gris oscuro, texto blanco centrado,
     * y padding de 5 puntos. Se utiliza para crear los títulos de columnas
     * en todas las tablas de los reportes.
     * 
     * @param table la tabla PdfPTable a la que se agregará la celda de encabezado
     * @param texto el contenido de texto que se mostrará en el encabezado
     * @param font la fuente a utilizar para el texto del encabezado
     *             (típicamente Helvetica 8-10pt, negrita, color blanco)
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
     * Agrega una celda de datos a una tabla PDF.
     * 
     * <p>Este método auxiliar crea y agrega celdas con datos regulares,
     * aplicando el formato y alineación especificados. Se utiliza para
     * las filas de contenido en las tablas de los reportes, permitiendo
     * control sobre la alineación horizontal del texto.
     * 
     * @param table la tabla PdfPTable a la que se agregará la celda de datos
     * @param texto el contenido de texto que se mostrará en la celda
     * @param font la fuente a utilizar para el texto
     *             (típicamente Helvetica 8-10pt, normal)
     * @param alineacion la alineación horizontal del texto, usando las constantes
     *                   de Element (Element.ALIGN_LEFT, Element.ALIGN_CENTER,
     *                   Element.ALIGN_RIGHT)
     */
    private void agregarCeldaDatos(PdfPTable table, String texto, Font font, int alineacion) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, font));
        celda.setHorizontalAlignment(alineacion);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(4);
        table.addCell(celda);
    }
    
    /**
     * Agrega una celda de totales a una tabla PDF.
     * 
     * <p>Este método auxiliar crea y agrega celdas formateadas para mostrar
     * totales o subtotales con estilo destacado: fondo gris medio oscuro,
     * texto blanco alineado a la derecha, y padding de 5 puntos. Se utiliza
     * para las filas de totales al final de las tablas de los reportes.
     * 
     * @param table la tabla PdfPTable a la que se agregará la celda de totales
     * @param texto el contenido de texto que se mostrará en la celda
     *              (típicamente valores monetarios formateados o la palabra "TOTALES")
     * @param font la fuente a utilizar para el texto
     *             (típicamente Helvetica 9pt, negrita, color blanco)
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
     * Agrega una fila completa al resumen de totales.
     * 
     * <p>Este método auxiliar crea y agrega una fila de dos columnas para
     * el cuadro de resumen ejecutivo. La primera celda contiene el concepto
     * (alineado a la izquierda) y la segunda el valor (alineado a la derecha).
     * Ambas celdas no tienen bordes para crear un aspecto más limpio.
     * 
     * @param table la tabla PdfPTable del resumen a la que se agregará la fila
     * @param concepto el texto descriptivo del concepto (ej: "Total Empleados:",
     *                 "Total Salarios:", "TOTAL A PAGAR:")
     * @param valor el valor correspondiente al concepto (ej: "25", "₡12,500,000.00")
     * @param fontConcepto la fuente a utilizar para el texto del concepto
     *                     (típicamente Helvetica 10-12pt, negrita)
     * @param fontValor la fuente a utilizar para el valor
     *                  (típicamente Helvetica 10-12pt, normal o negrita según énfasis)
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
     * Agrega una fila de dos columnas a una tabla PDF.
     * 
     * <p>Este método auxiliar crea y agrega una fila completa con dos celdas:
     * una para el concepto y otra para el monto monetario. Permite controlar
     * si la fila debe mostrarse con formato destacado (negrita y fondo gris claro)
     * o con formato normal. Se utiliza en las tablas principales de los reportes
     * de empleado y patrono individual.
     * 
     * @param table la tabla PdfPTable a la que se agregará la fila
     * @param concepto el texto descriptivo del concepto (ej: "Salario Bruto",
     *                 "CCSS - IVM (4.17%)", "TOTAL DEDUCCIONES")
     * @param monto el valor monetario a mostrar, que será formateado
     *              automáticamente usando el formato de moneda de la clase
     * @param negrita si es true, la fila se muestra en negrita con fondo gris claro
     *                (usado para totales y salarios); si es false, se muestra con
     *                formato normal (usado para detalles de deducciones y aportes)
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
