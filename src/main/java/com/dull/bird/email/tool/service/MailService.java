package com.dull.bird.email.tool.service;

import com.dull.bird.email.tool.data.entity.EmailSendDTO;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface MailService {
    /**
     * (todo:补充)
     * @param sendTo
     * @param title
     * @param content
     */
    void sendSimpleMail(String sendTo, String title, String content);

    /**
     * (todo:补充)
     * @param emailConfig
     * @param title
     * @param templateName
     */
    void batchSendTemplateMail(EmailSendDTO emailConfig, String title, String templateName) throws IOException, MessagingException;

    /**
     * (todo:补充)
     * @param mainConfig
     * @param sendMessage
     * @throws IOException
     * @throws MessagingException
     */
    List<String> batchSendTemplateMail(EmailSendDTO mainConfig, List<EmailSendDTO> sendMessage) throws MessagingException, IOException;
}
