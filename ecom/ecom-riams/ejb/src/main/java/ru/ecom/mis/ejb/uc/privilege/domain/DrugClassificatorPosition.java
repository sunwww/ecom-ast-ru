package ru.ecom.mis.ejb.uc.privilege.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class DrugClassificatorPosition extends BaseEntity{

	/** Классификатор лекарственных средств */
	@Comment("Классификатор лекарственных средств")
	@ManyToOne
	public DrugClassificator getDrugClassificator() {
		return drugClassificator;
	}

	/** Классификатор лекарственных средств */
	private DrugClassificator drugClassificator;
	
	/** Лекарственное средство */
	@Comment("Лекарственное средство")
	@ManyToOne
	public VocDrugClassify getDrug() {
		return drug;
	}

	/** Лекарственное средство */
	private VocDrugClassify drug;
}
