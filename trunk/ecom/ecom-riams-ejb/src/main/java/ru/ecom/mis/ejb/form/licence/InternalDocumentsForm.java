package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.licence.InternalDocuments;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = InternalDocuments.class)
@Comment("Внут. документы")
@WebTrail(comment = "Внут. документы", nameProperties = "id",list="entityParentList-doc_internal.do", view = "entityParentView-pres_prescriptList.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Document/Internal")
public class InternalDocumentsForm extends DocumentForm{
	/** Обоснование */
	@Comment("Обоснование")
	@Persist
	public String getHistory() {return theHistory;}
	public void setHistory(String aHistory) {theHistory = aHistory;}

	/** Рекомендации */
	@Comment("Рекомендации")
	@Persist
	public String getRecommendations() {return theRecommendations;}
	public void setRecommendations(String aRecommendations) {theRecommendations = aRecommendations;}

	/** Куда направлен */
	@Comment("Куда направлен")
	@Persist
	public Long getSentToLpu() {return theSentToLpu;}
	public void setSentToLpu(Long aSentToLpu) {theSentToLpu = aSentToLpu;}

	/** Диагноз */
	@Comment("Диагноз")
	@Persist
	public String getDiagnosis() {return theDiagnosis;}
	public void setDiagnosis(String aDiagnosis) {theDiagnosis = aDiagnosis;}

	/** Код диагноза */
	@Comment("Код диагноза")
	@Persist
	public Long getIdc10() {return theIdc10;}
	public void setIdc10(Long aIdc10) {theIdc10 = aIdc10;}

	/** Код диагноза */
	private Long theIdc10;
	/** Диагноз */
	private String theDiagnosis;
	/** Куда направлен */
	private Long theSentToLpu;
	/** Рекомендации */
	private String theRecommendations;
	/** Обоснование */
	private String theHistory;
}
