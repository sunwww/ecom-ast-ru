package ru.ecom.mis.ejb.form.pharmnet;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.pharmnetPharmacy.PharmnetStorageEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Created by rkurbanov on 27.04.2018.
 */
@EntityForm
@EntityFormPersistance(clazz=PharmnetStorageEntity.class)
@WebTrail(comment="Склад",
        nameProperties="id",
        list="entityParentList-pharmnet_storage.do",
        view="entityParentView-pharmnet_storage.do")
@EntityFormSecurityPrefix("/Policy/Mis/Pharmacy/Administration")
public class PharmnetStorageForm extends IdEntityForm {

    private Integer branchId;
    private Integer groupWorkfuncId;

    @Comment("Склад")
    @Persist
    public Integer getBranchId() {
        return branchId;
    }
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    @Comment("Рабочая функция")
    @Persist
    public Integer getGroupWorkfuncId() {
        return groupWorkfuncId;
    }
    public void setGroupWorkfuncId(Integer groupWorkfuncId) {
        this.groupWorkfuncId = groupWorkfuncId;
    }
}
