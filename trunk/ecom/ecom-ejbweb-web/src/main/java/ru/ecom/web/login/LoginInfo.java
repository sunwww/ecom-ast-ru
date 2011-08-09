package ru.ecom.web.login;

import java.io.Serializable;
import java.util.Set;

import javax.servlet.http.HttpSession;

import ru.nuzmsh.commons.auth.ILoginInfo;

/**
 *
 */
public class LoginInfo implements ILoginInfo, Serializable {

    public static final String KEY = ILoginInfo.class.getName() ;

    public LoginInfo(String aUsername, String aPassword) {
        theUsername = aUsername ;
        thePassword = getHashPassword(aUsername, aPassword) ;
    }

    public String getFullname() {
        // todo
        return "_"+getUsername()+"_";
    }

    public String getComment() {
        return "";
    }

    public boolean isUserInRole(String aRole) {
    	checkRolesExists() ;
        return theUserRoles.contains(aRole) ;
    }

    public void checkRolesExists() {
        if(theUserRoles==null) throw new NullPointerException("Нет ролей. theUserRoles==null") ;
    }

    public static LoginInfo find(HttpSession aSession) {
        return (LoginInfo) aSession.getAttribute(KEY) ;
    }
    public static String getHashPassword(String aUsername, String aPassword) {
    	String hash = String.valueOf(aPassword.hashCode() + aUsername.hashCode()) ;
    	System.out.println(hash) ;
    	return hash;
    }

    /** Имя пользователя для входа */
    public String getUsername() { return theUsername ; }

    /** Пароль пользователя */
    public String getPassword() { return thePassword ; }

    /** Пароль пользователя */
    private final String thePassword ;
    /** Имя пользователя для входа */
    private final String theUsername ;

    private Set theUserRoles = null ;


    public void setUserRoles(Set aUserRoles) {
        theUserRoles = aUserRoles ;
    }

    public void saveTo(HttpSession aSession) {
        aSession.setAttribute(KEY, this);
    }
}
