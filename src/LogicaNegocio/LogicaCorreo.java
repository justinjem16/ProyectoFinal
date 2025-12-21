/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaNegocio;

import Entidades.Correo;
import Utilidades.Constantes;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;

// ================================================================================
// CLASE LogicaCorreo
// ================================================================================

/**
 * Lógica para envío de correos electrónicos mediante protocolo SMTP.
 * 
 * <p>Esta clase gestiona la configuración, creación de sesiones y envío de
 * correos electrónicos con soporte para archivos adjuntos. Utiliza JavaMail API
 * y se conecta a un servidor SMTP configurado mediante constantes.</p>
 * 
 * <p><b>Estado inicial del objeto:</b></p>
 * <ul>
 *   <li>propiedades: null (se cargan al crear la sesión SMTP)</li>
 *   <li>session: null (se crea al enviar un correo)</li>
 *   <li>adjuntos: ArrayList vacío, listo para almacenar archivos adjuntos</li>
 * </ul>
 * 
 * <p><b>Uso típico:</b> Crear una instancia de Correo con los datos requeridos
 * (destinatario, asunto, mensaje y opcionalmente archivos adjuntos), luego llamar
 * a {@link #enviarCorreo(Correo)} para envío síncrono o {@link #enviarCorreoThread(Correo)}
 * para envío asíncrono con notificación visual.</p>
 * 
 * @author Justin Espinoza
 * @see Entidades.Correo
 * @see Utilidades.Constantes
 */
public class LogicaCorreo {
    
    // ================================================================================
    // ATRIBUTOS
    // ================================================================================
    
    /**
     * Propiedades de configuración del servidor SMTP.
     * <p>Incluye configuración de SSL, host, puerto y autenticación.</p>
     */
    private Properties propiedades = null;
    
    /**
     * Sesión SMTP autenticada para el envío de correos.
     */
    private Session session = null;
    
    /**
     * Lista de archivos adjuntos a incluir en el correo.
     * <p>Cada elemento representa un archivo que será anexado al mensaje.</p>
     */
    private final ArrayList<BodyPart> adjuntos = new ArrayList<>();
    
    // ================================================================================
    // MÉTODOS PRIVADOS - CONFIGURACIÓN
    // ================================================================================
    
    /**
     * Carga las propiedades de configuración del servidor SMTP.
     * 
     * <p>Configura los parámetros necesarios para la conexión segura con el servidor
     * de correo, incluyendo:</p>
     * <ul>
     *   <li>Habilitación de SSL</li>
     *   <li>Host y puerto del servidor SMTP (obtenidos de {@link Utilidades.Constantes})</li>
     *   <li>Autenticación requerida</li>
     *   <li>Protocolo TLS versión 1.2</li>
     * </ul>
     * 
     * @see Utilidades.Constantes#SMTP_HOST
     * @see Utilidades.Constantes#SMTP_PORT
     */
    private void cargarPropiedades() {
        propiedades = new Properties();
        propiedades.put("mail.smtp.ssl.enable", "true");
        propiedades.put("mail.smtp.host", Constantes.SMTP_HOST);
        propiedades.put("mail.smtp.port", Constantes.SMTP_PORT);
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.ssl.protocols", "TLSv1.2");
    }
    
