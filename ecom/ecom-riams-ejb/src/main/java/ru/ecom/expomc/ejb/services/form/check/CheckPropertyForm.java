package ru.ecom.expomc.ejb.services.form.check;

import javax.persistence.Id;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expomc.ejb.domain.check.CheckProperty;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 23.08.2006
 * Time: 23:44:45
 * To change this template use File | Settings | File Templates.
 */
@EntityForm
@EntityFormPersistance(clazz= CheckProperty.class)
@Comment("Настройка проверки")
@Parent(property = "check", parentForm=CheckForm.class)
@WebTrail(comment="Настройка проверки", nameProperties="property", view ="exp_checkPropertyEdit.do")
@EntityFormSecurityPrefix("/Policy/Exp/CheckProperty")
public class CheckPropertyForm extends BaseValidatorForm  implements IEntityForm {

    /** Идентификатор */
    @Id
    public Long getId() { return theId ; }
    public void setId(Long aId) { theId = aId ; }


    /** Свойство */
    @Persist
    @Required
    public String getProperty() { return theProperty ; }
    public void setProperty(String aProperty) { theProperty = aProperty ; }

    /** Значение */
    @Persist
    @Required
    public String getValue() { return theValue ; }
    public void setValue(String aValue) { theValue = aValue ; }

    /** Проверка */
    @Persist
    public long getCheck() { return theCheck ; }
    public void setCheck(long aCheck) { theCheck = aCheck ; }

    /** Справочник  */
    public String getVocName() { return theVocName ; }
    public void setVocName(String aVocName) { theVocName = aVocName ; }

    /** Справочник  */
    private String theVocName ;
    /** Проверка */
    private long theCheck ;
    /** Значение */
    private String theValue ;
    /** Свойство */
    private String theProperty ;
    /** Идентификатор */
    private Long theId ;
	public Integer getSn() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setSn(Integer aSn) {
		// TODO Auto-generated method stub
		
	}

}
