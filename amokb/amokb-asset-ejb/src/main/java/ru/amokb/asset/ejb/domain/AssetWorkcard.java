package ru.amokb.asset.ejb.domain;

import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.amokb.asset.ejb.domain.voc.VocAssetWork;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Рабочая карточка актива
	 */
	@Comment("Рабочая карточка актива")
@Entity
@Table(schema="SQLUser")
public class AssetWorkcard extends BaseEntity{
	/**
	 * Работа
	 */
	@Comment("Работа")
	@OneToOne
	public VocAssetWork getAssetWork() {
		return theAssetWork;
	}
	public void setAssetWork(VocAssetWork aAssetWork) {
		theAssetWork = aAssetWork;
	}
	/**
	 * Работа
	 */
	private VocAssetWork theAssetWork;
	/**
	 * Дата работы
	 */
	@Comment("Дата работы")
	
	public Date getWorkDate() {
		return theWorkDate;
	}
	public void setWorkDate(Date aWorkDate) {
		theWorkDate = aWorkDate;
	}
	/**
	 * Дата работы
	 */
	private Date theWorkDate;
	/**
	 * Исполнитель
	 */
	@Comment("Исполнитель")
	@OneToOne
	public Worker getWorker() {
		return theWorker;
	}
	public void setWorker(Worker aWorker) {
		theWorker = aWorker;
	}
	/**
	 * Исполнитель
	 */
	private Worker theWorker;
	/**
	 * Заказчик
	 */
	@Comment("Заказчик")
	@OneToOne
	public Worker getCustomer() {
		return theCustomer;
	}
	public void setCustomer(Worker aCustomer) {
		theCustomer = aCustomer;
	}
	/**
	 * Заказчик
	 */
	private Worker theCustomer;
	/**
	 * Время работы в часах
	 */
	@Comment("Время работы в часах")
	
	public BigDecimal getWorkTime() {
		return theWorkTime;
	}
	public void setWorkTime(BigDecimal aWorkTime) {
		theWorkTime = aWorkTime;
	}
	/**
	 * Время работы в часах
	 */
	private BigDecimal theWorkTime;
	/**
	 * Нарушение опечатывания
	 */
	@Comment("Нарушение опечатывания")
	public Boolean getSealingBreak() {
		return theSealingBreak;
	}
	public void setSealingBreak(Boolean aSealingBreak) {
		theSealingBreak = aSealingBreak;
	}
	/**
	 * Нарушение опечатывания
	 */
	private Boolean theSealingBreak;
	/**
	 * Проведено опечатывание
	 */
	@Comment("Проведено опечатывание")
	
	public Boolean getSealing() {
		return theSealing;
	}
	public void setSealing(Boolean aSealing) {
		theSealing = aSealing;
	}
	/**
	 * Проведено опечатывание
	 */
	private Boolean theSealing;
	/**
	 * Актив
	 */
	@Comment("Актив")
	@ManyToOne
	public Asset getAsset() {
		return theAsset;
	}
	public void setAsset(Asset aAsset) {
		theAsset = aAsset;
	}
	/**
	 * Актив
	 */
	private Asset theAsset;
}
