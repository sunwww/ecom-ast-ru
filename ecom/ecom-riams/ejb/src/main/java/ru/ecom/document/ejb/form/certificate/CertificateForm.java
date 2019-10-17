package ru.ecom.document.ejb.form.certificate;

import ru.ecom.document.ejb.domain.certificate.Certificate;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;
import ru.nuzmsh.forms.validator.validators.TimeString;

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
@Subclasses( { ConfinementCertificateForm.class, DeathCertificateForm.class })
public class CertificateForm extends IdEntityForm {

	/** Серия документа */
	@Comment("Серия документа")
	@Persist
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
	public String getDateIssue() {return theDateIssue;}
	public void setDateIssue(String aDateIssue) {theDateIssue = aDateIssue;}

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
	/** Серия предварительного свидетельства */
	private String theSeriesPreCertificate;
	/** Номер предварительного свидетельства */
	private String theNumberPreCertificate;
	/** Информация о сертификате */
	private String theInformation;
	
	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return theCreateDate;}
	public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return theEditDate;}
	public void setEditDate(String aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return theCreateTime;}
	public void setCreateTime(String aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return theEditTime;}
	public void setEditTime(String aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private String theEditTime;
	/** Время создания */
	private String theCreateTime;
	/** Дата редактирования */
	private String theEditDate;
	/** Дата создания */
	private String theCreateDate;
}
