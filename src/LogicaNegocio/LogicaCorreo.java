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

/**
 * Lógica para envío de correos electrónicos.
 * 
 * @author Rachell Mora Reyes
 */
public class LogicaCorreo {
    
    private Properties propiedades = null;
    private Session session = null;
    private final ArrayList<BodyPart> adjuntos = new ArrayList<>();
    
    /**
     * Carga las propiedades del servidor SMTP.
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
     * Crea la sesión SMTP.
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
     * Agrega archivos adjuntos al correo.
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
     * Envía un correo electrónico.
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
     * Envía correo en un hilo separado.
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
