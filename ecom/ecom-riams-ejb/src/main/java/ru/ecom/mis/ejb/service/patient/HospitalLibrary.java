package ru.ecom.mis.ejb.service.patient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HospitalLibrary {
	public static String getSqlForPatient(boolean aIsAnd,boolean aIsForeign
			,String aFieldDate, String aPatientAtr,String aSocialStatusAtr, String aMedPolicyAtr) {
		StringBuilder sql = new StringBuilder() ;
		if (aPatientAtr==null || aPatientAtr.equals("")) aPatientAtr="p";  
		if (aSocialStatusAtr==null || aSocialStatusAtr.equals("")) aSocialStatusAtr="pvss";  
		if (aMedPolicyAtr==null || aMedPolicyAtr.equals("")) aMedPolicyAtr="pmp";  
		if (aIsAnd) sql.append(" and ");
		
		if (aFieldDate==null || aFieldDate.equals("")) {
			Date date = new Date() ;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" ) ;
			aFieldDate = new StringBuilder().append("cast('").append(format.format(date)) .append("' as varchar(10)").toString() ;
		}
		if (aIsForeign) {
			sql
			.append("( ")
			.append(aPatientAtr).append(".territoryRegistrationNonresident_id is not null ")
			.append("or ")
			.append("").append(aSocialStatusAtr).append(".omcCode='И0' ")
			.append("or ")
			.append("( ")
			.append("  select ")
			.append("      count(case when ").append(aMedPolicyAtr).append(".dtype='MedPolicyOmcForeign' then 1 else null end) ")
			.append("  from medpolicy ").append(aMedPolicyAtr).append(" ")
			.append("  where ").append(aPatientAtr).append(".id=").append(aMedPolicyAtr).append(".patient_id and ").append(aMedPolicyAtr).append(".actualDateFrom <=").append(aFieldDate).append(" ")
			.append("  and coalesce(").append(aMedPolicyAtr).append(".actualDateTo,current_date) >=").append(aFieldDate).append(" ")
			.append("  having count(case when ").append(aMedPolicyAtr).append(".dtype='MedPolicyOmc' then 1 else null end)=0 ")
			.append(" )");
			sql.append(">0 ") ;
		} else {
			sql
			.append("( ")
			.append(aPatientAtr).append(".territoryRegistrationNonresident_id is null ")
			.append(" and ")
			.append(" (").append(aSocialStatusAtr).append(".omcCode!='И0' ")
			.append(" or ")
			.append(" ").append(aSocialStatusAtr).append(".omcCode is null) ")
			.append(" and ")
			.append("( ")
			.append("  select ")
			.append("      count(case when ").append(aMedPolicyAtr).append(".dtype='MedPolicyOmcForeign' then 1 else null end) ")
			.append("  from medpolicy ").append(aMedPolicyAtr).append(" ")
			.append("  where ").append(aPatientAtr).append(".id=").append(aMedPolicyAtr).append(".patient_id and ").append(aMedPolicyAtr).append(".actualDateFrom <=").append(aFieldDate).append(" ")
			.append("  and coalesce(").append(aMedPolicyAtr).append(".actualDateTo,current_date) >=").append(aFieldDate).append(" ")
			.append("  having count(case when ").append(aMedPolicyAtr).append(".dtype='MedPolicyOmc' then 1 else null end)=0 ")
			.append(" )");
			sql.append("=0 ") ;
		}
				

		sql.append(") ")
		;
		return sql.toString() ;
	}

}
