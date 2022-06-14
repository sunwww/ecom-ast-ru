package ru.ecom.mis.ejb.domain.lpu;


import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV021;
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

    private VocE2FondV021 profile;

    @Comment("Код департамента")
    public String getDepartmentAddressCode() {
        return departmentAddressCode;
    }

    private MisLpu department;

    @OneToOne
    @Comment("Профиль специальности V021")
    public VocE2FondV021 getProfile() {
        return profile;
    }

    /**
     * Код адреса подразделения
     */
    private String departmentAddressCode;
}
