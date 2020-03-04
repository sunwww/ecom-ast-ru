package ru.ecom.expert2.service;

import ru.ecom.expert2.domain.E2Bill;
import ru.ecom.expert2.domain.E2Entry;
import ru.ecom.expert2.domain.E2ListEntry;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.expert2.domain.voc.VocE2VidSluch;
import ru.ecom.expomc.ejb.domain.med.VocKsg;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

public interface IExpert2Service {
    void makeOncologyCase(Long aListEntryId, String aJsonString, String aDefectCode);
    String splitLongCase(Long aEntryId);
    String getMedcaseCost(Long aMedcaseId);
    E2Entry getEntryJson(Long aEntryId);
    String splitForeignOtherBill(Long aListEntryId, String aBillNumber, Date aBillDate, String aTerritories);
    BigDecimal calculatePolyclinicEntryPrice(VocE2VidSluch aVidSluch, Date aFinishDate, VocE2MedHelpProfile aMedHelpProfile);
    E2Bill getBillEntryByDateAndNumber(String sBillNumber, String aBillDate, String aComment);
    E2Bill getBillEntryByDateAndNumber(String aBillNumber, java.util.Date aBillDate);
    Long getBillIdByDateAndNumber(String aBillNumber, String aBillDate);
    boolean exportDefectNewListEntry(Long aListEntryId);
    void exportErrorsNewListEntry(Long aListEntryId, String[] aErrorCodes, String[] aSanctionCodes);
    void checkListEntry(Long aListEntryId, boolean updateKsgIfExist, String aParams, long aMonitorId);
    void makeCheckEntry (Long aEntryId, boolean updateKsgIfExist);
    E2Entry calculateEntryPrice(E2Entry  aEntry);
    void fillListEntry(E2ListEntry aListEntry, String aHistoryNumbers, long aMonitorId) throws NamingException, SQLException;
    void reFillListEntry(Long aListEntryId, long aMonitorId) ;
    Boolean addDiagnosisAndServiceToEntry(Long aEntryId, String aData) ;
    void addHospitalMedCaseToList(String aHistoryNumber, Long aListEntryId) throws SQLException, NamingException;
    BigDecimal calculateCusmo(String bedSubTypeCode, Long aDepartmentId, Long aProfileId, Date aDate);
    BigDecimal getActualKsgUprCoefficient(VocKsg aKsg, Date aFinishDate);
    <T> T getActualVocByClassName(Class aClass, Date aActualDate, String aSqlAdd);
    <T> T getActualVocBySqlString(Class aClass, String aSql);
    E2Entry cloneEntity(E2Entry aSourceObject);
    BigDecimal calculateResultDifficultyCoefficient(E2Entry aEntry);
}
