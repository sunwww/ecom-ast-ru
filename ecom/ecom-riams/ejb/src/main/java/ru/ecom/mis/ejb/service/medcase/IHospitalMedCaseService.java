package ru.ecom.mis.ejb.service.medcase;

import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.SurgicalOperationForm;
import ru.ecom.mis.ejb.form.patient.MedPolicyForm;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

/**
 * User: STkacheva
 * Date: 12.12.2007
 * Time: 10:23:02
 */
 public interface IHospitalMedCaseService {
	 String makeReportCostCase(String aDateFrom, String aDateTo, String aType, String aLpuCode);
	 String getAllServicesByMedCase(Long aMedcaseId);
     void finishMonitor(long aMonitorId) ;
     void startMonitor(long aMonitorId) ;
     void addMonitor(long aMonitorId, int aInt) ;

	 String getDischargeEpicrisis(long aMedCaseId) ;
	 String importFileDataFond(long aMonitorId, String aFilename) throws Exception ;
	 String importDataFond(long aMonitorId, String aFileType,List<WebQueryResult> aList) ;
	 String importDataFondForDBF(long aMonitorId) ;
	
	
	 void refreshReportByPeriod(String aEntranceDate,String aDischargeDate,long aIdMonitor) ;
	 void refreshCompTreatmentReportByPeriod(String aEntranceDate,String aDischargeDate,long aIdMonitor) ;
	 WebQueryResult[] exportFondZip23(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu, boolean aSaveInFolder) 
			throws ParserConfigurationException, TransformerException;
	 String[] exportFondZip45(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu, boolean aSaveInFolder) 
    		throws ParserConfigurationException, TransformerException;
	 WebQueryResult exportN0(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage
    		, String aVidN, boolean aSaveInFolder) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException ;
	 WebQueryResult exportN1(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aIsPolyc, boolean aIsHospital, boolean aSaveInFolder) throws ParserConfigurationException, TransformerException ;
	 WebQueryResult exportN1_planHosp(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder) 
    		throws ParserConfigurationException, TransformerException; 
     WebQueryResult exportN2(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder) throws ParserConfigurationException, TransformerException;
	 WebQueryResult exportN3(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder) throws ParserConfigurationException, TransformerException;
	 String exportN4(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder) throws ParserConfigurationException, TransformerException;
	 String exportN5(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder)  throws ParserConfigurationException, TransformerException;
	 String exportN6(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder)  throws ParserConfigurationException, TransformerException, ParseException;
	 WebQueryResult exportN2_plan_otherLpu(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder) 
    		throws ParserConfigurationException, TransformerException ;
	/**
	 * Выгрузка xml-файлов по переводам внутри ЛПУ
	 * @param aDateFrom дата с
	 * @param aDateTo дата по
	 * @param aPeriodByReestr период
	 * @param aLpu код федеральный ЛПУ
	 * @param aNPackage номер пакета
	 * @param aSaveInFolder сохранять в папку
	 * @return список файлов
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	 WebQueryResult exportN2_trasferInLpu(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder) 
    		throws ParserConfigurationException, TransformerException ;
	 void createNewDiary(String aTitle, String aText, String aUsername) ;
	 void updateDataFromParameterConfig(Long aDepartment, boolean aIsLowerCase, String aIds, boolean aIsRemoveExist) ;
	 void removeDataFromParameterConfig(Long aDepartment, String aIds) ;
	 void changeServiceStreamBySmo(Long aSmo,Long aServiceStream) ;
	 void unionSloWithNextSlo(Long aSlo) ;
	 void deniedHospitalizatingSls(Long aMedCaseId, Long aDeniedHospitalizating) ;
	 void setPatientByExternalMedservice(String aDocNumber, String aOrderDate, String aPatient) ;
	 void preRecordDischarge(Long aMedCaseId, String aDischargeEpicrisis) ;
	 void updateDischargeDateByInformationBesk(String aIds, String aDate) throws ParseException;
	 void addressClear() ;
	 long addressUpdate(long id) ;
	 String getOperationsText(Long aPatient, String aDateStart,String aDateFinish) ;
	//Получить данные диагноза по умолчанию для акушерства
	 String getTypeDiagByAccoucheur() ;
	//Удаление данных по выписке пациента
	 String deleteDataDischarge(Long aMedCaseId);
	//Получить номер пациента по ИД СЛС
     Long getPatient(long aIdSsl) ;
    //Изменить номер стат.карты
     String getChangeStatCardNumber(Long aMedCase, String aNewStatCardNumber, boolean aAlwaysCreate);
    
    //Получить список полисов по ИД СЛС
     Collection<MedPolicyForm> listPolicies(Long aMedCase) ;
     Collection<MedPolicyForm> listPoliciesToAdd(Long aMedCase) ;
     void removePolicies(long aMedCaseId, long[] aPolicies) ;
     void addPolicies(long aMedCaseId, long[] aPolicies) ;
     String getTemperatureCurve(long aMedCaseId) ;
     List<IEntityForm> listAll(Long aParentId) throws EntityFormException ;
     List<SurgicalOperationForm> getSurgicalOperationByDate(String aDate)  ;
     List<HospitalMedCaseForm> findOpenHospitalByDate(String aDate) ;
     String isOpenningSlo(long aIdSls) ;
    
    
    // Поиск СЛС по номеру стат.карты
     List<HospitalMedCaseForm>findSlsByStatCard(String aNumber) ;
    
    //Поиск дублей
     String findDoubleServiceByPatient(Long aMedService, Long aPatient, Long aService, String aDate) throws ParseException; 
     String findDoubleOperationByPatient(Long aSurOperation, Long aPatient, Long aOperation, String aDate) throws ParseException; 
}
