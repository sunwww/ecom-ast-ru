package ru.ecom.document.ejb.form.certificate;

import ru.ecom.document.ejb.domain.certificate.Certificate;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Свидетельство
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance (clazz=Certificate.class)
@Comment("Свидетельство")
@WebTrail(comment="Свидетельство",  nameProperties="id", view="entityView-stac_deathCertificate.do" )
@Parent(property="patient", parentForm=PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Certificate")
@Subclasses( { BirthCertificateForm.class, ConfinementCertificateForm.class, DeathCertificateForm.class })
public class CertificateForm extends IdEntityForm {

	/** Серия документа */
	@Comment("Серия документа")
	@Persist @Required
	public String getSeries() {return theSeries;}
	public void setSeries(String aSeries) {theSeries = aSeries;}
	
	/** Номер документа */
	@Comment("Номер документа")
	@Persist @Required
	public Integer getNumber() {return theNumber;}
	public void setNumber(Integer aNumber) {theNumber = aNumber;}
	
	/** Дата выдачи документа */
	@Comment("Дата выдачи документа")
	@Persist @DateString @DoDateString
	@Required
	public String getDateIssue() {return theDateIssue;}
	public void setDateIssue(String aDateIssue) {theDateIssue = aDateIssue;}

	/** Тип свидетельства */
	@Comment("Тип свидетельства")
	@Persist
	public Long getCertificateType() {return theCertificateType;}
	public void setCertificateType(Long aCertificateType) {theCertificateType = aCertificateType;}

	/** Серия предварительного свидетельства */
	@Comment("Серия предварительного свидетельства")
	@Persist
	public String getSeriesPreCertificate() {return theSeriesPreCertificate;}
	public void setSeriesPreCertificate(String aSeriesPreCertificate) {theSeriesPreCertificate = aSeriesPreCertificate;}

	
	/** Номер предварительного свидетельства */
	@Comment("Номер предварительного свидетельства")
	@Persist
	public String getNumberPreCertificate() {return theNumberPreCertificate;}
	public void setNumberPreCertificate(String aNumberPreCertificate) {theNumberPreCertificate = aNumberPreCertificate;}

	
	/** Информация о сертификате */
	@Comment("Информация о сертификате")
	@Persist
	public String getInformation() {return theInformation;}
	public void setInformation(String aInformation) {theInformation = aInformation;}

	/** Серия документа */
	private String theSeries;
	/** Номер документа */
	private Integer theNumber;
	/** Дата выдачи документа */
	private String theDateIssue;
	/** Тип свидетельства */
	private Long theCertificateType;
	/** Серия предварительного свидетельства */
	private String theSeriesPreCertificate;
	/** Номер предварительного свидетельства */
	private String theNumberPreCertificate;
	/** Информация о сертификате */
	private String theInformation;
}
