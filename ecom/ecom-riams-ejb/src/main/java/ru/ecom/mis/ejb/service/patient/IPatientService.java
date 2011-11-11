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
	public List<VocOrgForm> findOrg(String aNewNumber, String aOldNumber, String aName) ;
	public void movePatientDoubleData(Long aIdNew, Long aIdOld);
	// Информация по полисам ОМС по пациенту
	public String infoMedPolicy(Long aPatient) ;
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
    
    LpuAreaAddressPoint findPoint(Address aAddress, String aNumber, String aBuilding, Date aBirthday, String aFlat) ;
    
    public void setAddParamByMedCase(String aParam, Long aMedCase,Long aStatus)  ;
    
    
}
