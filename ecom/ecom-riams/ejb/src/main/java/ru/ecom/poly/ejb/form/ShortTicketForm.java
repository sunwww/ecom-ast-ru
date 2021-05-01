package ru.ecom.poly.ejb.form;

import lombok.Setter;
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
@Setter
public class ShortTicketForm extends IdEntityForm {
    @Comment("Медицинская карта")
    @Persist @Required
    public Long getMedcard() { return medcard;}

    @Comment("Дата приема")
    @Persist @Required
    @DateString @DoDateString
    public String getDate() {return date;}

    @Comment("Время приема")
    @Persist 
    @TimeString @DoTimeString
    public String getTime() { return time; }

	/** Рабочая функция исполнения */
	@Comment("Рабочая функция исполнения")
	@Persist @Required
	public Long getWorkFunction() {return workFunction;}

	/** Рабочая функция исполнения(Инфо) */
	@Comment("Рабочая функция исполнения(Инфо)")
	@Persist
	public String getWorkFunctionInfo() {return workFunctionInfo;}

    /** Информация по талону */
	@Comment("Информация по талону")
	@Persist
	public String getTicketInfo() {return ticketInfo;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DateString @DoDateString
	public String getDateCreate() {return dateCreate;}

	/** Время создания */
	@Comment("Время создания")
	@Persist @TimeString @DoTimeString
	public String getTimeCreate() {return timeCreate;}

	/** Пользователь, создавший запись */
	@Comment("Пользователь, создавший запись")
	@Persist
	public String getUsernameCreate() {return usernameCreate;}

	/** Представитель */
	@Comment("Представитель")
	@Persist
	public Long getKinsman() {return kinsman;}

	/** Представитель */
	private Long kinsman;
	/** Пользователь, создавший запись */
	private String usernameCreate;
	/** Время создания */
	private String timeCreate;
	/** Дата создания */
	private String dateCreate;
	/** Информация по талону */
	private String ticketInfo;
	/** Время приема **/
    private String time;
	/** Рабочая функция исполнения */
	private Long workFunction;
	/** Рабочая функция исполнения(Инфо) */
	private String workFunctionInfo;
    /** Дата выдачи/создания(?) талона */
    private String date;
    /** Медицинская карта */
    private Long medcard;
}
