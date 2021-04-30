package ru.ecom.mis.ejb.domain.report;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Comment("Агрегаты по стационару")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class AggregateHospitalReport extends BaseEntity {
	/** Дата выписки на 9 часов */
	private Date dischargeDate9;
	/** Дата поступления на 9 часов */
	private Date entranceDate9;
	/** Экстренное поступление */
	private boolean isEmergency;
	/** Возраст на конец СЛС */
	private Long ageDischargeSls;
	/** Возраст на начало СЛС */
	private Long ageEntranceSls;
	/** Возраст на конец СЛО */
	private Long ageDischargeSlo;
	/** Возраст на начало СЛО */
	private Long ageEntranceSlo;
	/** Отделение перевода */
	private Long transferDepartmentIn;
	/** Отделение */
	private Long department;
	/** Дата выписки */
	private Date dischargeDate7;
	/** Дата поступления */
	private Date entranceDate7;
	/** Пациент */
	private Long patient;
	/** СЛО */
	private Long slo;
	/** Госпитализация */
	private Long sls;
	/** Тип коек */
	private Long bedSubType;
	/** Профиль */
	private Long bedType;
	/** Поток обслуживания */
	private Long serviceStream;
	/** Кол-во койко дней по СЛС */
	private Long cntDaysSls;
	/** Недееспособный */
	private boolean isIncompetent;
	/** Впервые в жизни */
	private boolean isFirstLife;
	/** Впервые в данном году */
	private boolean isFirstCurrentYear;
	/** Перевод в ЛПУ */
	private String transferLpuCode;
	/** Операция в отделение */
	private boolean isOperation;
	/** Умер */
	private boolean isDeath;
	/** Диагноз */
	private String idcEntranceCode;
	/** Сельский житель */
	private boolean isVillage;
	/** Дата выписки круглосуточно */
	private Date dischargeDate24;
	/** Дата поступление круглосуточно */
	private Date entranceDate24;
	/** Пол */
	private String sexCode;
	/** Диагноз выписной */
	private String idcDischarge;
	/** Диагноз клинический (по отделению) */
	private String idcDepartmentCode;
	/** Перевели из отделения */
	private Long transferDepartmentFrom;
	/** Тип стационара */
	private Long hospType;
	/** Пол ид */
	private Long sex;
	/** Переводной диагноз */
	private String idcTransferCode;
	/** Дата поступления в стационар 9 */
	private Date entranceHospDate9;
	/** Дата поступления в стационар 7 */
	private Date entranceHospDate7;
	/** Дата поступлание в стационар 24 */
	private Date entranceHospDate24;
	/** Доп. койко день */
	private Long addBedDays;
	/** Дата рождения */
	private Date birthday;
	
}
