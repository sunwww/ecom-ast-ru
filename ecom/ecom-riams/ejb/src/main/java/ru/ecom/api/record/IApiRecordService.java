package ru.ecom.api.record;

import java.sql.Date;
import java.sql.Time;

public interface IApiRecordService {
    String recordPatient(Long aCalendarTimeId, String aPatientLastname, String aPatientFirstname, String aPatientMiddlename, Date aPatientBirthday, String aComment, String aPhone, String aRecordType);
    String annulRecord(Long aCalendarTimeId, String aLastname, String aFirstname, String aMiddlename, Date aBirthday);
    Long saveExternalDodument(String aFilename, String base64String, Long aPatientId, Long aMedcaseId, Long aCalendartimeId, String aDocumentType);
    String saveFile(String aFilename, String base64String);
    String recordPromedPatient(String aPromedDoctorId, String aLastname, String aFirstname, String aMiddlename, Date aBirthdate, Date aCalendarDate, Time aCalendarTime, String aPhone);
}
