package ru.ecom.mis.ejb.domain.psychiatry;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Принудительное лечение")
@Entity
/*@AIndexes({
	@AIndex(properties={"careCard"})
})*/
@Table(schema="SQLUser")
public class CompulsoryTreatmentAggregate extends BaseEntity {
	/** Дата поступления в стационар по прин.лечению */
	@Comment("Дата поступления в стационар по прин.лечению")
	public Date getEntranceHospDate(){return theEntranceHospDate;}
	public void setEntranceHospDate(Date aEntranceHospDate) {theEntranceHospDate = aEntranceHospDate;}

	/** Дата поступления в стационар по прин.лечению */
	private Date theEntranceHospDate;
	
	/** Дата выбытия/перевода из отделения */
	@Comment("Дата выбытия/перевода из отделения")
	public Date getDischargeDepDate() {
		return theDischargeDepDate;
	}

	public void setDischargeDepDate(Date aDischargeDepDate) {
		theDischargeDepDate = aDischargeDepDate;
	}

	/** Дата выбытия/перевода из отделения */
	private Date theDischargeDepDate;
	/** Дата поступления в отделение */
	@Comment("Дата поступления в отделение")
	public Date getEntranceDepDate() {
		return theEntranceDepDate;
	}

	public void setEntranceDepDate(Date aEntranceDepDate) {
		theEntranceDepDate = aEntranceDepDate;
	}

	/** Дата поступления в отделение */
	private Date theEntranceDepDate;
	
	/** Дата выписки в стационар по прин.лечению */
	@Comment("Дата выписки в стационар по прин.лечению")
	public Date getDischargeHospDate() {return theDischargeHospDate;}
	public void setDischargeHospDate(Date aDischargeHospDate) {theDischargeHospDate = aDischargeHospDate;}

	/** Дата выписки в стационар по прин.лечению */
	private Date theDischargeHospDate;
	
	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getBirthday() {return theBirthday;}
	public void setBirthday(Date aBirthday) {theBirthday = aBirthday;}

	/** Дата рождения */
	private Date theBirthday;
	
	/** Пол */
	@Comment("Пол")
	public String getSexCode() {return theSexCode;}
	public void setSexCode(String aSexCode) {theSexCode = aSexCode;}

	/** Пол */
	private String theSexCode;
	
	/** Диагноз клинический (по отделению) */
	@Comment("Диагноз клинический (по отделению)")
	public String getIdcDepartmentCode() {return theIdcDepartmentCode;}
	public void setIdcDepartmentCode(String aIdcDepartmentCode) {theIdcDepartmentCode = aIdcDepartmentCode;}

	/** Диагноз выписной */
	@Comment("Диагноз выписной")
	public String getIdcDischarge() {return theIdcDischarge;}
	public void setIdcDischarge(String aIdcDischarge) {theIdcDischarge = aIdcDischarge;}

	/** Диагноз выписной */
	private String theIdcDischarge;
	/** Диагноз клинический (по отделению) */
	private String theIdcDepartmentCode;
	
	/** Перевели из отделения */
	@Comment("Перевели из отделения")
	public Long getTransferDepartmentFrom() {return theTransferDepartmentFrom;}
	public void setTransferDepartmentFrom(Long aDepartmentTransferFrom) {theTransferDepartmentFrom = aDepartmentTransferFrom;}

	/** Перевели из отделения */
	private Long theTransferDepartmentFrom;
	
	/** Переводной диагноз */
	@Comment("Переводной диагноз")
	public String getIdcTransferCode() {return theIdcTransferCode;}
	public void setIdcTransferCode(String aIdcTransferCode) {theIdcTransferCode = aIdcTransferCode;}
	
	/** Переводной диагноз */
	private String theIdcTransferCode;
	
	/** Умер */
	@Comment("Умер")
	public boolean getIsDeath() {return theIsDeath;}
	public void setIsDeath(boolean aIsDeath) {theIsDeath = aIsDeath;}

	

	/** Умер */
	private boolean theIsDeath;
	
	
	
