package ru.ecom.mis.ejb.domain.worker;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class PersonalWorkFunction extends WorkFunction {
	/** Сотрудник */
	@Comment("Сотрудник")
	@ManyToOne	
	public Worker getWorker() {return worker;}

	/** Пользователь системы */
	@Comment("Пользователь системы")
	@OneToOne
	public SecUser getSecUser() {return secUser;}

	/** Пользователь системы */
	private SecUser secUser;
	
	@Transient
	public String getWorkerLpuInfo() {
		return (worker!=null&&worker.getLpu()!=null)? worker.getLpu().getFullname() :"";
	}
	
	@Transient
	public MisLpu getLpuRegister() {
		return worker!=null? worker.getLpu(): null ;
	}

	/** Сотрудник */
	private Worker worker;
	@Transient @Comment("Информация о работнике")
	public String getWorkerInfo() {
		return worker!=null ? worker.getDoctorInfo() : "" ;
	}

	@Transient @Comment("Информация")
	public String getWorkFunctionInfo() {
		return getName() + " " + getWorkerInfo();
	}
	/** Групповая рабочая функция */
	@Comment("Групповая рабочая функция")
	@ManyToOne
	public GroupWorkFunction getGroup() {return group;}

	@Transient
	public String getInfo() {
		return "ПО СПЕЦИАЛИСТУ "+getWorkerInfo();
	}
	@Transient
	public String getGroupInfo() {
		return group!=null? group.getInfo():"" ;
	}
	@Transient
	public boolean getViewDirect() {
		return group==null;
	}
	
	/** Групповая рабочая функция */
	private GroupWorkFunction group;

}
