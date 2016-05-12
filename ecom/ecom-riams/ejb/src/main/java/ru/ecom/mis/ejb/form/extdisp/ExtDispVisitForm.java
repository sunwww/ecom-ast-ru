package ru.ecom.mis.ejb.form.extdisp;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispVisit;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ExtDispVisit.class)
@Comment("Визит по дополнительной диспансеризации")
@WebTrail(comment = "Визит по дополнительной диспансеризации", nameProperties= "id", list="entityParentList-extdisp_extDispVisit.do", view="entityParentView-extdisp_extDispVisit.do")
@Parent(property="card", parentForm=ExtDispCardForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card/Service")
public class ExtDispVisitForm extends ExtDispServiceForm {
	/**
	 * Подозрение на ранее перенесенное нарушение мозгового кровообращения (suspicion on earlier transferred disturbance of a cerebral circulation)
	 */
	@Comment("Подозрение на ранее перенесенное нарушение мозгового кровообращения (suspicion on earlier transferred disturbance of a cerebral circulation)")
	@Persist
	public Boolean getIsEtdccSuspicion() {
		return theIsEtdccSuspicion;
	}
	public void setIsEtdccSuspicion(Boolean aIsEtdccSuspicion) {
		theIsEtdccSuspicion = aIsEtdccSuspicion;
	}
	/**
	 * Подозрение на ранее перенесенное нарушение мозгового кровообращения (suspicion on earlier transferred disturbance of a cerebral circulation)
	 */
	private Boolean theIsEtdccSuspicion;
	/**
	 * Рекомендации
	 */
	@Comment("Рекомендации")
	@Persist
	public String getRecommendation() {
		return theRecommendation;
	}
	public void setRecommendation(String aRecommendation) {
		theRecommendation = aRecommendation;
	}
	/**
	 * Рекомендации
	 */
	private String theRecommendation;
}
