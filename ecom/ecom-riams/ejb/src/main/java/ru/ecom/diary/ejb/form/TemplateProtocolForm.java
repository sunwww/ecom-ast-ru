package ru.ecom.diary.ejb.form;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.jaas.ejb.domain.SecGroup;
import ru.ecom.mis.ejb.form.medcase.MedServiceForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
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
@Setter
public class TemplateProtocolForm  extends IdEntityForm {

	/** Создавать браслет по шаблону */
	@Comment("Создавать браслет по шаблону")
	@Persist
	public Boolean getCreateBracelet() {return createBracelet;}
	private Boolean createBracelet ;

	/** Запрет на ручное редактирование */
	@Comment("Запрет на ручное редактирование")
	@Persist
	public Boolean getDisableEdit() {return disableEdit;}
	/** Запрет на ручное редактирование */
	private Boolean disableEdit;
	
	/** Заголовок */
    @Persist @Comment("Заголовок") @Required
    public String getTitle() {    return title ;}

    /** Текст */
    @Persist  @Comment("Текст") @Required
    public String getText() {    return text ;}

    /** Ключевые слова */
    @Persist @Comment("Ключевые слова")
    public String getKeyWord() {    return keyWord ;}

    /** Категории, в которые входит протокол */
	@Comment("Категории, в которые входит протокол")
	@PersistManyToManyOneProperty(collectionGenericType=TemplateCategory.class)
	@Persist @Required
	public String getCategories() {return categories;}

	/** Пользователь */
	@Comment("Пользователь") @Persist
	public String getUsername() {return username;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString 
	public String getDate() {return date;}

	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@Persist
	public Long getMedService() {return medService;}

	/** Информация по шаблону протокола */
	@Comment("Информация по шаблону протокола")
	@Persist
	public String getInformation() {return information;}

	/** Категории инфо*/
	@Comment("Категории инфо")
	@Persist
	public String getCategoriesInfo() {return categoriesInfo;}

	/** Группы пользователей */
	@Comment("Группы пользователей")
	@Persist @PersistManyToManyOneProperty(collectionGenericType = SecGroup.class)
	public String getSecGroups() {return secGroups;}

	/** Группы пользователей */
	private String secGroups;
	/** Категории инфо*/
	private String categoriesInfo;
	/** Информация по шаблону протокола */
	private String information;
	/** Медицинская услуга */
	private Long medService;
	/** Дата создания */
	private String date;
	/** Пользователь */
	private String username;
	/** Категории, в которые входит протокол */
	private String categories;
    /** Ключевые слова */
    private String keyWord ;
    /** Текст */
    private String text ;
    /** Заголовок */
    private String title ;
}
