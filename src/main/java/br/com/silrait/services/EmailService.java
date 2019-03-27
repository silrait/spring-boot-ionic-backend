package br.com.silrait.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.silrait.domain.Pedido;

public interface EmailService {	
	void sendEmail(SimpleMailMessage msg);

	void sendOrderConfirmationEmail(Pedido p);
}
