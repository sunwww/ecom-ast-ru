package ru.ecom.diary.ejb.form.protocol.parameter;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class ParameterGroupForm extends IdEntityForm{
	
	/** Доверительные группы */
	@Comment("Доверительные группы")
	@Persist @PersistManyToManyOneProperty(collectionGenericType = SecGroup.class)
	public String getSecGroups() {return secGroups;}

	/** Доверительные группы */
	private String secGroups;
	
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return name;}

	/** Родительская группа */
	@Comment("Родительская группа")
	@Persist
	public Long getParent() {return parent;}

	@Comment("Информация о параметрах")
	@Persist
	public String getParametersInfo() {return parametersInfo;}

	/** Информация о параметрах */
	private String parametersInfo;
	/** Родительская группа */
	private Long parent;
	/** Наименование */
	private String name;

}
