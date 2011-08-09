package ru.ecom.ejb.services.voc;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.nuzmsh.util.voc.VocAdditional;
import ru.nuzmsh.util.voc.VocValue;

/**
 * Редактирование справочника
 */
public interface IVocEditService {
//    public String createVocValue(HttpServletRequest aRequest, String aVocKey
            //, String aId, String aName, String aParentId) throws NamingException, RemoteException, CreateException {
	
	/**
	 * Создание нового значения
	 * @param aVocKey справочник
	 * @return идентификатор нового значения
	 */
	Object createVocValue(String aVocKey, VocValue aVocValue, VocAdditional aAdditional) ;
	
	/**
	 * Можно ли редактировать справочник
	 * @param aVocKey справочник
	 * @return true если можно
	 */
	boolean isVocEditabled(String aVocKey) ;
}
