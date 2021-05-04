package ru.ecom.diary.ejb.form.protocol;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.medcase.MedServiceForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= TemplateProtocol.class)
@Comment("Шаблон протокола")
@WebTrail(comment = "Шаблон протокола", nameProperties= "title", view="entityParentView-diary_template.do")
@EntityFormSecurityPrefix("/Policy/Diary/Template")
@Parent(property="medService", parentForm=MedServiceForm.class)
@Setter
public class TemplateProtocolForm extends IdEntityForm{

	/** Создавать браслет по шаблону */
	@Comment("Создавать браслет по шаблону")
	@Persist
	public Boolean getCreateBracelet() {return createBracelet;}
	private Boolean createBracelet ;
	
	/** Создавать дневник по умолчанию при приеме в лабораторию */
	@Comment("Создавать дневник по умолчанию при приеме в лабораторию")
	@Persist
	public Boolean getCreateDiaryByDefault() {return createDiaryByDefault;}
	/** Создавать дневник по умолчанию при приеме в лабораторию */
	private Boolean createDiaryByDefault;
	
	/** Заголовок */
	@Comment("Заголовок")
	@Persist @Required
	public String getTitle() {return title;}

	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@Persist
	public Long getMedService() {return medService;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return username;	}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getDate() {return date;}

	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInformation() {return information;}

	/** Категории инфо*/
	@Comment("Категории инфо")
	@Persist
	public String getCategoriesInfo() {return categoriesInfo;}

	/** Категории инфо*/
	private String categoriesInfo;
	/** Информация */
	private String information;
	/** Дата создания */
	private String date;
	/** Пользователь */
	private String username;
	/** Медицинская услуга */
	private Long medService;
	/** Заголовок */
	private String title;
}
