package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.lpu.WorkPlace;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

import javax.persistence.OneToOne;

@Comment("Код отделения по профилю подразделения")
@EntityForm
@EntityFormPersistance(clazz = WorkPlace.class)
@WebTrail(comment = "Код отделения по профилю подразделения", nameProperties = "name", view = "entityView-mis_departmentAddressCode.do")
@Parent(property = "department", parentForm = MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MisLpu")
@Setter
public class LpuDepartmentAddressCodeForm extends IdEntityForm {
    @Persist
    @Comment("Подразделение ЛПУ")
    public Long getDepartment() {
        return department;
    }

    @Persist
    @Comment("Профиль медицинской помощи")
    public Long getProfile() {
        return profile;
    }

    @Persist
    @Comment("Код департамента")
    public String getDepartmentAddressCode() {
        return departmentAddressCode;
    }

    private Long department;

    private Long profile;

    /**
     * Код адреса подразделения
     */
    private String departmentAddressCode;
}
