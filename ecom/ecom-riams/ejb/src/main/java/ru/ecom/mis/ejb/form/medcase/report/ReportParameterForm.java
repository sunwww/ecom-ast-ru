package ru.ecom.mis.ejb.form.medcase.report;

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
//@Parent(property="parameterType", parentForm= ReportParameterForm.class)
@EntityFormSecurityPrefix("/Policy/Voc/ReportConfig")
public class ReportParameterForm  extends IdEntityForm {
	/** Имя класса данных */
	@Comment("Имя класса данных")
	@Persist
	public String getClassName() {return theClassName;}
	public void setClassName(String aClassName) {theClassName = aClassName;}
	
	/** Пол */
	@Comment("Пол")
	@Persist
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}
	/** Пол код */
	@Comment("Пол код")
	@Persist
	public String getSexCode() {return theSexCode;}
	public void setSexCode(String aSexCode) {theSexCode = aSexCode;}

	/** Пол код */
	private String theSexCode;

	/** Строка */
	@Comment("Строка")
	@Persist
	public String getStrCode() {return theStrCode;}
	public void setStrCode(String aStr) {theStrCode = aStr;}

	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Код МКБ */
	@Comment("Код МКБ")
	@Persist
	public String getCode() {
		return theCode;
	}

	public void setCode(String aCode) {
		theCode = aCode;
	}

	/** Код МКБ */
	private String theCode;
	/** Наименование */
	private String theName;
	/** Строка */
	private String theStrCode;
	/** Пол */
	private Long theSex;

	/** Имя класса данных */
	private String theClassName;
}
