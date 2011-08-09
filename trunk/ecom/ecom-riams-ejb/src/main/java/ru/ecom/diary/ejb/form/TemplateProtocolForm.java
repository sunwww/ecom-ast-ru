package ru.ecom.diary.ejb.form;

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.mis.ejb.form.medcase.MedServiceForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Форма шаблона протокола
 * @author STkacheva
 */
@EntityForm
@EntityFormPersistance(clazz= TemplateProtocol.class)
@Comment("Шаблон протокола")
@WebTrail(comment = "Шаблон протокола", nameProperties= "title", view="entityView-temp_protocol.do")
@EntityFormSecurityPrefix("/Policy/Diary/Template")
@Parent(property="medService", parentForm=MedServiceForm.class)
public class TemplateProtocolForm  extends IdEntityForm {

    /** Заголовок */
    @Persist @Comment("Заголовок") @Required
    public String getTitle() {    return theTitle ;}
    public void setTitle(String aTitle ) {  theTitle = aTitle ; }

    /** Текст */
    @Persist  @Comment("Текст") @Required
    public String getText() {    return theText ;}
    public void setText(String aText ) {  theText = aText ; }

    /** Ключевые слова */
    @Persist @Comment("Ключевые слова")
    public String getKeyWord() {    return theKeyWord ;}
    public void setKeyWord(String aKeyWord ) {  theKeyWord = aKeyWord ; }

    /** Тип шаблона */
    @Persist @Comment("Тип шаблона")
    public Long getType() {    return theType ;}    
    public void setType(Long aType ) {  theType = aType ; }

    /** Категории, в которые входит протокол */
	@Comment("Категории, в которые входит протокол")
	@PersistManyToManyOneProperty(collectionGenericType=TemplateCategory.class)
	@Persist @Required
	public String getCategories() {return theCategories;}
	public void setCategories(String aCategories) {theCategories = aCategories;	}

	/** Пользователь */
	@Comment("Пользователь") @Persist
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString 
	public String getDate() {return theDate;}
	public void setDate(String aDate) {theDate = aDate;}

	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@Persist
	public Long getMedService() {return theMedService;}
	public void setMedService(Long aMedService) {theMedService = aMedService;}

	/** Информация по шаблону протокола */
	@Comment("Информация по шаблону протокола")
	@Persist
	public String getInformation() {return theInformation;}
	public void setInformation(String aInformation) {theInformation = aInformation;}
	
	/** Категории инфо*/
	@Comment("Категории инфо")
	@Persist
	public String getCategoriesInfo() {return theCategoriesInfo;}
	public void setCategoriesInfo(String aCategoriesInfo) {theCategoriesInfo = aCategoriesInfo;}

	/** Категории инфо*/
	private String theCategoriesInfo;
	/** Информация по шаблону протокола */
	private String theInformation;
	/** Медицинская услуга */
	private Long theMedService;
	/** Дата создания */
	private String theDate;
	/** Пользователь */
	private String theUsername;
	/** Категории, в которые входит протокол */
	private String theCategories;
    /** Тип шаблона */
    private Long theType ;
    /** Ключевые слова */
    private String theKeyWord ;
    /** Текст */
    private String theText ;
    /** Заголовок */
    private String theTitle ;
}
