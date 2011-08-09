package ru.ecom.poly.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.poly.ejb.domain.PrescriptionBlank;
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
 * Рецептурный бланк
 * User: Yoda Date: 15.01.2007 Time: 13:28:59
 */
@EntityForm
@EntityFormPersistance(clazz = PrescriptionBlank.class)
@Comment("Рецептурный бланк")
@WebTrail(comment = "Рецептурный бланк", nameProperties = "writingOutDate", view = "entityView-prescriptionBlank.do")
@EntityFormSecurityPrefix("/Policy/Poly/PrescriptionBlank")
@Parent(property = "ticket", parentForm = TicketForm.class)
public class PrescriptionBlankForm extends IdEntityForm {
    /**
     * Номер *
     */

    @Persist
    @Comment("Серия бланка")
    @Required
    public String getSeries() {
        return theSeries;
    }

    public void setSeries(String theSeries) {
        this.theSeries = theSeries;
    }

    @Persist
    @Comment("Номер бланка")
    @Required
    public String getNumber() {
        return theNumber;
    }

    public void setNumber(String aNumber) {
        theNumber = aNumber;
    }

    /**
     * Номер *
     */
    private String theNumber;

    /**
     * Дата выписки *
     */
    @Persist
    @Comment("Дата выписки")
    @Required
    @DateString
    @DoDateString
    public String getWritingOutDate() {
        return theWritingOutDate;
    }

    public void setWritingOutDate(String aWritingOutDate) {
        theWritingOutDate = aWritingOutDate;
    }

    /**
     * Дата выписки *
     */
    private String theWritingOutDate;

    /**
     * Талон *
     */
    @Persist
    @Comment("Талон")
    public Long getTicket() {
        return theTicket;
    }

    public void setTicket(Long aTicket) {
        theTicket = aTicket;
    }

    /**
     * Талон *
     */
    private Long theTicket;

    /**
     * Серия *
     */
    private String theSeries;
    
    /** СМО */
	@Comment("СМО")
	@Persist
	public Long getMedCase() {
		return theMedCase;
	}

	public void setMedCase(Long aMedCase) {
		theMedCase = aMedCase;
	}

	/** СМО */
	private Long theMedCase;


}
