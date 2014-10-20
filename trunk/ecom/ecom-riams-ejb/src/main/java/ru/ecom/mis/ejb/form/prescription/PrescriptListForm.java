package ru.ecom.mis.ejb.form.prescription;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.prescription.PrescriptList;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.DepartmentMedCaseCreateInterceptor;
import ru.ecom.mis.ejb.form.patient.interceptors.PatientCreateInterceptor;
import ru.ecom.mis.ejb.form.prescription.interceptor.PrescriptListCreateInterceptor;
import ru.ecom.mis.ejb.form.prescription.interceptor.PrescriptListPreCreateInterceptor;
import ru.ecom.mis.ejb.form.prescription.template.DietPrescriptionForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

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
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(PrescriptListPreCreateInterceptor.class)
)
@ACreateInterceptors({
    @AEntityFormInterceptor(PrescriptListCreateInterceptor.class)
})
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
	
	/** Форма диеты */
	@Comment("Форма диеты")
	public DietPrescriptionForm getDietForm() {
		return theDietForm;
	}

	public void setDietForm(DietPrescriptionForm aDietPrescriptionForm) {
		theDietForm = aDietPrescriptionForm;
	}

	/** Режим */
	@Comment("Режим")
	public ModePrescriptionForm getModeForm() {
		return theModeForm;
	}

	public void setModeForm(ModePrescriptionForm aModePrescriptionForm) {
		theModeForm = aModePrescriptionForm;
	}

	/** Режим */
	private ModePrescriptionForm theModeForm = new ModePrescriptionForm();
	/** Форма диеты */
	private DietPrescriptionForm theDietForm = new DietPrescriptionForm();
	
	/** Дата назначения */
	@Comment("Дата назначения")
	@DateString @DoDateString
	public String getStartDate() {
		return theStartDate;
	}

	public void setStartDate(String aStartDate) {
		theStartDate = aStartDate;
	}

	
	/** Дата назначения */
	private String theStartDate;
	
	/** Лек. ср-во 1 */
	@Comment("Лек. ср-во 1")
	public DrugPrescriptionForm getDrugForm1() {return theDrugForm1;}
	public void setDrugForm1(DrugPrescriptionForm aDrugForm1) {theDrugForm1 = aDrugForm1;}

	/** Лек. ср-во 2 */
	@Comment("Лек. ср-во 2")
	public DrugPrescriptionForm getDrugForm2() {return theDrugForm2;}
	public void setDrugForm2(DrugPrescriptionForm aDrugForm2) {theDrugForm2 = aDrugForm2;}

	/** Лек. ср-во 3 */
	@Comment("Лек. ср-во 3")
	public DrugPrescriptionForm getDrugForm3() {return theDrugForm3;}
	public void setDrugForm3(DrugPrescriptionForm aDrugForm3) {theDrugForm3 = aDrugForm3;}

	/** Лек. ср-во 4 */
	@Comment("Лек. ср-во 4")
	public DrugPrescriptionForm getDrugForm4() {return theDrugForm4;}
	public void setDrugForm4(DrugPrescriptionForm aDrugForm4) {theDrugForm4 = aDrugForm4;}

	/** Лек. ср-во 5 */
	@Comment("Лек. ср-во 5")
	public DrugPrescriptionForm getDrugForm5() {return theDrugForm5;}
	public void setDrugForm5(DrugPrescriptionForm aDrugForm5) {theDrugForm5 = aDrugForm5;}

	/** Лек. ср-во 6 */
	@Comment("Лек. ср-во 6")
	public DrugPrescriptionForm getDrugForm6() {return theDrugForm6;}
	public void setDrugForm6(DrugPrescriptionForm aDrugForm6) {theDrugForm6 = aDrugForm6;}

	/** Лек. ср-во 7 */
	@Comment("Лек. ср-во 7")
	public DrugPrescriptionForm getDrugForm7() {return theDrugForm7;}
	public void setDrugForm7(DrugPrescriptionForm aDrugForm7) {theDrugForm7 = aDrugForm7;}

	/** Лек. ср-во 8 */
	@Comment("Лек. ср-во 8")
	public DrugPrescriptionForm getDrugForm8() {return theDrugForm8;}
	public void setDrugForm8(DrugPrescriptionForm aDrugForm8) {theDrugForm8 = aDrugForm8;}

	/** Лек. ср-во 9 */
	@Comment("Лек. ср-во 9")
	public DrugPrescriptionForm getDrugForm9() {return theDrugForm9;}
	public void setDrugForm9(DrugPrescriptionForm aDrugForm9) {theDrugForm9 = aDrugForm9;}

	/** Лек. ср-во 10 */
	@Comment("Лек. ср-во 10")
	public DrugPrescriptionForm getDrugForm10() {return theDrugForm10;}
	public void setDrugForm10(DrugPrescriptionForm aDrugForm10) {theDrugForm10 = aDrugForm10;}

	/** Лек. ср-во 11 */
	@Comment("Лек. ср-во 11")
	public DrugPrescriptionForm getDrugForm11() {return theDrugForm11;}
	public void setDrugForm11(DrugPrescriptionForm aDrugForm11) {theDrugForm11 = aDrugForm11;}

	/** Лабораторные исследования */
	@Comment("Лабораторные исследования")
	public String getLabServicies() {
		return theLabServicies;
	}

	public void setLabServicies(String aLabServicies) {
		theLabServicies = aLabServicies;
	}
	
	/** Лабораторные исследования */
	private String theLabServicies;
	
	/** Дата по лаб. исследованию */
	@Comment("Дата по лаб. исследованию")
	@DateString @DoDateString
	public String getDateLab() {
		return theDateLab;
	}

	public void setDateLab(String aDateLab) {
		theDateLab = aDateLab;
	}

	/** Дата по лаб. исследованию */
	private String theDateLab;

	
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm theDrugForm11= new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm theDrugForm10= new DrugPrescriptionForm() ;
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm theDrugForm9 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm theDrugForm8 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm theDrugForm7 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm theDrugForm6 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm theDrugForm5 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm theDrugForm4 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm theDrugForm3 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm theDrugForm2 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm theDrugForm1 = new DrugPrescriptionForm();

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}

	private Long theWorkFunction ;
}
