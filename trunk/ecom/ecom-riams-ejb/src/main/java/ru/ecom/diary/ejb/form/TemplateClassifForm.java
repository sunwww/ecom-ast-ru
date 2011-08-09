package ru.ecom.diary.ejb.form;

import javax.persistence.Id;

import ru.ecom.diary.ejb.domain.protocol.template.TemplateClassif;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Протоколы
 */
@EntityForm
@EntityFormPersistance(clazz= TemplateClassif.class)
@Comment("Классификаторы")
@WebTrail(comment = "Классификаторы", nameProperties= "name", view="entityView-diary_templateClassif.do")
@EntityFormSecurityPrefix("/Policy/Diary/Template/Classif")
public class TemplateClassifForm extends IdEntityForm {
    /** Идентификатор */
    @Id
    @Comment("Идентификатор")
    public long getId() {    return theId ;}

    /** Название классификатора */
    @Persist
    @Comment("Название классификатора")
    public String getName() {    return theName ;}

    /** Класс, к которому принадлежит классификатор */
    @Persist
    @Comment("Класс, к которому принадлежит классификатор")
    public String getClazz() {    return theClazz ;}

    /** Свойство для отображения */
    @Persist
    @Comment("Свойство для отображения")
    public String getProperty() {    return theProperty ;}

    /** Свойство для отображения */
    public void setProperty(String aProperty ) {  theProperty = aProperty ; }

    /** Свойство для отображения */
    private String theProperty ;

    /** Класс, к которому принадлежит классификатор */
    public void setClazz(String aClazz ) {  theClazz = aClazz ; }

    /** Класс, к которому принадлежит классификатор */
    private String theClazz ;

    /** Название классификатора */
    public void setName(String aName ) {  theName = aName ; }

    /** Название классификатора */
    private String theName ;

    /** Идентификатор */
    public void setId(long aId ) {  theId = aId ; }

    /** Идентификатор */
    private long theId ;
}
