package ru.ecom.mis.ejb.form.medcase.hospital;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.PhoneMessage;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Сообщение об инфекции")
@EntityForm
@EntityFormPersistance(clazz= PhoneMessage.class)
@WebTrail(comment = "Сообщение об инфекции", nameProperties= "id", view="entityParentView-stac_infectiousMessages.do")
@Parent(property="medCase", parentForm= HospitalMedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages")
public class InfectiousMessagesForm extends MilitiaMessagesForm {


}
