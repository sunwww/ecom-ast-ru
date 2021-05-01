package ru.ecom.document.ejb.form.certificate;

import lombok.Getter;
import lombok.Setter;
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
@Setter
public class ConfinementCertificateForm extends CertificateForm {
	/** Беременность */
	@Comment("Беременность")
	@Persist
	public Long getPregnancy() {return pregnancy;}

	/** Информация */
	@Comment("Информация")
	@Persist
	public String getInformation() {return information;}

	/** Информация */
	private String information;
	/** Беременность */
	private Long pregnancy;
}
