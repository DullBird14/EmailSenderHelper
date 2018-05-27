package com.dull.bird.email.tool.web.controller;

import com.dull.bird.email.tool.data.entity.EmailSendDTO;
import com.dull.bird.email.tool.data.entity.ExeclAnalyzeResult;
import com.dull.bird.email.tool.execl.ExeclAnalyze;
import com.dull.bird.email.tool.service.ExeclService;
import com.dull.bird.email.tool.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
public class UploadController {

    @Autowired
    private ExeclService execlAnalyzeImp;
    @Autowired
    private MailService mailServiceImp;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("filetest") MultipartFile fileStream) {
        try {
            System.out.println(fileStream.getOriginalFilename());
            ExeclAnalyzeResult execlAnalyzeResult = ExeclAnalyze.getInstance().analyzeExecl(fileStream);
            System.out.println(execlAnalyzeResult.toString());
            return "ok";
        } catch (Exception e) {
            return "wrong";
        }
    }

    /**
     * 此处返回值需要重新处理，以返回json为佳
     * @param emailConfig   配置
     * @param fileStream    文件
     * @param title         标题
     * @return  结果
     */
    @RequestMapping(value = "/sendemail", method = RequestMethod.POST)
    public String sendEmail(EmailSendDTO emailConfig, @RequestParam("filetest") MultipartFile fileStream
        , String title){
        List<EmailSendDTO> emailSendDTOs = null;
        List<String> errorList = null;
        try {
            emailSendDTOs = execlAnalyzeImp.analyzeExecl(fileStream);
            errorList = mailServiceImp.batchSendTemplateMail(emailConfig, emailSendDTOs);
        } catch (IOException|MessagingException e) {
            e.printStackTrace();
            return "error";
        }

        if (errorList.size() == 0) {
            return errorList.toString();
        }
        return "success";
    }
}
