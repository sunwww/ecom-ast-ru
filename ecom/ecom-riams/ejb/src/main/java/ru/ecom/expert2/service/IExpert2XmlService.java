package ru.ecom.expert2.service;

import ru.ecom.expert2.domain.voc.enums.VocListEntryTypeCode;

import java.sql.Date;

public interface IExpert2XmlService {
     String makeMPFIle(Long aEntryListId, VocListEntryTypeCode aType, String aBillNumber, Date aBillDate, Long aEntryId, Boolean calcAllListEntry, long aMonitorId, String aVersion, String aFileType);
     String exportToCentralSegment(Long aListEntryId, String aHistoryNumbers);
}
