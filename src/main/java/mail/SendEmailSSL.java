package mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailSSL {
	
	public boolean sendMail(String recipientsEmail, String subject,  String text) {
        final String username = MailConfig.YOUR_EMAIL;
        final String password = MailConfig.YOUR_PASSWORD;

        Properties prop = new Properties();
        prop.put("mail.smtp.host", MailConfig.HOST_NAME); //SMTP Host
        prop.put("mail.smtp.port", MailConfig.SSL_PORT); //SSL Port
        prop.put("mail.smtp.socketFactory.port", MailConfig.SSL_PORT);
        prop.put("mail.smtp.auth", "true"); //enable authentication
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        Session session = Session.getInstance(prop, new Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(username, password);
        	}
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientsEmail));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            System.out.println("Send EmailSSL: Done");
            
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
		return false;		
	}
	
	public static void main(String[] args) {
		// test data
		String recipientsEmail = MailConfig.RECEIVE_EMAIL; 
        String subject = "Testing Gmail SSL: " + "sended from " + MailConfig.YOUR_EMAIL;
        String text = "Dear Mail Crawler,"
                		+ "\n\n Please do not spam my email!";
        // execute
        SendEmailSSL ssl = new SendEmailSSL();
        System.out.println(ssl.sendMail(recipientsEmail, subject, text));
    }
}