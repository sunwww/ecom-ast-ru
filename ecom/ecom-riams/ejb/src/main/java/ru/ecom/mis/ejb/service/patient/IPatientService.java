package ru.ecom.mis.ejb.service.patient;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressPoint;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.PatientFondCheckData;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.form.patient.VocOrgForm;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

/**
 */
public interface IPatientService {
	Patient getPatient(String aLastname, String aFirstname, String aMiddlename, Date aBirthDay, String aSexCode, String aCommonNumber
			, String addData);
	String exportDispensaryCard(java.util.Date aDateFrom, java.util.Date aDateTo, java.util.Date aDateChanged, String aPacketNumber);
	String getAddressByOkato (String aOkato, String aStreet);
	void changeMedPolicyType (Long aPolicyId, Long aNewPolicyTypeId);
	String updateOrCreateAttachment(Long aPatientId, String aCompany, String aLpu, String aAttachedType, String aAttachedDate
			,String aDoctorSnils, boolean ignoreType, boolean updateEditDate);
	void insertPatientNotFound(Long aPatientId, Long aCheckTimeId) throws ParseException;
	boolean updateDataByFondAutomatic (Long aPatientFondId, Long aCheckId
			,boolean needUpdatePatient, boolean needUpdateDocuments, boolean needUpdatePolicy, boolean needUpdateAttachment);
	PatientFondCheckData getNewPFCheckData(boolean aNeedUpdatePatient, boolean aNeedUpdateDocument, boolean aNeedUpdatePolicy, boolean aNeedUpdateAttachment);
	float getImageCompress() ;
	String getImageDir();
	String getConfigValue(String aConfigName, String aDefaultValue);
	void insertExternalDocumentByObject(String aObject,Long aObjectId, Long aType,String aReferenceComp,String aReferenceTo, String aComment,String aUsername) ;
	void insertCheckFondData(
			String aLastname,String aFirstname,String aMiddlename,String aBirthday
			,String aSnils
			,String aCommonNumber,String aPolicySeries,String aPolicyNumber
			,String aPolicyDateFrom, String aPolicyDateTo
			,String aUsername, String aCheckType
			,String aCompanyCode ,String aCompabyCodeF,String aCompanyOgrn, String aCompanyOkato
			,String aPatientId
			) throws ParseException ;
	Long insertCheckFondData(
			String aLastname,String aFirstname,String aMiddlename,String aBirthday
			,String aSnils
			,String aCommonNumber,String aPolicySeries,String aPolicyNumber
			,String aPolicyDateFrom, String aPolicyDateTo
			,String aUsername, String aCheckType
			,String aCompanyCode ,String aCompabyCodeF,String aCompanyOgrn, String aCompanyOkato
			,String aPatientId
			,String aDocumentType, String aDocumentSeries,String aDocumentNumber
			,String aKladr,String aHouse, String aHouseBuilding, String aFlat
			,String aLpuAttached, String aAttachedDate, String aAttachedType, String dateDeath
			,String aDocumentDateIssued, String aDocumentWhomIssued, String aDoctorSnils, String aCodeDepartment
			, PatientFondCheckData aCheckTime, String aStreet, String aOkato
			) throws ParseException ;
	String getCodeByMedPolicyOmc(Long aType) ;
	boolean isNaturalPerson(Long aPatient) ;
	void createNaturalPerson(Long aPatient) ;
	
	String getInfoVocForFond(String aPassportType,String aAddress, String aPolicy) ;
	boolean updateDataByFond(String aUsername,Long aPatientId, String aFiodr
			,String aDocument,String aPolicy,String aAddress,boolean aIsPatient, boolean aIsPolicy
			, boolean aIsDocument, boolean aIsAddress, boolean aIsAttachment) ;
	boolean updateOrCreatePolicyByFond(Long aPatientId, String aRz, String aLastname, String aFirstname
			, String aMiddlename, String aBirthday, String aComp, String aSeries
			, String aNumber, String aDateFrom, String aDateTo,String aCurrentDate) ;
	PatientForm getPatientById(Long aId) ;
	String getOmcCodeByPassportType(Long aPassportType);
	List<VocOrgForm> findOrg(String aNewNumber, String aOldNumber, String aName) ;
	void movePatientDoubleData(Long aIdNew, Long aIdOld);
	String addPatient(String aLastname, String aFirstname, String aMiddlename,
			String aBirthday, Long aSex, Long aSocialStatus, String aSnils) throws ParseException;
	
    WebQueryResult findPatient(Long aLpuId, Long aLpuAreaId, String aLastname, String aYear
    		, Boolean aNext, String aIdNext) ;
    
    String findPatientLpuInfo(Long aAddressId, String aNumber, String aBuilding, Date aBirthday, String aFlat) ;

    /**
     * Прикрепление пациента к ЛПУ
     * @param aPatientId
     */
    void updatePatientLpuByAddress(Patient aPatientId) ;

    String getDoubleByBaseData(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportType, String aPassportNumber, String aPassportSeries, String aAction,boolean aIsFullBirthdayCheck) throws ParseException;
    LpuAreaAddressPoint findPoint(Address aAddress, String aNumber, String aBuilding, Date aBirthday, String aFlat) ;
    
    void setAddParamByMedCase(String aParam, Long aMedCase,Long aStatus)  ;
}
