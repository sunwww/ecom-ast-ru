package ru.ecom.mis.ejb.form.medcase.report;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.report.voc.VocReportSetParameterType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz= VocReportSetParameterType.class)
@Comment("Параметр")
@WebTrail(comment = "Параметр", nameProperties= "id", view="entityView-rep_parameter.do")
@EntityFormSecurityPrefix("/Policy/Voc/ReportConfig")
@Setter
public class ReportParameterForm  extends IdEntityForm {
	/** Имя класса данных */
	@Comment("Имя класса данных")
	@Persist
	public String getClassName() {return className;}

	/** Пол */
	@Comment("Пол")
	@Persist
	public Long getSex() {return sex;}
	/** Пол код */
	@Comment("Пол код")
	@Persist
	public String getSexCode() {return sexCode;}

	/** Пол код */
	private String sexCode;

	/** Строка */
	@Comment("Строка")
	@Persist
	public String getStrCode() {return strCode;}

	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {
		return name;
	}

	/** Код МКБ */
	@Comment("Код МКБ")
	@Persist
	public String getCode() {
		return code;
	}

	/** Код МКБ */
	private String code;
	/** Наименование */
	private String name;
	/** Строка */
	private String strCode;
	/** Пол */
	private Long sex;
	/** Имя класса данных */
	private String className;
}
