package br.com.silrait.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.silrait.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido p) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(p);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido p) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(p.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + p.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(p.toString());
		return sm;
	}
	
	protected String htmlFromTemplatePedido(Pedido p) {
		Context context = new Context();
		context.setVariable("pedido", p);
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido p) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(p);
			sendHtmlEmail(mm);
		}catch(MessagingException e) {
			sendOrderConfirmationEmail(p);
		}
		
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido p) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(p.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + p.getId());
		mmh.setSentDate( new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(p), true);
		
		return mimeMessage;
	}
}
