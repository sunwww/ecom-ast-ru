package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.SurgicalImplant;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@Comment("Медицинское изделие в операции")
@EntityForm
@EntityFormPersistance(clazz= SurgicalImplant.class)
@WebTrail(comment = "Мед. изделие", nameProperties= "id", view="entityParentView-stac_surImplant.do")
@Parent(property="operation", parentForm= SurgicalOperationForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/SurOper")
@Setter
public class SurgicalImplantForm extends IdEntityForm {

    private Long operation;
    private String serialNumber;
    private Long type;

    @Persist
    @Comment("Серийный номер")
    @Required
    public String getSerialNumber() {
        return serialNumber;
    }

    @Persist
    @Comment("Операция")
    @Required
    public Long getOperation() {
        return operation;
    }

    @Persist
    @Comment("Вид медицинского изделия")
    @Required
    public Long getType() {
        return type;
    }
}
