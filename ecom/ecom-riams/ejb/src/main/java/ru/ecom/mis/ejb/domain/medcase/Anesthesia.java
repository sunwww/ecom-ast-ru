package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesia;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesiaMethod;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Анестезия
 * @author azviagin
 *
 */

@Comment("Анестезия")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="surgicalOperation"),
	@AIndex(properties="manipulation")
    }) 
public class Anesthesia extends BaseEntity{

	/** Метод */
	@Comment("Метод")
	@OneToOne
	public VocAnesthesiaMethod getMethod() {return theMethod;}
	public void setMethod(VocAnesthesiaMethod aMethod) {theMethod = aMethod;}
	private VocAnesthesiaMethod theMethod;
	
	/** Вид анестезии */
	@Comment("Вид анестезии")
	@OneToOne
	public VocAnesthesia getType() {return theType;}
	public void setType(VocAnesthesia aType) {theType = aType;}
	private VocAnesthesia theType;

	/** Длительность в минутах */
	@Comment("Длительность в минутах")
	public Integer getDuration() {return theDuration;}
	public void setDuration(Integer aDuration) {theDuration = aDuration;}
	private Integer theDuration;
	
	/** Описание */
	@Comment("Описание")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getDescription() {return theDescription;}
	public void setDescription(String aDescription) {theDescription = aDescription;}
	private String theDescription;
	
	/** Хирургическая операция */
	@Comment("Хирургическая операция")
	@ManyToOne
	public SurgicalOperation getSurgicalOperation() {return theSurgicalOperation;}
	public void setSurgicalOperation(SurgicalOperation aSurgicalOperation) {theSurgicalOperation = aSurgicalOperation;}
	private SurgicalOperation theSurgicalOperation;

	/* Манипуляция*/
	@Comment("Манипуляция")
	@ManyToOne
	public MedicalManipulation getManipulation() {return theManipulation;}
	public void setManipulation(MedicalManipulation aManipulation) {theManipulation = aManipulation;}
	private MedicalManipulation theManipulation;

	/** Анестезиолог */
	@Comment("Анестезист")
	@OneToOne
	public WorkFunction getAnesthesist() {return theAnesthesist;}
	public void setAnesthesist(WorkFunction aAnesthesist) {theAnesthesist = aAnesthesist;}
	private WorkFunction theAnesthesist;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	private Date theCreateDate;

	/** Пользователь создавший запись */
	@Comment("Пользователь создавший запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	private String theCreateUsername;

	@Transient
	public String getMethodInfo() {return theMethod!=null?theMethod.getName():"" ;}
	@Transient
	public String getAnesthesistInfo() {return theAnesthesist != null ? theAnesthesist.getWorkerInfo():"" ;}
	
	/** Мед.услуга */
	@Comment("Мед.услуга")
	@OneToOne
	public MedService getMedService() {return theMedService;}
	public void setMedService(MedService aMedService) {theMedService = aMedService;}
	private MedService theMedService;
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	private Time theCreateTime;
}