package ru.ecom.mis.ejb.form.directory;



import lombok.Setter;
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
@Setter

public class TelephoneNumberForm extends IdEntityForm{
    
	    /** номер телефона */
	    @Comment("номер телефона")
	    @Persist
	    public String getTelNumber() {return telNumber;}
	    private String telNumber;
	    
	      /** Ссылка на запись */
	    @Comment("Ссылка на запись")
	    @Persist
	    public Long getEntry() {return entry;}
	    private Long entry;
	    
	    /** Тип номера */
	    @Comment("Тип номера")
	    @Persist
	    public Long getTypeNumber() {return typeNumber;}
	    private Long typeNumber;
}
