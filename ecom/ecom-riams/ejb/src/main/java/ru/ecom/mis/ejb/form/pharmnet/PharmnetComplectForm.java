package ru.ecom.mis.ejb.form.pharmnet;


import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.pharmnetPharmacy.PharmnetComplectEntity;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz=PharmnetComplectEntity.class)
@WebTrail(comment="Комплект для списания",
        nameProperties="id",
        list="entityParentList-pharmnet_complect.do",
        view="entityParentView-pharmnet_complect.do")
@EntityFormSecurityPrefix("/Policy/Mis/Pharmacy/Administration")
public class PharmnetComplectForm extends IdEntityForm {

    private String name;
    private Long medService_id;

    @Comment("Наименование")
    @Persist
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Comment("Услуга")
    @Persist
    public Long getMedService_id() {
        return medService_id;
    }
    public void setMedService_id(Long medService_id) {
        this.medService_id = medService_id;
    }
}
