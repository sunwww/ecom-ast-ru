package ru.ecom.poly.ejb.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.poly.ejb.domain.Ticket;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

/**
 * Талон на прием
 * @author stkacheva
 */

@EntityForm
@EntityFormPersistance(clazz = Ticket.class)
@Comment("Талон на прием")
@Parent(property = "medcard", parentForm = MedcardForm.class)
@WebTrail(comment = "Талон", nameProperties = "ticketInfo", view = "entityView-poly_short_ticket.do")
@EntityFormSecurityPrefix("/Policy/Poly/ShortTicket")
public class ShortTicketForm extends IdEntityForm {
    @Comment("Медицинская карта")
    @Persist @Required
    public Long getMedcard() { return theMedcard;}
    public void setMedcard(Long aMedcard) {theMedcard = aMedcard;}

    @Comment("Дата приема")
    @Persist @Required
    @DateString @DoDateString
    public String getDate() {return theDate;}
    public void setDate(String aDate) {theDate = aDate;}

    @Comment("Время приема")
    @Persist 
    @TimeString @DoTimeString
    public String getTime() { return theTime; }
    public void setTime(String aTime) { theTime = aTime; }
	
	/** Рабочая функция исполнения */
	@Comment("Рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aNewProperty) {theWorkFunction = aNewProperty;}

	
	/** Рабочая функция исполнения(Инфо) */
	@Comment("Рабочая функция исполнения(Инфо)")
	@Persist
	public String getWorkFunctionInfo() {return theWorkFunctionInfo;}
	public void setWorkFunctionInfo(String aNewProperty) {theWorkFunctionInfo = aNewProperty;}

    /** Информация по талону */
	@Comment("Информация по талону")
	@Persist
	public String getTicketInfo() {return theTicketInfo;}
	public void setTicketInfo(String aTicketInfo) {theTicketInfo = aTicketInfo;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getDateCreate() {return theDateCreate;}
	public void setDateCreate(String aDateCreate) {theDateCreate = aDateCreate;}

	/** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getTimeCreate() {return theTimeCreate;}
	public void setTimeCreate(String aTimeCreate) {theTimeCreate = aTimeCreate;}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getUsernameCreate() {return theUsernameCreate;}
	public void setUsernameCreate(String aUsernameCreate) {theUsernameCreate = aUsernameCreate;}

	/** Представитель */
	@Comment("Представитель")
	@Persist
	public Long getKinsman() {return theKinsman;}
	public void setKinsman(Long aKinsman) {theKinsman = aKinsman;}

	/** Представитель */
	private Long theKinsman;
	/** Пользователь, создавший запись */
	private String theUsernameCreate;
	/** Время создания */
	private String theTimeCreate;
	/** Дата создания */
	private String theDateCreate;
	/** Информация по талону */
	private String theTicketInfo;
	/** Время приема **/
    private String theTime;
	/** Рабочая функция исполнения */
	private Long theWorkFunction;
	/** Рабочая функция исполнения(Инфо) */
	private String theWorkFunctionInfo;
    /** Дата выдачи/создания(?) талона */
    private String theDate;
    /** Медицинская карта */
    private Long theMedcard;
}
