package ru.ecom.mis.ejb.domain.prescription;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
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
	public List<Prescription> getPrescriptions() {return prescriptions;	}

	/** Комментарии */
	@Comment("Комментарии")
	@Column(length = 32000)
	public String getComments() {return comments;}

	/** Владелец */
	@Comment("Владелец")
	@OneToOne
	public Worker getOwner() {return owner;}

	@Comment("Владелец (инфо)")
	@Transient
	public String getOwnerInfo(){
		return getOwner()!=null?getOwner().getDoctorInfo():"" ;
	}
	/** Случай медицинского обслуживания */
	@Comment("Случай медицинского обслуживания")
	@OneToOne
	public MedCase getMedCase() {
		return medCase;
	}

	/** Тип назначения */
	@Comment("Тип назначения")
	@OneToOne
	public VocPrescriptType getPrescriptType() {
		return prescriptType;
	}

	@Transient
	@Comment("Тип назначения")
	public String getPrescriptTypeInfo() {
		return prescriptType!=null?prescriptType.getName():"" ;
	}
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {return workFunction;}

	@Transient
	public String getWorkFunctionInfo() {
		return workFunction!=null ? workFunction.getWorkFunctionInfo() : "" ;
	}
	
	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private Time editTime;
	/** Время создания */
	private Time createTime;
	/** Дата редактирования */
	private Date editDate;
	/** Дата создания */
	private Date createDate;	
	/** Рабочая функция */
	private WorkFunction workFunction;
	/** Тип назначения */
	private VocPrescriptType prescriptType;
	/** Случай медицинского обслуживания */
	private MedCase medCase;
	/** Владелец */
	private Worker owner;
	/** Комментарии */
	private String comments;
	/** Название  */
	private String name;
	/** Назначения */
	private List<Prescription> prescriptions;
}
