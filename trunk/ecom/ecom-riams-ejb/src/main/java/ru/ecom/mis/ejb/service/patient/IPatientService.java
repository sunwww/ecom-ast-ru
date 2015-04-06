package ru.ecom.mis.ejb.service.patient;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressPoint;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.form.patient.VocOrgForm;

/**
 */
public interface IPatientService {
	public float getImageCompress() ;
	public String getImageDir();
	public void insertExternalDocumentByObject(String aObject,Long aObjectId, Long aType,String aReferenceComp,String aReferenceTo, String aComment,String aUsername) ;
	public void insertCheckFondData(
			String aLastname,String aFirstname,String aMiddlename,String aBirthday
			,String aSnils
			,String aCommonNumber,String aPolicySeries,String aPolicyNumber
			,String aPolicyDateFrom, String aPolicyDateTo
			,String aUsername, String aCheckType
			,String aCompanyCode ,String aCompabyCodeF,String aCompanyOgrn, String aCompanyOkato
			,String aDocumentType, String aDocumentSeries,String aDocumentNumber
			,String aKladr,String aHouse, String aHouseBuilding, String aFlat
			,String aLpuAttached, String aAttachedDate, String aAttachedType
			) throws ParseException ;
	public String getCodeByMedPolicyOmc(Long aType) ;
	public boolean isNaturalPerson(Long aPatient) ;
	public void createNaturalPerson(Long aPatient) ;	
	
	public String getInfoVocForFond(String aPassportType,String aAddress, String aPolicy) ;
	public boolean updateDataByFond(String aUsername,Long aPatientId, String aFiodr
			,String aDocument,String aPolicy,String aAddress,boolean aIsPatient, boolean aIsPolicy
			, boolean aIsDocument, boolean aIsAddress) ;
	public boolean updateOrCreatePolicyByFond(Long aPatientId, String aRz, String aLastname, String aFirstname
			, String aMiddlename, String aBirthday, String aComp, String aSeries
			, String aNumber, String aDateFrom, String aDateTo,String aCurrentDate) ;
	public PatientForm getPatientById(Long aId) ;
	public String getOmcCodeByPassportType(Long aPassportType);
	public List<VocOrgForm> findOrg(String aNewNumber, String aOldNumber, String aName) ;
	public void movePatientDoubleData(Long aIdNew, Long aIdOld);
	public String addPatient(String aLastname, String aFirstname, String aMiddlename,
			String aBirthday, Long aSex, Long aSocialStatus, String aSnils) ;
	
	public String getDoubleByBaseData(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportNumber, String aPassportSeries, String aAction) throws ParseException ;
	
    List<PatientForm> findPatient(Long aLpuId, Long aLpuAreaId, String aLastname) ;
    
    public String findPatientLpuInfo(Long aAddressId, String aNumber, String aBuilding, Date aBirthday, String aFlat) ;

    /**
     * Прикрепление пациента к ЛПУ
     * @param aPatientId
     */
    void updatePatientLpuByAddress(Patient aPatientId) ;
    
    public String getDoubleByBaseData(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportNumber, String aPassportSeries) throws ParseException;
    public String getDoubleByBaseData(String aId, String aLastname, String aFirstname, String aMiddlename,
			String aSnils, String aBirthday, String aPassportNumber, String aPassportSeries, String aAction,boolean aIsFullBirthdayCheck) throws ParseException;
    LpuAreaAddressPoint findPoint(Address aAddress, String aNumber, String aBuilding, Date aBirthday, String aFlat) ;
    
    public void setAddParamByMedCase(String aParam, Long aMedCase,Long aStatus)  ;
    
    
}
