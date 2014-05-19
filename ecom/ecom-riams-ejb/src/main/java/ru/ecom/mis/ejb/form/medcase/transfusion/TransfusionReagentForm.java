package ru.ecom.mis.ejb.form.medcase.transfusion;

import java.sql.Date;

import javax.persistence.OneToOne;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.Transfusion;
import ru.ecom.mis.ejb.domain.medcase.TransfusionReagent;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= TransfusionReagent.class)
@Comment("Реактив")
@WebTrail(comment = "Реактив", nameProperties= "id", view="entityView-trans_reagent.do",list = "entityParentList-trans_transfusion.do")
@Parent(property="transfusion", parentForm= TransfusionForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Transfusion")
public class TransfusionReagentForm extends IdEntityForm {
	/** Реактив */
	@Comment("Реактив")
	@Persist @Required
	public Long getReagent() {return theReagent;}
	public void setReagent(Long aReagent) {theReagent = aReagent;}

	/** Серия */
	@Comment("Серия")
	@Persist @Required
	public String getSeries() {return theSeries;}
	public void setSeries(String aSeries) {theSeries = aSeries;}

	/** Срок годности */
	@Comment("Срок годности")
	@Persist @Required @DoDateString @DateString
	public String getExpirationDate() {return theExpirationDate;}
	public void setExpirationDate(String aExpirationDate) {theExpirationDate = aExpirationDate;}

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
	private String theExpirationDate;
	/** Серия */
	private String theSeries;
	/** Реактив */
	private Long theReagent;
}
