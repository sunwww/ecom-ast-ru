package ru.amokb.asset.ejb.domain;

import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.amokb.asset.ejb.domain.voc.VocAssetProducer;
import ru.amokb.asset.ejb.domain.voc.VocAssetResponsiblePerson;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Актив
	 */
	@Comment("Актив")
@Entity
@Table(schema="SQLUser")
public class Asset extends BaseEntity {
	/**
	 * ЛПУ
	 */
	@Comment("ЛПУ")
	@ManyToOne
	public MisLpu getLpu() {
		return theLpu;
	}
	public void setLpu(MisLpu aLpu) {
		theLpu = aLpu;
	}
	/**
	 * ЛПУ
	 */
	private MisLpu theLpu;
	/**
	 * Производитель
	 */
	@Comment("Производитель")
	@OneToOne
	public VocAssetProducer getProducer() {
		return theProducer;
	}
	public void setProducer(VocAssetProducer aProducer) {
		theProducer = aProducer;
	}
	/**
	 * Производитель
	 */
	private VocAssetProducer theProducer;
	/**
	 * Дата списания
	 */
	@Comment("Дата списания")
	
	public Date getDiscardingDate() {
		return theDiscardingDate;
	}
	public void setDiscardingDate(Date aDiscardingDate) {
		theDiscardingDate = aDiscardingDate;
	}
	/**
	 * Дата списания
	 */
	private Date theDiscardingDate;
	/**
	 * Дата поступления
	 */
	@Comment("Дата поступления")
	
	public Date getEntranceDate() {
		return theEntranceDate;
	}
	public void setEntranceDate(Date aEntranceDate) {
		theEntranceDate = aEntranceDate;
	}
	/**
	 * Дата поступления
	 */
	private Date theEntranceDate;
	/**
	 * Ответственное лицо
	 */
	@Comment("Ответственное лицо")
	@OneToOne
	public VocAssetResponsiblePerson getResponsiblePerson() {
		return theResponsiblePerson;
	}
	public void setResponsiblePerson(VocAssetResponsiblePerson aResponsiblePerson) {
		theResponsiblePerson = aResponsiblePerson;
	}
	/**
	 * Ответственное лицо
	 */
	private VocAssetResponsiblePerson theResponsiblePerson;
	@OneToMany(mappedBy="asset", cascade=CascadeType.ALL)
	public List<AssetWorkcard> getWorkcards() {
		return theWorkcards;
	}
	public void setWorkcards(List<AssetWorkcard> aWorkcards) {
		theWorkcards = aWorkcards;
	}
	/**
	 * Рабочие карточки
	 */
	private List<AssetWorkcard> theWorkcards;
	/**
	 * Комментарии
	 */
	@Comment("Комментарии")
	
	public String getComment() {
		return theComment;
	}
	public void setComment(String aComment) {
		theComment = aComment;
	}
	/**
	 * Комментарии
	 */
	private String theComment;
	/**
	 * Название
	 */
	@Comment("Название")
	
	public String getName() {
		return theName;
	}
	public void setName(String aName) {
		theName = aName;
	}
	/**
	 * Название
	 */
	private String theName;
}
