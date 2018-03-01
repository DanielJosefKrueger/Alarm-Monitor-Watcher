package main;




import configuration.Configuration;
import configuration.ConfigurationLoader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class EMailSender {



    private final Configuration config;
    private final List<String> receivers = new ArrayList<>();


    public EMailSender(ConfigurationLoader provider) {
        config = provider.get();
    }

    public boolean sendEmailToAdmin(String msg, boolean isHtml) {

        Properties props = new Properties();
            /*props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp-mail.outlook.com");
			props.put("mail.smtp.port", "587");*/

        props.put("mail.smtp.auth", config.smtpAuth());
        props.put("mail.smtp.starttls.enable", config.startTls());
        props.put("mail.smtp.host", config.smtpHost());
        props.put("mail.smtp.port", config.smtpPort());

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.username(), config.password());
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.username()));
            System.out.println("send email to " + config.getEmailReceiver());
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(config.getEmailReceiver()));
            message.setSubject(config.getEmailTopic());
            if (isHtml) {
                message.setContent(msg, "text/html");
            }else{
                message.setText(msg);
            }

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }


}
