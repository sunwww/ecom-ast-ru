package ru.ecom.diary.ejb.service.template;


import org.json.JSONException;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 22.12.2006
 * Time: 12:26:15
 * To change this template use File | Settings | File Templates.
 */

public interface ITemplateProtocolService {
    public void sendSms(String aPhone, String aMessage);
    public void registerPatientExternalResource(Long aPatientExternalServiceAccountId, EntityManager aManager, String aUsername);
    public void sendProtocolToExternalResource(Long aProtocolId, Long aMedCaseId, String aRecord, EntityManager aManager) ;
    public String getTextTemplate(long aId) ;
    public String getTextByProtocol(long aProtocolId) ;
    public String getNameVoc(String aClassif, long aId) ;
    public Long getCountSymbolsInProtocol(long aVisit) ;
}
