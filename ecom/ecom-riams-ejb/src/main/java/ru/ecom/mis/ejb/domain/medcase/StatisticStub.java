package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPigeonHole;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

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
		return new StringBuilder().append("Номер стат.карты ").append(getCode()).append("-").append(getYear()).toString() ;
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

}
