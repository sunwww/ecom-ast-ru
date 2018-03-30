package ru.ecom.expert2.service;

import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.domain.E2ListEntry;
import ru.ecom.expomc.ejb.domain.med.VocKsg;

import javax.naming.NamingException;
import java.sql.Date;
import java.sql.SQLException;

public interface IExpert2Service {
  boolean exportDefectNewListEntry(Long alistEntryId);
   //  void addMedServiceToEntry(Long aEntryId, Long aMedServiceId);
     void checkListEntry(Long aListEntryId, boolean updateKsgIfExist, String aParams);
     void unionHospitalMedCase ( Long aListEntryId, Long aHospitalMedCaseId);
     void makeCheckEntry (Long aEntryId, boolean updateKsgIfExist);
     VocKsg getBestKSG(E2Entry aEntry, boolean updateKsgIfExist);
     void calculateEntryPrice(E2Entry  aEntry);
     void fillListEntry(E2ListEntry aListEntry, String aHistoryNumbers) throws NamingException, SQLException;
     void addMedHelpProfileBedType (Long aMedHelpId, Long aBedTypeId);
      Boolean addDiagnosisAndServiceToEntry(Long aEntryId, String aData) ;
    void addHospitalMedCaseToList(String aHistoryNumber, Long aListEntryId) throws SQLException, NamingException;
}
