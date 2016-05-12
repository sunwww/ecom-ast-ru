package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesia;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesiaMethod;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Анестезия
 * @author azviagin
 *
 */

@Comment("Анестезия")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="surgicalOperation")
    }) 
public class Anesthesia extends BaseEntity{

	/** Метод */
	@Comment("Метод")
	@OneToOne
	public VocAnesthesiaMethod getMethod() {
		return theMethod;
	}

	public void setMethod(VocAnesthesiaMethod aMethod) {
		theMethod = aMethod;
	}

	/** Метод */
	private VocAnesthesiaMethod theMethod;
	
	/** Вид анестезии */
	@Comment("Вид анестезии")
	@OneToOne
	public VocAnesthesia getType() {return theType;}
	public void setType(VocAnesthesia aType) {theType = aType;}

	/** Вид анестезии */
	private VocAnesthesia theType;
	/** Длительность в минутах */
	@Comment("Длительность в минутах")
	public Integer getDuration() {return theDuration;}
	public void setDuration(Integer aDuration) {theDuration = aDuration;}

	/** Длительность в минутах */
	private Integer theDuration;
	
	/** Описание */
	@Comment("Описание")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getDescription() {return theDescription;}
	public void setDescription(String aDescription) {theDescription = aDescription;}

	/** Описание */
	private String theDescription;
	
	/** Хирургическая операция */
	@Comment("Хирургическая операция")
	@ManyToOne
	public SurgicalOperation getSurgicalOperation() {return theSurgicalOperation;}
	public void setSurgicalOperation(SurgicalOperation aSurgicalOperation) {theSurgicalOperation = aSurgicalOperation;}

	/** Хирургическая операция */
	private SurgicalOperation theSurgicalOperation;
	
	/** Анестезиолог */
	@Comment("Анестезист")
	@OneToOne
	public WorkFunction getAnesthesist() {return theAnesthesist;}
	public void setAnesthesist(WorkFunction aAnesthesist) {theAnesthesist = aAnesthesist;}

	/** Анестезиолог */
	private WorkFunction theAnesthesist;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}

	/** Пользователь создавший запись */
	@Comment("Пользователь создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}

	/** Пользователь создавший запись */
	private String theCreateUsername;
	/** Дата создания */
	private Date theCreateDate;

	@Transient
	public String getMethodInfo() {
		return theMethod!=null?theMethod.getName():"" ;
	}
	@Transient
	public String getAnesthesistInfo() {
		return theAnesthesist != null ? theAnesthesist.getWorkerInfo():"" ;
	}
	
	/** Мед.услуга */
	@Comment("Мед.услуга")
	@OneToOne
	public MedService getMedService() {
		return theMedService;
	}

	public void setMedService(MedService aMedService) {
		theMedService = aMedService;
	}

	/** Мед.услуга */
	private MedService theMedService;
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}

	/** Время создания */
	private Time theCreateTime;
}
