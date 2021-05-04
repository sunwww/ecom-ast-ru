package ru.ecom.mis.ejb.form.prescription.template;

import lombok.Setter;
import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.jaas.ejb.domain.SecGroup;
import ru.ecom.mis.ejb.domain.prescription.PrescriptListTemplate;
import ru.ecom.mis.ejb.form.prescription.AbstractPrescriptionListForm;
import ru.ecom.mis.ejb.form.prescription.template.interceptors.PrescriptListCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

//import ru.ecom.mis.ejb.form.prescription.interceptor.PrescriptListPreCreateInterceptor;

/**
 * Шаблон листа назначения
 * @author STkacheva
 */
@EntityForm
@EntityFormPersistance(clazz=PrescriptListTemplate.class)
@Comment("Шаблон листа назначения")
@WebTrail(comment = "Шаблон листа назначения", nameProperties = { "name" }, view = "entityView-pres_template.do")
@EntityFormSecurityPrefix("/Policy/Mis/Prescription/Template")
@ACreateInterceptors({
    @AEntityFormInterceptor(PrescriptListCreateInterceptor.class)
})
@Setter
public class PrescriptListForm extends AbstractPrescriptionListForm {
	
	/** Отделение (лаборатория) */
	@Comment("Отделение (лаборатория)")
	public Long getLabDepartment() {return labDepartment;}
	/** Отделение (лаборатория) */
	private Long labDepartment;
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist 
	public Long getWorkFunction() {return workFunction;}
	private Long workFunction ;
	
	/** Тип назначения */
	@Comment("Тип назначения")
	@Persist 
	public Long getPrescriptType() {return prescriptType;}
	private Long prescriptType;
	
	/** Категории классификатора */
	@Comment("Категории классификатора")
	@PersistManyToManyOneProperty(collectionGenericType=TemplateCategory.class)
	@Persist
	public String getCategories() {return categories;}

	/** Название шаблона */
	@Comment("Название шаблона")
	@Persist @Required
	public String getName() {return name;}
	private String name ;
	/** Категории классификатора */
	private String categories;
	
	/** Группы пользователей */
	@Comment("Группы пользователей")
	@Persist @PersistManyToManyOneProperty(collectionGenericType = SecGroup.class)
	public String getSecGroups() {return secGroups;}
	/** Группы пользователей */
	private String secGroups;

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
	
}
