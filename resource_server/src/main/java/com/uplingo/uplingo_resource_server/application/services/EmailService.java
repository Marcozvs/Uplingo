package com.uplingo.uplingo_resource_server.application.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

  @Value("${spring.mail.host}")
  private String mailHost;

  @Value("${spring.mail.port}")
  private int mailPort;

  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.password}")
  private String password;

  @Value("${spring.mail.ssl.trust}")
  private String sslTrust;

  public String sendMail(String recipient, String subject, String message) {
    try {
      JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
      mailSender.setHost(mailHost);
      mailSender.setPort(mailPort);
      mailSender.setUsername(username);
      mailSender.setPassword(password);

      Properties props = mailSender.getJavaMailProperties();
      props.put("mail.transport.protocol", "smtp");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.ssl.trust", sslTrust);
      props.put("mail.smtp.ssl.checkserveridentity", "true");

      SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
      simpleMailMessage.setFrom(username);
      simpleMailMessage.setTo(recipient);
      simpleMailMessage.setSubject(subject);
      simpleMailMessage.setText(message);

      mailSender.send(simpleMailMessage);

      return "Email sent";
    } catch (Exception e) {
      return "Error when trying to send email " + e.getLocalizedMessage();
    }
  }
}
