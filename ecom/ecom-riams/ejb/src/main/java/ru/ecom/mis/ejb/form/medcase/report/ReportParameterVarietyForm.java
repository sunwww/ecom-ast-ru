package ru.ecom.mis.ejb.form.medcase.report;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.report.voc.ReportSetTypeParameterType;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz= ReportSetTypeParameterType.class)
@Comment("Диагноз")
@WebTrail(comment = "Диапазон", nameProperties= "id", view="entityView-rep_parameterVariety.do")
@Parent(property="parameterType", parentForm= ReportParameterForm.class)
@EntityFormSecurityPrefix("/Policy/Voc/ReportConfig")
@Setter
public class ReportParameterVarietyForm  extends IdEntityForm {

	/** Тип параметра */
	@Comment("Тип параметра")
	@Persist
	public Long getParameterType() {return parameterType;}
	private Long parameterType;

	/** Начальный параметр */
	@Comment("Начальный параметр")
	@Persist
	public String getCodeFrom() {return codeFrom;}
	private String codeFrom;

	/** Последний параметер */
	@Comment("Последний параметр")
	@Persist
	public String getCodeTo() {return codeTo;}
	private String codeTo;

	/** Пол код */
	@Comment("Пол код")
	@Persist
	public String getSexCode() {return sexCode;}
	private String sexCode;

	/** Пол */
	@Comment("Пол")
	@Persist
	public Long getSex() {return sex;}
	private Long sex;
}