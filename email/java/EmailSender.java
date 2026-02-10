import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

	public void sendEmail(String recipient, String subject, String body) {
		//Crear Contraseñas de aplicación: java
		final String username = "manuelruizgutierrez810@gmail.com";
		final String password = "nzdq tpdt dhdy zxpc ";

		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", "587");
		props.put("mail.host", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		try {
			Session session = Session.getInstance(props,
				    new javax.mail.Authenticator() {
				        protected PasswordAuthentication getPasswordAuthentication() {
				            return new PasswordAuthentication(username, password);
				        }
				    }
				);
			session.setDebug(true);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(subject);
			message.setText(body);

			// set the message content here
			Transport.send(message);
			System.out.println("Email sent successfully to: " + recipient);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String email = "chosenmedusa82lol@gmail.com";
		String subject = "Test Email";
		String body = "Hola,\nEsto es una prueba de envio de correo.";

		// Sending email without attachment
		EmailSender emailSender = new EmailSender();
		emailSender.sendEmail(email, subject, body);
	}
}
