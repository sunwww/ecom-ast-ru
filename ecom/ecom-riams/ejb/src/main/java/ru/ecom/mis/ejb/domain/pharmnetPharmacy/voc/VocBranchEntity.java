package ru.ecom.mis.ejb.domain.pharmnetPharmacy.voc;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * Created by rkurbanov on 07.02.2018.
 */
@Comment("Справочник филиалов (кабинетов)")
@Entity
@Table(name = "vocBranch", schema="pharmnet")
@EntityListeners(DeleteListener.class)
public class VocBranchEntity extends BaseEntity  {

    private Integer branchId;
    private String branch;
    private String address;

    @Comment("ИД в сторонней системе")
    @Column(name = "branchId")
    public Integer getBranchId() {
        return branchId;
    }
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }


    @Comment("Наименование филиала")
    @Column(name = "branch")
    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Comment("Адрес")
    @Column(name = "address")
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
