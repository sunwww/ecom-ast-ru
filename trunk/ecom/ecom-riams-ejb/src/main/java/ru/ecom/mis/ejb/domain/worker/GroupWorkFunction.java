package ru.ecom.mis.ejb.domain.worker;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Групповая рабочая функция
 * @author stkacheva, azviagin
 */
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="groupName",table="WorkFunction")
})
public class GroupWorkFunction extends WorkFunction {
	@Transient @Comment("Информация")
	public String getWorkFunctionInfo() {
		return getName() + " " + theGroupName;
	}
	@Transient
	public String getWorkerLpuInfo() {
		return getLpu()!=null ? getLpu().getFullname() :"";
	}
	
	/** Рабочие функции входящие в состав группы */
	@Comment("Рабочие функции входящие в состав группы")
	@OneToMany(mappedBy="group")
	public List<PersonalWorkFunction> getFunctions() {return theFunctions;}
	public void setFunctions(List<PersonalWorkFunction> aFunctions) {
		theFunctions = aFunctions;
	}
	@Transient
	public MisLpu getLpuRegister() {
		return getLpu() ;
	}
	
	@Transient
	public String getWorkerInfo() {
		return theGroupName ;
	}
	
	/** Название группы */
	@Comment("Название группы")
	public String getGroupName() {return theGroupName;}
	public void setGroupName(String aGroupName) {theGroupName = aGroupName;}

	/** Название группы */
	private String theGroupName;
	
	@Transient
	public String getInfo() {
		return new StringBuilder().append("ГРУППОВАЯ: ").append(getGroupName()).toString() ;
	}
	/** Рабочие функции входящие в состав группы */
	private List<PersonalWorkFunction> theFunctions;
	


}
