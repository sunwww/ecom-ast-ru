package ru.ecom.mis.ejb.form.prescription;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.prescription.PrescriptList;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Лист назначений
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance(clazz = PrescriptList.class)
@Comment("Лист назначений")
@WebTrail(comment = "Лист назначений", nameProperties = "id",list="entityParentList-pres_prescriptList.do", view = "entityParentView-pres_prescriptList.do")
@Parent(property = "medCase", parentForm = MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/Prescript")
public class PrescriptListForm extends AbstractPrescriptionListForm{
	
	/** Шаблон листа назначений */
	@Comment("Шаблон листа назначений")
	@Persist
	public Long getTemplate() {
		return theTemplate;
	}

	public void setTemplate(Long aTemplate) {
		theTemplate = aTemplate;
	}
	/** Период актуальности */
	@Comment("Период актуальности")
	@Persist
	public String getPeriodActual() {return thePeriodActual;}
	public void setPeriodActual(String aPeriodActual) {thePeriodActual = aPeriodActual;}

	/** Период актуальности */
	private String thePeriodActual;

	/** Шаблон листа назначений */
	private Long theTemplate;
	


}
