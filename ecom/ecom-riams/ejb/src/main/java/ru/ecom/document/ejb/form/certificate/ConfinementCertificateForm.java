package ru.ecom.document.ejb.form.certificate;

import ru.ecom.document.ejb.domain.certificate.ConfinementCertificate;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.birth.PregnancyForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance (clazz=ConfinementCertificate.class)
@Comment("Родовый сертификат")
@WebTrail(comment="Родовый сертификат",  nameProperties="information", view="entityParentView-preg_confCertificate.do" )
@Parent(property="pregnancy", parentForm=PregnancyForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Pregnancy/ConfinementCertificate")
public class ConfinementCertificateForm extends CertificateForm {
	/** Беременность */
	@Comment("Беременность")
	@Persist
	public Long getPregnancy() {return thePregnancy;}
	public void setPregnancy(Long aPregnancy) {thePregnancy = aPregnancy;}

	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInformation() {return theInformation;}
	public void setInformation(String aInformation) {theInformation = aInformation;}

	/** Информация */
	private String theInformation;
	/** Беременность */
	private Long thePregnancy;
}
