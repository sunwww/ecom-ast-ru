package ru.ecom.mis.ejb.domain.worker;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRoomType;
import ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptType;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Услуги по рабочим функциям
 * @author azviagin
 *
 */

@Comment("Услуги по рабочим функциям")
@Entity
@AIndexes({
	@AIndex(properties={"vocWorkFunction"})
	,@AIndex(properties={"medService"})
	,@AIndex(properties={"vocWorkFunction","medService"})
	,@AIndex(properties={"vocWorkFunction","lpu"})
	,@AIndex(properties={"lpu","bedType"})
})
@Table(schema="SQLUser")
public class WorkFunctionService extends BaseEntity{
	
	/** Специалист (группа) */
	@Comment("Специалист (группа)")
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}
	/** Специалист (группа) */
	private WorkFunction theWorkFunction;

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@ManyToOne
	public VocWorkFunction getVocWorkFunction() {
		return theVocWorkFunction;
	}

	public void setVocWorkFunction(VocWorkFunction aVocWorkFunction) {
		theVocWorkFunction = aVocWorkFunction;
	}

	/** Рабочая функция */
	private VocWorkFunction theVocWorkFunction;
	
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@ManyToOne
	public MedService getMedService() {
		return theMedService;
	}

	public void setMedService(MedService aMedService) {
		theMedService = aMedService;
	}

	/** Мед. услуга */
	private MedService theMedService;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}

	/** ЛПУ */
	private MisLpu theLpu;
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@OneToOne
	public VocBedType getBedType() {return theBedType;}
	public void setBedType(VocBedType aBedType) {theBedType = aBedType;}

	/** Профиль коек */
	private VocBedType theBedType;
	
	/** Тип коек */
	@Comment("Тип коек")
	@OneToOne
	public VocBedSubType getBedSubType() {return theBedSubType;}
	public void setBedSubType(VocBedSubType aBedSubType) {theBedSubType = aBedSubType;}

	/** Тип коек */
	private VocBedSubType theBedSubType;
	
	/** Уровень палат */
	@Comment("Уровень палат")
	@OneToOne
	public VocRoomType getRoomType() {return theRoomType;}
	public void setRoomType(VocRoomType aRoomType) {theRoomType = aRoomType;}

	/** Уровень палат */
	private VocRoomType theRoomType;
	
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
	
	/** Неактивно для назначений */
	@Comment("Неактивно для назначений")
	public Boolean getNoActiveByPrescript() {return theNoActiveByPrescript;}
	public void setNoActiveByPrescript(Boolean aNoActiveByPrescript) {theNoActiveByPrescript = aNoActiveByPrescript;}

	/** Неактивно для назначений */
	private Boolean theNoActiveByPrescript;
	
	/** Список диагнозов */
	@Comment("Список диагнозов")
	public String getListIdc10() {return theListIdc10;}
	public void setListIdc10(String aListIdc10) {theListIdc10 = aListIdc10;}

	/** Список диагнозов */
	private String theListIdc10;
	
	/** Запрещенный тип назначений */
	@Comment("Запрещенный тип назначений")
	@OneToOne
	public VocPrescriptType getPrescriptType() {return thePrescriptType;}
	public void setPrescriptType(VocPrescriptType aPrescriptType) {thePrescriptType = aPrescriptType;}

	/** Запрещенный тип назначений */
	private VocPrescriptType thePrescriptType;
}
