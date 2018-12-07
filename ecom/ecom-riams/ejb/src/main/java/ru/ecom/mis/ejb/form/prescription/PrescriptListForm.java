package ru.ecom.mis.ejb.form.prescription;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.prescription.PrescriptList;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.prescription.interceptor.PrescriptListCreateInterceptor;
import ru.ecom.mis.ejb.form.prescription.interceptor.PrescriptListPreCreateInterceptor;
import ru.ecom.mis.ejb.form.prescription.template.DietPrescriptionForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

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
	
	/** Операционная */
	@Comment("Операционная")
	public Long getSurgCabinet() {return theSurgCabinet;}
	public void setSurgCabinet(Long aSurgCabinet) {theSurgCabinet = aSurgCabinet;}
	/** Операционная */
	private Long theSurgCabinet;
	
	/** Шаблон листа назначений */
	@Comment("Шаблон листа назначений")
	@Persist
	public Long getTemplate() {return theTemplate;}
	public void setTemplate(Long aTemplate) {theTemplate = aTemplate;}
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
	public DietPrescriptionForm getDietForm() {return theDietForm;}
	public void setDietForm(DietPrescriptionForm aDietPrescriptionForm) {theDietForm = aDietPrescriptionForm;}

	/** Режим */
	@Comment("Режим")
	public ModePrescriptionForm getModeForm() {return theModeForm;}
	public void setModeForm(ModePrescriptionForm aModePrescriptionForm) {theModeForm = aModePrescriptionForm;}

	/** Режим */
	private ModePrescriptionForm theModeForm = new ModePrescriptionForm();
	/** Форма диеты */
	private DietPrescriptionForm theDietForm = new DietPrescriptionForm();
	
	
	/** Диеты создавать */
	@Comment("Диеты создавать")
	public Boolean getIsDiet() {return theIsDiet;}
	public void setIsDiet(Boolean aIsDiet) {theIsDiet = aIsDiet;}

	/** Режим создавать */
	@Comment("Режим создавать")
	public Boolean getIsMode() {return theIsMode;}
	public void setIsMode(Boolean aIsMode) {theIsMode = aIsMode;}

	/** Добавить лекарственные назначения */
	@Comment("Добавить лекарственные назначения")
	public Boolean getIsDrug() {return theIsDrug;}
	public void setIsDrug(Boolean aIsDrug) {theIsDrug = aIsDrug;}

	/** Добавить информацию по функциональной диагностике */
	@Comment("Добавить информацию по функциональной диагностике")
	public Boolean getIsFuncDiag() {return theIsFuncDiag;}
	public void setIsFuncDiag(Boolean aIsFuncDiag) {theIsFuncDiag = aIsFuncDiag;}

	/** Добавить информацию по лаборатории */
	@Comment("Добавить информацию по лаборатории")
	public Boolean getIsLabSurvey() {return theIsLabSurvey;}
	public void setIsLabSurvey(Boolean aIsLabSurvey) {theIsLabSurvey = aIsLabSurvey;}

	/** Добавить операцию */
	@Comment("Добавить операцию")
	public Boolean getIsSurgOperation() {return theIsSurgOperation;}
	public void setIsSurgOperation(Boolean aIsSurgOperation) {theIsSurgOperation = aIsSurgOperation;	}

	/** Добавить койко-день */
	@Comment("Добавить койко-день")
	public Boolean getIsHospital() {
		return theIsHospital;
	}

	public void setIsHospital(Boolean aIsHospital) {
		theIsHospital = aIsHospital;
	}

	/** Добавить койко-день */
	private Boolean theIsHospital;
	/** Добавить операцию */
	private Boolean theIsSurgOperation;
	
	/** Добавить информацию по лаборатории */
	private Boolean theIsLabSurvey;
	/** Добавить информацию по функциональной диагностике */
	private Boolean theIsFuncDiag;
	/** Добавить лекарственные назначения */
	private Boolean theIsDrug;
	/** Режим создавать */
	private Boolean theIsMode;
	/** Диеты создавать */
	private Boolean theIsDiet;
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

	/** Дата начала койко-дня */
	@Comment("Дата начала койко-дня")
	@DateString @DoDateString
	public String getHospDate() {return theHospDate;}
	public void setHospDate(String aHospDate) {theHospDate = aHospDate;}
	/** Дата начала койко-дня */
	private String theHospDate;

	/** Койко-день */
	@Comment("Койко-день")
	
	public String getHospServicies() {return theHospServicies;}
	public void setHospServicies(String aHospServicies) {theHospServicies = aHospServicies;}
	/** Койко-день */
	private String theHospServicies;

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
	public String getLabDate() {return theLabDate;}
	public void setLabDate(String aLabDate) {theLabDate = aLabDate;}

	/** Дата по лаб. исследованию */
	private String theLabDate;
		
	/** Функциональные исследования */
	@Comment("Функциональные исследования")
	public String getFuncServicies() {
		return theFuncServicies;
	}

	public void setFuncServicies(String aFuncServicies) {
		theFuncServicies = aFuncServicies;
	}
	/** Кабинет для лабораторных исследования*/
	@Comment("Кабинет для лабораторных исследования")
	public String getLabCabinet() {
		return theLabCabinet;
	}

	public void setLabCabinet(String aLabCabinet) {
		theLabCabinet = aLabCabinet;
	}

	/** Кабинет для лабораторных исследования */
	private String theLabCabinet;
	/** Функциональные исследования */
	private String theFuncServicies;
	
	/** Дата функционального исследования */
	@Comment("Дата функционального исследования")
	@DateString @DoDateString
	public String getFuncDate() {
		return theFuncDate;
	}

	public void setFuncDate(String aFuncDate) {
		theFuncDate = aFuncDate;
	}

	/** Дата функционального исследования */
	private String theFuncDate;
	/** Кабинет для функ. исследования */
	@Comment("Кабинет для функ. исследования")
	public String getFuncCabinet() {
		return theFuncCabinet;
	}

	public void setFuncCabinet(String aFuncCabinet) {
		theFuncCabinet = aFuncCabinet;
	}

	/** Кабинет для функ. исследования */
	private String theFuncCabinet;
	
	/** Список функциональных исследований */

	//Проверем, вроде как, этот список нигде не используем
	/*	@Comment("Список функциональных исследований")
	public String getFuncList() {
		return theFuncList;
	}

	public void setFuncList(String aFuncList) {
		theFuncList = aFuncList;
	}
*/
	/** Список функциональных исследований */
