package ru.ecom.mis.ejb.service.synclpufond;

import java.sql.Date;

/**
 * Синхронизация из фонда омс
 */
public interface ISyncLpuFondService {
    void sync(long aMonitorId, long aTimeId) ;
    Long findPatientId(String aLastname, String aFirstname, String aMiddlename, Date aBirthday) ;
    Long findMedPolicyId(String aSeries, String aNumber) ;
}