	/** Госпитализация */
	@Comment("Госпитализация")
	public Long getSls() {return theSls;}
	public void setSls(Long aSls) {theSls = aSls;}

	/** СЛО */
	@Comment("СЛО")
	public Long getSlo() {return theSlo;}
	public void setSlo(Long aSlo) {theSlo = aSlo;}

	/** Пациент */
	@Comment("Пациент")
	public Long getPatient() {return thePatient;}
	public void setPatient(Long aPatient) {thePatient = aPatient;}

	/** Пациент */
	private Long thePatient;
	/** СЛО */
	private Long theSlo;
	/** Госпитализация */
	private Long theSls;
	

	/** Отделение */
	@Comment("Отделение")
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Отделение перевода */
	@Comment("Отделение перевода")
	public Long getTransferDepartmentIn() {return theTransferDepartmentIn;}
	public void setTransferDepartmentIn(Long aDepartmentTransfer) {theTransferDepartmentIn = aDepartmentTransfer;}

	
	/** Отделение перевода */
	private Long theTransferDepartmentIn;
	/** Отделение */
	private Long theDepartment;
	
	/** Номер принуд. лечения */
	@Comment("Номер принуд. лечения")
	public String getOrderCompTr() {return theOrderCompTr;}
	public void setOrderCompTr(String aOrderCompTr) {theOrderCompTr = aOrderCompTr;}

	/** Номер принуд. лечения */
	private String theOrderCompTr;
	
	/** Впервые по данному УД */
	@Comment("Впервые по данному УД")
	public Boolean getIsFirstByCrimCase() {return theIsFirstByCrimCase;}
	public void setIsFirstByCrimCase(Boolean aIsFirstByCrimCase) {theIsFirstByCrimCase = aIsFirstByCrimCase;}

	/** Впервые по данному УД */
	private Boolean theIsFirstByCrimCase;
	
	/** В связи с изменением вида ПЛ по данному УД (при поступлению) */
	@Comment("В связи с изменением вида ПЛ по данному УД (при поступлению)")
	public Boolean getIsChangeTypeByCrimCase() {return theIsChangeTypeByCrimCase;}
	public void setIsChangeTypeByCrimCase(Boolean aIsChangeTypeByCrimCase) {theIsChangeTypeByCrimCase = aIsChangeTypeByCrimCase;}

	/** В связи с изменением вида ПЛ по данному УД (при поступлению) */
	private Boolean theIsChangeTypeByCrimCase;
	
	
	/** Переведен на АПНЛ после госпитализации */
	@Comment("Переведен на АПНЛ после госпитализации")
	public Boolean getIsAfterAPNL() {return theIsAfterAPNL;}
	public void setIsAfterAPNL(Boolean aIsAfterAPNL) {theIsAfterAPNL = aIsAfterAPNL;}

	/** Переведен на АПНЛ после госпитализации */
	private Boolean theIsAfterAPNL;
	
	/** Прекращено лечение */
	@Comment("Прекращено лечение")
	public Boolean getIsFinishCompTreat() {return theIsFinishCompTreat;}
	public void setIsFinishCompTreat(Boolean aIsFinishCompTreat) {theIsFinishCompTreat = aIsFinishCompTreat;}

	/** Прекращено лечение */
	private Boolean theIsFinishCompTreat;
	
	/** Прекращено в связи с изменением вида принуд. лечения */
	@Comment("Прекращено в связи с изменением вида принуд. лечения")
	public Boolean getIsFinishWithChangeType() {return theIsFinishWithChangeType;}
	public void setIsFinishWithChangeType(Boolean aIsFinishWithChangeType) {theIsFinishWithChangeType = aIsFinishWithChangeType;}

	/** Прекращено в связи с изменением вида принуд. лечения */
	private Boolean theIsFinishWithChangeType;
	
	/** Переведен с АПНЛ */
	@Comment("Переведен с АПНЛ")
	public Boolean getIsBeforeAPNL() {return theIsBeforeAPNL;}
	public void setIsBeforeAPNL(Boolean aIsBeforeAPNL) {theIsBeforeAPNL = aIsBeforeAPNL;}

