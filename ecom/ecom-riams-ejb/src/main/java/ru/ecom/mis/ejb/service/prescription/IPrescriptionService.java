package ru.ecom.mis.ejb.service.prescription;

/**
 * Интерфейс сервиса для работы с назначениями
 * @author STkacheva
 */
public interface IPrescriptionService {
	/**
	 * Проверка на возможность создавать направление с типом "экстренно".
	 * @param aPrescriptionListId - номер листа назначения
	 * @return true - может быть создано назначение с типом "экстренно"
	 */
	public boolean checkMedCaseEmergency(Long aId, String idType) ;
	
	/**
	 * Добавить все назначения в существующий лист
	 * @param aIdTemplateList - ИД шаблона листа назначений
	 * @param aIdParent - ИД листа назначений
	 * @return true - при успешном сохранении, false - при ошибке при сохранении
	 */
	public boolean savePrescriptExists(Long aIdTemplateList,Long aIdParent) ;
	/**
	 * Добавить все назначения в новый лист
	 * @param aIdTemplateList - ИД шаблона листа назначений
	 * @param aIdParent - ИД СМО (если СМО не указан создается шаблон !!!)
	 * @return true - при успешном сохранении, false - при ошибке при сохранении
	 */
	public boolean savePrescriptNew(Long aIdTemplateList,Long aIdParent) ;
	/**
	 * Получить описание шаблона листа назначения
	 * @param aIdTemplateList - ИД листа назначения
	 * @return описание листа назначений
	 */
	public String getDescription(Long aIdTemplateList) ;
	
//	boolean canShowPrescriptionFulfilments(long aPrescriptionId) ;
	
}
