package com.maestre.correobatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class MailProcessor implements ItemProcessor<EmailData, SimpleMailMessage> {
	private static final Logger log = LoggerFactory.getLogger(MailProcessor.class);

	@Override
	public SimpleMailMessage process(EmailData item) throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("no-reply@gmail.com");
		message.setTo(item.email());
		message.setSubject(item.asunto());
		message.setText(item.cuerpo());
		log.info("Converting (" + item + ") into (" + message + ")");
		return message;
	}
}