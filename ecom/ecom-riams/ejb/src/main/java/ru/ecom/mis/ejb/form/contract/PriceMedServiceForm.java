package ru.ecom.mis.ejb.form.contract;

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
public class PriceMedServiceForm extends IdEntityForm{
	/**
	 * Позиция прейскуранта
	 */
	@Comment("Позиция прейскуранта")
	@Persist
	public Long getPricePosition() {
		return thePricePosition;
	}
	public void setPricePosition(Long aPricePosition) {
		thePricePosition = aPricePosition;
	}
	/**
	 * Позиция прейскуранта
	 */
	private Long thePricePosition;
	/**
	 * Медицинская услуга
	 */
	@Comment("Медицинская услуга")
	@Persist 
	public Long getMedService() {
		return theMedService;
	}
	public void setMedService(Long aMedService) {
		theMedService = aMedService;
	}
	/**
	 * Медицинская услуга
	 */
	private Long theMedService;
	/**
	 * Дата начала действия
	 */
	@Comment("Дата начала действия")
	@Persist
	@DateString @DoDateString
	public String getDateFrom() {
		return theDateFrom;
	}
	public void setDateFrom(String aDateFrom) {
		theDateFrom = aDateFrom;
	}
	/**
	 * Дата начала действия
	 */
	private String theDateFrom;
	/**
	 * Дата окончания действия
	 */
	@Comment("Дата окончания действия")
	@Persist
	@DateString @DoDateString
	public String getDateTo() {
		return theDateTo;
	}
	public void setDateTo(String aDateTo) {
		theDateTo = aDateTo;
	}
	/**
	 * Дата окончания действия
	 */
	private String theDateTo;
	
	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@Persist
	public String getMedServiceInfo() {
		return theMedServiceInfo;
	}

	public void setMedServiceInfo(String aMedServiceInfo) {
		theMedServiceInfo = aMedServiceInfo;
	}

	/** Медицинская услуга */
	private String theMedServiceInfo;
	
	/** Услуга */
	@Comment("Услуга")
	public MedServiceForm getMedServiceForm() {return theMedServiceForm;}
	public void setMedServiceForm(MedServiceForm aMedServiceForm) {theMedServiceForm = aMedServiceForm;}

	/** Услуга */
	private MedServiceForm theMedServiceForm = new MedServiceForm();
	
	/** Создать новую услугу */
	@Comment("Создать новую услугу")
	public Boolean getMedServiceIsCreate() {return theMedServiceIsCreate;}
	public void setMedServiceIsCreate(Boolean aMedServiceIsCreate) {theMedServiceIsCreate = aMedServiceIsCreate;}

	/** Создать новую услугу */
	private Boolean theMedServiceIsCreate;
}
