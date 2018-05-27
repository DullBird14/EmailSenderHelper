package com.dull.bird.email.tool.service.imp;

import com.dull.bird.email.tool.data.entity.EmailSendDTO;
import com.dull.bird.email.tool.service.MailService;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MailServiceImp implements MailService {
    private static final String SPLIT_MARK = ";";
    private static final Pattern emailPattern = Pattern.compile("\\w+@\\w+\\.\\w+");

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void sendSimpleMail(String sendTo, String title, String content) {

    }

    @Override
    public void batchSendTemplateMail(EmailSendDTO emailConfig, String title, String templateName)
            throws IOException, MessagingException {
        String sendToUser = emailConfig.getSendToUser();
        // TODO: 2018/5/6 检查是否已经有execl内容缓存
        if (sendToUser == null) {
            throw new IllegalArgumentException("接收方不能为空！！");
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        helper = new MimeMessageHelper(message, true);
        helper.setFrom(emailConfig.getSendFromUser());
        helper.setSubject(title);


        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);

//        String[] users = sendToUsers.split(SPLIT_MARK);
//        for (String user : users) {
//            boolean isEmailFormat = emailPattern.matcher(user).find();
//            if (isEmailFormat) {
//
//            }
//        }
    }

    @Override
    public List<String> batchSendTemplateMail(EmailSendDTO mainConfig, List<EmailSendDTO> sendMessage) throws MessagingException, IOException {
        List<String> errorEmail = new ArrayList<>();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = checkAndInit(mainConfig, message);
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(mainConfig.getTemplateName());

        for (EmailSendDTO data : sendMessage) {
            helper.setTo(data.getSendToUser());
            try {
                String test = FreeMarkerTemplateUtils.processTemplateIntoString(template, data.getBodies());
                helper.setText(test, true);
                mailSender.send(message);
            } catch (Exception e) {
                errorEmail.add(data.getSendToUser());
                e.printStackTrace();
                continue;
            }
        }
        return errorEmail;
    }

    private MimeMessageHelper checkAndInit(EmailSendDTO mainConfig, MimeMessage message) throws MessagingException {
        if (mainConfig.getSendFromUser() == null) {
            throw new IllegalArgumentException("发件人不能为空");
        }

        if (mainConfig.getTitle() == null){
            throw new IllegalArgumentException("标题不能为空");
        }

        if (mainConfig.getTemplateName() == null) {
            throw new IllegalArgumentException("模板不能为空");
        }

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mainConfig.getSendFromUser());
        helper.setSubject(mainConfig.getTitle());
        return helper;
    }
}
