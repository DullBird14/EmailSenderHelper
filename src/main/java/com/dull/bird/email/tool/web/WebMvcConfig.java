package com.dull.bird.email.tool.web;

import com.dull.bird.email.tool.service.ExeclService;
import com.dull.bird.email.tool.service.MailService;
import com.dull.bird.email.tool.service.imp.ExeclAnalyzeImp;
import com.dull.bird.email.tool.service.imp.MailServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Bean
    public ExeclService getExeclService(){
        return ExeclAnalyzeImp.getInstance();
    }

    @Bean
    public MailService getMailService(){
        return new MailServiceImp();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/test").setViewName("/upload");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
    }

    //    @Bean(name = "multipartResolver")
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setMaxUploadSize(1000000);
//        return resolver;
//    }
}
