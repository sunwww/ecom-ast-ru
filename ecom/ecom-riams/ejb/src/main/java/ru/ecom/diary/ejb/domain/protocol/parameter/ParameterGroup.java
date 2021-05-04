package ru.ecom.diary.ejb.domain.protocol.parameter;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.jaas.ejb.domain.SecGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Группа параметров
 * @author stkacheva
 */
@Comment("Группа параметров")
@Entity
@Table(schema="SQLUser")
@Setter
@Getter
public class ParameterGroup extends BaseEntity {
	
	/** Доверительные группы */
	@Comment("Доверительные группы")
	@ManyToMany
	public List<SecGroup> getSecGroups() {return secGroups;}
	/** Доверительные группы */
	private List<SecGroup> secGroups;

	/** Список параметров, входящих в группу */
	@Comment("Список параметров, входящих в группу")
	@OneToMany(mappedBy="group")
	public List<Parameter> getParameters() {return parameters;}

	/** Родительская группа */
	@Comment("Родительская группа")
	@ManyToOne
	public ParameterGroup getParent() {return parent;}

	/** Дочерние группы */
	@Comment("Дочерние группы")
	@OneToMany(mappedBy="parent")
	public List<ParameterGroup> getChildGroups() {return childGroups;}

	/** Информация о параметрах */
	@Comment("Информация о параметрах")
	@Transient
	public String getParametersInfo() {
		//TODO доделать
		return "";
	}
	
	/** Дочерние группы */
	private List<ParameterGroup> childGroups;
	/** Родительская группа */
	private ParameterGroup parent;
	/** Список параметров, входящих в группу */
	private List<Parameter> parameters;
	/** Наименование */
	private String name;

}
