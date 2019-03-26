package ru.ecom.mis.ejb.service.disability;

import ru.ecom.mis.ejb.form.disability.DisabilityDocumentForm;
import ru.ecom.poly.ejb.services.GroupByDate;

import javax.naming.NamingException;
import java.text.ParseException;
import java.util.List;

public interface IDisabilityService {

    String importDisabilityDocument(String aDisabilityNumber, String aSnils, Long aPatientId, String aMethod);

    String getLNNumberRange(Long aCount);

    String annulDisabilityDocument(Long aDocumentId, String aReasonAnnulId, String textReason, String snils);

    String exportDisabilityDocument(Long aDocumentId);

    void createF16vn(String aDateStart, String aDateEnd);

    String closeDisabilityDocument(Long aDocumentId, Long aReasonId, String aSeries, String aNumber, String aOtherCloseDate, String dateGoToWork);

    List<DisabilityDocumentForm> findDocumentBySeriesAndNumber(String aFind);

    List<DisabilityDocumentForm> findOpenTicketByDate(String aDate);

    List<DisabilityDocumentForm> findCloseTicketByDate(String aDate, String aType);

    List<GroupByDate> findOpenDocumentGroupByDate();

    List<GroupByDate> findCloseDocumentGroupByDate(String aDateFrom, String aDateTo);

    String getDataByClose(Long aDocumentId);

    Long createDuplicateDocument(Long aDocId, Long aReasonId, String aSeries, String aNumber, Long aWorkFuntion2, String aJob, Boolean aUpdateJob);

    Long createWorkComboDocument(Long aDocId, String aJob, String aSeries, String aNumber, Long aVocCombo, Long aPrevDocument);

    boolean isRightSnils(String aSNILS) throws ParseException, NamingException;

    String getSoftConfigValue(String aKey, String aDefaultValue);
}
