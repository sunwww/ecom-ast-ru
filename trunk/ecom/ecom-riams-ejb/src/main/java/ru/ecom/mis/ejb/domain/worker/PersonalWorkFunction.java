package ru.ecom.mis.ejb.domain.worker;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;
/**
 * Персональная рабочая функция
 * @author stkacheva,azviagin
 *
 */
@Entity
@AIndexes({
	@AIndex(properties={"secUser"},table="WorkFunction")
	,@AIndex(properties={"worker"},table="WorkFunction")
	
})
@Table(schema="SQLUser")
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
		StringBuilder res = new StringBuilder() ;
		Patient pat = theWorker.getPerson() ;
		if (pat!=null) {
            add(res, pat.getLastname(),"");
            add(res, pat.getFirstname()," ");
            add(res, pat.getMiddlename()," ");
            add(res, pat.getSnils()," ");
		}
		//theWorker.getDoctorInfo() : "" ;
		return theWorker!=null ? theWorker.getDoctorInfo() : "" ;
	}
    private static void add(StringBuilder aSb, String aStr, String aPre) {
        if(!StringUtil.isNullOrEmpty(aStr)) {
            if(aSb.length()!=0) aSb.append(aPre) ;
            aSb.append(aStr) ;
        }
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
		return new StringBuilder().append("ПО СПЕЦИАЛИСТУ ").append(getWorkerInfo()).toString() ;
	}
	@Transient
	public String getGroupInfo() {
		return theGroup!=null? theGroup.getInfo():"" ;
	}
	@Transient
	public boolean getViewDirect() {
		return theGroup==null?true :false;
	}
	/** Групповая рабочая функция */
	private GroupWorkFunction theGroup;

}
