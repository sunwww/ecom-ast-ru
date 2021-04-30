package ru.ecom.mis.ejb.form.claim;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.claim.Claim;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;


@EntityForm
@EntityFormPersistance(clazz= Claim.class)
@Comment("Заявка в тех. поддержку")
@WebTrail(comment = "Заявка в тех. поддержку", nameProperties= "id", view="entityView-mis_claim.do" ,list = "entityList-mis_claim.do")
@EntityFormSecurityPrefix("/Policy/Mis/Claim")
@Setter
public class ClaimForm extends IdEntityForm{
	
	/** Дата заморозки задачи */
	@Comment("Дата заморозки задачи")
	@Persist
	public String getFreezeDate() {return freezeDate;}
	/** Дата заморозки задачи */
	private String freezeDate;
	
	/** Время заморозки */
	@Comment("Время заморозки")
	@Persist
	public String getFreezeTime() {return freezeTime;}
	/** Время заморозки */
	private String freezeTime;
	
	/** Пользователь, заморозивший задачу */
	@Comment("Пользователь, заморозивший задачу")
	@Persist
	public String getFreezeUsername() {return freezeUsername;}
	/** Пользователь, заморозивший задачу */
	private String freezeUsername;
	
	/** Создатель */
	@Comment("Создатель")
	@Persist
	public String getUsername() {return username;}
	/** Создатель */
	private String username;
	
	/** Рабочая функция создателя */
	@Comment("Рабочая функция создателя")
	@Persist
	public Long getWorkfunction() {return workfunction;}
	/** Рабочая функция создателя */
	private Long workfunction;
	
	/** Дата создания */
	@Comment("Дата создания")
	@Persist
	@DateString @DoDateString
	public String getCreateDate() {return createDate;}
	/** Дата создания */
	private String createDate;
	
	/** Время создания */
	@Comment("Время создания")
	@Persist
	@TimeString @DoTimeString
	public String getCreateTime() {return createTime;}
	/** Время создания */
	private String createTime;
	
	/** Текст заявки */
	@Comment("Текст заявки")
	@Persist @Required
	public String getDescription() {return description;}
	/** Текст заявки */
	private String description;
	
	/** Дата просмотра оператором */
	@Comment("Дата просмотра оператором")
	@Persist
	@DateString @DoDateString
	public String getViewDate() {return viewDate;}
	/** Дата просмотра оператором */
	private String viewDate;
	
	/** Время просмотра оператором */
	@Comment("Время просмотра оператором")
	@Persist
	@TimeString @DoTimeString
	public String getViewTime() {return viewTime;}
	/** Время просмотра оператором */
	private String viewTime;
	
	/** Оператор */
	@Comment("Оператор")
	@Persist
	public String getViewUsername() {return viewUsername;}
	/** Оператор */
	private String viewUsername;
	
	/** Дата получения в работу */
	@Comment("Дата получения в работу")
	@Persist
	@DateString @DoDateString
	public String getStartWorkDate() {return startWorkDate;}
	/** Дата получения в работу */
	private String startWorkDate;
	
	/** Время получения в работу */
	@Comment("Время получения в работу")
	@Persist
	@TimeString @DoTimeString
	public String getStartWorkTime() {return startWorkTime;}
	/** Время получения в работу */
	private String startWorkTime;
	
	/** Исполнитель */
	@Comment("Исполнитель")
	@Persist
	public String getStartWorkUsername() {return startWorkUsername;}
	/** Исполнитель */
	private String startWorkUsername;
	
	/** Комментарий исполнителя */
	@Comment("Комментарий исполнителя")
	@Persist
	public String getExecutorComment() {return executorComment;}
	/** Комментарий исполнителя */
	private String executorComment;
	
	/** Дата отмены */
	@Comment("Дата отмены")
	@Persist
	@DateString @DoDateString
	public String getCancelDate() {return cancelDate;}
	/** Дата отмены */
	private String cancelDate;
	
	/** Время отмены */
	@Comment("Время отмены")
	@Persist
	@TimeString @DoTimeString
	public String getCancelTime() {return cancelTime;}
	/** Время отмены */
	private String cancelTime;
	
	/** Пользователь, отменивший заявку */
	@Comment("Пользователь, отменивший заявку")
	@Persist
	public String getCancelUsername() {return cancelUsername;}
	/** Пользователь, отменивший заявку */
	private String cancelUsername;

	/** Было уведомление через СМС */
	@Comment("Было уведомление через СМС")
	@Persist
	public Boolean getSendMessage() {return sendMessage;}
	/** Было уведомление через СМС */
	private Boolean sendMessage;
	
	/** Тип заявки */
	@Comment("Тип заявки")
	@Persist @Required
	public Long getClaimType() {return claimType;}
	/** Тип заявки */
	private Long claimType;
	
	/** Контактный телефон */
	@Comment("Контактный телефон")
	@Persist @Required
	public String getPhone() {return phone;}
	/** Контактный телефон */
	private String phone;
	
	/** Дата исполнения */
	@Comment("Дата исполнения")
	@Persist @DateString @DoDateString
	public String getFinishDate() {return finishDate;}
	/** Дата исполнения */
	private String finishDate;
	
	/** Время исполнения */
	@Comment("Время исполнения")
	@Persist @TimeString @DoTimeString
	public String getFinishTime() {return finishTime;}
	/** Время исполнения */
	private String finishTime;
	
	/** Пользователь, исполнивший заявку*/
	@Comment("Пользователь, исполнивший заявку")
	@Persist
	public String getFinishUsername() {return finishUsername;}
	/** Пользователь, исполнивший заявку*/
	private String finishUsername;
	
	/** Место исполнения заявки */
	@Comment("Место исполнения заявки")
	@Persist @Required
	public String getAddress() {return address;}
	/** Место исполнения заявки */
	private String address;
	
	/** Пользователь подтвердил выполнение */
	@Comment("Пользователь подтвердил выполнение")
	@Persist
	public Boolean getCompleteConfirmed() {return completeConfirmed;}
	/** Пользователь подтвердил выполнение */
	private Boolean completeConfirmed;
	
	/** Комментарий пользователя */
	@Comment("Комментарий пользователя")
	@Persist
	public String getCreatorComment() {return creatorComment;}
	/** Комментарий пользователя */
	private String creatorComment;


	/** Имя файла-скриншота */
	@Comment("Имя файла-скриншота")
	@Persist
	public String getScreenFileName() {return screenFileName;}
	/** Имя файла-скриншота */
	private String screenFileName;
}