package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
public class TransfusionReagent extends BaseEntity{
	/** Реактив */
	@Comment("Реактив")
	@OneToOne
	public VocTransfusionReagent getReagent() {return theReagent;}
	public void setReagent(VocTransfusionReagent aReagent) {theReagent = aReagent;}

	/** Серия */
	@Comment("Серия")
	public String getSeries() {return theSeries;}
	public void setSeries(String aSeries) {theSeries = aSeries;}

	/** Срок годности */
	@Comment("Срок годности")
	public Date getExpirationDate() {return theExpirationDate;}
	public void setExpirationDate(Date aExpirationDate) {theExpirationDate = aExpirationDate;}

	/** Порядковый номер */
	@Comment("Порядковый номер")
	public Integer getNumberReagent() {return theNumberReagent;}
	public void setNumberReagent(Integer aNumberReagent) {theNumberReagent = aNumberReagent;}

	/** Переливание */
	@Comment("Переливание")
	@OneToOne
	public Transfusion getTransfusion() {return theTransfusion;}
	public void setTransfusion(Transfusion aTransfusion) {theTransfusion = aTransfusion;}

	/** Переливание */
	private Transfusion theTransfusion;
	/** Порядковый номер */
	private Integer theNumberReagent;
	/** Срок годности */
	private Date theExpirationDate;
	/** Серия */
	private String theSeries;
	/** Реактив */
	private VocTransfusionReagent theReagent;
}
