package ru.ecom.mis.ejb.domain.prescription;

import ru.ecom.mis.ejb.domain.pharmacy.PharmDrug;
import ru.ecom.mis.ejb.domain.pharmacy.VocDrug;
import ru.ecom.mis.ejb.domain.prescription.voc.VocDrugAmountUnit;
import ru.ecom.mis.ejb.domain.prescription.voc.VocFrequencyUnit;
import ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptOrderType;
import ru.ecom.mis.ejb.uc.privilege.domain.voc.VocDrugMethod;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Назначение на лекарства
 * @author azviagin,rkurbanov
 *
 */

@Comment("Назначение на лекарства")
@Entity
@Table(schema="SQLUser")
public class DrugPrescription extends Prescription{

	//unused временно не используется.
	private PharmDrug theDrug;
	private VocDrugMethod theMethod;
	private VocFrequencyUnit theFrequencyUnit;
	private Integer theFrequency;
	private VocPrescriptOrderType theOrderType;
	private Integer theOrderTime;
	private VocDrugAmountUnit theAmountUnit;
	private Float theAmount;
	private VocDrug theVocDrug;

	@Comment("Лекарство")
	@OneToOne
	public PharmDrug getDrug() {
		return theDrug;
	}
	public void setDrug(PharmDrug aDrug) {
		theDrug = aDrug;
	}

	@Comment("Метод введения")
	@OneToOne
	public VocDrugMethod getMethod() {
		return theMethod;
	}
	public void setMethod(VocDrugMethod aMethod) {
		theMethod = aMethod;
	}

	@Comment("Единица частоты использования")
	@OneToOne
	public VocFrequencyUnit getFrequencyUnit() {
		return theFrequencyUnit;
	}
	public void setFrequencyUnit(VocFrequencyUnit aFrequencyUnit) {
		theFrequencyUnit = aFrequencyUnit;
	}

	@Comment("Частота использования")
	public Integer getFrequency() {
		return theFrequency;
	}
	public void setFrequency(Integer aFrequency) {
		theFrequency = aFrequency;
	}

	@Comment("Тип порядка использования")
	@OneToOne
	public VocPrescriptOrderType getOrderType() {
		return theOrderType;
	}
	public void setOrderType(VocPrescriptOrderType aOrderType) {
		theOrderType = aOrderType;
	}

	@Comment("Время порядка использования")
	public Integer getOrderTime() {
		return theOrderTime;
	}
	public void setOrderTime(Integer aOrderTime) {
		theOrderTime = aOrderTime;
	}

	@Comment("Единица измерения количества")
	@OneToOne
	public VocDrugAmountUnit getAmountUnit() {
		return theAmountUnit;
	}
	public void setAmountUnit(VocDrugAmountUnit aAmountUnit) {
		theAmountUnit = aAmountUnit;
	}

	@Comment("Количество")
	public Float getAmount() {
		return theAmount;
	}
	public void setAmount(Float aAmount) {
		theAmount = aAmount;
	}

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
		return theDrug!=null? theDrug.getDrug().getName():"";
	}

	/** Описание лекарственного назначения */
	@Comment("Описание лекарственного назначения")
	@Transient
	public String getDescriptionInfo()  {
		StringBuilder sb = new StringBuilder();
		if (getDrug()!=null) {
			sb.append(getDrug().getDrug().getName());
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
		return sb.toString();
	}

	/** Лекарство */
	@Comment("Лекарство")
	@OneToOne
	public VocDrug getVocDrug() {return theVocDrug;}
	public void setVocDrug(VocDrug aVocDrug) {theVocDrug = aVocDrug;}
}