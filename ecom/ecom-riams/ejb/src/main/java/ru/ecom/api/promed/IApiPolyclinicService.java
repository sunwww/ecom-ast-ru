package ru.ecom.api.promed;

import java.sql.Date;

/**
 * Created by Milamesher on 06.02.2019.
 */
public interface IApiPolyclinicService {
    String getPolyclinicCase(Date dateTo, String sstream, Boolean isUpload, boolean includeNeoUzi);
    String setEvnTap(Long medcaseId, Long tapId);
    String getWfInfo(Long workfunctionId);
    String setWfInfo(Long workfunctionId,Long promedcodeLpuSection,Long promedcodeWorkstaff);
}
