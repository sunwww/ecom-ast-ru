package ru.ecom.expert2.service;


import org.jdom.Document;

public interface IExpert2ImportService {
    String createEntryByFondXml(String aFilename) ;
    String getConfigValue (String aKeyName, String aDefaultName);
    String importFondMPAnswer(String aMpFilename);
    String importN5File(Document doc, Long aListEntryId);
    String importFlkAnswer(String aFilename, Long aListEntryId);//, String aBillNumber, Date aBillDate);
    String importElmed(String aXmlFilename);

}
