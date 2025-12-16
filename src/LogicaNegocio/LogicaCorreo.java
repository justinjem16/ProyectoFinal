/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogicaNegocio;

import Entidades.Correo;
import Utilidades.Constantes;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Clase que gestiona el envío de correos electrónicos a través de un servidor SMTP.
 * Permite enviar correos con archivos adjuntos de forma síncrona o asíncrona.
 * Utiliza JavaMail API para la comunicación con el servidor de correo.
 * 
 * @author Justin
 */
public class LogicaCorreo {
     /** Configuración del servidor SMTP */
    private Properties propiedades = null;
    
    /** Sesión de correo con el servidor SMTP */
    private Session session = null;
    
    /** Lista de archivos adjuntos que se incluirán en el correo */
    private final ArrayList<BodyPart> adjuntos = new ArrayList<>();
    
    /**
     * Carga las propiedades necesarias para conectarse al servidor SMTP.
     * Configura el protocolo SSL, el host, el puerto y la autenticación.
     * Los valores se obtienen de la clase Constantes.
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
     * Crea una nueva sesión SMTP con autenticación.
     * Utiliza las credenciales configuradas en la clase Constantes para autenticarse.
     * Debe llamarse antes de enviar cualquier correo.
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
    
    /**
     * Agrega los archivos adjuntos del objeto Correo a la lista de adjuntos.
     * Cada archivo se convierte en un BodyPart para ser incluido en el mensaje.
     * 
     * @param datosCorreo objeto que contiene la lista de archivos a adjuntar
     * @throws MessagingException si ocurre un error al procesar los archivos adjuntos
     */
    private void agregarAdjuntos(Correo datosCorreo) throws MessagingException {
        for (File archivo : datosCorreo.getArchivosAdjuntos()) {
            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(archivo.getAbsolutePath())));
            adjunto.setFileName(archivo.getName());
            adjuntos.add(adjunto);
        }
    }
    
    /**
     * Envía un correo electrónico con la información proporcionada.
     * El correo puede incluir archivos adjuntos si están especificados en el objeto datosCorreo.
     * Este método es síncrono, por lo que bloquea la ejecución hasta que el correo sea enviado.
     * 
     * @param datosCorreo objeto que contiene el destinatario, asunto, mensaje y archivos adjuntos
     * @throws MessagingException si ocurre un error durante el proceso de envío
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
     * Envía un correo electrónico en un hilo separado para no bloquear la interfaz.
     * Muestra un mensaje al usuario indicando si el envío fue exitoso o si ocurrió un error.
     * Esta es la forma recomendada de enviar correos desde la interfaz gráfica.
     * 
     * @param datosCorreo objeto que contiene la información del correo a enviar
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
