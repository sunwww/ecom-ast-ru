package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionReagent;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Реактивы, используемые при определение показателей")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="transfusion")
    })
@Getter
@Setter
public class TransfusionReagent extends BaseEntity{
	/** Реактив */
	@Comment("Реактив")
	@OneToOne
	public VocTransfusionReagent getReagent() {return reagent;}

	/** Переливание */
	@Comment("Переливание")
	@OneToOne
	public Transfusion getTransfusion() {return transfusion;}

	/** Переливание */
	private Transfusion transfusion;
	/** Порядковый номер */
	private Integer numberReagent;
	/** Срок годности */
	private Date expirationDate;
	/** Серия */
	private String series;
	/** Реактив */
	private VocTransfusionReagent reagent;
}
