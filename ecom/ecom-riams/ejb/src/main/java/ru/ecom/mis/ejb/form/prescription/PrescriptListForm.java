package ru.ecom.mis.ejb.form.prescription;

import lombok.Setter;
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
@Setter
public class PrescriptListForm extends AbstractPrescriptionListForm{
	
	/** Операционная */
	@Comment("Операционная")
	public Long getSurgCabinet() {return surgCabinet;}
	/** Операционная */
	private Long surgCabinet;
	
	/** Шаблон листа назначений */
	@Comment("Шаблон листа назначений")
	@Persist
	public Long getTemplate() {return template;}
	/** Период актуальности */
	@Comment("Период актуальности")
	@Persist
	public String getPeriodActual() {return periodActual;}

	/** Период актуальности */
	private String periodActual;

	/** Шаблон листа назначений */
	private Long template;
	
	/** Форма диеты */
	@Comment("Форма диеты")
	public DietPrescriptionForm getDietForm() {return dietForm;}

	/** Режим */
	@Comment("Режим")
	public ModePrescriptionForm getModeForm() {return modeForm;}

	/** Режим */
	private ModePrescriptionForm modeForm = new ModePrescriptionForm();
	/** Форма диеты */
	private DietPrescriptionForm dietForm = new DietPrescriptionForm();
	
	
	/** Диеты создавать */
	@Comment("Диеты создавать")
	public Boolean getIsDiet() {return isDiet;}

	/** Режим создавать */
	@Comment("Режим создавать")
	public Boolean getIsMode() {return isMode;}

	/** Добавить лекарственные назначения */
	@Comment("Добавить лекарственные назначения")
	public Boolean getIsDrug() {return isDrug;}

	/** Добавить информацию по функциональной диагностике */
	@Comment("Добавить информацию по функциональной диагностике")
	public Boolean getIsFuncDiag() {return isFuncDiag;}

	/** Добавить информацию по лаборатории */
	@Comment("Добавить информацию по лаборатории")
	public Boolean getIsLabSurvey() {return isLabSurvey;}

	/** Добавить операцию */
	@Comment("Добавить операцию")
	public Boolean getIsSurgOperation() {return isSurgOperation;}

	/** Добавить койко-день */
	@Comment("Добавить койко-день")
	public Boolean getIsHospital() {
		return isHospital;
	}

	/** Добавить койко-день */
	private Boolean isHospital;
	/** Добавить операцию */
	private Boolean isSurgOperation;
	
	/** Добавить информацию по лаборатории */
	private Boolean isLabSurvey;
	/** Добавить информацию по функциональной диагностике */
	private Boolean isFuncDiag;
	/** Добавить лекарственные назначения */
	private Boolean isDrug;
	/** Режим создавать */
	private Boolean isMode;
	/** Диеты создавать */
	private Boolean isDiet;
	/** Дата назначения */
	@Comment("Дата назначения")
	@DateString @DoDateString
	public String getStartDate() {
		return startDate;
	}

	/** Дата назначения */
	private String startDate;
	
	/** Лек. ср-во 1 */
	@Comment("Лек. ср-во 1")
	public DrugPrescriptionForm getDrugForm1() {return drugForm1;}

	/** Лек. ср-во 2 */
	@Comment("Лек. ср-во 2")
	public DrugPrescriptionForm getDrugForm2() {return drugForm2;}

	/** Лек. ср-во 3 */
	@Comment("Лек. ср-во 3")
	public DrugPrescriptionForm getDrugForm3() {return drugForm3;}

	/** Лек. ср-во 4 */
	@Comment("Лек. ср-во 4")
	public DrugPrescriptionForm getDrugForm4() {return drugForm4;}

	/** Лек. ср-во 5 */
	@Comment("Лек. ср-во 5")
	public DrugPrescriptionForm getDrugForm5() {return drugForm5;}

	/** Лек. ср-во 6 */
	@Comment("Лек. ср-во 6")
	public DrugPrescriptionForm getDrugForm6() {return drugForm6;}

	/** Лек. ср-во 7 */
	@Comment("Лек. ср-во 7")
	public DrugPrescriptionForm getDrugForm7() {return drugForm7;}

