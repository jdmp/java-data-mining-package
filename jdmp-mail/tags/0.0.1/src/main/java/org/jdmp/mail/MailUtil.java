package org.jdmp.mail;

import java.net.InetAddress;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.jdmp.matrix.util.JDMPSettings;

public class MailUtil {

    public static void sendSystemOut(String recipient, String subject, String userName, String smtpServer)
            throws Exception {
        sendMessage(recipient, subject, JDMPSettings.getSystemOut(), userName, smtpServer);
    }

    public static void sendSystemErr(String recipient, String subject, String userName, String smtpServer)
            throws Exception {
        sendMessage(recipient, subject, JDMPSettings.getSystemErr(), userName, smtpServer);
    }

    public static void sendMessage(String recipient, String subject, String text, String userName, String smtpServer)
            throws Exception {

        Properties properties = new Properties();
        // properties.put("mail.store.protocol", "pop3");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.user", userName);
        // properties.put("mail.pop3.host", "pop3.provider.de");
        properties.put("mail.smtp.host", smtpServer);
        // properties.put("User", "user");
        // properties.put("Password", "passwd");
        properties.put("mail.from", "jdmp@" + InetAddress.getLocalHost().getHostName());

        Session mailSession = Session.getInstance(properties);

        Message message = new MimeMessage(mailSession);
        message.setSubject(subject);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        MimeMultipart mimeMultipart = new MimeMultipart();
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(text);
        textPart.setDisposition(MimeBodyPart.INLINE);
        mimeMultipart.addBodyPart(textPart);
        message.setContent(mimeMultipart);
        message.saveChanges();

        Transport.send(message);
    }

}
