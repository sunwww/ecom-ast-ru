package ru.ecom.diary.ejb.form.protocol.parameter;

import ru.ecom.diary.ejb.domain.protocol.parameter.Parameter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= Parameter.class)
@Comment("Параметр")
@WebTrail(comment = "Параметр", nameProperties= "id", view="entityParentView-diary_parameter.do")
@EntityFormSecurityPrefix("/Policy/Diary/ParameterGroup/Parameter")
@Parent(property="group", parentForm=ParameterGroupForm.class)
public class ParameterForm extends IdEntityForm{
	
	/** Внешний код */
	@Comment("Внешний код")
	@Persist
	public String getExternalCode() {return theExternalCode;}
	public void setExternalCode(String aExternalCode) {theExternalCode = aExternalCode;}
	/** Внешний код */
	private String theExternalCode;
	
	/** Код */
	@Comment("Код")
	@Persist 
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}
	/** Код */
	private String theCode;
	
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Единица измерения */
	@Comment("Единица измерения")
	@Persist
	public Long getMeasureUnit() {return theMeasureUnit;}
	public void setMeasureUnit(Long aMeasureUnit) {theMeasureUnit = aMeasureUnit;}

	/** Максимальное значение */
	@Comment("Максимальное значение")
	@Persist
	public Long getMaximum() {return theMaximum;}
	public void setMaximum(Long aMaximum) {theMaximum = aMaximum;}

	/** Минимальное значение */
	@Comment("Минимальное значение")
	@Persist
	public Long getMinimum() {return theMinimum;}
	public void setMinimum(Long aMinimum) {theMinimum = aMinimum;}

	/** Норма - максимальное значение */
	@Comment("Норма - максимальное значение")
	@Persist
	public Long getNormMaximum() {return theNormMaximum;}
	public void setNormMaximum(Long aNormMaximum) {theNormMaximum = aNormMaximum;}

	/** Норма - минимальное значение */
	@Comment("Норма - минимальное значение")
	@Persist
	public Long getNormMinimum() {return theNormMinimum;}
	public void setNormMinimum(Long aNormMinimum) {theNormMinimum = aNormMinimum;}

	/** Короткое имя */
	@Comment("Короткое имя")
	@Persist @Required
	public String getShortName() {return theShortName;}
	public void setShortName(String aShortName) {theShortName = aShortName;}

	/** Группа */
	@Comment("Группа")
	@Persist @Required
	public Long getGroup() {return theGroup;}
	public void setGroup(Long aGroup) {theGroup = aGroup;}

	/** Пользовательский справочник */
	@Comment("Пользовательский справочник")
	@Persist
	public Long getValueDomain() {return theValueDomain;}
	public void setValueDomain(Long aValueDomain) {theValueDomain = aValueDomain;}

	/** Тип параметра */
	@Comment("Тип параметра")
	@Persist @Required
	public Long getType() {return theType;}
	public void setType(Long aType) {theType = aType;}

	/** Тип параметра */
	private Long theType;
	/** Пользовательский справочник */
	private Long theValueDomain;
	/** Группа */
	private Long theGroup;
	/** Короткое имя */
	private String theShortName;
	/** Норма - минимальное значение */
	private Long theNormMinimum;
	/** Норма - максимальное значение */
	private Long theNormMaximum;
	/** Минимальное значение */
	private Long theMinimum;
	/** Максимальное значение */
	private Long theMaximum;
	/** Единица измерения */
	private Long theMeasureUnit;
	/** Наименование */
	private String theName;
	
	/** Норма - минимальное значение */
	@Comment("Норма - минимальное значение")
	@Persist
	public String getNormMinimumBD() {return theNormMinimumBD;}
	public void setNormMinimumBD(String aNormMinimumBD) {theNormMinimumBD = aNormMinimumBD;}

	/** Норма - максимальное значение */
	@Comment("Норма - максимальное значение")
	@Persist
	public String getNormMaximumBD() {return theNormMaximumBD;}
	public void setNormMaximumBD(String aNormMaximumBD) {theNormMaximumBD = aNormMaximumBD;}

	/** Максимальное значение */
	@Comment("Максимальное значение")
	@Persist
	public String getMaximumBD() {return theMaximumBD;}
	public void setMaximumBD(String aMaximumBD) {theMaximumBD = aMaximumBD;}

	/** Минимальное значение */
	@Comment("Минимальное значение")
	@Persist
	public String getMinimumBD() {return theMinimumBD;}
	public void setMinimumBD(String aMinimumBD) {theMinimumBD = aMinimumBD;}
	/** Кол-во знаков после запятой */
	@Comment("Кол-во знаков после запятой")
	@Persist
	public Long getCntDecimal() {return theCntDecimal;}
	public void setCntDecimal(Long aCntDecimal) {theCntDecimal = aCntDecimal;}

	/** Кол-во знаков после запятой */
	private Long theCntDecimal;
	/** Минимальное значение */
	private String theMinimumBD;
	/** Максимальное значение */
	private String theMaximumBD;
	/** Норма - максимальное значение */
	private String theNormMaximumBD;
	/** Норма - минимальное значение */
	private String theNormMinimumBD;
	
	/** Значение по умолчанию для текстового поля */
	@Comment("Значение по умолчанию для текстового поля")
	@Persist
	public String getValueTextDefault() {return theValueTextDefault;}
	public void setValueTextDefault(String aValueTextDefault) {theValueTextDefault = aValueTextDefault;}
 
	/** Значение по умолчанию для текстового поля */
	private String theValueTextDefault;
}
