package ru.ecom.mis.ejb.form.pharmnet;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.pharmnetPharmacy.PharmnetComplectRowEntity;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = PharmnetComplectRowEntity.class)
@Comment("Форма товары для комплекта")
@WebTrail(comment = "Форма товары для комплекта"
        , nameProperties= "id"
        ,list="entityParentList-pharmnet_complectRow.do"
        ,view="entityParentView-pharmnet_complectRow.do")
@Parent(property = "complectid", parentForm = PharmnetComplectForm.class )
@EntityFormSecurityPrefix("/Policy/Mis/Pharmacy/Administration")
public class PharmnetComplectRowForm extends IdEntityForm {

    private Integer regid;
    private Float count;
    private Long complectid;

    @Persist
    public Integer getRegid() {
        return regid;
    }
    public void setRegid(Integer regid) {
        this.regid = regid;
    }

    @Persist
    public Float getCount() {
        return count;
    }
    public void setCount(Float count) {
        this.count = count;
    }

    @Persist
    public Long getComplectid() {
        return complectid;
    }
    public void setComplectid(Long complectid) {
        this.complectid = complectid;
    }
}
