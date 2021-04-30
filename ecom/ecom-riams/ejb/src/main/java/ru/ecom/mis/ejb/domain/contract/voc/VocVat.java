package ru.ecom.mis.ejb.domain.contract.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Справочник ставок НДС
 * @author user
 *
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocVat extends VocBaseEntity{
	
	/** Ставка налога */
	private Long taxRate;

}
