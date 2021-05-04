package ru.ecom.mis.ejb.form.workcalendar;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarHospitalBed;
import ru.ecom.mis.ejb.form.workcalendar.interceptor.WorkCalendarHospitalBedSave;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

@EntityForm
@EntityFormPersistance(clazz = WorkCalendarHospitalBed.class)
@Comment("Планирование госпитализации")
@WebTrail(comment = "Планирование госпитализации", nameProperties= "id"
, list="stac_planning_hospitalizations.do"
, view="entityView-stac_planHospital.do")
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Planning")
@ACreateInterceptors(
		@AEntityFormInterceptor(WorkCalendarHospitalBedSave.class)
)
@Setter
public class WorkCalendarHospitalBedForm extends IdEntityForm {

	/** Внутренний номер направления */
	@Comment("Внутренний номер направления")
	@Persist
	public String getInternalCode() {return internalCode;}
	/** Внутренний номер направления */
	private String internalCode ;

	/** Отделение */
	@Comment("Отделение")
	@Persist @Required
	public Long getDepartment() {return department;}

	/** Пациент */
	@Comment("Пациент")
	@Persist @Required
	public Long getPatient() {return patient;}

	/** Палата */
	@Comment("Палата")
	@Persist
	public Long getHospitalRoom() {return hospitalRoom;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@Persist @Required
	public Long getServiceStream() {return serviceStream;}

	/** Примечание */
	@Comment("Примечание")
	@Persist
	public String getComment() {return comment;}

	/** Предполагается операция */
	@Comment("Предполагается операция")
	@Persist
	public Boolean getIsOperation() {return isOperation;}

	/** Диагноз */
	@Comment("Диагноз")
	@Persist @Required
	public Long getIdc10() {return idc10;}

	/** Текст диагноза */
	@Comment("Текст диагноза")
	@Persist
	public String getDiagnosis() {return diagnosis;}

	/** Фактическая госпитализация */
	@Comment("Фактическая госпитализация")
	@Persist
	public Long getMedCase() {return medCase;}

	/** Предполагаемая дата начала госпитализации */
	@Comment("Предполагаемая дата начала госпитализации")
	@Persist @DateString @DoDateString @Required
	public String getDateFrom() {return dateFrom;}

	/** Предполагаемая дата окончания госпитализации */
	@Comment("Предсполагаемая дата окончания госпитализации")
	@Persist @DateString @DoDateString
	public String getDateTo() {return dateTo;}

	/** Предполагаемое количество дней госпитализации */
	@Comment("Предполагаемое количество дней госпитализации")
	@Persist
	public Long getCntDays() {return cntDays;}

	/** ФИО пациента */
	@Comment("ФИО пациента")
	@Persist
	public String getFio() {return fio;}

	/** Телефон пациента */
	@Comment("Телефон пациента")
	@Persist @Required
	public String getPhone() {return phone;}

	/** Пол */
	@Comment("Пол")
	@Persist
	public Long getSex() {return sex;}

	/** Койка */
	@Comment("Койка")
	@Persist
	public Long getHospitalBed() {return hospitalBed;}

	/** СМО */
	@Comment("СМО")
	@Persist 
	public Long getVisit() {return visit;}

	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return createDate;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return editDate;}

	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return createTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return editTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist 
	public Long getWorkFunction() {return workFunction;}

	/** Рабочая функция */
	private Long workFunction;
	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private String editTime;
	/** Время создания */
	private String createTime;
	/** Дата редактирования */
	private String editDate;
	/** Дата создания */
	private String createDate;
	/** СМО */
	private Long visit;	
	/** Койка */
	private Long hospitalBed;
	/** Пол */
	private Long sex;
	/** Телефон пациента */
	private String phone;
	/** ФИО пациента */
	private String fio;
	/** Предполагаемое количество дней госпитализации */
	private Long cntDays;
	/** Предполагаемая дата окончания госпитализации */
	private String dateTo;
	/** Предполагаемая дата начала госпитализации */
	private String dateFrom;
	/** Фактическая госпитализация */
	private Long medCase;
	/** Текст диагноза */
	private String diagnosis;
	/** Диагноз */
	private Long idc10;
	/** Предполагается операция */
	private Boolean isOperation;
	/** Примечание */
	private String comment;
	/** Поток обслуживания */
	private Long serviceStream;
	/** Палата */
	private Long hospitalRoom;
	/** Пациент */
	private Long patient;
	/** Отделение */
	private Long department;
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@Persist @Required
	public Long getBedType() {return bedType;}

	/** Тип коек */
	@Comment("Тип коек")
	@Persist @Required
	public Long getBedSubType() {return bedSubType;}

	/** Тип коек */
	private Long bedSubType;
	/** Профиль коек */
	private Long bedType;	
	
	/** Откуда направление */
	@Comment("Откуда направление")
	@Persist @Required
	public Long getOrderLpu() {return orderLpu;}
	/** Откуда направление */
	private Long orderLpu;

	/** ЛПУ куда направляется */
	@Comment("ЛПУ куда направляется")
	@Persist
	public Long getDirectLpu() {return directLpu;}
	/** ЛПУ куда направляется */
	private Long directLpu ;

}
