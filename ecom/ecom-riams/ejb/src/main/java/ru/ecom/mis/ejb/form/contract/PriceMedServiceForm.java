package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.mis.ejb.domain.contract.PriceMedService;
import ru.ecom.mis.ejb.form.contract.interceptor.PriceMedServiceSaveInterceptor;
import ru.ecom.mis.ejb.form.medcase.MedServiceForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

@EntityForm
@EntityFormPersistance(clazz = PriceMedService.class)
@Comment("Медицинская услуга прескуранта")
@WebTrail(comment = "Медицинская услуга прескуранта", nameProperties= "id", list="entityParentList-contract_priceMedService.do", view="entityParentView-contract_priceMedService.do")
@ACreateInterceptors({
    //@AEntityFormInterceptor(PriceMedServiceCreateInterceptor.class),
       @AEntityFormInterceptor(PriceMedServiceSaveInterceptor.class)
})

@ASaveInterceptors(
       @AEntityFormInterceptor(PriceMedServiceSaveInterceptor.class)
)
@Parent(property="pricePosition", parentForm=PricePositionForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/PriceList/PricePosition/PriceMedService")
@Setter
public class PriceMedServiceForm extends IdEntityForm{
	/**
	 * Позиция прейскуранта
	 */
	@Comment("Позиция прейскуранта")
	@Persist
	public Long getPricePosition() {
		return pricePosition;
	}
	/**
	 * Позиция прейскуранта
	 */
	private Long pricePosition;
	/**
	 * Медицинская услуга
	 */
	@Comment("Медицинская услуга")
	@Persist 
	public Long getMedService() {
		return medService;
	}
	/**
	 * Медицинская услуга
	 */
	private Long medService;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	@Persist
	@DateString @DoDateString
	public String getDateFrom() {
		return dateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private String dateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return dateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private String dateTo;
	
	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@Persist
	public String getMedServiceInfo() {
		return medServiceInfo;
	}

	/** Медицинская услуга */
	private String medServiceInfo;
	
	/** Услуга */
	@Comment("Услуга")
	public MedServiceForm getMedServiceForm() {return medServiceForm;}

	/** Услуга */
	private MedServiceForm medServiceForm = new MedServiceForm();
	
	/** Создать новую услугу */
	@Comment("Создать новую услугу")
	public Boolean getMedServiceIsCreate() {return medServiceIsCreate;}

	/** Создать новую услугу */
	private Boolean medServiceIsCreate;
}
