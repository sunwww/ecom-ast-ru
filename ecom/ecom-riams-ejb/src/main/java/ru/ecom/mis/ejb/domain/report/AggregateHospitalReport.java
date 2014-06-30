package ru.ecom.mis.ejb.domain.report;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Comment("Агрегаты по стационару")
@Entity
@Table(schema="SQLUser")
public class AggregateHospitalReport extends BaseEntity {
	/** Дата поступления */
	@Comment("Дата поступления")
	public Date getEntranceDate7() {return theEntranceDate7;}
	public void setEntranceDate7(Date aEntranceDate7) {theEntranceDate7 = aEntranceDate7;}

	/** Дата выписки */
	@Comment("Дата выписки")
	public Date getDischargeDate7() {return theDischargeDate7;}
	public void setDischargeDate7(Date aDischargeDate7) {theDischargeDate7 = aDischargeDate7;}

	/** Отделение */
	@Comment("Отделение")
	public Long getDepartment() {return theDepartment;}
	public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}

	/** Отделение перевода */
	@Comment("Отделение перевода")
	public Long getTransferDepartmentIn() {return theTransferDepartmentIn;}
	public void setTransferDepartmentIn(Long aDepartmentTransfer) {theTransferDepartmentIn = aDepartmentTransfer;}

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

	/** Экстренное поступление */
	@Comment("Экстренное поступление")
	public boolean getIsEmergency() {return theIsEmergency;}
	public void setIsEmergency(boolean aIsEmergency) {theIsEmergency = aIsEmergency;}

	
	/** Дата поступления на 9 часов */
	@Comment("Дата поступления на 9 часов")
	public Date getEntranceDate9() {return theEntranceDate9;}
	public void setEntranceDate9(Date aEntranceDate9) {theEntranceDate9 = aEntranceDate9;}

	/** Дата выписки на 9 часов */
	@Comment("Дата выписки на 9 часов")
	public Date getDischargeDate9() {return theDischargeDate9;}
	public void setDischargeDate9(Date aDischargeDate9) {theDischargeDate9 = aDischargeDate9;}

	/** Дата выписки на 9 часов */
	private Date theDischargeDate9;
	/** Дата поступления на 9 часов */
	private Date theEntranceDate9;
	/** Экстренное поступление */
	private boolean theIsEmergency;
	/** Возраст на конец СЛС */
	private Long theAgeDischargeSls;
	/** Возраст на начало СЛС */
	private Long theAgeEntranceSls;
	/** Возраст на конец СЛО */
	private Long theAgeDischargeSlo;
	/** Возраст на начало СЛО */
	private Long theAgeEntranceSlo;
	/** Отделение перевода */
	private Long theTransferDepartmentIn;
	/** Отделение */
	private Long theDepartment;
	/** Дата выписки */
	private Date theDischargeDate7;
	/** Дата поступления */
	private Date theEntranceDate7;
	
	/** Кол-во койко дней по СЛС */
	@Comment("Кол-во койко дней по СЛС")
	public Long getCntDaysSls() {return theCntDaysSls;}
	public void setCntDaysSls(Long aCntDaysSls) {theCntDaysSls = aCntDaysSls;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	public Long getServiceStream() {return theServiceStream;}
	public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}

	/** Профиль */
	@Comment("Профиль")
	public Long getBedType() {return theBedType;}
	public void setBedType(Long aBedType) {theBedType = aBedType;}

	/** Тип коек */
	@Comment("Тип коек")
	public Long getBedSubType() {return theBedSubType;}
	public void setBedSubType(Long aBedSubType) {theBedSubType = aBedSubType;}

	
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
	/** Тип коек */
	private Long theBedSubType;
	/** Профиль */
	private Long theBedType;
	/** Поток обслуживания */
	private Long theServiceStream;
	/** Кол-во койко дней по СЛС */
	private Long theCntDaysSls;
	
	/** Сельский житель */
	@Comment("Сельский житель")
	public boolean getIsVillage() {return theIsVillage;}
	public void setIsVillage(boolean aIsVillage) {theIsVillage = aIsVillage;}

	/** Диагноз */
	@Comment("Диагноз при поступлении")
	public String getIdcEntranceCode() {return theIdcEntranceCode;}
	public void setIdcEntranceCode(String aIdcCode) {theIdcEntranceCode = aIdcCode;}

	/** Умер */
	@Comment("Умер")
	public boolean getIsDeath() {return theIsDeath;}
	public void setIsDeath(boolean aIsDeath) {theIsDeath = aIsDeath;}

	/** Операция в отделение */
	@Comment("Операция в отделение")
	public boolean getIsOperation() {return theIsOperation;}
	public void setIsOperation(boolean aIsOperation) {theIsOperation = aIsOperation;}

	/** Перевод в ЛПУ */
	@Comment("Перевод в ЛПУ")
	public String getTransferLpuCode() {return theTransferLpuCode;}
	public void setTransferLpuCode(String aTransferLpuCode) {theTransferLpuCode = aTransferLpuCode;}

	/** Впервые в данном году */
	@Comment("Впервые в данном году")
	public boolean getIsFirstCurrentYear() {return theIsFirstCurrentYear;}
	public void setIsFirstCurrentYear(boolean aIsFirstCurrentYear) {theIsFirstCurrentYear = aIsFirstCurrentYear;}

	/** Впервые в жизни */
	@Comment("Впервые в жизни")
	public boolean getIsFirstLife() {return theIsFirstLife;}
	public void setIsFirstLife(boolean aIsFirstLife) {theIsFirstLife = aIsFirstLife;}

	/** Недееспособный */
	@Comment("Недееспособный")
	public boolean getIsIncompetent() {return theIsIncompetent;}
	public void setIsIncompetent(boolean aIsIncompetent) {theIsIncompetent = aIsIncompetent;}

	/** Недееспособный */
	private boolean theIsIncompetent;
	/** Впервые в жизни */
	private boolean theIsFirstLife;
	/** Впервые в данном году */
	private boolean theIsFirstCurrentYear;
	/** Перевод в ЛПУ */
	private String theTransferLpuCode;
	/** Операция в отделение */
	private boolean theIsOperation;
	/** Умер */
	private boolean theIsDeath;
	/** Диагноз */
	private String theIdcEntranceCode;
	/** Сельский житель */
	private boolean theIsVillage;
	
	/** Дата поступление круглосуточно */
	@Comment("Дата поступление круглосуточно")
	public Date getEntranceDate24() {return theEntranceDate24;}
	public void setEntranceDate24(Date aEntranceDate24) {theEntranceDate24 = aEntranceDate24;}

	/** Дата выписки круглосуточно */
	@Comment("Дата выписки круглосуточно")
	public Date getDischargeDate24() {return theDischargeDate24;}
	public void setDischargeDate24(Date aDischargeDate24) {theDischargeDate24 = aDischargeDate24;}

	/** Дата выписки круглосуточно */
	private Date theDischargeDate24;
	/** Дата поступление круглосуточно */
	private Date theEntranceDate24;
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
	
	/** Тип стационара */
	@Comment("Тип стационара")
	public Long getHospType() {return theHospType;	}
	public void setHospType(Long aHospType) {theHospType = aHospType;}

	/** Тип стационара */
	private Long theHospType;
	/** Пол ид */
	@Comment("Пол ид")
	public Long getSex() {return theSex;}
	public void setSex(Long aSex) {theSex = aSex;}

	/** Пол ид */
	private Long theSex;
	
	/** Переводной диагноз */
	@Comment("Переводной диагноз")
	public String getIdcTransferCode() {return theIdcTransferCode;}
	public void setIdcTransferCode(String aIdcTransferCode) {theIdcTransferCode = aIdcTransferCode;}

	/** Переводной диагноз */
	private String theIdcTransferCode;

	/** Дата поступлание в стационар 24 */
	@Comment("Дата поступлание в стационар 24")
	public Date getEntranceHospDate24() {return theEntranceHospDate24;}
	public void setEntranceHospDate24(Date aEntranceHospDate24) {theEntranceHospDate24 = aEntranceHospDate24;}

	/** Дата поступления в стационар 7 */
	@Comment("Дата поступления в стационар 7")
	public Date getEntranceHospDate7() {return theEntranceHospDate7;}
	public void setEntranceHospDate7(Date aEntranceHospDate7) {theEntranceHospDate7 = aEntranceHospDate7;}

	/** Дата поступления в стационар 9 */
	@Comment("Дата поступления в стационар 9")
	public Date getEntranceHospDate9() {return theEntranceHospDate9;}
	public void setEntranceHospDate9(Date aEntranceHospDate9) {theEntranceHospDate9 = aEntranceHospDate9;}

	/** Дата поступления в стационар 9 */
	private Date theEntranceHospDate9;
	/** Дата поступления в стационар 7 */
	private Date theEntranceHospDate7;
	/** Дата поступлание в стационар 24 */
	private Date theEntranceHospDate24;
	
	/** Доп. койко день */
	@Comment("Доп. койко день")
	public Long getAddBedDays() {return theAddBedDays;}
	public void setAddBedDays(Long aAddBedDays) {theAddBedDays = aAddBedDays;}

	/** Доп. койко день */
	private Long theAddBedDays;
	
	/** Дата рождения */
	@Comment("Дата рождения")
	public Date getBirthday() {return theBirthday;}
	public void setBirthday(Date aBirthday) {theBirthday = aBirthday;}

	/** Дата рождения */
	private Date theBirthday;
	
}
