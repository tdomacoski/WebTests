package arca.controllers.email;

import arca.util.DateUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    private static final String user = "alerta.arca@gmail.com";
    private static final String ps = "2FtYKa%U55m7HVGGeA3Z8";

    public static void main(String[] args) {

        final StringBuilder builder = new StringBuilder("Este é um email de teste!\n");
        builder.append("<b> Se você está lendo este email</b>, é porque ele funcionou! \n ");
        builder.append("Paranbéns! \n");
        builder.append(String.format("Este email foi criado em %s as %s", DateUtils.formatFromAPI(System.currentTimeMillis()), DateUtils.currentHours()));

        send("[ALERTA - Arca] Teste de envio " ,builder.toString(),
                "thiago.domacoski@arcasoltec.com.br");

    }

    private static boolean send(final String descricao, final String msg, final String to){

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,ps);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            message.setSubject(descricao);
            message.setText(msg);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            return false;
        }

    }
}
