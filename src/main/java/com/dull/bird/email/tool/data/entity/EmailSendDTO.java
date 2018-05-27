package com.dull.bird.email.tool.data.entity;

import java.util.Map;

public class EmailSendDTO {
    private String sendFromUser;
    private String sendToUser;
    private String userPassWord;
    private String emailHost;
    private Map<String, Object> bodies;
    private String title;
    private String templateName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public EmailSendDTO(){
        super();
    }

    public EmailSendDTO(String sendToUser) {
        this(null, sendToUser, null, null);
    }

    public EmailSendDTO(String sendFromUser, String sendToUser, String userPassWord, String emailHost) {
        this.sendFromUser = sendFromUser;
        this.sendToUser = sendToUser;
        this.userPassWord = userPassWord;
        this.emailHost = emailHost;
    }

    public String getSendFromUser() {
        return sendFromUser;
    }

    public void setSendFromUser(String sendFromUser) {
        this.sendFromUser = sendFromUser;
    }

    public String getSendToUser() {
        return sendToUser;
    }

    public void setSendToUser(String sendToUser) {
        this.sendToUser = sendToUser;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    public Map<String, Object> getBodies() {
        return bodies;
    }

    public void setBodies(Map<String, Object> bodies) {
        this.bodies = bodies;
    }

    @Override
    public String toString() {
        return "EmailSendDTO{" + "sendFromUser='" + sendFromUser + '\'' + ", sendToUser='" + sendToUser + '\'' + ", userPassWord='" + userPassWord + '\'' + ", emailHost='" + emailHost + '\'' + ", bodies=" + bodies + ", title='" + title + '\'' + ", templateName='" + templateName + '\'' + '}';
    }
}
