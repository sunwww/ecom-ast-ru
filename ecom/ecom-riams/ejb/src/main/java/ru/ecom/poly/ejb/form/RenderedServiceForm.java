package ru.ecom.poly.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.poly.ejb.domain.RenderedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Медицинская услуга
 * User: morgun Date: 15.01.2007 Time: 22:19:02
 */
@EntityForm
@EntityFormPersistance(clazz = RenderedService.class)
@Comment("Медицинская услуга")
@WebTrail(comment = "Медицинская услуга", nameProperties = "medServiceName", view = "entityView-renderedService.do")
@EntityFormSecurityPrefix("/Policy/Poly/RenderedService")
@Parent(property = "ticket", parentForm = TicketForm.class)
public class RenderedServiceForm extends IdEntityForm {
     /** @return Услуга **/
    @Persist
    @Comment("Услуга")
    public Long getMedService() { return theMedService; }
    public void setMedService(Long aMedService) { theMedService = aMedService; }

    /** @return Талон **/
    @Persist
    @Comment("Талон")
    public Long getTicket() { return theTicket; }
    public void setTicket(Long aTicket) { theTicket = aTicket; }
    
    /** Мед.услуга */
	@Comment("Мед.услуга")
	@Persist
	public String getMedServiceInfo() {return theMedServiceInfo;}
	public void setMedServiceInfo(String aMedServiceInfo) {theMedServiceInfo = aMedServiceInfo;}

	/** Мед.услуга */
	private String theMedServiceInfo;
    /** Талон **/
    private Long theTicket;

    /** Диагноз **/
    private Long theMedService;
}
