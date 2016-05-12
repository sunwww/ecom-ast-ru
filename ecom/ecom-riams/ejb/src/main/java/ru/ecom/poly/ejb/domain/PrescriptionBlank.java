package ru.ecom.poly.ejb.domain;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Рецептурный бланк
 */
@Entity
@Table(schema="SQLUser")
public class PrescriptionBlank extends BaseEntity {

    /**
     * Серия *
     */
    public String getSeries() {
        return theSeries;
    }

    public void setSeries(String aSerie) {
        theSeries = aSerie;
    }

    /**
     * Номер *
     */
    public String getNumber() {
        return theNumber;
    }

    public void setNumber(String aNumber) {
        theNumber = aNumber;
    }

    /** Талоны **/
    @ManyToOne
    public Ticket getTicket() { return theTicket; }
    public void setTicket(Ticket aTicket) { theTicket = aTicket; }

    /** Талоны **/
    private Ticket theTicket;

    /**
     * Дата выписки *
     */
    public Date getWritingOutDate() {
        return theWritingOutDate;
    }

    public void setWritingOutDate(Date aWritingOutDate) {
        theWritingOutDate = aWritingOutDate;
    }

    /**
     * Дата выписки *
     */
    private Date theWritingOutDate;

    /**
     * Номер *
     */
    private String theNumber;

    /**
     * Серия *
     */
    private String theSeries;
    
    /** СМО */
	@Comment("СМО")
	@ManyToOne
	public MedCase getMedCase() {
		return theMedCase;
	}

	public void setMedCase(MedCase aMedCase) {
		theMedCase = aMedCase;
	}

	/** СМО */
	private MedCase theMedCase;
}
