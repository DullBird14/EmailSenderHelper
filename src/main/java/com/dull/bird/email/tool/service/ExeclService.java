package com.dull.bird.email.tool.service;

import com.dull.bird.email.tool.data.entity.EmailSendDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExeclService {

    /**
     * 解析execl，并且返回解析的结果
     * @param fileStream    execl文件流
     * @return  返回List<EmailSendDTO>
     * @throws IOException  解析过程中抛出异常
     */
    List<EmailSendDTO> analyzeExecl(MultipartFile fileStream)  throws IOException;


}
