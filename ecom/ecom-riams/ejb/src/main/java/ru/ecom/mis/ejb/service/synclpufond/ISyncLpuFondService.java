package ru.ecom.mis.ejb.service.synclpufond;

import java.sql.Date;

/**
 * Синхронизация из фонда омс
 */
public interface ISyncLpuFondService {
    public void sync(long aMonitorId, long aTimeId) ;

    public Long findPatientId(String aLastname, String aFirstname, String aMiddlename, Date aBirthday) ;

    public Long findMedPolicyId(String aSeries, String aNumber) ;

}
