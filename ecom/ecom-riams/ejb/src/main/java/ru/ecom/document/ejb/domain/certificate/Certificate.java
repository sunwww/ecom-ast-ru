package ru.ecom.document.ejb.domain.certificate;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.birth.Pregnancy;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;
import java.sql.Time;


/**
 * Свидетельство
 * @author oegorova
 *
 */

@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class Certificate extends BaseEntity {

	/** Беременность */
	@Comment("Беременность") 
	@ManyToOne
	public Pregnancy getPregnancy() {return pregnancy;}

	/** Беременность */
	private Pregnancy pregnancy;
	@Transient
	public String getInformation() {return "" ;}
	/** Серия документа */
	private String series;
	/** Номер документа */
	private Integer number;
	/** Дата выдачи документа */
	private Date dateIssue;
	/** Серия предварительного свидетельства */
	private String seriesPreCertificate;
	/** Номер предварительного свидетельства */
	private String numberPreCertificate;

	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private Time editTime;
	/** Время создания */
	private Time createTime;
	/** Дата редактирования */
	private Date editDate;
	/** Дата создания */
	private Date createDate;
}
