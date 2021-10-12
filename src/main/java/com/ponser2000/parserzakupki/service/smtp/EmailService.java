package com.ponser2000.parserzakupki.service.smtp;

import java.io.FileNotFoundException;
import java.util.List;
import javax.mail.MessagingException;

/**
 * @author Sergey Ponomarev on 15.09.2021
 * @project parser-zakupki/com.ponser2000.parserzakupki.service.smtp
 */
public interface EmailService {

    void sendSimpleEmail(final String[] toAddress, final String subject, final String message);

    void sendEmailWithAttachment(final String[] toAddress, final String subject, final String message, final List<String> files) throws MessagingException, FileNotFoundException;

}
