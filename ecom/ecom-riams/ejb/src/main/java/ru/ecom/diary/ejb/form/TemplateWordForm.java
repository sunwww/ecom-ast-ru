package ru.ecom.diary.ejb.form;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class TemplateWordForm extends IdEntityForm {

    /** Расшифровка */
    @Persist @Required
    @Comment("Расшифровка")
    public String getDecryption() {    return decryption ;}

    /** Сокращение */
    @Persist @Required @DoUpperCase
    @Comment("Сокращение")
    public String getReduction() {    return reduction ;}

    /** Расшифровка */
    private String decryption ;
    /** Сокращение */
    private String reduction ;

    /** Пользователь */
    @Comment("Пользователь")
    @Persist
	public String getCreateUsername() {return createUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return createDate;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return editDate;}

	/** Группы пользователей */
	@Comment("Группы пользователей")
	@Persist @PersistManyToManyOneProperty(collectionGenericType = SecGroup.class)
	public String getSecGroups() {return secGroups;}

	/** Группы пользователей */
	private String secGroups;
	/** Дата редактирования */
	private String editDate;
	/** Дата создания */
	private String createDate;
	/** Пользователь */
	private String createUsername;
	
	/** Пользователь, отредактировающий запись */
	@Comment("Пользователь, отредактировающий запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Пользователь, отредактировающий запись */
	private String editUsername;

}
