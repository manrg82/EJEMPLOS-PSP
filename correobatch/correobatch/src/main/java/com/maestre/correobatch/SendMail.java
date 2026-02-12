package com.maestre.correobatch;

import java.util.Properties;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import jakarta.mail.Session;

public class SendMail implements ItemWriter<SimpleMailMessage> {
	@Override
	public void write(Chunk<? extends SimpleMailMessage> messages) throws
		Exception {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", "587");
		props.put("mail.host", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.debug", "true");
		final String username = "manuelruizgutierrez810@gmail.com";
		final String password = "zics sdzj tqil wtsh";
		Session session = Session.getInstance(props);
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setSession(session);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		for(SimpleMailMessage message: messages) {
			mailSender.send(message);
		}
	}
}