package ru.ecom.mis.ejb.domain.psychiatry;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Принудительное лечение")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class CompulsoryTreatmentAggregate extends BaseEntity {
	/** Дата поступления в стационар по прин.лечению */
	private Date entranceHospDate;

	/** Дата выбытия/перевода из отделения */
	private Date dischargeDepDate;

	/** Дата поступления в отделение */
	private Date entranceDepDate;
	

	/** Дата выписки в стационар по прин.лечению */
	private Date dischargeHospDate;
	

	/** Дата рождения */
	private Date birthday;
	
	/** Пол */
	private String sexCode;

	/** Диагноз выписной */
	private String idcDischarge;
	/** Диагноз клинический (по отделению) */
	private String idcDepartmentCode;
	
	/** Перевели из отделения */
	private Long transferDepartmentFrom;
	
	/** Переводной диагноз */
	private String idcTransferCode;

	/** Умер */
	private boolean isDeath;
	/** Пациент */
	private Long patient;
	/** СЛО */
	private Long slo;
	/** Госпитализация */
	private Long sls;
	/** Отделение перевода */
	private Long transferDepartmentIn;
	/** Отделение */
	private Long department;
	/** Номер принуд. лечения */
	private String orderCompTr;

	/** Впервые по данному УД */
	private Boolean isFirstByCrimCase;

	/** В связи с изменением вида ПЛ по данному УД (при поступлению) */
	private Boolean isChangeTypeByCrimCase;
	
	/** Переведен на АПНЛ после госпитализации */
	private Boolean isAfterAPNL;

	/** Прекращено лечение */
	private Boolean isFinishCompTreat;

	/** Прекращено в связи с изменением вида принуд. лечения */
	private Boolean isFinishWithChangeType;
	
	/** Переведен с АПНЛ */
	private Boolean isBeforeAPNL;

	/** Состоял ли на динамике, когда человек совершил ООД */
	private Boolean isOodDynamic;
	
	/** Ранее находились на принуд. лечение */
	private Boolean isPrevComTreat;

	/** Кол-во дней после последнего принуд. лечения */
	private Long cntDaysFromPrevCase;

	/** Кол-во дней всего принуд. лечения */
	private Long cntDaysCompTreat;

	/** Порядковый номер госпитализации */
	private Long numberHosp;
	/** Диагноз при поступлении */
	private String idcEntranceCode ;
	/** Возраст на конец СЛС */
	private Long ageDischargeSls;
	/** Возраст на начало СЛС */
	private Long ageEntranceSls;
	/** Возраст на конец СЛО */
	private Long ageDischargeSlo;
	/** Возраст на начало СЛО */
	private Long ageEntranceSlo;
}