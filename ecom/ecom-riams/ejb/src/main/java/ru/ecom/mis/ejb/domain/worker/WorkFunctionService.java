package ru.ecom.mis.ejb.domain.worker;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class WorkFunctionService extends BaseEntity{
	
	/** Специалист (группа) */
	@Comment("Специалист (группа)")
	@OneToOne
	public WorkFunction getWorkFunction() {return workFunction;}
	private WorkFunction workFunction;

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@ManyToOne
	public VocWorkFunction getVocWorkFunction() {
		return vocWorkFunction;
	}

	/** Рабочая функция */
	private VocWorkFunction vocWorkFunction;
	
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@ManyToOne
	public MedService getMedService() {
		return medService;
	}

	/** Мед. услуга */
	private MedService medService;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return lpu;}

	/** ЛПУ */
	private MisLpu lpu;
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@OneToOne
	public VocBedType getBedType() {return bedType;}

	/** Профиль коек */
	private VocBedType bedType;
	
	/** Тип коек */
	@Comment("Тип коек")
	@OneToOne
	public VocBedSubType getBedSubType() {return bedSubType;}
	/** Тип коек */
	private VocBedSubType bedSubType;
	
	/** Уровень палат */
	@Comment("Уровень палат")
	@OneToOne
	public VocRoomType getRoomType() {return roomType;}

	/** Уровень палат */
	private VocRoomType roomType;

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
	
	/** Неактивно для назначений */
	private Boolean noActiveByPrescript;
	
	/** Список диагнозов */
	private String listIdc10;
	
	/** Запрещенный тип назначений */
	@Comment("Запрещенный тип назначений")
	@OneToOne
	public VocPrescriptType getPrescriptType() {return prescriptType;}

	/** Запрещенный тип назначений */
	private VocPrescriptType prescriptType;
}
