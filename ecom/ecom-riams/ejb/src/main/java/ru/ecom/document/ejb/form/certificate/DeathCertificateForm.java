package ru.ecom.document.ejb.form.certificate;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.document.ejb.domain.certificate.DeathCertificate;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.medcase.death.DeathCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Свидетельство о смерти
 * @author oegorova
 *
 */
@EntityForm
@EntityFormPersistance (clazz=DeathCertificate.class)
@Comment("Свидетельство о смерти")
@WebTrail(comment="Свидетельство о смерти",  nameProperties="id", view="entityView-stac_deathCertificate.do" )
@Parent(property="deathCase", parentForm=DeathCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Certificate/Death")
@Setter
public class DeathCertificateForm extends CertificateForm{

	/** Случай смерти */
	@Comment("Случай смерти")
	@Persist @Required
	public Long getDeathCase() {
		return deathCase;
	}

	/** Случай смерти */
	private Long deathCase;
	
}
