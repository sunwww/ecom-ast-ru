package ru.ecom.document.ejb.form.certificate;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class CertificateForm extends IdEntityForm {

	/** Серия документа */
	@Comment("Серия документа")
	@Persist
	public String getSeries() {return series;}

	/** Номер документа */
	@Comment("Номер документа")
	@Persist @Required
	public Integer getNumber() {return number;}

	/** Дата выдачи документа */
	@Comment("Дата выдачи документа")
	@Persist @DateString @DoDateString
	public String getDateIssue() {return dateIssue;}

	/** Серия предварительного свидетельства */
	@Comment("Серия предварительного свидетельства")
	@Persist
	public String getSeriesPreCertificate() {return seriesPreCertificate;}

	
	/** Номер предварительного свидетельства */
	@Comment("Номер предварительного свидетельства")
	@Persist
	public String getNumberPreCertificate() {return numberPreCertificate;}

	
	/** Информация о сертификате */
	@Comment("Информация о сертификате")
	@Persist
	public String getInformation() {return information;}

	/** Серия документа */
	private String series;
	/** Номер документа */
	private Integer number;
	/** Дата выдачи документа */
	private String dateIssue;
	/** Серия предварительного свидетельства */
	private String seriesPreCertificate;
	/** Номер предварительного свидетельства */
	private String numberPreCertificate;
	/** Информация о сертификате */
	private String information;
	
	/** Дата создания */
	@Comment("Дата создания")
	@DateString @DoDateString @Persist
	public String getCreateDate() {return createDate;}

	/** Дата редактирования */
	@Comment("Дата редактирования")
	@DateString @DoDateString @Persist
	public String getEditDate() {return editDate;}

	/** Время создания */
	@Comment("Время создания")
	@TimeString @DoTimeString @Persist
	public String getCreateTime() {return createTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	@TimeString @DoTimeString @Persist
	public String getEditTime() {return editTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	@Persist
	public String getCreateUsername() {return createUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	@Persist
	public String getEditUsername() {return editUsername;}

	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private String editTime;
	/** Время создания */
	private String createTime;
	/** Дата редактирования */
	private String editDate;
	/** Дата создания */
	private String createDate;
}
