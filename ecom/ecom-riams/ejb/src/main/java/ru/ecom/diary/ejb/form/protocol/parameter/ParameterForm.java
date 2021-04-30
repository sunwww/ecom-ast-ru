package ru.ecom.diary.ejb.form.protocol.parameter;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class ParameterForm extends IdEntityForm{
	
	/** Внешний код */
	@Comment("Внешний код")
	@Persist
	public String getExternalCode() {return externalCode;}
	/** Внешний код */
	private String externalCode;
	
	/** Код */
	@Comment("Код")
	@Persist 
	public String getCode() {return code;}
	/** Код */
	private String code;
	
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return name;}

	/** Единица измерения */
	@Comment("Единица измерения")
	@Persist
	public Long getMeasureUnit() {return measureUnit;}

	/** Максимальное значение */
	@Comment("Максимальное значение")
	@Persist
	public Long getMaximum() {return maximum;}

	/** Минимальное значение */
	@Comment("Минимальное значение")
	@Persist
	public Long getMinimum() {return minimum;}

	/** Норма - максимальное значение */
	@Comment("Норма - максимальное значение")
	@Persist
	public Long getNormMaximum() {return normMaximum;}

	/** Норма - минимальное значение */
	@Comment("Норма - минимальное значение")
	@Persist
	public Long getNormMinimum() {return normMinimum;}

	/** Короткое имя */
	@Comment("Короткое имя")
	@Persist @Required
	public String getShortName() {return shortName;}

	/** Группа */
	@Comment("Группа")
	@Persist @Required
	public Long getGroup() {return group;}

	/** Пользовательский справочник */
	@Comment("Пользовательский справочник")
	@Persist
	public Long getValueDomain() {return valueDomain;}

	/** Тип параметра */
	@Comment("Тип параметра")
	@Persist @Required
	public Long getType() {return type;}

	/** Тип параметра */
	private Long type;
	/** Пользовательский справочник */
	private Long valueDomain;
	/** Группа */
	private Long group;
	/** Короткое имя */
	private String shortName;
	/** Норма - минимальное значение */
	private Long normMinimum;
	/** Норма - максимальное значение */
	private Long normMaximum;
	/** Минимальное значение */
	private Long minimum;
	/** Максимальное значение */
	private Long maximum;
	/** Единица измерения */
	private Long measureUnit;
	/** Наименование */
	private String name;
	
	/** Норма - минимальное значение */
	@Comment("Норма - минимальное значение")
	@Persist
	public String getNormMinimumBD() {return normMinimumBD;}

	/** Норма - максимальное значение */
	@Comment("Норма - максимальное значение")
	@Persist
	public String getNormMaximumBD() {return normMaximumBD;}

	/** Максимальное значение */
	@Comment("Максимальное значение")
	@Persist
	public String getMaximumBD() {return maximumBD;}

	/** Минимальное значение */
	@Comment("Минимальное значение")
	@Persist
	public String getMinimumBD() {return minimumBD;}
	/** Кол-во знаков после запятой */
	@Comment("Кол-во знаков после запятой")
	@Persist
	public Long getCntDecimal() {return cntDecimal;}

	/** Кол-во знаков после запятой */
	private Long cntDecimal;
	/** Минимальное значение */
	private String minimumBD;
	/** Максимальное значение */
	private String maximumBD;
	/** Норма - максимальное значение */
	private String normMaximumBD;
	/** Норма - минимальное значение */
	private String normMinimumBD;
	
	/** Значение по умолчанию для текстового поля */
	@Comment("Значение по умолчанию для текстового поля")
	@Persist
	public String getValueTextDefault() {return valueTextDefault;}

	/** Значение по умолчанию для текстового поля */
	private String valueTextDefault;
}
