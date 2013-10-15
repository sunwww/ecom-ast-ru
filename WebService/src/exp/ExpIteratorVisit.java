package exp;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.stream.XMLStreamException;

import util.FileUtil;
import util.SqlUtil;


public class ExpIteratorVisit {
	public static void main(String args[]) throws IOException, XMLStreamException, SQLException{
		
		System.out.println("Start"+new java.util.Date().getTime()) ;
		ExpIterator.toXmlFile("Visit",FileUtil.getTempFileName("testExpIteratorVisit.xml"),"20130121", "20130121", "1", true, false, false);
		System.out.println("Finish"+new java.util.Date().getTime()) ;
	}
	public static String getSql(String aDateStartE, String aDateEndE
			, String aLpu, Boolean aCasesFromSPO) {
	
		String sql = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(SqlUtil.getDISTINCT("mc.id"));
		sb.append(" mc.dateStart AS admissionDate");
		sb.append(", mc.timeExecute AS admissionTime");
		sb.append(", '1' AS bedDays");
		sb.append(", "+SqlUtil.getISNULL("mcd.number","p.patientSync",true,"caseHistoryNumber"));
		sb.append(", '0' AS complications");
		sb.append(", ml.name AS departmentName");
		sb.append(", CASE WHEN vlf.code='6' THEN va.omcDepType ELSE vodt.code END AS depType");
		sb.append(", mc.dateStart AS dischargeDate");
	 	//sb.append(", 'П' AS directionType");
		sb.append(", mc.emergency AS emergency");
		sb.append(", "+SqlUtil.getISNULL("ml.omccode",aLpu,true,"kodLpu"));
		sb.append(", "+SqlUtil.getISNULL("mlo.omcCode",aLpu,true,"lpuFrom"));
		sb.append(", 'O' AS lpuFromUnit");
		sb.append(", vlf.code AS lpuFunction");
		sb.append(", mc.id AS medcaseId");
		sb.append(", "+SqlUtil.getISNULL("mc.privilegeRecipeAmount","1",false,"privilegeRecipeAmount"));
		sb.append(", mc.provisional AS provisional");
		sb.append(", vr.omcCode AS reason");
		sb.append(", vr.code AS reasonC");
		sb.append(", '0' AS recipeAmount");
		sb.append(", vms.code AS render");
		sb.append(", "+SqlUtil.getISNULL("vvr.omccode","1",true,"result"));
		//sb.append(", vvr.codefpl AS resultX");
		sb.append(", mc.parent_id AS smo");
		sb.append(", CASE WHEN vlf.code='6' THEN '8' ELSE '2' END AS vidLpu");
		sb.append(", CASE WHEN vlf.code='6' THEN vvo.codefamb ELSE vvr.codefpl END AS resultX");
		sb.append(", vwpt.code AS workPlaceType");
		sb.append(", mc.uet AS yet");
		
		ExpIterator.addCommonSelect(sb);

		sb.append(" FROM MedCase mc");
		sb.append(" INNER JOIN Patient p ON mc.patient_id=p.id");
		sb.append(" LEFT JOIN MedCase mcp ON mc.parent_id=mcp.id");
		sb.append(" LEFT JOIN VocWorkPlaceType vwpt ON mc.workPlaceType_id=vwpt.id");
		sb.append(" LEFT JOIN VocVisitResult vvr ON mc.visitResult_id=vvr.id");
		sb.append(" LEFT JOIN VocVisitOutcome vvo ON mc.visitOutcome_id=vvo.id");
		sb.append(" LEFT JOIN VocReason vr ON mc.visitReason_id=vr.id");
		sb.append(" LEFT JOIN WorkFunction wf ON mc.workFunctionExecute_id=wf.id");
		sb.append(" LEFT JOIN MisLpu mlo ON mc.orderLpu_id=mlo.id");
		sb.append(" LEFT JOIN Kinsman ksm ON mc.kinsman_id = ksm.id");
		sb.append(" LEFT JOIN VocServiceStream vsstr ON mc.serviceStream_id=vsstr.id");
		sb.append(" LEFT JOIN Medcard mcd ON p.id=mcd.person_id");
		sb.append(" LEFT JOIN VocAmbulance va ON mc.ambulance_id=va.id");

		sb.append(" LEFT JOIN MedPolicy mp ON mp.patient_id=p.id AND mp.id=");
			sb.append("(");
				sb.append("SELECT max(mp1.id)");
				sb.append(" FROM MedPolicy mp1");
				sb.append(" WHERE mp1.patient_id=p.id");
				sb.append(" AND mc.dateStart BETWEEN mp1.actualDateFrom");
				sb.append(" AND "+SqlUtil.getISNULL("mp1.actualDateTo","CURRENT_DATE",false,""));
				sb.append(" GROUP BY mp1.patient_id");
			sb.append(")");		
	
		sb.append(" LEFT JOIN Diagnosis dmain ON dmain.medcase_id=mc.id AND dmain.id=");
			sb.append("(");
				sb.append("SELECT max(d1.id)");
				sb.append(" FROM Diagnosis d1");
				sb.append(" LEFT JOIN VocPriorityDiagnosis vpd1 ON d1.priority_id=vpd1.id");
				sb.append(" WHERE d1.medcase_id=mc.id AND vpd1.code='1'");
				sb.append(" GROUP BY d1.medcase_id");
			sb.append(")");
		
		sb.append(" LEFT JOIN Diagnosis dcont ON dcont.medcase_id=mc.id AND dcont.id=");
			sb.append("(");
				sb.append("SELECT max(d2.id)");
				sb.append(" FROM Diagnosis d2");
				sb.append(" LEFT JOIN VocPriorityDiagnosis vpd2 ON d2.priority_id=vpd2.id");
				sb.append(" WHERE d2.medcase_id=mc.id AND vpd2.code='3'");
				sb.append(" GROUP BY d2.medcase_id");
			sb.append(")");

		sb.append(" LEFT JOIN MedCase mcs ON mcs.parent_id=mc.id AND mcs.id=");
			sb.append("(");
				sb.append("SELECT min(mcs1.id)");
				sb.append(" FROM MedCase mcs1");
				sb.append(" WHERE mcs1.parent_id=mc.id AND mcs1.DTYPE='ServiceMedCase'");
				sb.append(" GROUP BY mcs1.parent_id");
			sb.append(")");
		sb.append(" LEFT JOIN MedService ms ON mcs.medservice_id=ms.id");
		sb.append(" LEFT JOIN VocMedService vms ON ms.vocmedservice_id=vms.id");

		ExpIterator.addCommonJoin(sb);
		
		sb.append(" LEFT JOIN MisLpu ml ON w.lpu_id=ml.id");
		sb.append(" LEFT JOIN VocLpuFunction vlf ON ml.lpuFunction_id = vlf.id");
		
 		sb.append(" WHERE");
		sb.append(" (mc.DTYPE='Visit'");
		sb.append(" OR mc.DTYPE='ShortMedCase')");
		sb.append(" AND "+(aCasesFromSPO?"mcp.dateFinish":"mc.dateStart"));
		sb.append(" BETWEEN TO_DATE('"+aDateStartE+"','YYYYMMDD') AND TO_DATE('"+aDateEndE+"','YYYYMMDD')");
		sb.append(" AND mc.dateStart IS NOT NULL");
		sb.append(" AND (mc.noActuality='0' OR mc.noActuality IS NULL)");
		
		//sb.append(" LIMIT 10");

		sql = sb.toString();
		
		return sql;
	}
}

