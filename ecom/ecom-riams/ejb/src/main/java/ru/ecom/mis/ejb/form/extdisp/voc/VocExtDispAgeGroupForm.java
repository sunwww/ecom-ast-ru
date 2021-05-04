package ru.ecom.mis.ejb.form.extdisp.voc;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispAgeGroup;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = VocExtDispAgeGroup.class)
@Comment("Возрастные группы")
@WebTrail(comment = "Возрастные группы", nameProperties= "id"
, view="entityView-extDisp_vocAgeGroup.do")
@Parent(property="dispType", parentForm=VocExtDispForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card/Voc/AgeGroup")
@Setter
public class VocExtDispAgeGroupForm extends IdEntityForm {
	/** Вид доп. диспансеризации */
	@Comment("Вид доп. диспансеризации")
	@Persist
	public Long getDispType() {return dispType;}

	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {return name;}

	/** Код */
	@Comment("Код")
	@Persist
	public String getCode() {return code;}

	/** Возрастная группа для отчета */
	@Comment("Возрастная группа для отчета")
	@Persist @Required
	public Long getReportGroup() {return reportGroup;}

	/** Возрастная группа для отчета */
	private Long reportGroup;
	/** Код */
	private String code;
	/** Наименование */
	private String name;
	/** Вид доп. диспансеризации */
	private Long dispType;
	
	/** Архивная */
	@Comment("Архивная")
	@Persist
	public Boolean getIsArchival() {return isArchival;}
	/** Архивная */
	private Boolean isArchival;
}
