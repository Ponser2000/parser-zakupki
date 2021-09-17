package com.ponser2000.parserzakupki.service.smtp.impl;

import com.ponser2000.parserzakupki.service.smtp.EmailService;
import java.io.FileNotFoundException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

/**
 * @author Sergey Ponomarev on 15.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.service.smtp
 */
@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  public JavaMailSender emailSender;

  @Override
  public void sendSimpleEmail(String toAddress, String subject, String message) {
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(toAddress);
    simpleMailMessage.setFrom("info@nika-es.ru");
    simpleMailMessage.setSubject(subject);
    simpleMailMessage.setText(message);
    emailSender.send(simpleMailMessage);
  }

  @Override
  public void sendEmailWithAttachment(String toAddress, String subject,
      String message, String attachment)
      throws MessagingException, FileNotFoundException {

    MimeMessage mimeMessage = emailSender.createMimeMessage();
    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
    messageHelper.setTo(toAddress);
    messageHelper.setFrom("info@nika-es.ru");
    messageHelper.setSubject(subject);
    messageHelper.setText(message);
    FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));

    messageHelper.addAttachment(file.getFilename(), file);
    emailSender.send(mimeMessage);
    // System.out.println("Email sent!!!!");
  }
}
