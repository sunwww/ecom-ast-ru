package ru.ecom.diary.ejb.form;

import javax.persistence.Id;

import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocolType;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Тип шаблона
 */
@EntityForm
@EntityFormPersistance(clazz= TemplateProtocolType.class)
@Comment("Тип шаблона протокола")
@WebTrail(comment = "Тип шаблона протокола", nameProperties= "name", view="entityView-diary_templateProtokolType.do")
@EntityFormSecurityPrefix("/Policy/Diary/TemplateType")
public class TemplateProtocolTypeForm extends IdEntityForm {
    /** Идентификатор */
    @Id
    @Comment("Идентификатор")
    public long getId() {    return theId ;}

    /** Наименование */
    @Persist
    @Comment("Наименование")
    public String getName() {    return theName ;}

    /** Наименование */
    public void setName(String aName ) {  theName = aName ; }

    /** Наименование */
    private String theName ;

    /** Идентификатор */
    public void setId(long aId ) {  theId = aId ; }

    /** Идентификатор */
    private long theId ;
}
