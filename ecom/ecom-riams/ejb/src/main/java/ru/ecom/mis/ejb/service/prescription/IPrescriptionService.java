package ru.ecom.mis.ejb.service.prescription;

import org.json.JSONException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;


/**
 * Интерфейс сервиса для работы с назначениями
 * @author STkacheva
 */
public interface IPrescriptionService {

	Long createServicePrescription(Long aMedServiceId, Long aPrescriptionId);
	void checkXmlFiles() throws ParserConfigurationException, SAXException, IOException;

	void setPatientDateNumber(String aPrescriptions, String aDate, String aTime, String aUsername, Long aSpec ) throws ParseException ;
	Long clonePrescription(Long aPrescriptionId, Long aMedServiceId, Long aWorkFunctionId, String aCreateUsername) ;
	String createNewDirectionFromPrescription(Long aPrescriptionListId, Long aWorkFunctionPlanId, Long aDatePlanId, Long aTimePlanId, Long aMedServiceId, String aUsername, Long aOrderWorkFunction, Long aGuaranteeId) ;
	String saveLabAnalyzed(Long aSmoId,Long aPrescriptId,Long aProtocolId, String aParams, String aUsername, Long aTemplateId) throws JSONException  ;
	Long createTempPrescriptList(String aName,String aComment,String aCategories,String aSecGroups) ;

	/**
	 * Проверка на возможность создавать направление с типом "экстренно".
	 * @param aId - номер листа назначения
	 * @return true - может быть создано назначение с типом "экстренно"
	 */
	boolean checkMedCaseEmergency(Long aId, String idType) ;
	Long checkLabAnalyzed(Long aPrescriptId,String aUsername) ;

	/**
	 *  Получение списка типов назначений
	 */
	String getPrescriptionTypes(boolean isEmergency);

	/**
	 * Создаем список назначений для нового ЛН
	 * @param aID - номер шаблона листа назначения
	 * @return - список назначений
	 */
	String getLabListFromTemplate(Long aID) ;
	/**
	 * Добавить все назначения в существующий лист
	 * @param aIdTemplateList - ИД шаблона листа назначений
	 * @param aIdParent - ИД листа назначений
	 * @return true - при успешном сохранении, false - при ошибке при сохранении
	 */
	boolean savePrescriptExists(Long aIdTemplateList,Long aIdParent) ;
	/**
	 * Добавить все назначения в новый лист
	 * @param aIdTemplateList - ИД шаблона листа назначений
	 * @param aIdParent - ИД СМО (если СМО не указан создается шаблон !!!)
	 * @return true - при успешном сохранении, false - при ошибке при сохранении
	 */
	Long savePrescriptNew(Long aIdTemplateList,Long aIdParent) ;
	Long savePrescriptNew(Long aIdTemplateList,Long aIdParent, String aName) ;
	/**
	 * Получить описание шаблона листа назначения
	 * @param aIdTemplateList - ИД листа назначения
	 * @return описание листа назначений
	 */
	String getDescription(Long aIdTemplateList) ;

	String getRealLabTechUsername(Long aPrescriptId,String aUsername);
	String getWorkfuntctionInfoByLabTechUsername(String aUsername);
	void sendEmergencyReferenceMsg(Long aDiaryId,Long aPrescriptId);
	void sendMessageCurrentDate(String messageText, String messageTitle, String recipient
			,String username,String messageUrl, Boolean isEmergency);
	void setPatientIdentityBracelet(String infoStr);
}
