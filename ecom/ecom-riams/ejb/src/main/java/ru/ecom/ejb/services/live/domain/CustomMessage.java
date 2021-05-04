package ru.ecom.ejb.services.live.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@AIndexes({
	@AIndex(properties= {"username"})
	, @AIndex(properties= {"recipient"})
	, @AIndex(properties= {"validityDate"})
    , @AIndex(properties= {"readDate"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class CustomMessage {
	 /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id ; }
    /** Идентификатор */
    private long id ;

	/** Url */
	private String messageUrl;
	/** Время чтения */
	private Time readTime;
	/** Дата чтения */
	private Date readDate;
	/** Скрыть из общего списка */
	private Boolean isHidden;
	/** Прочтено */
	private Boolean timeRead;
	/** Системное сообшение */
	private Boolean isSystem;
	/** Срок действия */
	private Date validityDate;
	/** Получатель */
	private String recipient;
	/** Заголовок */
	private String messageTitle;
	/** Текст сообщения */
	private String messageText;
	/** Время отправки сообщения */
	private Time dispatchTime;
	/** Дата отправки сообщения */
	private Date dispatchDate;
	/** Время получения */
	private Time timeReceipt;
	/** Дата получения */
	private Date dateReceipt;
	/** Пользователь */
	private String username;
	

	/** Эксренное */
	private Boolean isEmergency;

	/** Время действия */
	private Time validityTime;

	public CustomMessage() {}
	public CustomMessage(CustomMessage cm) {
		this.username=cm.username;
		this.recipient=cm.recipient;
		this.validityTime=cm.validityTime;
		this.validityDate=cm.validityDate;
		this.isEmergency=cm.isEmergency;
		this.messageUrl=cm.messageUrl;
		this.isHidden=cm.isHidden;
		this.isSystem=cm.isSystem;
		this.messageTitle=cm.messageTitle;
		this.messageText=cm.messageText;
		this.dispatchTime=cm.dispatchTime;
		this.dispatchDate=cm.dispatchDate;

	}
	public CustomMessage(Boolean isEmergency) {
		long currentTime = System.currentTimeMillis();
		this.setDispatchDate(new Date(currentTime));
		this.setDispatchTime(new Time(currentTime));
		this.setIsEmergency(isEmergency);
	}
}

