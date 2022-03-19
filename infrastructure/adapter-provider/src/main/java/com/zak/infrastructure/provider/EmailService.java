package com.zak.infrastructure.provider;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

@Slf4j
public class EmailService {

    public boolean sendEmail(String destination, String mailSubject, String mailBody) {
        boolean mailSentStatus = false;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");


//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "888");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("zzak4482@gmail.com", "ahiebhpwromiqitu");
//                return new PasswordAuthentication("zzak4482@gmail.com", "ZakZak2020");
            }
        });
        try {

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("zzak4482@gmail.com", "ZAK Company"));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
            msg.setSubject(mailSubject);
            msg.setSentDate(new Date());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(mailBody, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);
            Transport.send(msg);
            mailSentStatus = true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            log.error("Error with Internet email address syntax");
        }
        return mailSentStatus;
    }
}
