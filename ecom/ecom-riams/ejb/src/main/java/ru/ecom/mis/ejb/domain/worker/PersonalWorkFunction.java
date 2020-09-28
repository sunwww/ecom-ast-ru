package ru.ecom.mis.ejb.domain.worker;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
/**
 * Персональная рабочая функция
 * @author stkacheva,azviagin
 *
 */
@Entity
@AIndexes({
	@AIndex(properties={"secUser"},table="WorkFunction")
	,@AIndex(properties={"worker"},table="WorkFunction")
	,@AIndex(properties={"group"},table="WorkFunction")
	
})
public class PersonalWorkFunction extends WorkFunction {
	/** Сотрудник */
	@Comment("Сотрудник")
	@ManyToOne	
	public Worker getWorker() {return theWorker;}
	public void setWorker(Worker aWorkers) {theWorker = aWorkers;}
	
	/** Пользователь системы */
	@Comment("Пользователь системы")
	@OneToOne
	public SecUser getSecUser() {return theSecUser;}
	public void setSecUser(SecUser aSecUser) {theSecUser = aSecUser;
	}


	/** Пользователь системы */
	private SecUser theSecUser;
	
	@Transient
	public String getWorkerLpuInfo() {
		return (theWorker!=null&&theWorker.getLpu()!=null)? theWorker.getLpu().getFullname() :"";
	}
	
	@Transient
	public MisLpu getLpuRegister() {
		return theWorker!=null? theWorker.getLpu(): null ;
	}

	/** Сотрудник */
	private Worker theWorker;
	@Transient @Comment("Информация о работнике")
	public String getWorkerInfo() {
		return theWorker!=null ? theWorker.getDoctorInfo() : "" ;
	}

	@Transient @Comment("Информация")
	public String getWorkFunctionInfo() {
		return getName() + " " + getWorkerInfo();
	}
	/** Групповая рабочая функция */
	@Comment("Групповая рабочая функция")
	@ManyToOne
	public GroupWorkFunction getGroup() {return theGroup;}
	public void setGroup(GroupWorkFunction aGroup) {theGroup = aGroup;}

	@Transient
	public String getInfo() {
		return "ПО СПЕЦИАЛИСТУ "+getWorkerInfo();
	}
	@Transient
	public String getGroupInfo() {
		return theGroup!=null? theGroup.getInfo():"" ;
	}
	@Transient
	public boolean getViewDirect() {
		return theGroup==null;
	}
	
	/** Групповая рабочая функция */
	private GroupWorkFunction theGroup;

}
