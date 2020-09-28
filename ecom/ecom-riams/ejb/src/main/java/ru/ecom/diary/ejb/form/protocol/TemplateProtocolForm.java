package ru.ecom.diary.ejb.form.protocol;

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
public class TemplateProtocolForm extends IdEntityForm{

	/** Создавать браслет по шаблону */
	@Comment("Создавать браслет по шаблону")
	@Persist
	public Boolean getCreateBracelet() {return theCreateBracelet;}
	public void setCreateBracelet(Boolean aCreateBracelet) {theCreateBracelet = aCreateBracelet;}
	private Boolean theCreateBracelet ;
	
	/** Создавать дневник по умолчанию при приеме в лабораторию */
	@Comment("Создавать дневник по умолчанию при приеме в лабораторию")
	@Persist
	public Boolean getCreateDiaryByDefault() {return theCreateDiaryByDefault;}
	public void setCreateDiaryByDefault(Boolean aCreateDiaryByDefault) {theCreateDiaryByDefault = aCreateDiaryByDefault;}
	/** Создавать дневник по умолчанию при приеме в лабораторию */
	private Boolean theCreateDiaryByDefault;
	
	/** Заголовок */
	@Comment("Заголовок")
	@Persist @Required
	public String getTitle() {return theTitle;}
	public void setTitle(String aTitle) {theTitle = aTitle;}

	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@Persist
	public Long getMedService() {return theMedService;}
	public void setMedService(Long aMedService) {theMedService = aMedService;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return theUsername;	}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getDate() {return theDate;}
	public void setDate(String aDate) {theDate = aDate;}

	/** Информация */
	@Comment("Информация")
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
	/** Информация */
	private String theInformation;
	/** Дата создания */
	private String theDate;
	/** Пользователь */
	private String theUsername;
	/** Медицинская услуга */
	private Long theMedService;
	/** Заголовок */
	private String theTitle;
}
