package ru.ecom.diary.ejb.service.template;


import javax.persistence.EntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 22.12.2006
 * Time: 12:26:15
 * To change this template use File | Settings | File Templates.
 */

public interface ITemplateProtocolService {
    void sendSms(String aPhone, String aMessage);
    void registerPatientExternalResource(Long aPatientExternalServiceAccountId, EntityManager aManager, String aUsername);
    void sendProtocolToExternalResource(Long aProtocolId, Long aMedCaseId, String aRecord, EntityManager aManager) ;
    String getTextTemplate(long aId) ;
    String getTextByProtocol(long aProtocolId) ;
    String getNameVoc(String aClassif, long aId) ;
    Long getCountSymbolsInProtocol(long aVisit) ;
}
