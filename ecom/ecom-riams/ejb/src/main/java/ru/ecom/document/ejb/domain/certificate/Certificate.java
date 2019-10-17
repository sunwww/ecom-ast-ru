package ru.ecom.document.ejb.domain.certificate;

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
public class Certificate extends BaseEntity {
	
	/** Серия документа */
	@Comment("Серия документа")
	public String getSeries() {return theSeries;}
	public void setSeries(String aSeries) {theSeries = aSeries;}
	
	/** Номер документа */
	@Comment("Номер документа")
	public Integer getNumber() {return theNumber;}
	public void setNumber(Integer aNumber) {theNumber = aNumber;}

	
	/** Дата выдачи документа */
	@Comment("Дата выдачи документа")
	public Date getDateIssue() {return theDateIssue;}
	public void setDateIssue(Date aDateIssue) {theDateIssue = aDateIssue;}

	/** Серия предварительного свидетельства */
	@Comment("Серия предварительного свидетельства")
	public String getSeriesPreCertificate() {return theSeriesPreCertificate;}
	public void setSeriesPreCertificate(String aSeriesPreCertificate) {theSeriesPreCertificate = aSeriesPreCertificate;}

	
	/** Номер предварительного свидетельства */
	@Comment("Номер предварительного свидетельства")
	public String getNumberPreCertificate() {return theNumberPreCertificate;}
	public void setNumberPreCertificate(String aNumberPreCertificate) {theNumberPreCertificate = aNumberPreCertificate;}

	/** Беременность */
	@Comment("Беременность") 
	@ManyToOne
	public Pregnancy getPregnancy() {return thePregnancy;}
	public void setPregnancy(Pregnancy aPregnancy) {thePregnancy = aPregnancy;}

	/** Беременность */
	private Pregnancy thePregnancy;
	@Transient
	public String getInformation() {return "" ;}
	/** Серия документа */
	private String theSeries;
	/** Номер документа */
	private Integer theNumber;
	/** Дата выдачи документа */
	private Date theDateIssue;
	/** Серия предварительного свидетельства */
	private String theSeriesPreCertificate;
	/** Номер предварительного свидетельства */
	private String theNumberPreCertificate;
	
	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;
}
