package ru.ecom.mis.ejb.domain.pharmacy;

/**
 * Created by rkurbanov on 05.09.2017.
 */

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Comment("Склад")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class PharmStorage extends BaseEntity {

    private MisLpu department;
    @Comment("Отделение")
    @OneToOne
    public MisLpu getDepartment() {
        return department;
    }
    public void setDepartment(MisLpu department) {
        this.department = department;
    }

}
