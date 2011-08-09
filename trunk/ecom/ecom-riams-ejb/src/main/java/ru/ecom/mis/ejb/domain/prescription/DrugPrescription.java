package ru.ecom.mis.ejb.domain.prescription;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import ru.ecom.mis.ejb.domain.prescription.voc.VocDrugAmountUnit;
import ru.ecom.mis.ejb.domain.prescription.voc.VocDurationUnit;
import ru.ecom.mis.ejb.domain.prescription.voc.VocFrequencyUnit;
import ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptOrderType;
import ru.ecom.mis.ejb.uc.privilege.domain.VocDrugClassify;
import ru.ecom.mis.ejb.uc.privilege.domain.voc.VocDrugMethod;
import ru.ecom.mis.ejb.uc.privilege.domain.voc.VocDrugUnlicensedName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Назначение на лекарства
 * @author azviagin
 *
 */

@Comment("Назначение на лекарства")
@Entity
@Table(schema="SQLUser")
public class DrugPrescription extends Prescription{
	
	/** Лекарство */
	@Comment("Лекарство")
	@OneToOne
	public VocDrugClassify getDrug() {
		return theDrug;
	}

	public void setDrug(VocDrugClassify aDrug) {
		theDrug = aDrug;
	}

	/** Лекарство */
	private VocDrugClassify theDrug;
	
	/** Метод введения */
	@Comment("Метод введения")
	@OneToOne
	public VocDrugMethod getMethod() {
		return theMethod;
	}

	public void setMethod(VocDrugMethod aMethod) {
		theMethod = aMethod;
	}

	/** Метод введения */
	private VocDrugMethod theMethod;
	
	/** Единица частоты использования */
	@Comment("Единица частоты использования")
	@OneToOne
	public VocFrequencyUnit getFrequencyUnit() {
		return theFrequencyUnit;
	}

	public void setFrequencyUnit(VocFrequencyUnit aFrequencyUnit) {
		theFrequencyUnit = aFrequencyUnit;
	}

	/** Единица частоты использования*/
	private VocFrequencyUnit theFrequencyUnit;
	
	/** Частота использования */
	@Comment("Частота использования")
	public Integer getFrequency() {
		return theFrequency;
	}

	public void setFrequency(Integer aFrequency) {
		theFrequency = aFrequency;
	}

	/** Частота использования */
	private Integer theFrequency;
	
	/** Единица длительности использования */
	@Comment("Единица длительности использования")
	@OneToOne
	public VocDurationUnit getDurationUnit() {
		return theDurationUnit;
	}

	public void setDurationUnit(VocDurationUnit aDurationUnit) {
		theDurationUnit = aDurationUnit;
	}

	/** Единица длительности использования */
	private VocDurationUnit theDurationUnit;
	
	/** Длительность использования */
	@Comment("Длительность использования")
	public Integer getDuration() {
		return theDuration;
	}

	public void setDuration(Integer aDuration) {
		theDuration = aDuration;
	}

	/** Длительность использования */
	private Integer theDuration;
	
	/** Тип порядка использования */
	@Comment("Тип порядка использования")
	@OneToOne
	public VocPrescriptOrderType getOrderType() {
		return theOrderType;
	}

	public void setOrderType(VocPrescriptOrderType aOrderType) {
		theOrderType = aOrderType;
	}

	/** Тип порядка использования */
	private VocPrescriptOrderType theOrderType;
	
	/** Время порядка использования */
	@Comment("Время порядка использования")
	public Integer getOrderTime() {
		return theOrderTime;
	}

	public void setOrderTime(Integer aOrderTime) {
		theOrderTime = aOrderTime;
	}

	/** Время порядка использования */
	private Integer theOrderTime;

	/** Единица измерения количества */
	@Comment("Единица измерения количества")
	@OneToOne
	public VocDrugAmountUnit getAmountUnit() {
		return theAmountUnit;
	}

	public void setAmountUnit(VocDrugAmountUnit aAmountUnit) {
		theAmountUnit = aAmountUnit;
	}

	/** Единица измерения количества */
	private VocDrugAmountUnit theAmountUnit;
	
	/** Количество */
	@Comment("Количество")
	public BigDecimal getAmount() {
		return theAmount;
	}

	public void setAmount(BigDecimal aAmount) {
		theAmount = aAmount;
	}
	/** Количество */
	private BigDecimal theAmount;
	
	///** Краткий справочник лекарств */
	//@Comment("Краткий справочник лекарств")
	//@OneToOne
	//public VocDrugUnlicensedName getDrugName() {
	//	return theDrugName;
	//}

	//public void setDrugName(VocDrugUnlicensedName aDrugName) {
	//	theDrugName = aDrugName;
	//}

	///** Краткий справочник лекарств */
	//private VocDrugUnlicensedName theDrugName;
	
	/** Наименование лекарственного препарата */
	@Comment("Наименование лекарственного препарата")
	@Transient
	public String getDrugInfo() {
		return theDrug!=null? theDrug.getName():"";
	}

	/** Описание лекарственного назначения */
	@Comment("Описание лекарственного назначения")
	@Transient
	public String getDescriptionInfo()  {
		StringBuilder sb = new StringBuilder();
		if (getDrug()!=null) {
			sb.append(getDrug().getName());
			sb.append(",");
			sb.append(getAmount());
		
		}
		if(getAmountUnit()!=null) {
			sb.append(" ");
			sb.append(getAmountUnit().getName());
		}
		    sb.append(",");
			sb.append(getFrequency());
		if(getFrequencyUnit()!=null) {
			sb.append(" раза в ");
			sb.append(getFrequencyUnit().getName());
			}
			
		if(getMethod()!=null) {
			sb.append(",");
			sb.append(getMethod().getName());
			}
		if(getOrderTime()!=null && (getOrderTime()!=0)) {    
			sb.append(",");
		    sb.append(getOrderTime());
		    sb.append(" ");
		    sb.append("мин.");
		}
		 if(getOrderType()!=null) {
			
			sb.append(" ");
			sb.append(getOrderType().getName());
		}
		    sb.append(",");
			sb.append(getDuration());
		if(getDurationUnit()!=null) {
			sb.append(" ");
			sb.append(getDurationUnit().getName());
		   }	
		
		return sb.toString();
	}
}