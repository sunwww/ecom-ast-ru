package ru.ecom.diary.ejb.form;

import java.sql.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.ManyToMany;

import ru.ecom.diary.ejb.domain.protocol.template.TemplateWord;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.jaas.ejb.domain.SecGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 */
@EntityForm
@EntityFormPersistance(clazz= TemplateWord.class)
@Comment("Ключевые слова")
@WebTrail(comment = "Ключевые слова", nameProperties= "reduction", view="entityView-diary_templateWord.do")
@EntityFormSecurityPrefix("/Policy/Diary/KeyWord")
public class TemplateWordForm extends IdEntityForm {

    /** Расшифровка */
    @Persist @Required
    @Comment("Расшифровка")
    public String getDecryption() {    return theDecryption ;}
    public void setDecryption(String aDecryption ) {  theDecryption = aDecryption ; }

    /** Сокращение */
    @Persist @Required 
    @Comment("Сокращение")
    public String getReduction() {    return theReduction ;}
    public void setReduction(String aReduction ) {  theReduction = aReduction ; }

    /** Расшифровка */
    private String theDecryption ;
    /** Сокращение */
    private String theReduction ;

    /** Пользователь */
    @Comment("Пользователь")
    @Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}

	/** Группы пользователей */
	@Comment("Группы пользователей")
	@Persist @PersistManyToManyOneProperty(collectionGenericType = SecGroup.class)
	public String getSecGroups() {return theSecGroups;}
	public void setSecGroups(String aSecGroups) {theSecGroups = aSecGroups;}

	/** Группы пользователей */
	private String theSecGroups;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;
	/** Пользователь */
	private String theCreateUsername;

}
