package ru.ecom.web.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

/**
 * Форма для ввода пароля и имени пользователя
 */
public class LoginForm extends ValidatorForm {

    /** Имя пользователя */
    public String getUsername() { return theUsername ; }
    public void setUsername(String aUsername) { theUsername = aUsername ; }

    /** Пароль пользователя */
    public String getPassword() { return thePassword ; }
    public void setPassword(String aPassword) { thePassword = aPassword ; }

    /** Следующий URI */
    public String getNext() { return theNext ; }
    public void setNext(String aNext) { theNext = aNext ; }

    private boolean isNotEmpty (String aStr) {
        return aStr!=null && !aStr.trim().equals("") ;
    }


    public ActionErrors validate(ActionMapping aMapping, HttpServletRequest aRequest) {

        ActionErrors errors = new ActionErrors();
        if(!isNotEmpty(getUsername())) {
            errors.add("username", new ActionMessage("Имя пользователя не должно быть пустым")) ;
        }
        if(!isNotEmpty(getPassword())) {
            errors.add("password", new ActionMessage("Пароль не введен")) ;
        }
        return errors ;
    }

    /** Пароль пользователя */
    private String thePassword ;
    /** Имя пользователя */
    private String theUsername ;
    /** Следующий URI */
    private String theNext ;

}
