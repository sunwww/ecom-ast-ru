package ru.ecom.document.ejb.domain.certificate;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.document.ejb.domain.voc.VocCertificateType;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.birth.Pregnancy;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


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

	/** Тип свидетельства */
	@Comment("Тип свидетельства")
	@OneToOne
	public VocCertificateType getCertificateType() {return theCertificateType;}
	public void setCertificateType(VocCertificateType aCertificateType) {theCertificateType = aCertificateType;}

	
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
	/** Тип свидетельства */
	private VocCertificateType theCertificateType;
	/** Серия предварительного свидетельства */
	private String theSeriesPreCertificate;
	/** Номер предварительного свидетельства */
	private String theNumberPreCertificate;
}
