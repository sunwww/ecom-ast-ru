package ru.ecom.ejb.services.login;

import java.util.Set;

/**
 *
 */
public interface ILoginService {
    /** Получить список ролей пользователя */
    public Set getUserRoles() ;
	public Long createSystemMessage(String aTitle, String aText, String aRecipient) ;
	public void dispatchMessage(Long aIdMessage) ;
    public void checkMessage(Long aIdMessage);
	public void hideMessage(Long aIdMessage) ;
	public String[] getConfigUrl() ;
}
