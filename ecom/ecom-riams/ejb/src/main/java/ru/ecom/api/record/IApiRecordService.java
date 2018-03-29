package ru.ecom.api.record;

import java.sql.Date;

public interface IApiRecordService {
    String recordPatient(Long aCalendarTimeId, String aPatientLastname, String aPatientFirstname, String aPatientMiddlename, Date aPatientBirthday, String aPatientGUID, String aComment);
    String annulRecord(Long aCalendarTimeId, String aLastname, String aFirstname, String aMiddlename, Date aBirthday, String aPatientGUID);
    Long saveExternalDodument(String aFilename, String base64String, Long aPatientId, Long aMedcaseId, Long aCalendartimeId, String aDocumentType);
    String saveFile(String aFilename, String base64String);
}
