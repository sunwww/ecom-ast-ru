package ru.ecom.diary.ejb.form;

import javax.persistence.Id;

import ru.ecom.diary.ejb.domain.protocol.template.TemplateWord;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 */
@EntityForm
@EntityFormPersistance(clazz= TemplateWord.class)
@Comment("Ключевые слова")
@WebTrail(comment = "Ключевые слова", nameProperties= "reduction", view="entityView-diary_templateWord.do")
@EntityFormSecurityPrefix("/Policy/Diary/KeyWord")
public class TemplateWordForm extends IdEntityForm {
    /** Идентификатор */
    @Id
    @Comment("Идентификатор")
    public long getId() {    return theId ;}

    /** Сокращение */
    @Persist
    @Required @DoUpperCase
    @Comment("Сокращение")
    public String getReduction() {    return theReduction ;}

    /** Расшифровка */
    @Persist
    @Required
    @Comment("Расшифровка")
    public String getDecryption() {    return theDecryption ;}

    /** Расшифровка */
    public void setDecryption(String aDecryption ) {  theDecryption = aDecryption ; }

    /** Расшифровка */
    private String theDecryption ;

    /** Сокращение */
    public void setReduction(String aReduction ) {  theReduction = aReduction ; }

    /** Сокращение */
    private String theReduction ;

    /** Идентификатор */
    public void setId(long aId ) {  theId = aId ; }

    /** Идентификатор */
    private long theId ;
}
