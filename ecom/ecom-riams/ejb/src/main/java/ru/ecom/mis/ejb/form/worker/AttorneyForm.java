package ru.ecom.mis.ejb.form.worker;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.Attorney;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= Attorney.class)
@Comment("Доверенность")
@WebTrail(comment = "Доверенность", nameProperties= "id", view="entityView-work_attorney.do")
@EntityFormSecurityPrefix("/Policy/Mis/Worker/Attorney")
@Setter
public class AttorneyForm extends IdEntityForm{

	/** Лицо, на которое выдана доверенность */
	@Comment("Лицо, на которое выдана доверенность")
	@Persist @Required
	public Long getPerson() {return person;}
	/** Лицо, на которое выдана доверенность */
	private Long person;
	
	/** ФИО в родительном падеже */
	@Comment("ФИО в родительном падеже")
	@Persist @Required
	public String getAltPersonInfo() {return altPersonInfo;}
	/** ФИО в родительном падеже */
	private String altPersonInfo;
		
	/** Номер доверенности */
	@Comment("Номер доверенности")
	@Persist @Required
	public String getAttorneyNumber() {return attorneyNumber;}
	/** Номер доверенности */
	private String attorneyNumber;
	
	/** Дата выдачи доверенности */
	@Comment("Дата выдачи доверенности")
	@Persist @DateString @DoDateString
	@Required
	public String getAttorneyStartDate() {return attorneyStartDate;}
	/** Дата выдачи доверенности */
	private String attorneyStartDate;
	
	/** Недействительна */
	@Comment("Недействительна")
	@Persist
	public Boolean getIsArchive() {return isArchive;}
	/** Недействительна */
	private Boolean isArchive;
	
	/** Тип доверенности */
	@Comment("Тип доверенности")
	@Persist @Required
	public Long getType() {return type;}
	/** Тип доверенности */
	private Long type;
	
	/** Использовать в системе по умолчанию */
	@Comment("Использовать в системе по умолчанию")
	@Persist
	public Boolean getIsDefault() {return isDefault;}
	/** Использовать в системе по умолчанию */
	private Boolean isDefault;
}