/*	private String theFuncList;*/
	
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
	
	/** Отделение для забора крови */
	@Comment("Отделение для забора крови")
	public Long getLabDepartment() {return theLabDepartment;}
	public void setLabDepartment(Long aLabDepartment) {theLabDepartment = aLabDepartment;}

	/** Отделение для забора крови */
	private Long theLabDepartment;
	
	/** Время для направления на диагностику */
	@Comment("Время для направления на диагностику")
	public Long getFuncCalTime() {
		return theFuncCalTime;
	}

	public void setFuncCalTime(Long aFuncCalTime) {
		theFuncCalTime = aFuncCalTime;
	}

	/** Время для направления на диагностику */
	private Long theFuncCalTime;
	
	/** Операци */
	@Comment("Операци")
	public String getSurgServicies() {return theSurgServicies;}
	public void setSurgServicies(String aSurgServicies) {theSurgServicies = aSurgServicies;	}
	/** Операци */
	private String theSurgServicies;
	
	/** Дата операции */
	@Comment("Дата операции")
	@DateString @DoDateString
	public String getSurgDate() {return theSurgDate;}
	public void setSurgDate(String aSurgDate) {theSurgDate = aSurgDate;}
	/** Дата операции */
	private String theSurgDate;
	
	/** Время для направления на операцию */
	@Comment("Время для направления на операцию")
	public Long getSurgCalTime() {return theSurgCalTime;}
	public void setSurgCalTime(Long aSurgCalTime) {	theSurgCalTime = aSurgCalTime;}
	/** Время для направления на операцию */
	private Long theSurgCalTime;
	
	/** Дата для направления на операцию */
	@Comment("Дата для направления на операцию")
	public Long getSurgCalDate() {return theSurgCalDate;}
	public void setSurgCalDate(Long aSurgCalDate) {theSurgCalDate = aSurgCalDate;}
	/** Дата для направления на операцию */
	private Long theSurgCalDate;

}
