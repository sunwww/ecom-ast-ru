package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.LpuDepartmentAddressCode;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Код отделения по профилю подразделения")
@EntityForm
@EntityFormPersistance(clazz = LpuDepartmentAddressCode.class)
@WebTrail(comment = "Код отделения по профилю подразделения", nameProperties = "departmentAddressCode", view = "entityView-mis_departmentAddressCode.do")
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
    @Comment("Код адреса подразделения")
    public String getDepartmentAddressCode() {
        return departmentAddressCode;
    }

    private Long department;

    private Long profile;

    private String departmentAddressCode;
}
