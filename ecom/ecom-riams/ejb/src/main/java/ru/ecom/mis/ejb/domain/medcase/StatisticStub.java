package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPigeonHole;
import ru.ecom.mis.ejb.domain.medcase.voc.VocReasonDischarge;
import ru.ecom.mis.ejb.domain.medcase.voc.VocResultDischarge;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@AIndexes({
    @AIndex(properties="year"),
    @AIndex(properties="code")
    }) 
@Table(schema="SQLUser")
public abstract class StatisticStub extends BaseEntity {
	/** Год */
	@Comment("Год")
	public Long getYear() {return theYear;}
	public void setYear(Long aYear) {theYear = aYear;}

	/** Код */
	@Comment("Код")
	public String getCode() {return theCode;}
	public void setCode(String aCode) {theCode = aCode;}

	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/**Информация (текст)*/
	@Transient
	@Comment("Информация")
	public String getInfo() {
		return "Номер стат.карты " + getCode() + "-" + getYear();
	}
	
	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}

	/**Лечебное учреждение инфо */
	@Transient
	@Comment("Лечебное учреждение инфо")
	public String getLpuInfo() {
		//return theLpu!=null ? theLpu.getFullname() : "" ;
		return "";
	}
	
	
	/** Приемник */
	@Comment("Приемник")
	@OneToOne
	public VocPigeonHole getPigeonHole() {return thePigeonHole;}
	public void setPigeonHole(VocPigeonHole aPigeonHole) {thePigeonHole = aPigeonHole;}

	/** Экстренно */
	@Comment("Экстренно")
	public Boolean getIsEmergency() {return theIsEmergency;}
	public void setIsEmergency(Boolean aIsEmergency) {theIsEmergency = aIsEmergency;}

	/** Планово */
	@Comment("Планово")
	public Boolean getIsPlan() {return theIsPlan;}
	public void setIsPlan(Boolean aIsPlan) {theIsPlan = aIsPlan;}

	/** Планово */
	private Boolean theIsPlan;
	/** Экстренно */
	private Boolean theIsEmergency;
	/** Приемник */
	private VocPigeonHole thePigeonHole;
	/** Лечебное учреждение */
	private MisLpu theLpu;
	/** СМО */
	private MedCase theMedCase;
	/** Код */
	private String theCode;
	/** Год */
	private Long theYear;
	/** Причина выписки */
	@Comment("Причина выписки")
	@OneToOne
	public VocReasonDischarge getReasonDischarge() {return theReasonDischarge;}
	public void setReasonDischarge(VocReasonDischarge aReasonDischarge) {theReasonDischarge = aReasonDischarge;}

	/** Причина выписки */
	private VocReasonDischarge theReasonDischarge;
	
	/** Итог лечения */
	@Comment("Итог лечения")
	@OneToOne
	public VocResultDischarge getResultDischarge() {return theResultDischarge;}
	public void setResultDischarge(VocResultDischarge aResultDischarge) {theResultDischarge = aResultDischarge;}

	/** Итог лечения */
	private VocResultDischarge theResultDischarge;
	
	/** Номер архивного дела */
	@Comment("Номер архивного дела")
	public Long getArchiveCase() {return theArchiveCase;}
	public void setArchiveCase(Long aArchiveCase) {theArchiveCase = aArchiveCase;}
	/** Номер архивного дела */
	private Long theArchiveCase;

	/** Рост */
	@Comment("Рост")
	public Integer getHeight() {
		return theHeight;
	}

	public void setHeight(Integer aHeight) {
		theHeight = aHeight;
	}

	/** Рост */
	private Integer theHeight;

	/** Вес */
	@Comment("Вес")
	public Integer getWeight() { return theWeight; }

	public void setWeight(Integer aWeight) {
		theWeight = aWeight;
	}

	/** Вес */
	private Integer theWeight;

	/** Индекс массы тела */
	@Comment("Индекс массы тела")
	public Double getIMT() { return theIMT; }

	public void setIMT(Double aIMT) {
		theIMT = aIMT;
	}

	/** Индекс массы тела */
	private Double theIMT;

	/** Визит оформлен диетологом */
	@Comment("Визит оформлен диетологом")
	public Boolean getDietDone() { return theDietDone; }

	public void setDietDone(Boolean aDietDone) { theDietDone = aDietDone; }

	/** Визит оформлен диетологом */
	private Boolean theDietDone;

}
