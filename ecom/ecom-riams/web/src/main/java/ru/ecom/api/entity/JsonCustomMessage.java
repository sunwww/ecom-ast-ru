package ru.ecom.api.entity;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class JsonCustomMessage extends JsonEntity {

    /** Текст сообщения */
    public String getMessageText() {return messageText;}
    public void setMessageText(String aMessageText) {messageText = aMessageText;}

    /** Заголовок */
    public String getMessageTitle() {return messageTitle;}
    public void setMessageTitle(String aMessageTitle) {messageTitle = aMessageTitle;}

    /** Получатель */
    public String[] getRecipients() {return recipients;}
    public void setRecipients(String[] aRecipients) {recipients = aRecipients;}

    /** Срок действия */
    public Date getValidityDate() {return validityDate;}
    public void setValidityDate(Date aValidityDate) {validityDate = aValidityDate;}

    /** Время действия */
    public Integer getValidityMinutes() {return validityMinutes;}
    public void setValidityMinutes(Integer aValidityMinutes) {validityMinutes = aValidityMinutes;}

    /** Url */
    public String getMessageUrl() {return messageUrl;}
    public void setMessageUrl(String aUrl) {messageUrl = aUrl;}

    /** Эксренное */
    public Boolean getIsEmergency() {return isEmergency;}
    public void setIsEmergency(Boolean aIsEmergency) {isEmergency = aIsEmergency;}

    /** Отправить всем пользователям */
    public Boolean getSendAllUsers() {return sendAllUsers;}
    public void setSendAllUsers(Boolean aSendAllUsers) {sendAllUsers = aSendAllUsers;}

    /** Токен */
    public String getToken() {return token;}
    public void setToken(String aToken) {token = aToken;}

    /** Отправить всем пользователям */
    private Boolean sendAllUsers;
    /** Url */
    private String messageUrl;
    /** Срок действия */
    private Date validityDate;
    /** Получатель */
    private String[] recipients;
    /** Заголовок */
    private String messageTitle;
    /** Текст сообщения */
    private String messageText;
    /** Эксренное */
    private Boolean isEmergency;
    /** Токен */
    private String token ;
    /** Время действия */
    private Integer validityMinutes;
}