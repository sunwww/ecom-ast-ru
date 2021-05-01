package ru.ecom.mis.ejb.domain.extdisp;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispAgeGroup;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispService;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

	/**
	 * Услуга плана дополнительной диспансеризации
	 */
	@Comment("Услуга плана дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
	@AIndexes({
		@AIndex(properties="plan")
		,@AIndex(properties="sex")
		,@AIndex(properties="ageGroup")
	    })
	@Getter
	@Setter
public class ExtDispPlanService extends BaseEntity{
	/**
	 * План
	 */
	@Comment("План")
	@ManyToOne
	public ExtDispPlan getPlan() {return plan;}
	/**
	 * План
	 */
	private ExtDispPlan plan;
	/**
	 * Пол
	 */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {return sex;}
	/**
	 * Пол
	 */
	private VocSex sex;
	/**
	 * Тип услуги
	 */
	@Comment("Тип услуги")
	@OneToOne
	public VocExtDispService getServiceType() {return serviceType;}
	/**
	 * Тип услуги
	 */
	private VocExtDispService serviceType;
	/**
	 * Возрастная группа
	 */
	@Comment("Возрастная группа")
	@OneToOne
	public VocExtDispAgeGroup getAgeGroup() {return ageGroup;}
	/**
	 * Возрастная группа
	 */
	private VocExtDispAgeGroup ageGroup;
	/**
	 * Визит
	 */
	private Boolean isVisit;
}
