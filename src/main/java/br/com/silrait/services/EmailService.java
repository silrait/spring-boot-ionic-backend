package br.com.silrait.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.silrait.domain.Pedido;

public interface EmailService {	
	void sendEmail(SimpleMailMessage msg);

	void sendOrderConfirmationEmail(Pedido p);
	
	void sendOrderConfirmationHtmlEmail(Pedido p);
	
	void sendHtmlEmail(MimeMessage msg);
}
