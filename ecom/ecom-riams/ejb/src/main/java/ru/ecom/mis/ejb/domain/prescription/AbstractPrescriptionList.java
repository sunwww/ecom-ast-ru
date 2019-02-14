package ru.ecom.mis.ejb.domain.prescription;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Абстрактный лист назначений
 * @author azviagin
 *
 */
@Comment("Лист назначений")
@Entity
@Table(name="PrescriptionList",schema="SQLUser")
public abstract class AbstractPrescriptionList extends BaseEntity{

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Transient
	public VocServiceStream getServiceStream() {
		return getMedCase()!=null ? getMedCase().getServiceStream(): null;
	}
	
	/** Назначения */
	@Comment("Назначения")
	@OneToMany(mappedBy="prescriptionList", cascade=CascadeType.ALL)
	public List<Prescription> getPrescriptions() {return thePrescriptions;	}
	public void setPrescriptions(List<Prescription> aPrescriptions) {thePrescriptions = aPrescriptions;	}

	/** Название  */
	@Comment("Название шаблона")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Комментарии */
	@Comment("Комментарии")
	@Column(length = 32000)
	public String getComments() {return theComments;}
	public void setComments(String aComments) {theComments = aComments; }

	/** Владелец */
	@Comment("Владелец")
	@OneToOne
	public Worker getOwner() {return theOwner;}
	public void setOwner(Worker aOwner) {theOwner = aOwner;}

	@Comment("Владелец (инфо)")
	@Transient
	public String getOwnerInfo(){
		return getOwner()!=null?getOwner().getDoctorInfo():"" ;
	}
	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@OneToOne
	public MedCase getMedCase() {
		return theMedCase;
	}

	public void setMedCase(MedCase aMedCase) {
		theMedCase = aMedCase;
	}

	/** Тип назначения */
	@Comment("Тип назначения")
	@OneToOne
	public VocPrescriptType getPrescriptType() {
		return thePrescriptType;
	}

	public void setPrescriptType(VocPrescriptType aPrescriptType) {
		thePrescriptType = aPrescriptType;
	}
	
	@Transient
	@Comment("Тип назначения")
	public String getPrescriptTypeInfo() {
		return thePrescriptType!=null?thePrescriptType.getName():"" ;
	}
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}

	@Transient
	public String getWorkFunctionInfo() {
		return theWorkFunction!=null ? theWorkFunction.getWorkFunctionInfo() : "" ;
	}
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;	
	/** Рабочая функция */
	private WorkFunction theWorkFunction;
	/** Тип назначения */
	private VocPrescriptType thePrescriptType;
	/** Случай медицинского обслуживания */
	private MedCase theMedCase;
	/** Владелец */
	private Worker theOwner;
	/** Комментарии */
	private String theComments;
	/** Название  */
	private String theName;
	/** Назначения */
	private List<Prescription> thePrescriptions;
}
