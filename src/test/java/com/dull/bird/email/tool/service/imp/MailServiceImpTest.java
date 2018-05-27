package com.dull.bird.email.tool.service.imp;

import com.dull.bird.email.tool.ToolApplication;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ToolApplication.class)
public class MailServiceImpTest {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Test
    public void sendSimpleMail() throws Exception {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("dullbird14@163.com");
//        message.setTo("dullbird14@163.com");
//        message.setSubject("主题：简单邮件");
//        message.setText("测试邮件内容");
        Map param = new HashMap(16);
        param.put("userName", "DullBird14");
        param.put("age", "14");

        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("test.ftl");
        String test = FreeMarkerTemplateUtils.processTemplateIntoString(template, param);
        System.out.println(test);
//        mailSender.send(message);
    }

    @Test
    public void testTemplateEmail(){
        MimeMessage message = null;
        message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("dullbird14@163.com");
            helper.setTo("dullbird14@163.com");
            helper.setSubject("主题：测试模板邮件");

            Map param = new HashMap(16);
            param.put("userName", "DullBird14");
            param.put("age", "14");
            param.put("temp", "ttttt");

            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("test.ftl");
            String test = FreeMarkerTemplateUtils.processTemplateIntoString(template, param);
            System.out.println(test);
            helper.setText(test, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mailSender.send(message);
        try {
            helper.setTo("524318257@qq.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(message);
    }
}