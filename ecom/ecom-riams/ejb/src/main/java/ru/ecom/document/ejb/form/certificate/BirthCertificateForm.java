package ru.ecom.document.ejb.form.certificate;

import ru.ecom.document.ejb.domain.certificate.BirthCertificate;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.form.medcase.birth.BirthCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Свидетельство о рождении
 * @author oegorova
 *
 */

@EntityForm
@EntityFormPersistance (clazz=BirthCertificate.class)
@Comment("Свидетельство о рождении")
@WebTrail(comment="Свидетельство о рождении",  nameProperties="id", view="entityView-stac_birthCertificate.do" )
@Parent(property="birthCase", parentForm=BirthCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Certificate/Birth")
public class BirthCertificateForm extends CertificateForm {
	
	/** Случай рождения */
	@Comment("Случай рождения")
	@Persist @Required
	public Long getBirthCase() {
		return theBirthCase;
	}

	public void setBirthCase(Long aBirthCase) {
		theBirthCase = aBirthCase;
	}

	/** Случай рождения */
	private Long theBirthCase;
	

}
