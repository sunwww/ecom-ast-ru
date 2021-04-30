package ru.ecom.mis.ejb.form.medstandard;


import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medstandard.MedicalStandard;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;


@EntityForm
@EntityFormPersistance(clazz= MedicalStandard.class)
@Comment("Стандарт оказания мед. помощи")
@WebTrail(comment = "Стандарт оказания мед. помощи", nameProperties= "name"
, view="entityView-mis_medicalStandard.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedicalStandard")
@Setter

public class MedicalStandardForm extends IdEntityForm {
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return name;}
	private String name;
	/** Код */
	@Comment("Код")
	@Persist @Required
	public String getCode() {return code;}

	/** Код */
	private String code;
	
	/** Дата начала действия */
	@Comment("Дата начала действия")
	@Persist @Required
	@DateString @DoDateString
	public String getStartDate() {return startDate;}
	/** Дата начала действия */
	private String startDate;
	
	/** Дата окончания действия */
	@Persist
	@Comment("Дата окончания действия")
	@DateString @DoDateString
	public String getFinishDate() {return finishDate;}
	/** Дата окончания действия */
	private String finishDate;
	
	/** Родительский стандарт */
	@Comment("Родительский стандарт")
	public Long getParent() {return parent;}
	/** Родительский стандарт */
	private Long parent;
}
