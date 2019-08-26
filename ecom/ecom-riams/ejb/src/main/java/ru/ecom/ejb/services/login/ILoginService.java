package ru.ecom.ejb.services.login;

import java.util.Set;

/**
 *
 */
public interface ILoginService {
    /** Получить список ролей пользователя */
    Set getUserRoles() ;
	Long createSystemMessage(String aTitle, String aText, String aRecipient) ;
	void dispatchMessage(Long aIdMessage) ;
    void checkMessage(Long aIdMessage);
	void hideMessage(Long aIdMessage) ;
	String[] getConfigUrl() ;
	void createRecordInAuthJournal(String aUsername, String aRemoteIp, String aLocalIp
    		,String aServerName,boolean aIsChecked,String aError,String aErrorPassword);
}