    /**
     * Crea y configura la sesión SMTP autenticada.
     * 
     * <p>Carga las propiedades del servidor mediante {@link #cargarPropiedades()} y
     * establece la autenticación utilizando las credenciales almacenadas en
     * {@link Utilidades.Constantes}. La sesión resultante se utilizará para
     * crear y enviar mensajes de correo.</p>
     * 
     * @see #cargarPropiedades()
     * @see Utilidades.Constantes#EMAIL_FROM
     * @see Utilidades.Constantes#EMAIL_PASSWORD
     */
    private void crearSessionSmtp() {
        cargarPropiedades();
        session = Session.getDefaultInstance(propiedades,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                        Constantes.EMAIL_FROM, 
                        Constantes.EMAIL_PASSWORD
                    );
                }
            }
        );
    }
    
    // ================================================================================
    // MÉTODOS PRIVADOS - PROCESAMIENTO DE ADJUNTOS
    // ================================================================================
    
    /**
     * Procesa y agrega los archivos adjuntos al correo electrónico.
     * 
     * <p>Itera sobre la lista de archivos proporcionados en el objeto Correo,
     * crea un BodyPart para cada archivo y lo agrega a la lista de adjuntos.
     * Cada archivo mantiene su nombre original.</p>
     * 
     * @param datosCorreo objeto que contiene la información del correo, incluyendo
     *                    la lista de archivos a adjuntar
     * @throws MessagingException si ocurre un error al procesar los archivos adjuntos
     * 
     * @see Entidades.Correo#getArchivosAdjuntos()
     */
    private void agregarAdjuntos(Correo datosCorreo) throws MessagingException {
        for (File archivo : datosCorreo.getArchivosAdjuntos()) {
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(archivo.getAbsolutePath())));
            adjunto.setFileName(archivo.getName());
            adjuntos.add(adjunto);
        }
    }
    
    // ================================================================================
    // MÉTODOS PÚBLICOS - ENVÍO DE CORREOS
    // ================================================================================
    
    /**
     * Envía un correo electrónico de forma síncrona.
     * 
     * <p>Este método realiza las siguientes operaciones:</p>
     * <ol>
     *   <li>Crea la sesión SMTP autenticada mediante {@link #crearSessionSmtp()}</li>
     *   <li>Configura el remitente, destinatario y asunto del mensaje</li>
     *   <li>Establece el cuerpo del mensaje</li>
     *   <li>Procesa y agrega archivos adjuntos si existen</li>
     *   <li>Envía el correo a través del servidor SMTP configurado</li>
     * </ol>
     * 
     * <p><b>Nota:</b> Este método es bloqueante. Para envío asíncrono con notificación
     * visual, utilizar {@link #enviarCorreoThread(Correo)}.</p>
     * 
     * @param datosCorreo objeto que contiene todos los datos del correo a enviar:
     *                    destinatario, asunto, mensaje y archivos adjuntos opcionales
     * @throws MessagingException si ocurre un error durante la creación, configuración
     *                           o envío del mensaje de correo
     * 
     * @see #crearSessionSmtp()
     * @see #agregarAdjuntos(Correo)
     * @see Entidades.Correo
     */
    public void enviarCorreo(Correo datosCorreo) throws MessagingException {
        crearSessionSmtp();
        
        Message objCorreo = new MimeMessage(session);
        objCorreo.setFrom(new InternetAddress(Constantes.EMAIL_FROM));
        objCorreo.setRecipients(Message.RecipientType.TO, 
                               InternetAddress.parse(datosCorreo.getEmail()));
        objCorreo.setSubject(datosCorreo.getAsunto());
        
        BodyPart objBodyPart = new MimeBodyPart();
        objBodyPart.setText(datosCorreo.getMensaje());
        
        Multipart objMultipart = new MimeMultipart();
        objMultipart.addBodyPart(objBodyPart);
        
        if (datosCorreo.getArchivosAdjuntos() != null) {
            agregarAdjuntos(datosCorreo);
            for (BodyPart adjunto : adjuntos) {
                objMultipart.addBodyPart(adjunto);
            }
        }
        
        objCorreo.setContent(objMultipart);
        Transport.send(objCorreo);
    }
    
    /**
     * Envía un correo electrónico de forma asíncrona en un hilo separado.
     * 
     * <p>Este método crea y ejecuta un hilo independiente que realiza el envío
     * del correo mediante {@link #enviarCorreo(Correo)}. Al finalizar, muestra
     * un cuadro de diálogo con el resultado de la operación:</p>
     * <ul>
     *   <li><b>Éxito:</b> Mensaje de confirmación con el email del destinatario</li>
     *   <li><b>Error:</b> Mensaje con los detalles del error ocurrido</li>
     * </ul>
     * 
     * <p><b>Ventajas:</b> No bloquea el hilo principal de la aplicación, permitiendo
     * que la interfaz gráfica permanezca responsiva durante el envío del correo.</p>
     * 
     * @param datosCorreo objeto que contiene todos los datos del correo a enviar:
     *                    destinatario, asunto, mensaje y archivos adjuntos opcionales
     * 
     * @see #enviarCorreo(Correo)
     * @see Entidades.Correo
     */
    public void enviarCorreoThread(Correo datosCorreo) {
        Thread hilo = new Thread(() -> {
            try {
                enviarCorreo(datosCorreo);
                JOptionPane.showMessageDialog(null, 
                    "Correo enviado correctamente a " + datosCorreo.getEmail());
            } catch (MessagingException e) {
                JOptionPane.showMessageDialog(null, 
                    "Error enviando correo: " + e.getMessage());
            }
        });
        hilo.start();
    }
}
