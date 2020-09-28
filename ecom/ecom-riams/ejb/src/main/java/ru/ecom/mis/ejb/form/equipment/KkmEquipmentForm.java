package ru.ecom.mis.ejb.form.equipment;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.equipment.KkmEquipment;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Created by Milamesher on 10.01.2019.
 */
@EntityForm
@EntityFormPersistance(clazz= KkmEquipment. class)
@Comment("Оборудование")
@WebTrail(comment = "Оборудование", nameProperties= "name", view="entityView-mis_kkmequipment.do", list="entityParentList-mis_kkmequipment.do")
@Parent(property="lpu", parentForm= MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Equipment/KkmEquipment")
public class KkmEquipmentForm extends EquipmentForm {
    /** Название */
    @Comment("Название")
    @Persist
    public String getName() {return theName;}
    public void setName(String aName) {theName = aName;}
    /** Название */
    private String theName;

    /** URL аппарата */
    @Persist
    @Comment("URL аппарата ")
    public String getUrl() {    return theUrl ;}
    public void setUrl(String aUrl ) {  theUrl = aUrl ; }
    /** URL аппарата */
    private String theUrl;
}