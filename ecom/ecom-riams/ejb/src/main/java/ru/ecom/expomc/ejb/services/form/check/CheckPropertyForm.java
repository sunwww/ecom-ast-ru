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
    public Long getId() { return id ; }
    public void setId(Long aId) { id = aId ; }


    /** Свойство */
    @Persist
    @Required
    public String getProperty() { return property ; }
    public void setProperty(String aProperty) { property = aProperty ; }

    /** Значение */
    @Persist
    @Required
    public String getValue() { return value ; }
    public void setValue(String aValue) { value = aValue ; }

    /** Проверка */
    @Persist
    public long getCheck() { return check ; }
    public void setCheck(long aCheck) { check = aCheck ; }

    /** Справочник  */
    public String getVocName() { return vocName ; }
    public void setVocName(String aVocName) { vocName = aVocName ; }

    /** Справочник  */
    private String vocName ;
    /** Проверка */
    private long check ;
    /** Значение */
    private String value ;
    /** Свойство */
    private String property ;
    /** Идентификатор */
    private Long id ;
	public Integer getSn() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setSn(Integer aSn) {
		// TODO Auto-generated method stub
		
	}

}
