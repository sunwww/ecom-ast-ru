package ru.ecom.mis.ejb.domain.extdisp;

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
public class ExtDispPlanService extends BaseEntity{
	/**
	 * План
	 */
	@Comment("План")
	@ManyToOne
	public ExtDispPlan getPlan() {return thePlan;}
	public void setPlan(ExtDispPlan aPlan) {thePlan = aPlan;}
	/**
	 * План
	 */
	private ExtDispPlan thePlan;
	/**
	 * Пол
	 */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {return theSex;}
	public void setSex(VocSex aSex) {theSex = aSex;}
	/**
	 * Пол
	 */
	private VocSex theSex;
	/**
	 * Тип услуги
	 */
	@Comment("Тип услуги")
	@OneToOne
	public VocExtDispService getServiceType() {return theServiceType;}
	public void setServiceType(VocExtDispService aServiceType) {theServiceType = aServiceType;}
	/**
	 * Тип услуги
	 */
	private VocExtDispService theServiceType;
	/**
	 * Возрастная группа
	 */
	@Comment("Возрастная группа")
	@OneToOne
	public VocExtDispAgeGroup getAgeGroup() {return theAgeGroup;}
	public void setAgeGroup(VocExtDispAgeGroup aAgeGroup) {theAgeGroup = aAgeGroup;}
	/**
	 * Возрастная группа
	 */
	private VocExtDispAgeGroup theAgeGroup;
	/**
	 * Визит
	 */
	@Comment("Визит")
	
	public Boolean getIsVisit() {return theIsVisit;}
	public void setIsVisit(Boolean aIsVisit) {theIsVisit = aIsVisit;}
	/**
	 * Визит
	 */
	private Boolean theIsVisit;
}
