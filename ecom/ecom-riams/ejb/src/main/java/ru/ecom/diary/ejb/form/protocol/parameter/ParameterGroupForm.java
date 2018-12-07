package ru.ecom.diary.ejb.form.protocol.parameter;

import ru.ecom.diary.ejb.domain.protocol.parameter.ParameterGroup;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.jaas.ejb.domain.SecGroup;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= ParameterGroup.class)
@Comment("Группа параметров")
@WebTrail(comment = "Группа параметров", nameProperties= "name", view="entityParentView-diary_parameterGroup.do")
@EntityFormSecurityPrefix("/Policy/Diary/ParameterGroup")
@Parent(property="parent", parentForm=ParameterGroupForm.class)
public class ParameterGroupForm extends IdEntityForm{
	
	/** Доверительные группы */
	@Comment("Доверительные группы")
	@Persist @PersistManyToManyOneProperty(collectionGenericType = SecGroup.class)
	public String getSecGroups() {return theSecGroups;}
	public void setSecGroups(String aSecGroups) {theSecGroups = aSecGroups;}
	
	/** Доверительные группы */
	private String theSecGroups;
	
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
