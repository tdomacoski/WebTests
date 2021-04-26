package arca.controllers.email;


import arca.ci.OperadoraIntegration;
import arca.domain.usecases.None;
import arca.domain.usecases.implementation.ValidarOperadoraUseCase;
import arca.util.ThreadUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class ReadMail {
    private static final String user = "alerta.arca@gmail.com";
    private static final String ps = "2FtYKa%U55m7HVGGeA3Z8";
    private static final String OPERATOR = "thiago.domacoski@arcasoltec.com.br";
    private static final String VALIDAR = "VALIDAR";
    private static final String PRINCESA_DOS_CAMPOS = "PRINCESA DOS CAMPOS";


    public static void main(String[] args) {

        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";

        while (true) {
            check(host, mailStoreType, user, ps);
            ThreadUtils.sleepTreeSecond();
        }
    }


    public static void check(String host, String storeType, String user,
                             String password) {
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "465");


            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");

            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("Total de Emails " + messages.length);


            boolean isSend = false;
            for (final Message message : messages) {
                final String from = InternetAddress.class.cast(message.getFrom()[0]).getAddress();

                if (from.equals(OPERATOR)) {
                    isSend = true;
                    final MimeMultipart body = MimeMultipart.class.cast(message.getContent());
                    final BodyPart textPart = body.getBodyPart(0);
                    final String content = String.class.cast(textPart.getContent());
                    final String subject = message.getSubject();
                    System.out.println("Processando . .  .");
//                    if (isSend) {
                        processMessage("", "", emailSession);
//                    }

                } else {
                    toDraftMessage(emailFolder, message);
                }

            }
            emailFolder.close(true);
            store.close();


        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final void toDraftMessage(final Folder folder, final Message message) throws Exception {
        Flags seen = new Flags(Flags.Flag.DELETED);
        folder.setFlags(new Message[]{message}, seen, true);
    }


    private static final void processMessage(final String subject, final String content, final Session session) {
//        if(subject.equals(VALIDAR) && content.equals(PRINCESA_DOS_CAMPOS)){

        System.out.println("Enviando Email...");

        final ValidarOperadoraUseCase validar =
                new ValidarOperadoraUseCase(OperadoraIntegration.expressoPrincesaDosCampos());
        if (validar.execute(new None()).isSuccess()) {
            System.out.println("Sucesso!");
        }

//            final Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    final ValidarOperadoraUseCase validar =
//                            new ValidarOperadoraUseCase(OperadoraIntegration.princesaDosCampos());
//                    if(validar.execute(new None()).isSuccess()){
//                        System.out.println("Sucesso!");
//                    }
//                }
//            };
//            final Thread thread = new Thread(runnable);
//            thread.start();

//        }

    }


}