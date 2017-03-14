package ru.ecom.mis.ejb.domain.contract.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Справочник ставок НДС
 * @author user
 *
 */
@Entity
@Table(schema="SQLUser")
public class VocVat extends VocBaseEntity{
	
	/** Ставка налога */
	@Comment("Ставка налога")
	public Long getTaxRate() {return theTaxRate;}
	public void setTaxRate(Long aTaxRate) {theTaxRate = aTaxRate;}
	/** Ставка налога */
	private Long theTaxRate;

}
