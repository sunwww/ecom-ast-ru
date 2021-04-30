package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageEmploye;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageState;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageSubType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPhoneMessageType;
import ru.ecom.mis.ejb.domain.patient.voc.VocRayon;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.domain.worker.Worker;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

   /**
    * Телефонное сообщение 
    */
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="medCase")
})
@Getter
@Setter
public class PhoneMessage extends BaseEntity {

	/**
	 * Дата регистрации
	 */
	private Date phoneDate;
	
	/**
	 * Время регистрации
	 */
	private Time phoneTime;
	

	/** Фамилия принявшего сообщение */
	@Comment("Фамилия принявшего сообщение")
	@OneToOne
	public VocPhoneMessageEmploye getRecieverEmploye() {return recieverEmploye;}

	/** Фамилия принявшего сообщение */
	private VocPhoneMessageEmploye recieverEmploye;
	/** Должность принявшего сообщение*/
	private String recieverPost;
	/** Фамилия принявшего */
	private String recieverFio;
	/** Телефон */
	private String phone;
	/** Принявшая сообщение организация */
	private String recieverOrganization = "";

	/**
	 * Текст сообщения
	 */
	private String text = "";

	/**
	 * Передавший сообщение специалист
	 */
	private Worker worker;

	/**
	 * Getter of the property <tt>theWorker</tt>

	 */
	@Comment("Передавший сообщение специалист")
	@OneToOne
	public Worker getWorker() {
		return worker;
	}
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@OneToOne
	public WorkFunction getWorkFunction() {return workFunction;}

	/** Рабочая функция */
	private WorkFunction workFunction;

	/**
	 * Тип сообщения
	 */
	@Comment("Тип сообщения")
	@OneToOne
	public VocPhoneMessageType getPhoneMessageType() {
		return phoneMessageType;
	}

	/**
	 * Тип сообщения
	 */
	private VocPhoneMessageType phoneMessageType;

	/**
	 * Номер сообщения
	 */
	private String number;

	/**
	 * Случай медицинского обслуживания
	 */
	@Comment("Случай медицинского обслуживания")
	@ManyToOne
	public MedCase getMedCase() {
		return medCase;
	}

	/**
	 * Случай медицинского обслуживания
	 */
	private MedCase medCase;
		

	/** Подтип сообщения*/
	@Comment("Подтип сообщения")
	@OneToOne
	public VocPhoneMessageSubType getPhoneMessageSubType() {return phoneMessageSubType;}

	/** Подтип сообщения*/
	private VocPhoneMessageSubType phoneMessageSubType;
	/** Время редакции */
	private Time editTime;
	/** Дата редакции */
	private Date editDate;
	/** Пользователь, которые последним редактировал запись */
	private String editUsername;
	/** Время создания */
	private Time createTime;
	/** Дата создания */
	private Date createDate;
	/** Пользователь, создавший запись */
	private String createUsername;
	
	/** Район */
	@Comment("Район")
	@OneToOne
	public VocRayon getRayon() {return rayon;}

	/** Код МКБ */
	@Comment("Код МКБ")
	@OneToOne
	public VocIdc10 getIdc10() {return idc10;}


	/** Тяжесть состояния */
	@Comment("Тяжесть состояния")
	@OneToOne
	public VocPhoneMessageState getState() {return state;}

	/** Тяжесть состояния */
	private VocPhoneMessageState state;
	/** Диагноз текст */
	private String diagnosis;
	/** Код МКБ */
	private VocIdc10 idc10;
	/** Район */
	private VocRayon rayon;
}
