package ru.ecom.mis.ejb.uc.privilege.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Позиция классификатора лекарственных средств
 * @author azviagin
 *
 */
@Comment("Позиция классификатора лекарственных средств")
@Entity
@Table(schema="SQLUser")
public class DrugClassificatorPosition extends BaseEntity{

	/** Классификатор лекарственных средств */
	@Comment("Классификатор лекарственных средств")
	@ManyToOne
	public DrugClassificator getDrugClassificator() {
		return theDrugClassificator;
	}

	public void setDrugClassificator(DrugClassificator aDrugClassificator) {
		theDrugClassificator = aDrugClassificator;
	}

	/** Классификатор лекарственных средств */
	private DrugClassificator theDrugClassificator;
	
	/** Лекарственное средство */
	@Comment("Лекарственное средство")
	@ManyToOne
	public VocDrugClassify getDrug() {
		return theDrug;
	}

	public void setDrug(VocDrugClassify aDrug) {
		theDrug = aDrug;
	}

	/** Лекарственное средство */
	private VocDrugClassify theDrug;
}
