package ru.ecom.mis.ejb.domain.pharmnetPharmacy;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by rkurbanov on 07.02.2018.
 */

@Comment("Склад")
@Entity
@Table(name = "pharmnetStorage", schema="pharmnet")
public class PharmnetStorageEntity extends BaseEntity {

    private Integer branchId;
    private Integer groupWorkfuncId;


    @Column(name = "branchId")
    public Integer getBranchId() {
        return branchId;
    }
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }


    @Column(name = "groupWorkfuncId")
    public Integer getGroupWorkfuncId() {
        return groupWorkfuncId;
    }
    public void setGroupWorkfuncId(Integer groupWorkfuncId) {
        this.groupWorkfuncId = groupWorkfuncId;
    }
}
