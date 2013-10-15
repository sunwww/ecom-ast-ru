package exp;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.stream.XMLStreamException;

import util.FileUtil;
import util.SqlUtil;


public class ExpIteratorExtDisp {
	public static void main(String args[]) throws IOException, XMLStreamException, SQLException{
		
		System.out.println("Start"+new java.util.Date().getTime()) ;
		ExpIterator.toXmlFile("ExtDisp",FileUtil.getTempFileName("testExpIteratorExtDisp.xml"),"20130901", "20130931", "1", true, false, false);
		System.out.println("Finish"+new java.util.Date().getTime()) ;
	}
	public static String getSql(String aDateStartE, String aDateEndE
			, String aLpu) {
	
		String sql = null;
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT"); 
		sb.append(SqlUtil.getDISTINCT("edc.id"));
		addExtDispServiceSql(sb, "D1_014", 0, "abdominalUS",true);
		sb.append(", vedhg.code AS addDispHealthGroup");
		sb.append(", vedsg.code AS addDispSocialGroup");
		sb.append(", edc.finishDate AS admissionDate");
		sb.append(", '08.00' AS admissionTime");
		sb.append(", '1' AS bedDays");
		addExtDispServiceSql(sb, "D1_010", 1, "biochemicalBloodDate");
		addExtDispServiceSql(sb, "D1_008", 2, "bloodDate");
		addExtDispServiceSql(sb, "D1_009", 3, "bloodFormulaDate");
		sb.append(", CASE WHEN mcd.number IS NULL THEN p.patientSync ELSE mcd.number END AS caseHistoryNumber");
		addExtDispServiceSql(sb, "D1_005", 4, "cervicalChannelDate");
		addExtDispServiceSql(sb, "D1_002", 5, "cholesterolDate");
		sb.append(", '0' AS complications");
		sb.append(", ml.name AS departmentName");
		sb.append(", '0A' AS depType");
		sb.append(", edc.finishDate AS dischargeDate");
		sb.append(", 'П' AS directionType");
		addExtDispServiceSql(sb, "D1_004", 6, "ecgDate");
		sb.append(", '0' AS emergency");
		sb.append(", ved.code AS extDisp");
		addExtDispServiceSql(sb, "D1_012", 7, "fecesBloodDate");
		addExtDispServiceSql(sb, "D1_006", 8, "fluorographyDate");		
		sb.append(", edc.isServiceIndication AS toNextStage");
		sb.append(", CASE WHEN ml.omccode IS NULL THEN 'Lpu' ELSE ml.omccode END AS kodLpu");
		sb.append(", '' AS lpuFrom");
		sb.append(", 'O' AS lpuFromUnit");
		sb.append(", vlf.code AS lpuFunction");
		addExtDispServiceSql(sb, "D1_007", 9, "mammographyDate");
		sb.append(", '' AS medcaseId");
		addExtDispServiceSql(sb, "D1_015", 10, "neurologistDate");
		addExtDispServiceSql(sb, "D1_001_1", 11, "nurseDate");
		addExtDispServiceSql(sb, "D1_013", 12, "psaDate");
		sb.append(", '0' AS privilegeRecipeAmount");
		sb.append(", '1' AS provisional");
		sb.append(", '2' AS reason");
		sb.append(", 'PROFYLACTIC' AS reasonC");
		sb.append(", '0' AS recipeAmount");
		sb.append(", '20' AS result");
		addExtDispServiceSql(sb, "D1_003", 13, "saccharumDate");
		sb.append(", '' AS smo");
		sb.append(", '2' AS vidLpu");
		sb.append(", '301' AS resultX");
		addExtDispServiceSql(sb, "D1_011", 14, "urineDate");
		sb.append(", 'POLYCLINIC' AS workPlaceType");
		sb.append(", '0' AS yet");
		
	
		ExpIterator.addCommonSelect(sb);
		
		sb.append(" FROM ExtDispCard edc");
		sb.append(" LEFT JOIN VocExtDisp ved on edc.dispType_id=ved.id");
		sb.append(" LEFT JOIN VocExtDispAgeGroup vedag on edc.ageGroup_id=vedag.id");
		sb.append(" LEFT JOIN VocExtDispSocialGroup vedsg on edc.socilaGroup_id=vedsg.id");
		sb.append(" LEFT JOIN MisLpu ml on edc.lpu_id=ml.id");
		sb.append(" LEFT JOIN VocIdc10 vi on edc.idcMain_id=vi.id");
		sb.append(" LEFT JOIN VocExtDispHealthGroup vedhg on edc.healthGroup_id=vedhg.id");
		sb.append(" LEFT JOIN Patient p ON edc.patient_id=p.id");
		sb.append(" LEFT JOIN WorkFunction wf ON edc.workFunction_id=wf.id");
		sb.append(" LEFT JOIN Kinsman ksm ON edc.kinsman_id = ksm.id");
		sb.append(" LEFT JOIN Medcard mcd ON p.id=mcd.person_id");
		sb.append(" LEFT JOIN VocLpuFunction vlf ON ml.lpuFunction_id = vlf.id");
		
		sb.append(" LEFT JOIN MedPolicy mp ON mp.patient_id=p.id AND mp.id=");
		sb.append("(");
			sb.append("SELECT max(mp1.id)");
			sb.append(" FROM MedPolicy mp1");
			sb.append(" WHERE mp1.patient_id=p.id");
			sb.append(" AND edc.finishDate BETWEEN mp1.actualDateFrom");
			sb.append(" AND "+SqlUtil.getISNULL("mp1.actualDateTo","CURRENT_DATE",false,""));
			sb.append(" GROUP BY mp1.patient_id");
		sb.append(")");	
		
		ExpIterator.addCommonJoin(sb);

		sb.append(" WHERE edc.finishDate BETWEEN TO_DATE('20130901','YYYYMMDD') AND TO_DATE('20130931','YYYYMMDD')");

		
		sql = sb.toString();
		
		return sql;
	}
	private static void addExtDispServiceSql(StringBuilder aSb, String aService, int aCount, String aAlias){
		addExtDispServiceSql(aSb, aService, aCount, aAlias, false);
	}
	private static void addExtDispServiceSql(StringBuilder aSb, String aService, int aCount, String aAlias, boolean aIsFirst){
		String eds = "eds"+aCount;
		String veds = "veds"+aCount;
		aSb.append(aIsFirst ? " " : ", ");
		aSb.append("(SELECT");
		aSb.append(" "+eds+".serviceDate");
		aSb.append(" FROM ExtDispService "+eds);
		aSb.append(" INNER JOIN VocExtDispService "+veds);
		aSb.append(" ON "+eds+".serviceType_id="+veds+".id");
		aSb.append(" WHERE edc.id=eds"+aCount+".card_id");
		aSb.append(" AND "+veds+".code='"+aService+"')");
		aSb.append(" AS "+aAlias);
	}
}

