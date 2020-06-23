package ru.ecom.mis.ejb.domain.worker;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

/**
 * Групповая рабочая функция
 * @author stkacheva, azviagin
 */
@Entity
@AIndexes({
	@AIndex(properties="groupName",table="WorkFunction")
})
public class GroupWorkFunction extends WorkFunction {
	
	/** Направлять анализы по умолчанию в этот кабинет */
	@Comment("Направлять анализы по умолчанию в этот кабинет")
	public Boolean getIsDefaultLabCabinet() {return theIsDefaultLabCabinet;}
	public void setIsDefaultLabCabinet(Boolean aIsDefaultLabCabinet) {theIsDefaultLabCabinet = aIsDefaultLabCabinet;}
	/** Направлять анализы по умолчанию в этот кабинет */
	private Boolean theIsDefaultLabCabinet;
	
	/** Есть обслуживающий персонал */
	@Comment("Есть обслуживающий персонал")
	public Boolean getHasServiceStuff() {return theHasServiceStuff;}
	public void setHasServiceStuff(Boolean aHasServiceStuff) {theHasServiceStuff = aHasServiceStuff;}
	/** Есть обслуживающий персонал */
	private Boolean theHasServiceStuff;
	
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
		return "ГРУППОВАЯ: " + getGroupName();
	}
	/** Рабочие функции входящие в состав группы */
	private List<PersonalWorkFunction> theFunctions;

}
