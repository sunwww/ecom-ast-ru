package ru.ecom.poly.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 15.01.2007
 * Time: 22:10:38
 * To change this template use File | Settings | File Templates.
 */
@Entity
@AIndexes({
	@AIndex(properties={"ticket"})
	,@AIndex(properties={"medService"})
	,@AIndex(properties={"ticket","medService"})
})
@Table(schema="SQLUser")
public class RenderedService extends BaseEntity {
   /** Талоны **/
    @ManyToOne
    public Ticket getTicket() { return theTicket; }
    public void setTicket(Ticket aTicket) { theTicket = aTicket; }

    /** Мед.услуга */
	@Comment("Мед.услуга")
	@ManyToOne
	public MedService getMedService() {return theMedService;}
	public void setMedService(MedService aMedService) {theMedService = aMedService;}

	@Transient
	public String getMedServiceName() {
		return theMedService!=null?theMedService.getName():"" ;
		
	}
	/** Мед.услуга */
	private MedService theMedService;
    /** Талоны **/
    private Ticket theTicket;


}
