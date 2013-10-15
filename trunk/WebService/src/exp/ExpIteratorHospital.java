package exp;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.stream.XMLStreamException;

import util.FileUtil;
import util.SqlUtil;

public class ExpIteratorHospital {
	public static void main(String args[]) throws IOException, XMLStreamException, SQLException{
		
		System.out.println("Start"+new java.util.Date().getTime()) ;
		ExpIterator.toXmlFile("Hospital",FileUtil.getTempFileName("testExpIteratorHospital.xml"),"20130121", "20130131", "1", false, false, false);
		System.out.println("Finish"+new java.util.Date().getTime()) ;
	}
	public static String getSql(String aDateStartE, String aDateEndE
			, String aLpu, boolean aCasesFromSLS, boolean aAllLpu) {
				
		String sql = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(SqlUtil.getDISTINCT("mc.id"));
		sb.append(" mc.dateStart AS admissionDate");
		sb.append(", mc.entranceTime AS admissionTime");
		sb.append(", "+SqlUtil.getISNULL("mc.dateFinish", "mc.TransferDate", false, "")+"-mc.dateStart AS bedDays");
		sb.append(", vbst.code AS bedSubType");
		sb.append(", ss.code AS caseHistoryNumber");
		sb.append(", ml.name AS departmentName");
		sb.append(", vbt.omcCode AS depType");
		sb.append(", "+SqlUtil.getISNULL("mc.dateFinish","mc.TransferDate", false, "dischargeDate"));
	 	//sb.append(", 'П' AS directionType");
		sb.append(", "+SqlUtil.getISNULL("mc.emergency","mcp.emergency", false, "emergency"));
		sb.append(", vht.code AS hospitalizationType");
		sb.append(", "+SqlUtil.getISNULL("mc.hotelServices","mcp.hotelServices",false,"hotelServices"));
		sb.append(", "+SqlUtil.getISNULL("mlp.omccode",aLpu,true,"kodLpu"));
		sb.append(", "+SqlUtil.getISNULL("mlo.omcCode",aLpu,true,"lpuFrom"));
		sb.append(", omcf.voc_code AS lpuFromUnit");
		sb.append(", vlf.code AS lpuFunction");
		sb.append(", mc.id AS medcaseId");
		sb.append(", "+SqlUtil.getISNULL("ostanE.model","ostan.model",true,"omcStandart"));
		sb.append(", mmp.policies_id AS policy");
		sb.append(", mc.privilegeRecipeAmount AS privilegeRecipeAmount");
		sb.append(", mc.provisional AS provisional");
		sb.append(", vh.code AS rehospitalization");
		sb.append(", "+SqlUtil.getISNULL("vhr.omccode","2",true,"result"));
		sb.append(", mc.parent_id AS smo");

		sb.append(", (");
  			sb.append("SELECT "+SqlUtil.getLIST("vms.code",""));
			sb.append(" FROM SurgicalOperation so");
			sb.append(" LEFT JOIN MedService ms ON so.medservice_id=ms.id");
			sb.append(" LEFT JOIN VocServiceType vst ON ms.serviceType_id=vst.id");
			sb.append(" LEFT JOIN VocMedService vms ON ms.vocmedservice_id=vms.id");
			sb.append(" LEFT JOIN VocServiceStream vsstr1 ON so.serviceStream_id=vsstr1.id");
			sb.append(" WHERE so.medcase_id=mc.id");
			sb.append(" AND (vsstr1.code IS NULL OR vsstr1.code='OBLIGATORYINSURANCE')");
			sb.append(" AND vst.code='OPERATION'");
		sb.append(") AS operations");	
		
		sb.append(", (");
			sb.append(getMedcaseDatesSql("mc1", "min"));
		sb.append(") AS admissionDateC");
		
		sb.append(", (");
			sb.append(getMedcaseDatesSql("mc2", "max"));
		sb.append(") AS dischargeDateC");

		sb.append(", (");
			sb.append(getMedcaseListSql("mcl"));
		sb.append(") AS medcaselist");
				
		ExpIterator.addCommonSelect(sb);
	
		sb.append(" FROM MedCase mc");
		sb.append(" INNER JOIN Patient p ON mc.patient_id=p.id");
		sb.append(" LEFT JOIN MedCase mcp ON mc.parent_id=mcp.id");
		sb.append(" LEFT JOIN MisLpu ml ON mc.department_id=ml.id");
		sb.append(" LEFT JOIN WorkFunction wf ON mc.ownerFunction_id=wf.id");
		sb.append(" LEFT JOIN BedFund bf ON mc.bedfund_id=bf.id");
		sb.append(" LEFT JOIN VocHospType vht ON mc.hospType_id=vht.id");
		sb.append(" LEFT JOIN MedService ms ON mc.medService_id=ms.id");
		sb.append(" LEFT JOIN MisLpu mlo ON mcp.orderLpu_id=mlo.id");
		sb.append(" LEFT JOIN MisLpu mlp ON mcp.lpu_id=mlp.id");
		sb.append(" LEFT JOIN StatisticStub ss ON mcp.statisticStub_id=ss.id");
		sb.append(" LEFT JOIN VocHospitalizationResult vhr ON mcp.result_id=vhr.id");
		sb.append(" LEFT JOIN OMC_FRM omcf ON mcp.orderType_id=omcf.id");
		sb.append(" LEFT JOIN VocHospitalization vh ON mcp.hospitalization_id=vh.id");
		sb.append(" LEFT JOIN VocBedType vbt ON bf.bedtype_id=vbt.id");
		sb.append(" LEFT JOIN VocBedSubType vbst ON bf.bedSubType_id=vbst.id");
		sb.append(" LEFT JOIN Kinsman ksm ON mcp.kinsman_id = ksm.id");
		sb.append(" LEFT JOIN VocLpuFunction vlf ON ml.lpuFunction_id = vlf.id");
		sb.append(" LEFT JOIN VocServiceStream vsstr ON mc.serviceStream_id=vsstr.id");
		sb.append(" LEFT JOIN MedCase_MedPolicy mmp ON mcp.id=mmp.medcase_id");
		sb.append(" LEFT JOIN MedPolicy mp ON mmp.policies_id=mp.id");
		sb.append(" LEFT JOIN Medcard mcd ON p.id=mcd.person_id");
		sb.append(" LEFT JOIN OMC_STANDART ostan ON mc.omcStandart_id=ostan.id");
		sb.append(" LEFT JOIN OMC_STANDART ostanE ON mc.omcStandartExpert_id=ostanE.id");
	
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

		ExpIterator.addCommonJoin(sb);
		
		sb.append(" WHERE");
		if(aCasesFromSLS){	
			sb.append(" mcp.dateFinish"); 
			sb.append(" BETWEEN TO_DATE('"+aDateStartE+"','YYYYMMDD')");
			sb.append(" AND TO_DATE('"+aDateEndE+"','YYYYMMDD')");
		} else  {
			sb.append(" (");
			sb.append("((mp.DTYPE IS NULL OR mp.DTYPE<>'MedPolicyOmcForeign')");
			sb.append(" AND "+SqlUtil.getISNULL("mc.dateFinish","mc.TransferDate",false,""));
			sb.append(" BETWEEN TO_DATE('"+aDateStartE+"','YYYYMMDD')");
			sb.append(" AND '"+aDateEndE+"')");
			sb.append(" OR (mp.DTYPE='MedPolicyOmcForeign' AND mcp.dateFinish"); 
			sb.append(" BETWEEN '"+aDateStartE+"' AND '"+aDateEndE+"')");
			sb.append(")");
		}			
		sb.append(" AND mc.DTYPE='DepartmentMedCase'");
		sb.append(" AND ("+SqlUtil.getISNULL("mc.noActuality","'0'",false,"")+")='0'");
		if(aAllLpu) sb.append(" AND ("+SqlUtil.getISNULL("ml.isNoOmc","'0'",false,"")+")='0'");
			
			

		//sb.append(" LIMIT 100");

		sql = sb.toString();
		
		return sql;
	}
	private static String getMedcaseDatesSql(String aMedcaseAlias, String aFunction){
		StringBuilder sbd = new StringBuilder();
		if(aFunction.equalsIgnoreCase("min")||aFunction.equalsIgnoreCase("max")){
			sbd.append("SELECT");
			if(aFunction.equalsIgnoreCase("min")) sbd.append(" min("+aMedcaseAlias+".datestart)");
			else if(aFunction.equalsIgnoreCase("max")) sbd.append(" max("+SqlUtil.getISNULL(aMedcaseAlias+".datefinish",aMedcaseAlias+".transferDate",false,"")+")");
			sbd.append(" FROM MedCase "+aMedcaseAlias);
			sbd.append(" LEFT JOIN BedFund bf1 ON "+aMedcaseAlias+".bedfund_id=bf1.id");
			sbd.append(" LEFT JOIN VocBedType vbt1 ON bf1.bedtype_id=vbt1.id");
			sbd.append(" WHERE "+aMedcaseAlias+".parent_id = mc.parent_id");
			sbd.append(" AND "+aMedcaseAlias+".DTYPE = 'DepartmentMedCase'");
			sbd.append(" AND "+aMedcaseAlias+".servicestream_id = mc.servicestream_id");
			sbd.append(" AND bf1.bedtype_id = bf.bedtype_id");
			sbd.append(" AND bf1.bedsubtype_id = bf.bedsubtype_id");
			sbd.append(" AND ("+SqlUtil.getISNULL(aMedcaseAlias+".noActuality","'0'",false,"")+")='0'");
		}
		return sbd.toString();
	}
	private static String getMedcaseListSql(String aMedcaseAlias){
		StringBuilder sbl = new StringBuilder();
		StringBuilder sba = new StringBuilder();

	String dateFinishSql=SqlUtil.getISNULL(aMedcaseAlias+"."+"datefinish",aMedcaseAlias+"."+"transferDate");
	String dlm="||'#'||";
	sba.append(SqlUtil.getISNULL(aMedcaseAlias+".prevmedcase_id","",true));
	sba.append(dlm+SqlUtil.getISNULL("vsrs.name"));
	sba.append(dlm+SqlUtil.getISNULL("vbt.omcCode"));
	sba.append(dlm+SqlUtil.getISNULL("vbst.code"));
	sba.append(dlm+SqlUtil.getIFNULL("ml.IsNoOmc","1","0"));
	sba.append(dlm+aMedcaseAlias+"."+"datestart");
	sba.append(dlm+dateFinishSql);
	sba.append(dlm+aMedcaseAlias+".id");
	
	sbl.append("SELECT");
	sbl.append(" "+SqlUtil.getLIST(sba.toString(),""));
	sbl.append(" FROM MedCase "+aMedcaseAlias);
	sbl.append(" LEFT JOIN MisLpu ml ON "+aMedcaseAlias+".department_id=ml.id");
	sbl.append(" LEFT JOIN VocServiceStream vsrs ON "+aMedcaseAlias+".servicestream_id=vsrs.id");
	sbl.append(" LEFT JOIN BedFund bf ON "+aMedcaseAlias+".bedfund_id=bf.id");
	sbl.append(" LEFT JOIN VocBedType vbt ON bf.bedtype_id=vbt.id");
	sbl.append(" LEFT JOIN VocBedSubType vbst ON bf.bedsubtype_id=vbst.id");
	sbl.append(" WHERE "+aMedcaseAlias+".parent_id = mc.parent_id");
	sbl.append(" AND "+aMedcaseAlias+".DTYPE = 'DepartmentMedCase'");
	sbl.append(" AND "+aMedcaseAlias+".patient_id is not null");

		return sbl.toString();
	}
}

