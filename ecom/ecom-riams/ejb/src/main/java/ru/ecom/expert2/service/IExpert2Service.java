package ru.ecom.expert2.service;

import ru.ecom.expert2.domain.E2Bill;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.domain.E2ListEntry;
import ru.ecom.expomc.ejb.domain.med.VocKsg;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

public interface IExpert2Service {
    E2Bill getBillEntryByDateAndNumber(String sBillNumber, String aBillDate);
  boolean exportDefectNewListEntry(Long alistEntryId);
   //  void addMedServiceToEntry(Long aEntryId, Long aMedServiceId);
     void checkListEntry(Long aListEntryId, boolean updateKsgIfExist, String aParams, long aMonitorId);
     void testUnionMecCase (Long aListEntryId, Long aHospitalMedcaseId, Long aPatientId, String aEntryType);
    void makeCheckEntry (Long aEntryId, boolean updateKsgIfExist);
     VocKsg getBestKSG(E2Entry aEntry, boolean updateKsgIfExist);
     void calculateEntryPrice(E2Entry  aEntry);
     void fillListEntry(E2ListEntry aListEntry, String aHistoryNumbers) throws NamingException, SQLException;
     void reFillListEntry(Long aListEntryId) ;
     void addMedHelpProfileBedType (Long aMedHelpId, Long aBedTypeId, Long aBedSubTypeId);
      Boolean addDiagnosisAndServiceToEntry(Long aEntryId, String aData) ;
    void addHospitalMedCaseToList(String aHistoryNumber, Long aListEntryId) throws SQLException, NamingException;
    BigDecimal calculateCusmo(String bedSubTypeCode, Long aDepartmentId, Long aProfileId, Date aDate);
    BigDecimal getActualKsgUprCoefficient(VocKsg aKsg, Date aFinishDate);
    <T> T getActualVocByClassName(Class aClass, Date aActualDate, String aSqlAdd);
}
