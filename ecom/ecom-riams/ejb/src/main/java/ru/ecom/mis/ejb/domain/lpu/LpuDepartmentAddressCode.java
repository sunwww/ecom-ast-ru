package ru.ecom.mis.ejb.domain.lpu;


import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Setter
@Table(name = "LpuDepartmentAddressCode")
public class LpuDepartmentAddressCode extends BaseEntity {

    @OneToOne
    @Comment("Подразделение ЛПУ")
    public MisLpu getDepartment() {
        return department;
    }

    @OneToOne
    @Comment("Профиль медицинской помощи")
    public VocE2MedHelpProfile getProfile() {
        return profile;
    }

    @Comment("Код департамента")
    public String getDepartmentAddressCode() {
        return departmentAddressCode;
    }

    private MisLpu department;

    private VocE2MedHelpProfile profile;

    /**
     * Код адреса подразделения
     */
    private String departmentAddressCode;
}
