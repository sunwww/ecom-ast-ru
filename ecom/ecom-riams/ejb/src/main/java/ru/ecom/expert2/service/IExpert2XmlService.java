package ru.ecom.expert2.service;

import java.sql.Date;

public interface IExpert2XmlService {
     String makeMPFIle(Long aEntryListId, String aType, String aBillNumber, Date aBillDate, Long aEntryId, Boolean calcAllListEntry, long aMonitorId, String aVersion);
}
