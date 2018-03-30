package ru.ecom.expert2.form.voc.federal;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdNameOmcCode;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;

import java.io.Serializable;

@EntityForm
public abstract class VocBaseFederalForm extends IdEntityForm implements IEntityForm, Serializable {


}
