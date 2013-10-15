package exp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.stream.XMLStreamException;

import util.DataSourceUtil;
import util.FileUtil;
import util.SqlUtil;

public class ExpIterator {
	public static void main(String args[]){
		sqlFileToXmlFile("vocSex.sql", "/tmp/riams/vocSex.xml");
	}
	public static void toXmlFile(String aIterationType, String aXmlFileName
			, String aDateStartE, String aDateEndE
			, String aLpu
			, boolean aCasesFromSPO
			, boolean aCasesFromSLS
			, boolean aAllLpu){
		
		String sql = "";
		
		if(aIterationType.equals("Visit")) sql = ExpIteratorVisit.getSql(aDateStartE, aDateEndE, aLpu, aCasesFromSPO);
		else if(aIterationType.equals("Hospital")) sql = ExpIteratorHospital.getSql(aDateStartE, aDateEndE, aLpu, aCasesFromSLS, aAllLpu);
		else if(aIterationType.equals("ExtDisp")) sql = ExpIteratorExtDisp.getSql(aDateStartE, aDateEndE, aLpu);
		
		String sqlFileName = FileUtil.getTempFileName("sql"+aIterationType+".txt");
		FileUtil.deleteFile(sqlFileName);
		BufferedWriter bw = FileUtil.getBufferedWriter(sqlFileName, "");
		try {
			bw.write(sql);
		} catch (IOException e) {
			FileUtil.writeErrorFile(e, getErrorFileName());
		}
		finally {
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				FileUtil.writeErrorFile(e, getErrorFileName());
			}
		} 
		sqlToXmlFile(sql, aXmlFileName);

	}
	public static void sqlFileToXmlFile(String aSqlFileName, String aXmlFileName){
		
		String sqlFileName = FileUtil.getTempFileName(aSqlFileName);
		String sql = FileUtil.toString(sqlFileName);
		sqlToXmlFile(sql, aXmlFileName);

	}
	private static void sqlToXmlFile(String aSql, String aXmlFileName){
		DataSourceUtil dsu = null;
		try {
			dsu = new DataSourceUtil("");
		} catch (Exception e) {
			FileUtil.writeErrorFile(e, getErrorFileName());
		}
		Connection con = dsu.createConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(aSql);	
			try {
				SqlUtil.ResultSetToXml(rs, aXmlFileName,"rows","row");
			} catch (XMLStreamException e) {
				FileUtil.writeErrorFile(e, getErrorFileName());
			}
		} catch (SQLException e1) {
			FileUtil.writeErrorFile(e1, getErrorFileName());
		}
		finally{
			if(rs!=null)
				try {
					rs.close();
					if(stmt!=null) stmt.close();
					if(con!=null) con.close();
				} catch (SQLException e) {
					FileUtil.writeErrorFile(e, getErrorFileName());
				}

			}		
	}
	public static void addCommonJoin(StringBuilder aSb){
		aSb.append(" LEFT JOIN VocRayon vrn ON p.rayon_id=vrn.id");
		aSb.append(" LEFT JOIN VocSex vs ON p.sex_id=vs.id");
		aSb.append(" LEFT JOIN Address2 a ON p.address_Addressid=a.AddressId");
		aSb.append(" LEFT JOIN Address2 ar ON p.realAddress_Addressid=ar.AddressId");
		aSb.append(" LEFT JOIN OMC_KODTER okt ON p.territoryRegistrationNonresident_id = okt.id");
		aSb.append(" LEFT JOIN OMC_QNP oqnp ON p.typeSettlementNonresident_id = oqnp.id");
		aSb.append(" LEFT JOIN OMC_STREETT ost ON p.typeStreetNonresident_id = ost.id");
		aSb.append(" LEFT JOIN OMC_OKSM oksm ON p.nationality_id = oksm.id");
		aSb.append(" LEFT JOIN VocSocialStatus vss ON p.socialStatus_id=vss.id");
		aSb.append(" LEFT JOIN VocIdentityCard vicard ON p.passportType_id=vicard.id");
		aSb.append(" LEFT JOIN VocPassportBirthPlace vpbp ON p.passportBirthPlace_id=vpbp.id");
		aSb.append(" LEFT JOIN VocPassportWhomIssue vpwi ON p.passportDivision_id=vpwi.id");
		aSb.append(" LEFT JOIN VocNewBorn vnb ON p.newborn_id=vnb.id");
		aSb.append(" LEFT JOIN VocWorkFunction vwf ON wf.workFunction_id=vwf.id");
		aSb.append(" LEFT JOIN VocPost vp ON vwf.vocPost_id=vp.id");
		aSb.append(" LEFT JOIN Worker w ON wf.worker_id=w.id");
		aSb.append(" LEFT JOIN Patient wp ON w.person_id=wp.id");
		aSb.append(" LEFT JOIN VocOmcDoctorPost vodp ON vp.omcDoctorPost_id=vodp.id");
		aSb.append(" LEFT JOIN VocOmcDepType vodt ON vp.omcDepType_id=vodt.id");
		aSb.append(" LEFT JOIN Patient ksmp ON ksm.kinsman_id = ksmp.id");
		aSb.append(" LEFT JOIN VocSex vsksmp ON ksmp.sex_id=vsksmp.id");	
		aSb.append(" LEFT JOIN VocKinsmanRole vkr ON ksm.kinsmanRole_id = vkr.id");		

		aSb.append(" LEFT JOIN REG_IC ri ON mp.company_id=ri.id");
		aSb.append(" LEFT JOIN OMC_KODTER oa ON mp.insuranceCompanyArea_id=oa.id");
		aSb.append(" LEFT JOIN OMC_SPRSMO ossmo ON mp.insuranceCompanyCode_id=ossmo.id");
		aSb.append(" LEFT JOIN VocMedPolicyOmc vmpo ON mp.type_id=vmpo.id");

		aSb.append(" LEFT JOIN VocIdc10 vidcm ON dmain.idc10_id=vidcm.id");
		aSb.append(" LEFT JOIN VocIdc10 vidcc ON dcont.idc10_id=vidcc.id");
	
	}
	public static void addCommonSelect(StringBuilder aSb){
		aSb.append(", "+SqlUtil.getISNULL("p.address_Addressid","p.realAddress_AddressId",false,"addressId"));
		aSb.append(", p.realAddress_AddressId AS addressIdReal");
		aSb.append(", p.address_AddressId AS addressIdReg");
		aSb.append(", p.birthday AS birthDate");
		aSb.append(", vnb.birthOrder AS birthOrder");
		aSb.append(", "+SqlUtil.getISNULL("p.birthPlace","vpbp.name",true,"birthPlace"));
		aSb.append(", p.buildingHousesNonresident AS buildCoun");
		aSb.append(", p.passportDateIssued AS docD");
		aSb.append(", "+SqlUtil.getISNULL("wp.lastname","",true,"")+"||' '||"+SqlUtil.getISNULL("wp.firstname","",true,"")+"||' '||"+SqlUtil.getISNULL("wp.middlename","",true,"")+" AS doctorFio");
		aSb.append(", p.passportNumber AS docN");
		aSb.append(", p.passportSeries AS docS");
		aSb.append(", vicard.omcCode AS docT");
		aSb.append(", vodp.code AS doctorPost");
		aSb.append(", wp.snils AS doctorSnils");
		aSb.append(", "+SqlUtil.getISNULL("p.passportWhomIssued","vpwi.name",true,"docV"));
		aSb.append(", p.firstname");
		aSb.append(", p.apartmentNonresident AS flatCoun");
		aSb.append(", "+SqlUtil.getISNULL("p.flatNumber","p.realFlatNumber",true,"flat"));
		aSb.append(", p.realFlatNumber AS flatNumberReal");
		aSb.append(", p.flatNumber AS flatNumberReg");
		aSb.append(", ksmp.birthDay AS forBD");
		aSb.append(", ksmp.lastname AS forFam");
		aSb.append(", ksmp.firstname AS forIm");
		aSb.append(", ksmp.middlename AS forOth");
		aSb.append(", vsksmp.omcCode AS forSex");
		aSb.append(", vkr.omcCode AS forStat");
		aSb.append(", p.settlementNonresident AS gorCoun");
		aSb.append(", oqnp.voc_code AS gorCounType");
		aSb.append(", oqnp.name AS gorCounTypeName");
		aSb.append(", p.houseNonresident AS houseCoun");
		aSb.append(", "+SqlUtil.getISNULL("p.houseNumber","p.realHouseNumber",true,"houseNumber"));
		aSb.append(", p.realHouseNumber AS houseNumberReal");
		aSb.append(", p.houseNumber AS houseNumberReg");
		aSb.append(", "+SqlUtil.getISNULL("p.houseBuilding","p.realHouseBuilding",true,"houseBuilding"));
		aSb.append(", p.realHouseBuilding AS houseBuildingReal");
		aSb.append(", p.houseBuilding AS houseBuildingReg");
		aSb.append(", p.isCompatriot");
		aSb.append(", "+SqlUtil.getISNULL("a.kladr","ar.kladr",true,"kladr"));
		aSb.append(", ar.kladr AS kladrReal");
		aSb.append(", a.kladr AS kladrReg");
		aSb.append(", p.lastname");
		aSb.append(", p.middlename");
		aSb.append(", vnb.isPolycarpous AS mulitplyBirth");
		aSb.append(", okt.voc_code AS okatoCoun");
		aSb.append(", okt.name AS okatoCounName");
		aSb.append(", okt.okato AS okatoCounOkato");
		aSb.append(", oksm.voc_code AS oksmCoun");
		aSb.append(", vicard.omcCode AS passportType");
		aSb.append(", p.id AS patient");
		aSb.append(", p.snils AS patientSnils");
		aSb.append(", p.works_id AS patientWork");
		aSb.append(", p.regionRegistrationNonresident AS RCoun");
		aSb.append(", vrn.code AS region");
		aSb.append(", vsstr.code AS serviceStream");
		aSb.append(", vs.omcCode AS sex");
		aSb.append(", vss.omcCode AS socialStatus");
		aSb.append(", ost.voc_code AS streetCounType");
		aSb.append(", ost.name AS streetCounTypeName");
		aSb.append(", p.streetNonresident AS streetCoun");
		
		aSb.append(", mp.commonNumber");
		aSb.append(", mp.insuranceCompanyCity");
		aSb.append(", ossmo.smoCode AS insuranceCompanyCode");
		aSb.append(", mp.insuranceCompanyName");
		aSb.append(", oa.voc_code AS oblSmo");
		aSb.append(", ossmo.voc_code AS ogrnSmo");
		aSb.append(", oa.okato AS okatoTer");
		aSb.append(", ri.omccode AS policyCompany");
		aSb.append(", mp.firstname AS policyFirstname");
		aSb.append(", CASE WHEN mp.DTYPE='MedPolicyOmcForeign' THEN 1 ELSE 0 END AS policyForeign");
		aSb.append(", mp.lastname AS policyLastname");
		aSb.append(", mp.birthday AS policyBirthday");
		aSb.append(", mp.middlename AS policyMiddlename");
		aSb.append(", mp.polNumber AS policyNumber");
		aSb.append(", mp.patient_id AS policyPatient");
		aSb.append(", mp.series AS policySeries");
		aSb.append(", mp.org_id AS policyWork");
		aSb.append(", mp.actualDateFrom AS polisDateFrom");
		aSb.append(", mp.actualDateTo AS polisDateTo");
		aSb.append(", "+SqlUtil.getISNULL("vmpo.code","1",true,"policyVid"));

		aSb.append(", vidcm.code AS diagnosisMain");
		aSb.append(", vidcc.code AS diagnosisConcomitant");
	}
	public static String getErrorFileName(){
		return "ExpIteratorError.txt";
	}
}

