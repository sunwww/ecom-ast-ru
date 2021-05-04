package ru.ecom.mis.ejb.domain.prescription;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.pharmacy.PharmDrug;
import ru.ecom.mis.ejb.domain.pharmacy.VocDrug;
import ru.ecom.mis.ejb.domain.prescription.voc.VocDrugAmountUnit;
import ru.ecom.mis.ejb.domain.prescription.voc.VocFrequencyUnit;
import ru.ecom.mis.ejb.domain.prescription.voc.VocPrescriptOrderType;
import ru.ecom.mis.ejb.uc.privilege.domain.voc.VocDrugMethod;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Назначение на лекарства
 * @author azviagin,rkurbanov
 *
 */

@Comment("Назначение на лекарства")
@Entity
@Getter
@Setter
public class DrugPrescription extends Prescription{

	//unused временно не используется.
	private PharmDrug drug;
	private VocDrugMethod method;
	private VocFrequencyUnit frequencyUnit;
	private Integer frequency;
	private VocPrescriptOrderType orderType;
	private Integer orderTime;
	private VocDrugAmountUnit amountUnit;
	private Float amount;
	private VocDrug vocDrug;

	@Comment("Лекарство")
	@OneToOne
	public PharmDrug getDrug() {
		return drug;
	}

	@Comment("Метод введения")
	@OneToOne
	public VocDrugMethod getMethod() {
		return method;
	}

	@Comment("Единица частоты использования")
	@OneToOne
	public VocFrequencyUnit getFrequencyUnit() {
		return frequencyUnit;
	}


	@Comment("Тип порядка использования")
	@OneToOne
	public VocPrescriptOrderType getOrderType() {
		return orderType;
	}


	@Comment("Единица измерения количества")
	@OneToOne
	public VocDrugAmountUnit getAmountUnit() {
		return amountUnit;
	}

	/** Наименование лекарственного препарата */
	@Comment("Наименование лекарственного препарата")
	@Transient
	public String getDrugInfo() {
		return drug!=null? drug.getDrug().getName():"";
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
	public VocDrug getVocDrug() {return vocDrug;}
}