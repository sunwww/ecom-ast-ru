package ru.ecom.mis.ejb.domain.extdisp;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

	/**
	 * Визит по дополнительной диспансеризации
	 */
	@Comment("Визит по дополнительной диспансеризации")
@Entity
public class ExtDispVisit extends ExtDispService {

	@OneToMany(mappedBy="visit", cascade=CascadeType.ALL)
	public List<ExtDispServiceIndication> getServiceIndications() {return theServiceIndications;}
	public void setServiceIndications(List<ExtDispServiceIndication> aServiceIndications) {theServiceIndications = aServiceIndications;}
	/**
	 * Показания к услугам 2 этапа
	 */
	private List<ExtDispServiceIndication> theServiceIndications;
	/**
	 * Подозрение на ранее перенесенное нарушение мозгового кровообращения (suspicion on earlier transferred disturbance of a cerebral circulation)
	 */
	@Comment("Подозрение на ранее перенесенное нарушение мозгового кровообращения (suspicion on earlier transferred disturbance of a cerebral circulation)")
	public Boolean getIsEtdccSuspicion() {return theIsEtdccSuspicion;}
	public void setIsEtdccSuspicion(Boolean aIsEtdccSuspicion) {theIsEtdccSuspicion = aIsEtdccSuspicion;}
	/**
	 * Подозрение на ранее перенесенное нарушение мозгового кровообращения (suspicion on earlier transferred disturbance of a cerebral circulation)
	 */
	private Boolean theIsEtdccSuspicion;
	/**
	 * Рекомендации
	 */
	@Comment("Рекомендации")
	public String getRecommendation() {return theRecommendation;}
	public void setRecommendation(String aRecommendation) {theRecommendation = aRecommendation;}
	/**
	 * Рекомендации
	 */
	private String theRecommendation;
}
