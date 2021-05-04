package ru.ecom.mis.ejb.uc.privilege.form.voc;

import lombok.Setter;
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
@Setter
public class VocDrugClassifyForm extends IdEntityForm {
	/** Код */
	@Comment("Код")
	@Persist
	public String getCode() {
		return code;
	}

	/** Код */
	private String code;
	/** Позиции классификаторов */
	@Comment("Позиции классификаторов")
	public String getDrugClassificatorPositions() {
		return drugClassificatorPositions;
	}

	/** Позиции классификаторов */
	private String drugClassificatorPositions;
	
	/** Производитель */
	@Comment("Производитель")
	@Persist
	public Long getDrugVendor() {
		return drugVendor;
	}

	/** Производитель */
	private Long drugVendor;
	
	/** Количество доз в упаковке */
	@Comment("Количество доз в упаковке")
	@Persist
	public Integer getPackingAmount() {
		return packingAmount;
	}

	/** Количество доз в упаковке */
	private Integer packingAmount;
	
	/** Дозировка */
	@Comment("Дозировка")
	@Persist
	public String getDozage() {
		return dozage;
	}

	/** Дозировка */
	private String dozage;
	
	/** Лекарственная форма */
	@Comment("Лекарственная форма")
	@Persist
	public Long getDrugForm() {
		return drugForm;
	}

	/** Лекарственная форма */
	private Long drugForm;
	
	/** Патентованное наименование */
	@Comment("Патентованное наименование")
	@Persist
	public Long getLicensedName() {
		return licensedName;
	}

	/** Патентованное наименование */
	private Long licensedName;
	
	/** Международное непатентованное наименование */
	@Comment("Международное непатентованное наименование")
	@Persist
	public Long getDrugUnlicensedName() {
		return drugUnlicensedName;
	}

	/** Международное непатентованное наименование */
	private Long drugUnlicensedName;
	
	/** Название */
	@Comment("Название")
	@Persist @Required
	public String getName() {
		return name;
	}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return username;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return createDate;}
	/** Дата создания */
	private String createDate;
	/** Пользователь */
	private String username;
	/** Название */
	private String name;
}
