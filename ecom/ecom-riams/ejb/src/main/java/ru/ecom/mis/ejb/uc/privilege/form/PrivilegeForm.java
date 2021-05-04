package ru.ecom.mis.ejb.uc.privilege.form;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.ecom.mis.ejb.uc.privilege.domain.Privilege;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= Privilege.class)
@WebTrail(comment = "Льгота", nameProperties= "info", view="entityParentView-mis_privilege.do", shortView="entityShortView-mis_privilege.do")
@Parent(property="person", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Person/Privilege")
@Setter
public class PrivilegeForm extends IdEntityForm {

	private Long category;
	private Long person;
	private String endDate;
	private String beginDate;
	private String serialDoc;
	private String numberDoc;
	private Boolean isDelete;
	private String info;

	@Comment("Серия документа")
	@Persist
	public String getSerialDoc() {
		return serialDoc;
	}

	@Comment("Серия документа")
	@Persist
	public String getNumberDoc() {
		return numberDoc;
	}

	@Comment("Персона")
	@Persist
	public Long getPerson() {
		return person;
	}

	@Comment("Категория льготников")
	@Persist @Required
	public Long getCategory() {
		return category;
	}

	@Comment("Дата начала")
	@DateString @DoDateString @Required
	@Persist
	public String getBeginDate() {
		return beginDate;
	}

	@Comment("Дата исключения")
	@DateString @DoDateString
	@Persist
	public String getEndDate() {
		return endDate;
	}

	@Comment("Признак удаления")
	@Persist
	public Boolean getIsDelete() {
		return isDelete;
	}

	@Comment("Информация")
	public String getInfo() {return info;}

}

