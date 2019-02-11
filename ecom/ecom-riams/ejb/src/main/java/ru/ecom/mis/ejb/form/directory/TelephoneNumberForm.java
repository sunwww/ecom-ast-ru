package ru.ecom.mis.ejb.form.directory;



import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.directory.TelephoneNumber;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz = TelephoneNumber.class)
@Comment("Форма телефонных номеров")
@WebTrail(comment = "Форма телефонных номеров"
, nameProperties= "id", list="entityParentList-directory_telephonenumber.do", view="entityParentView-directory_telephonenumber.do")
@EntityFormSecurityPrefix("/Policy/Mis/Directory/Department")

public class TelephoneNumberForm extends IdEntityForm{
    
	    /** номер телефона */
	    @Comment("номер телефона")
	    @Persist
	    public String getTelNumber() {return theTelNumber;}
	    public void setTelNumber(String aTelNumber) {theTelNumber = aTelNumber;}
	    private String theTelNumber;
	    
	      /** Ссылка на запись */
	    @Comment("Ссылка на запись")
	    @Persist
	    public Long getEntry() {return theEntry;}
	    public void setEntry(Long aEntry) {theEntry = aEntry;}
	    private Long theEntry;
	    
	    /** Тип номера */
	    @Comment("Тип номера")
	    @Persist
	    public Long getTypeNumber() {return theTypeNumber;}
	    public void setTypeNumber(Long aTypeNumber) {theTypeNumber = aTypeNumber;}
	    private Long theTypeNumber;
}
