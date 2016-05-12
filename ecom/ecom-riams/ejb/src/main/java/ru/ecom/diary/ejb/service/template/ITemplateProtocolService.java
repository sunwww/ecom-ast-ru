package ru.ecom.diary.ejb.service.template;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 22.12.2006
 * Time: 12:26:15
 * To change this template use File | Settings | File Templates.
 */

public interface ITemplateProtocolService {
    public String getTextTemplate(long aId) ;
    public String getTextByProtocol(long aProtocolId) ;
    public String getNameVoc(String aClassif, long aId) ;
    public Long getCountSymbolsInProtocol(long aVisit) ;


}
