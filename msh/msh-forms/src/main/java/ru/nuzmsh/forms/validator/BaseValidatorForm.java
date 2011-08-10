package ru.nuzmsh.forms.validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import ru.nuzmsh.forms.response.FormMessage;

/**
 * 
 */
public class BaseValidatorForm extends ValidatorForm implements Serializable {

    public static final int TYPE_SAVE = 0 ;
    public static final int TYPE_CREATE = 1 ;
    public static final int TYPE_DELETE = 2 ;
    public static final int TYPE_VIEW_ONLY = 3 ;
	
    public ActionErrors validate(ActionMapping aMapping, HttpServletRequest aRequest) {
        return ValidateUtil.validate(this,aRequest);
    }

    /**
     * Добавить информационное сообщение
     * @param aMessage информационное сообщение
     */
    public void addMessage(FormMessage aMessage) {
        theFormMessages.add(aMessage) ;
    }

    /** Тип сохранения */
    public int getSaveType() { return theSaveType ; }
    public void setSaveType(int aSaveType) { theSaveType = aSaveType ; }

    public boolean isTypeCreate() {
        return theSaveType == TYPE_CREATE ;
    }

    public void setTypeViewOnly() {
        theSaveType = TYPE_VIEW_ONLY ;
    }

    public boolean isViewOnly() {
        return theSaveType == TYPE_VIEW_ONLY ;
    }

    public void setTypeCreate() {
        theSaveType = TYPE_CREATE ;
    }
    public void setTypeSave() {
        theSaveType = TYPE_SAVE ;
    }

    public void addDisabledField(String aFieldName) {
    	theDisabledFields.add(aFieldName);
    }

//    /**
//     * Список закрытых для редактирования полей
//     * Iterator&lt;String&gt;
//     */
//    public Iterator getDisabledFieldsIterator() {
////        System.out.println(" - getDisabledFieldsIterator()");
//        for (Iterator<String> iterator = theDisabledFields.iterator(); iterator.hasNext();) {
//            String s = iterator.next();
////            System.out.println(" ---- disabled field = " + s);
//        }
//        return theDisabledFields.iterator() ;
//    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        super.reset(actionMapping, httpServletRequest);
        //theDisabledFields.clear();
        System.out.println(" *********** CLEAR *******************" + this);
    }

    /** Поле по-умолчанию */
    public String getDefaultFocusField() { return theDefaultFocusField ; }
    public void setDefaultFocusField(String aDefaultFocusField) { theDefaultFocusField = aDefaultFocusField ; }


    // для копирование
    public Set<String> getDisabledFields() {
        return theDisabledFields ;
    }

    // для копирование
    public void setDisabledFields(Set<String> aDisabledFields) {
        theDisabledFields = aDisabledFields ;
    }

    /** Конфиденциальные поля */
	public Set<String> getPrivateFields() {
		return thePrivateFields;
	}

	public void setPrivateFields(Set<String> aPrivateFields) {
		thePrivateFields = aPrivateFields;
	}
	
	/**
	 * Добавить поле, данные которого не будут показываться
	 * @param aFieldName название свойства
	 */
	public void addPrivateField(String aFieldName) {
		thePrivateFields.add(aFieldName);
	}
	
	public boolean isPrivateField(String aFieldname) {
		return thePrivateFields.contains(aFieldname);
	}

	public boolean isDisabledField(String aFieldname) {
		return theDisabledFields.contains(aFieldname);
	}
	
    // для копирование
    public ArrayList<FormMessage> getFormMessages() {return theFormMessages; }
    // для копирование
    public void setFormMessages(ArrayList<FormMessage> aMessages) { theFormMessages = aMessages ;}

    /** Поле по-умолчанию */
    private String theDefaultFocusField = null ;

    /** Тип сохранения */
    private int theSaveType ;

    private ArrayList<FormMessage> theFormMessages = new ArrayList<FormMessage>();
	/** Конфиденциальные поля */
	private Set<String> thePrivateFields = new HashSet<String>();
	private Set<String> theDisabledFields = new HashSet<String>(); 
}
