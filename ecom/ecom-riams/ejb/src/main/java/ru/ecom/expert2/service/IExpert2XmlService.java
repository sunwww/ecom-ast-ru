package ru.ecom.expert2.service;

import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.domain.E2ListEntry;
import ru.ecom.expomc.ejb.domain.med.VocKsg;

import javax.naming.NamingException;
import java.sql.Date;
import java.sql.SQLException;

public interface IExpert2XmlService {
     String makeMPFIle(Long aEntryListId, String aType, String aBillNumber, Date aBillDate, Long aEntryId);
     String makeMPFIle(Long aEntryListId, String aType, String aBillNumber, Date aBillDate, Long aEntryId, Boolean calcAllListEntry);

}
