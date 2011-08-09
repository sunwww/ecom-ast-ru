package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.amokb.asset.ejb.domain.voc.VocExpensesMaterial;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Расходный материал
	 */
	@Comment("Расходный материал")
@Entity
@Table(schema="SQLUser")
public class ExpensesMaterial extends TransitoryAsset{
	/**
	 * Тип расходного материала
	 */
	@Comment("Тип расходного материала")
	@OneToOne
	public VocExpensesMaterial getMaterialType() {
		return theMaterialType;
	}
	public void setMaterialType(VocExpensesMaterial aMaterialType) {
		theMaterialType = aMaterialType;
	}
	/**
	 * Тип расходного материала
	 */
	private VocExpensesMaterial theMaterialType;
}
