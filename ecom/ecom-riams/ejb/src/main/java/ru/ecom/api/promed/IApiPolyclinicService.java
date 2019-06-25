package ru.ecom.api.promed;

import java.sql.Date;

/**
 * Created by Milamesher on 06.02.2019.
 */
public interface IApiPolyclinicService {
    String getPolyclinicCase(Date dateTo, String sstream, Boolean isUpload, boolean includeNeoUzi);
    String setEvnTap(Long medcase_id, String tap_id);
    String getWfInfo(Long workfunction_id);
    String setWfInfo(Long workfunction_id,String promedcode_lpusection,String promedcode_workstaff);
}
