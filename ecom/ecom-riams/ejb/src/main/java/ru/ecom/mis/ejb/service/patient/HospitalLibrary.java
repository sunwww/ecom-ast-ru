package ru.ecom.mis.ejb.service.patient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HospitalLibrary {
    public static String getSqlGringo(boolean aIsAnd, String aNationalityAtr) {
        if (aNationalityAtr == null || aNationalityAtr.equals("")) aNationalityAtr = "ok";
        StringBuilder sql = new StringBuilder();
        if (aIsAnd) sql.append(" and ");
        sql.append(" (").append(aNationalityAtr).append(".id is not null and ").append(aNationalityAtr).append(".voc_code!='643') ");

        return sql.toString();
    }

    public static String getSqlForPatient(boolean aIsAnd, boolean aIsForeign
            , String aFieldDate, String aPatientAtr, String aSocialStatusAtr
            , String aMedPolicyAtr, String aNationalityAtr) {
        StringBuilder sql = new StringBuilder();
        if (aPatientAtr == null || aPatientAtr.equals("")) aPatientAtr = "p";
        if (aSocialStatusAtr == null || aSocialStatusAtr.equals("")) aSocialStatusAtr = "pvss";
        if (aMedPolicyAtr == null || aMedPolicyAtr.equals("")) aMedPolicyAtr = "pmp";
        if (aNationalityAtr == null || aNationalityAtr.equals("")) aNationalityAtr = "ok";
        if (aIsAnd) sql.append(" and ");
        sql.append(" (").append(aNationalityAtr).append(".id is null or ").append(aNationalityAtr).append(".voc_code='643') and ");
        if (aFieldDate == null || aFieldDate.equals("")) {
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            aFieldDate = "cast('" + format.format(date) + "' as varchar(10)";
        }
        if (aIsForeign) {
            sql
                    .append("( ")
                    .append(aPatientAtr).append(".territoryRegistrationNonresident_id is not null or ")
                    .append(aSocialStatusAtr).append(".omcCode='И0' ")
                    .append("or  coalesce((   select  count(case when ").append(aMedPolicyAtr).append(".dtype='MedPolicyOmcForeign' then 1 else null end) ")
                    .append("  from medpolicy ").append(aMedPolicyAtr).append(" ")
                    .append("  where ").append(aPatientAtr).append(".id=").append(aMedPolicyAtr).append(".patient_id and ").append(aMedPolicyAtr).append(".actualDateFrom <=").append(aFieldDate).append(" ")
                    .append("  and coalesce(")
                    .append(aMedPolicyAtr).append(".actualDateTo,current_date) >=").append(aFieldDate).append(" ")
                    .append("  having count(case when ").append(aMedPolicyAtr).append(".dtype='MedPolicyOmc' then 1 else null end)=0 ")
                    .append(" )");
            sql.append(",0)>0 ");
        } else {
            sql
                    .append("( ")
                    .append(aPatientAtr).append(".territoryRegistrationNonresident_id is null ")
                    .append(" and ")
                    .append(" (").append(aSocialStatusAtr).append(".omcCode!='И0' ")
                    .append(" or ")
                    .append(" ").append(aSocialStatusAtr).append(".omcCode is null) ")
                    .append(" and ")
                    .append(" coalesce(( ")
                    .append("  select ")
                    .append("      count(case when ").append(aMedPolicyAtr).append(".dtype='MedPolicyOmcForeign' then 1 else null end) ")
                    .append("  from medpolicy ").append(aMedPolicyAtr).append(" ")
                    .append("  where ").append(aPatientAtr).append(".id=").append(aMedPolicyAtr).append(".patient_id and ").append(aMedPolicyAtr).append(".actualDateFrom <=").append(aFieldDate).append(" ")
                    .append("  and coalesce(").append(aMedPolicyAtr).append(".actualDateTo,current_date) >=").append(aFieldDate).append(" ")
                    .append("  having count(case when ").append(aMedPolicyAtr).append(".dtype='MedPolicyOmc' then 1 else null end)=0 ")
                    .append(" ),0)");
            sql.append("=0 ");
        }


        sql.append(") ")
        ;
        return sql.toString();
    }

}
