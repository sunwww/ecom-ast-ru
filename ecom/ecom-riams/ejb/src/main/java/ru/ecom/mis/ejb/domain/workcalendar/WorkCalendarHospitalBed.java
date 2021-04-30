package ru.ecom.mis.ejb.domain.workcalendar;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.lpu.HospitalBed;
import ru.ecom.mis.ejb.domain.lpu.HospitalRoom;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocIndicationHospitalization;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties={"hospitalBed"}) 
		, @AIndex(properties={"visit"}) 
		, @AIndex(properties={"medCase"}) 
		})
@Getter
@Setter
public class WorkCalendarHospitalBed extends BaseEntity {

	/** Внутренний номер направлания */
	private String internalCode ;

	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {return department;}

	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}

	/** Палата */
	@Comment("Палата")
	@OneToOne
	public HospitalRoom getHospitalRoom() {return hospitalRoom;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return serviceStream;}

	/** Примечание */
	@Comment("Примечание")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getComment() {return comment;}


	/** Диагноз */
	@Comment("Диагноз")
	@OneToOne
	public VocIdc10 getIdc10() {return idc10;}

	/** Фактическая госпитализация */
	@Comment("Фактическая госпитализация")
	@OneToOne
	public MedCase getMedCase() {return medCase;}

	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {return sex;}

	/** Койка */
	@Comment("Койка")
	@OneToOne
	public HospitalBed getHospitalBed() {return hospitalBed;}

	/** Койка */
	private HospitalBed hospitalBed;
	/** Пол */
	private VocSex sex;
	/** Телефон пациента */
	private String phone;
	/** ФИО пациента */
	private String fio;
	/** Предполагаемое количество дней госпитализации */
	private Long cntDays;
	/** Предполагаемая дата окончания госпитализации */
	private Date dateTo;
	/** Предполагаемая дата начала госпитализации */
	private Date dateFrom;
	/** Фактическая госпитализация */
	private MedCase medCase;
	/** Текст диагноза */
	private String diagnosis;
	/** Диагноз */
	private VocIdc10 idc10;
	/** Предполагается операция */
	private Boolean isOperation;
	/** Примечание */
	private String comment;
	/** Поток обслуживания */
	private VocServiceStream serviceStream;
	/** Палата */
	private HospitalRoom hospitalRoom;
	/** Пациент */
	private Patient patient;
	/** Отделение */
	private MisLpu department;
	
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getVisit() {
		return visit;
	}

	/** СМО */
	private MedCase visit;

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
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {return workFunction;}

	/** Рабочая функция */
	private WorkFunction workFunction;
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@OneToOne
	public VocBedType getBedType() {return bedType;}

	/** Тип коек */
	@Comment("Тип коек")
	@OneToOne
	public VocBedSubType getBedSubType() {return bedSubType;}

	/** Тип коек */
	private VocBedSubType bedSubType;
	/** Профиль коек */
	private VocBedType bedType;
	
	/** Откуда направление */
	@Comment("Откуда направление")
	@OneToOne
	public MisLpu getOrderLpu() {
		return orderLpu;
	}
	/** Откуда направление */
	private MisLpu orderLpu;

	/** ЛПУ куда направляется */
	@Comment("ЛПУ куда направляется")
	@OneToOne
	public MisLpu getDirectLpu() {return directLpu;}
	/** ЛПУ куда направляется */
	private MisLpu directLpu ;
	
	/** Показания для госпитализации */
	@Comment("Показания для госпитализации")
	@OneToOne
	public VocIndicationHospitalization getIndicationToHosp() {return indicationToHosp;}
	/** Показания для госпитализации */
	private VocIndicationHospitalization indicationToHosp;
}