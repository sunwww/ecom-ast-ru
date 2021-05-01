package ru.ecom.mis.ejb.domain.claim;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


@Comment("Заявка в техническую поддержку")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class Claim extends BaseEntity{

	/** Дата заморозки задачи */
	private Date freezeDate;
	
	/** Время заморозки */
	private Time freezeTime;
	
	/** Пользователь, заморозивший задачу */
	private String freezeUsername;
	
	/** Создатель */
	private String username;
	
	/** Рабочая функция создателя */
	private Long workfunction;
	
	/** Дата создания */
	private Date createDate;
	
	/** Время создания */
	private Time createTime;
	
	/** Текст заявки */
	private String description;
	
	/** Дата просмотра оператором */
	private Date viewDate;
	
	/** Время просмотра оператором */
	private Time viewTime;
	
	/** Оператор */
	private String viewUsername;
	
	/** Дата получения в работу */
	private Date startWorkDate;
	
	/** Время получения в работу */
	private Time startWorkTime;
	
	/** Исполнитель */
	private String startWorkUsername;
	
	/** Комментарий исполнителя */
	private String executorComment;
	
	/** Дата отмены */
	private Date cancelDate;
	
	/** Время отмены */
	private Time cancelTime;
	
	/** Пользователь, отменивший заявку */
	private String cancelUsername;

	/** Было уведомление через СМС */
	private Boolean sendMessage;
	
	/** Тип заявки */
	private Long claimType;
	
	/** Контактный телефон */
	private String phone;
	
	/** Дата исполнения */
	private Date finishDate;
	
	/** Время исполнения */
	private Time finishTime;
	
	/** Пользователь, исполнивший заявку*/
	private String finishUsername;
	
	/** Место исполнения заявки */
	private String address;
	
	/** Пользователь подтвердил выполнение заявки */
	private Boolean completeConfirmed;
	
	/** Комментарий пользователя */
	private String creatorComment;

	/** Имя файла-скриншота */
	private String screenFileName;
}