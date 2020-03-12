package ru.ecom.expert2.service;


import org.jdom.Document;

public interface IExpert2ImportService {
    void createEntryByFondXml(long monitorId, String aFilename) ;
    String getConfigValue (String aKeyName, String aDefaultName);
    void importFondMPAnswer(long monitorId, String aMpFilename);
    @Deprecated
    String importN5File(Document doc, Long aListEntryId);
    String importFlkAnswer(String aFilename, Long aListEntryId);//, String aBillNumber, Date aBillDate);
    void importElmed(long monitorId, String aXmlFilename);

}
