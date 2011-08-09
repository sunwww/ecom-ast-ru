package ru.ecom.diary.ejb.form;

import javax.persistence.Id;

import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocolCategory;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = TemplateProtocolCategory.class)
@Comment("Категории классификатора")
@WebTrail(comment = "Категории классификатора", nameProperties = "classif", view = "entityParentView-diary_templateProtocolCategory.do")
@Parent(property = "templateProtocol", parentForm =TemplateProtocolForm.class)
@EntityFormSecurityPrefix("/Policy/Diary/Template/Classif/Category")
public class TemplateProtocolCategoryForm extends IdEntityForm {
    /** Название категории */
    @Persist
    @Comment("Название категории")
    public String getCategoryName() {    return theCategoryName ;}

    /** Название классификаора */
    @Persist
    @Comment("Название классификаора")
    public String getClassifName() {    return theClassifName ;}

    /** Идентификатор */
    @Id
    @Comment("Идентификатор")
    public long getId() {    return theId ;}

    /** Классификатор */
    @Persist
    @Comment("Классификатор")
    public Long getClassif() {    return theClassif ;}

    /** Категория классификатора */
    @Persist
    @Comment("Категория классификатора")
    public Long getCategory() {    return theCategory ;}

    /** Шаблон протокола */
    @Persist
    @Comment("Шаблон протокола")
    public Long getTemplateProtocol() {    return theTemplateProtocol ;}

    /** Шаблон протокола */
    public void setTemplateProtocol(Long aTemplateProtocol ) {  theTemplateProtocol = aTemplateProtocol ; }

    /** Шаблон протокола */
    private Long theTemplateProtocol ;

    /** Категория классификатора */
    public void setCategory(Long aCategory ) {  theCategory = aCategory ; }

    /** Категория классификатора */
    private Long theCategory ;

    /** Классификатор */
    public void setClassif(Long aClassif ) {  theClassif = aClassif ; }

    /** Классификатор */
    private Long theClassif ;

    /** Идентификатор */
    public void setId(long aId ) {  theId = aId ; }

    /** Идентификатор */
    private long theId ;

    /** Название классификаора */
    public void setClassifName(String aClassifName ) {  theClassifName = aClassifName ; }

    /** Название классификаора */
    private String theClassifName ;

    /** Название категории */
    public void setCategoryName(String aCategoryName ) {  theCategoryName = aCategoryName ; }

    /** Название категории */
    private String theCategoryName ;
}