	/** Лек. ср-во 8 */
	@Comment("Лек. ср-во 8")
	public DrugPrescriptionForm getDrugForm8() {return drugForm8;}

	/** Лек. ср-во 9 */
	@Comment("Лек. ср-во 9")
	public DrugPrescriptionForm getDrugForm9() {return drugForm9;}

	/** Лек. ср-во 10 */
	@Comment("Лек. ср-во 10")
	public DrugPrescriptionForm getDrugForm10() {return drugForm10;}

	/** Лек. ср-во 11 */
	@Comment("Лек. ср-во 11")
	public DrugPrescriptionForm getDrugForm11() {return drugForm11;}

	/** Дата начала койко-дня */
	@Comment("Дата начала койко-дня")
	@DateString @DoDateString
	public String getHospDate() {return hospDate;}
	/** Дата начала койко-дня */
	private String hospDate;

	/** Койко-день */
	@Comment("Койко-день")
	
	public String getHospServicies() {return hospServicies;}
	/** Койко-день */
	private String hospServicies;

	/** Лабораторные исследования */
	@Comment("Лабораторные исследования")
	public String getLabServicies() {
		return labServicies;
	}

	/** Лабораторные исследования */
	private String labServicies;
	
	/** Дата по лаб. исследованию */
	@Comment("Дата по лаб. исследованию")
	@DateString @DoDateString
	public String getLabDate() {return labDate;}

	/** Дата по лаб. исследованию */
	private String labDate;
		
	/** Функциональные исследования */
	@Comment("Функциональные исследования")
	public String getFuncServicies() {
		return funcServicies;
	}

	/** Кабинет для лабораторных исследования*/
	@Comment("Кабинет для лабораторных исследования")
	public String getLabCabinet() {
		return labCabinet;
	}

	/** Кабинет для лабораторных исследования */
	private String labCabinet;
	/** Функциональные исследования */
	private String funcServicies;
	
	/** Дата функционального исследования */
	@Comment("Дата функционального исследования")
	@DateString @DoDateString
	public String getFuncDate() {
		return funcDate;
	}

	/** Дата функционального исследования */
	private String funcDate;
	/** Кабинет для функ. исследования */
	@Comment("Кабинет для функ. исследования")
	public String getFuncCabinet() {
		return funcCabinet;
	}

	/** Кабинет для функ. исследования */
	private String funcCabinet;

	/** Лек. ср-во 1 */
	private DrugPrescriptionForm drugForm11= new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm drugForm10= new DrugPrescriptionForm() ;
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm drugForm9 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm drugForm8 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm drugForm7 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm drugForm6 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm drugForm5 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm drugForm4 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm drugForm3 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm drugForm2 = new DrugPrescriptionForm();
	/** Лек. ср-во 1 */
	private DrugPrescriptionForm drugForm1 = new DrugPrescriptionForm();

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist
	public Long getWorkFunction() {return workFunction;}

	private Long workFunction ;
	
	/** Отделение для забора крови */
	@Comment("Отделение для забора крови")
	public Long getLabDepartment() {return labDepartment;}

	/** Отделение для забора крови */
	private Long labDepartment;
	
	/** Время для направления на диагностику */
	@Comment("Время для направления на диагностику")
	public Long getFuncCalTime() {
		return funcCalTime;
	}

	/** Время для направления на диагностику */
	private Long funcCalTime;
	
	/** Операци */
	@Comment("Операци")
	public String getSurgServicies() {return surgServicies;}
	/** Операци */
	private String surgServicies;
	
	/** Дата операции */
	@Comment("Дата операции")
	@DateString @DoDateString
	public String getSurgDate() {return surgDate;}
	/** Дата операции */
	private String surgDate;
	
	/** Время для направления на операцию */
	@Comment("Время для направления на операцию")
	public Long getSurgCalTime() {return surgCalTime;}
	/** Время для направления на операцию */
	private Long surgCalTime;
	
	/** Дата для направления на операцию */
	@Comment("Дата для направления на операцию")
	public Long getSurgCalDate() {return surgCalDate;}
	/** Дата для направления на операцию */
	private Long surgCalDate;

}
