package ru.ecom.diary.ejb.domain.protocol.parameter;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Группа параметров
 * @author stkacheva
 */
@Comment("Группа параметров")
@Entity
@Table(schema="SQLUser")
public class ParameterGroup extends BaseEntity {
	/** Наименование */
	@Comment("Наименование")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Список параметров, входящих в группу */
	@Comment("Список параметров, входящих в группу")
	@OneToMany(mappedBy="group")
	public List<Parameter> getParameters() {return theParameters;}
	public void setParameters(List<Parameter> aParameters) {theParameters = aParameters;}

	/** Родительская группа */
	@Comment("Родительская группа")
	@ManyToOne
	public ParameterGroup getParent() {return theParent;}
	public void setParent(ParameterGroup aParent) {theParent = aParent;}

	/** Дочерние группы */
	@Comment("Дочерние группы")
	@OneToMany(mappedBy="parent")
	public List<ParameterGroup> getChildGroups() {return theChildGroups;}
	public void setChildGroups(List<ParameterGroup> aChildGroups) {theChildGroups = aChildGroups;}

	/** Информация о параметрах */
	@Comment("Информация о параметрах")
	@Transient
	public String getParametersInfo() {
		//TODO доделать
		return "";
	}
	
	/** Дочерние группы */
	private List<ParameterGroup> theChildGroups;
	/** Родительская группа */
	private ParameterGroup theParent;
	/** Список параметров, входящих в группу */
	private List<Parameter> theParameters;
	/** Наименование */
	private String theName;

}
