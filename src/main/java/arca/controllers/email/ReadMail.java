package arca.controllers.email;


import arca.util.ThreadUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

public class ReadMail {
    private static final String user = "alerta.arca@gmail.com";
    private static final String ps = "2FtYKa%U55m7HVGGeA3Z8";

    public static void main(String[] args) {

        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";

        while(true){
            check(host, mailStoreType, user, ps);
            ThreadUtils.sleepTreeSecond();
        }
    }


    public static void check(String host, String storeType, String user,
                             String password)
    {
        try {

            //create properties field
            Properties properties = new Properties();

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
            System.out.println("messages.length---" + messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {

                Message message = messages[i];
                final String from = message.getFrom()[0].toString();

                System.out.println("From: "+InternetAddress.class.cast(message.getFrom()[0]).getAddress());
                if(!message.getFlags().contains(Flags.Flag.SEEN)){
                    System.out.println("Marcando como lida! ");
                    seenMessage(emailFolder, message);
                }else{
                    System.out.println("Já está como lida! ");
                }

                final String content = message.getContent().toString();

//                System.out.println("---------------------------------");
//                System.out.println("Email Number " + (i + 1));
//                System.out.println("Subject: " + message.getSubject());
//                System.out.println("From: " + message.getFrom()[0]);
//                System.out.println("Text: " + message.getContent().toString());

            }

            //close the store and folder objects
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

    private static final void seenMessage(final Folder folder, final Message message) throws Exception {
        Flags seen = new Flags( Flags.Flag.DELETED);
        folder.setFlags(new Message[]{message}, seen, true);
    }

}