	/** Переведен с АПНЛ */
	private Boolean theIsBeforeAPNL;
	
	/** Состоял ли на динамике, когда человек совершил ООД */
	@Comment("Состоял ли на динамике, когда человек совершил ООД")
	public Boolean getIsOodDynamic() {return theIsOodDynamic;}
	public void setIsOodDynamic(Boolean aIsOodDynamic) {theIsOodDynamic = aIsOodDynamic;}

	/** Состоял ли на динамике, когда человек совершил ООД */
	private Boolean theIsOodDynamic;
	
	/** Ранее находились на принуд. лечение */
	@Comment("Ранее находились на принуд. лечение")
	public Boolean getIsPrevComTreat() {return theIsPrevComTreat;}
	public void setIsPrevComTreat(Boolean aIsPrevComTreat) {theIsPrevComTreat = aIsPrevComTreat;}

	/** Ранее находились на принуд. лечение */
	private Boolean theIsPrevComTreat;
	
	/** Кол-во дней после последнего принуд. лечения */
	@Comment("Кол-во дней после последнего принуд. лечения")
	public Long getCntDaysFromPrevCase() {return theCntDaysFromPrevCase;}
	public void setCntDaysFromPrevCase(Long aCntDaysFromPrevCase) {theCntDaysFromPrevCase = aCntDaysFromPrevCase;}

	/** Кол-во дней после последнего принуд. лечения */
	private Long theCntDaysFromPrevCase;
	
	/** Кол-во дней всего принуд. лечения */
	@Comment("Кол-во дней всего принуд. лечения")
	public Long getCntDaysCompTreat() {return theCntDaysCompTreat;}
	public void setCntDaysCompTreat(Long aCntDaysCompTreat) {theCntDaysCompTreat = aCntDaysCompTreat;}

	/** Кол-во дней всего принуд. лечения */
	private Long theCntDaysCompTreat;
	
	/** Порядковый номер госпитализации */
	@Comment("Порядковый номер госпитализации")
	public Long getNumberHosp() {
		return theNumberHosp;
	}

	public void setNumberHosp(Long aNumberHosp) {
		theNumberHosp = aNumberHosp;
	}

	/** Порядковый номер госпитализации */
	private Long theNumberHosp;
	/** Диагноз */
	@Comment("Диагноз при поступлении")
	public String getIdcEntranceCode() {return theIdcEntranceCode;}
	public void setIdcEntranceCode(String aIdcCode) {theIdcEntranceCode = aIdcCode;}
	/** Диагноз при поступлении */
	private String theIdcEntranceCode ;
	
	/** Возраст на начало СЛО */
	@Comment("Возраст на начало СЛО")
	public Long getAgeEntranceSlo() {return theAgeEntranceSlo;}
	public void setAgeEntranceSlo(Long aAgeEntranceSlo) {theAgeEntranceSlo = aAgeEntranceSlo;}

	/** Возраст на конец СЛО */
	@Comment("Возраст на конец СЛО")
	public Long getAgeDischargeSlo() {return theAgeDischargeSlo;}
	public void setAgeDischargeSlo(Long aAgeDischargeSlo) {theAgeDischargeSlo = aAgeDischargeSlo;}

	/** Возраст на начало СЛС */
	@Comment("Возраст на начало СЛС")
	public Long getAgeEntranceSls() {return theAgeEntranceSls;}
	public void setAgeEntranceSls(Long aAgeEntranceSls) {theAgeEntranceSls = aAgeEntranceSls;}

	/** Возраст на конец СЛС */
	@Comment("Возраст на конец СЛС")
	public Long getAgeDischargeSls() {return theAgeDischargeSls;}
	public void setAgeDischargeSls(Long aAgeDischargeSls) {theAgeDischargeSls = aAgeDischargeSls;}
	
	/** Возраст на конец СЛС */
	private Long theAgeDischargeSls;
	/** Возраст на начало СЛС */
	private Long theAgeEntranceSls;
	/** Возраст на конец СЛО */
	private Long theAgeDischargeSlo;
	/** Возраст на начало СЛО */
	private Long theAgeEntranceSlo;
}