package ru.ecom.mis.ejb.domain.worker;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendar;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Место работы
 */
@Entity
@Comment("Рабочая функция")
@AIndexes({
	@AIndex(properties="archival")
	,@AIndex(properties="workFunction")
	//,@AIndex(properties={"archival","workFunction"})
	,@AIndex(properties={"code"})
})
@Table(schema="SQLUser")
abstract public class WorkFunction extends BaseEntity {

  

    /** Рабочий календарь */
	@Comment("Рабочий календарь")
	@OneToOne
	public WorkCalendar getWorkCalendar() {
		return theWorkCalendar;
	}

	public void setWorkCalendar(WorkCalendar aWorkCalendars) {
		theWorkCalendar = aWorkCalendars;
	}

	/** Рабочий календарь */
	private WorkCalendar theWorkCalendar;
  
    /** Наименование */
	@Comment("Наименование")
	@Transient
	public String getName() {
		return theWorkFunction!=null ? theWorkFunction.getName() : "" ;
	}

	
	@Transient @Comment("Информация")
	public String getWorkFunctionInfo() {
		return getName() ;
	}
	@Transient @Comment("Информация по коду ОМС врача")
	public String getOmcCodeInfo() {
		String ret="" ;
		if (theWorkFunction!=null && theWorkFunction.getVocPost()!=null
				&&theWorkFunction.getVocPost().getOmcDoctorPost()!=null) {
			ret = theWorkFunction.getVocPost().getOmcDoctorPost().getCode();
		}
		return ret ;
	}
	
	@Transient
	public MisLpu getLpuRegister() {
		return null ;
	}
	
	/** Функция */
	@Comment("Функция")
	@OneToOne
	public VocWorkFunction getWorkFunction() {
		return theWorkFunction;
	}

	public void setWorkFunction(VocWorkFunction aWorkFunction) {
		theWorkFunction = aWorkFunction;
	}

	/** Функция */
	private VocWorkFunction theWorkFunction;    
	
	/** Поместить в архив */
	@Comment("Поместить в архив")
	public Boolean getArchival() {
		return theArchival;
	}

	public void setArchival(Boolean aArchival) {
		theArchival = aArchival;
	}

	/** Поместить в архив */
	private Boolean theArchival;
	
	@Transient
	public String getWorkerLpuInfo() {
		return "";
	}
	@Transient 
	public String getInfo() {
		return "" ;
	}
	@Transient
	public boolean getViewDirect() {
		return true ;
	}
	@Transient
	public String getWorkerInfo() {
		return "" ;
	}
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@ManyToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}
	/** ЛПУ */
	private MisLpu theLpu;
	/** Код специалиста */
	@Comment("Код специалиста")
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** Код специалиста */
	private String theCode;

}
