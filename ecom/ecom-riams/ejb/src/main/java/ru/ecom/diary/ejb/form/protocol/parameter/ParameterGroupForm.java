package ru.ecom.diary.ejb.form.protocol.parameter;

import ru.ecom.diary.ejb.domain.protocol.parameter.ParameterGroup;
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
@EntityFormPersistance(clazz= ParameterGroup.class)
@Comment("Группа параметров")
@WebTrail(comment = "Группа параметров", nameProperties= "name", view="entityParentView-diary_parameterGroup.do")
@EntityFormSecurityPrefix("/Policy/Diary/ParameterGroup")
@Parent(property="parent", parentForm=ParameterGroupForm.class)
public class ParameterGroupForm extends IdEntityForm{
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Родительская группа */
	@Comment("Родительская группа")
	@Persist
	public Long getParent() {return theParent;}
	public void setParent(Long aParent) {theParent = aParent;}

	@Comment("Информация о параметрах")
	@Persist
	public String getParametersInfo() {return theParametersInfo;}
	public void setParametersInfo(String aParameter) {theParametersInfo = aParameter;}

	/** Информация о параметрах */
	private String theParametersInfo;
	/** Родительская группа */
	private Long theParent;
	/** Наименование */
	private String theName;

}
