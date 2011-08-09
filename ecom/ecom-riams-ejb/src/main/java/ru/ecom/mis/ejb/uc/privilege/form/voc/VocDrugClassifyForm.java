package ru.ecom.mis.ejb.uc.privilege.form.voc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.uc.privilege.domain.VocDrugClassify;
import ru.ecom.mis.ejb.uc.privilege.form.interceptor.DrugSaveInterceptor;
import ru.ecom.mis.ejb.uc.privilege.form.interceptor.DrugViewInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= VocDrugClassify.class)
@WebTrail(comment = "Лекарственное средство (торг.)", nameProperties= {"name"}, view="entityView-voc_drug.do")
//@Parent(property="parent", parentForm=DrugClassificatorForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Drug")
@ASaveInterceptors(
        @AEntityFormInterceptor(DrugSaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(DrugViewInterceptor.class)
)
@ACreateInterceptors( {
	@AEntityFormInterceptor(DrugSaveInterceptor.class)
	
})
public class VocDrugClassifyForm extends IdEntityForm {
	/** Код */
	@Comment("Код")
	@Persist
	public String getCode() {
		return theCode;
	}

	public void setCode(String aCode) {
		theCode = aCode;
	}

	/** Код */
	private String theCode;
	/** Позиции классификаторов */
	@Comment("Позиции классификаторов")
	public String getDrugClassificatorPositions() {
		return theDrugClassificatorPositions;
	}

	public void setDrugClassificatorPositions(String aDrugClassificatorPositions) {
		theDrugClassificatorPositions = aDrugClassificatorPositions;
	}

	/** Позиции классификаторов */
	private String theDrugClassificatorPositions;
	
	/** Производитель */
	@Comment("Производитель")
	@Persist
	public Long getDrugVendor() {
		return theDrugVendor;
	}

	public void setDrugVendor(Long aDrugVendor) {
		theDrugVendor = aDrugVendor;
	}

	/** Производитель */
	private Long theDrugVendor;
	
	/** Количество доз в упаковке */
	@Comment("Количество доз в упаковке")
	@Persist
	public Integer getPackingAmount() {
		return thePackingAmount;
	}

	public void setPackingAmount(Integer aPackingAmount) {
		thePackingAmount = aPackingAmount;
	}

	/** Количество доз в упаковке */
	private Integer thePackingAmount;
	
	/** Дозировка */
	@Comment("Дозировка")
	@Persist
	public String getDozage() {
		return theDozage;
	}

	public void setDozage(String aDozage) {
		theDozage = aDozage;
	}

	/** Дозировка */
	private String theDozage;
	
	/** Лекарственная форма */
	@Comment("Лекарственная форма")
	@Persist
	public Long getDrugForm() {
		return theDrugForm;
	}

	public void setDrugForm(Long aDrugForm) {
		theDrugForm = aDrugForm;
	}

	/** Лекарственная форма */
	private Long theDrugForm;
	
	/** Патентованное наименование */
	@Comment("Патентованное наименование")
	@Persist
	public Long getLicensedName() {
		return theLicensedName;
	}

	public void setLicensedName(Long aLicensedName) {
		theLicensedName = aLicensedName;
	}

	/** Патентованное наименование */
	private Long theLicensedName;
	
	/** Международное непатентованное наименование */
	@Comment("Международное непатентованное наименование")
	@Persist
	public Long getDrugUnlicensedName() {
		return theDrugUnlicensedName;
	}

	public void setDrugUnlicensedName(Long aDrugUnlicensedName) {
		theDrugUnlicensedName = aDrugUnlicensedName;
	}

	/** Международное непатентованное наименование */
	private Long theDrugUnlicensedName;
	
	/** Название */
	@Comment("Название")
	@Persist @Required
	public String getName() {
		return theName;
	}

	public void setName(String aName) {
		theName = aName;
	}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата создания */
	private String theCreateDate;
	/** Пользователь */
	private String theUsername;
	/** Название */
	private String theName;
}
