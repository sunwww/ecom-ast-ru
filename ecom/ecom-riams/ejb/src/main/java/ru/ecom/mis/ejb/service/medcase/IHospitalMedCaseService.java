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
	public String makeReportCostCase(String aDateFrom, String aDateTo, String aType, String aLpuCode);
	public String getAllServicesByMedCase(Long aMedcaseId);
    public void finishMonitor(long aMonitorId) ;
    public void startMonitor(long aMonitorId) ;
    public void addMonitor(long aMonitorId, int aInt) ;

	public String getDischargeEpicrisis(long aMedCaseId) ;
	public String importFileDataFond(long aMonitorId, String aFilename) throws Exception ;
	public String importDataFond(long aMonitorId, String aFileType,List<WebQueryResult> aList) ;
	public String importDataFondForDBF(long aMonitorId) ;
	
	
	public void refreshReportByPeriod(String aEntranceDate,String aDischargeDate,long aIdMonitor) ;
	public void refreshCompTreatmentReportByPeriod(String aEntranceDate,String aDischargeDate,long aIdMonitor) ;
	public WebQueryResult[] exportFondZip23(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu, boolean aSaveInFolder) 
			throws ParserConfigurationException, TransformerException;
	public String[] exportFondZip45(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu, boolean aSaveInFolder) 
    		throws ParserConfigurationException, TransformerException;
	public WebQueryResult exportN0(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage
    		, String aVidN, boolean aSaveInFolder) throws TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException ;
	public WebQueryResult exportN1(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aIsPolyc, boolean aIsHospital, boolean aSaveInFolder) throws ParserConfigurationException, TransformerException ;
	public WebQueryResult exportN1_planHosp(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder) 
    		throws ParserConfigurationException, TransformerException; 
    public WebQueryResult exportN2(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder) throws ParserConfigurationException, TransformerException;
	public WebQueryResult exportN3(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder) throws ParserConfigurationException, TransformerException;
	public String exportN4(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder) throws ParserConfigurationException, TransformerException;
	public String exportN5(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder)  throws ParserConfigurationException, TransformerException;
	public String exportN6(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder)  throws ParserConfigurationException, TransformerException, ParseException;
	public WebQueryResult exportN2_plan_otherLpu(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder) 
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
	public WebQueryResult exportN2_trasferInLpu(String aDateFrom, String aDateTo,String aPeriodByReestr, String aLpu,String aNPackage, boolean aSaveInFolder) 
    		throws ParserConfigurationException, TransformerException ;
	public void createNewDiary(String aTitle, String aText, String aUsername) ;
	public void updateDataFromParameterConfig(Long aDepartment, boolean aIsLowerCase, String aIds, boolean aIsRemoveExist) ;
	public void removeDataFromParameterConfig(Long aDepartment, String aIds) ;
	public void changeServiceStreamBySmo(Long aSmo,Long aServiceStream) ;
	public void unionSloWithNextSlo(Long aSlo) ;
	public void deniedHospitalizatingSls(Long aMedCaseId, Long aDeniedHospitalizating) ;
	public void setPatientByExternalMedservice(String aDocNumber, String aOrderDate, String aPatient) ;
	public void preRecordDischarge(Long aMedCaseId, String aDischargeEpicrisis) ;
	public void updateDischargeDateByInformationBesk(String aIds, String aDate) throws ParseException;
	public void addressClear() ;
	public long addressUpdate(long id) ;
	public String getIdc10ByDocDiag(Long aIdDocDiag) ;
	public String getOperationsText(Long aPatient, String aDateStart,String aDateFinish) ;
	public String getnvestigationsTextDTM(Long aPatient, String aDateStart,String aDateFinish,boolean aLabsIs,boolean aFisioIs,boolean aFuncIs,boolean aConsIs, boolean aLuchIs) ;
	//Получить данные диагноза по умолчанию для акушерства
	public String getTypeDiagByAccoucheur() ;
	//Удаление данных по выписке пациента
	public String deleteDataDischarge(Long aMedCaseId);
	//Получить номер пациента по ИД СЛС
    public Long getPatient(long aIdSsl) ;
    //Изменить номер стат.карты
    public String getChangeStatCardNumber(Long aMedCase, String aNewStatCardNumber, boolean aAlwaysCreate);
    
    //Получить список полисов по ИД СЛС
    public Collection<MedPolicyForm> listPolicies(Long aMedCase) ;
    public Collection<MedPolicyForm> listPoliciesToAdd(Long aMedCase) ;
    public void removePolicies(long aMedCaseId, long[] aPolicies) ;
    public void addPolicies(long aMedCaseId, long[] aPolicies) ;
    public String getTemperatureCurve(long aMedCaseId) ;
    public List<IEntityForm> listAll(Long aParentId) throws EntityFormException ;
    public List<SurgicalOperationForm> getSurgicalOperationByDate(String aDate)  ;
    public List<HospitalMedCaseForm> findOpenHospitalByDate(String aDate) ;
    public String isOpenningSlo(long aIdSls) ;
    
    
    // Поиск СЛС по номеру стат.карты
    public List<HospitalMedCaseForm>findSlsByStatCard(String aNumber) ;
    
    //Поиск дублей
    public String findDoubleServiceByPatient(Long aMedService, Long aPatient, Long aService, String aDate) throws ParseException; 
    public String findDoubleOperationByPatient(Long aSurOperation, Long aPatient, Long aOperation, String aDate) throws ParseException; 
}
