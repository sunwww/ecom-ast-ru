package ru.ecom.mis.ejb.form.medcase.transfusion;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.Transfusion;
import ru.ecom.mis.ejb.domain.medcase.TransfusionReagent;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

import javax.persistence.OneToOne;

@EntityForm
@EntityFormPersistance(clazz= TransfusionReagent.class)
@Comment("Реактив")
@WebTrail(comment = "Реактив", nameProperties= "id", view="entityView-trans_reagent.do",list = "entityParentList-trans_transfusion.do")
@Parent(property="transfusion", parentForm= TransfusionForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Transfusion")
@Setter
public class TransfusionReagentForm extends IdEntityForm {
	/** Реактив */
	@Comment("Реактив")
	@Persist
	public Long getReagent() {return reagent;}

	/** Серия */
	@Comment("Серия")
	@Persist
	public String getSeries() {return series;}

	/** Срок годности */
	@Comment("Срок годности")
	@Persist @DoDateString @DateString
	public String getExpirationDate() {return expirationDate;}

	/** Порядковый номер */
	@Comment("Порядковый номер")
	public Integer getNumberReagent() {return numberReagent;}

	/** Переливание */
	@Comment("Переливание")
	@OneToOne
	public Transfusion getTransfusion() {return transfusion;}

	/** Переливание */
	private Transfusion transfusion;
	/** Порядковый номер */
	private Integer numberReagent;
	/** Срок годности */
	private String expirationDate;
	/** Серия */
	private String series;
	/** Реактив */
	private Long reagent;
}