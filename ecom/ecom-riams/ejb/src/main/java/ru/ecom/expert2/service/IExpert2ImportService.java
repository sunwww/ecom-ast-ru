package ru.ecom.expert2.service;


public interface IExpert2ImportService {
    String getConfigValue (String aKeyName, String aDefaultName);
    void importFondMPAnswer(long monitorId, String aMpFilename);
    void importFlkAnswer(long monitorId, String aFilename, Long aListEntryId);
    void importElmed(long monitorId, String aXmlFilename);

}
