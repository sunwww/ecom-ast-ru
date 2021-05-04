package ru.ecom.mis.ejb.form.worker;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.GroupWorkFunction;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = GroupWorkFunction.class)
@Comment("Групповая рабочая функция")
@WebTrail(comment = "Групповая рабочая функция", nameProperties = "name", view = "entityParentView-work_groupWorkFunction.do", list = "entityParentList-work_groupWorkFunction.do")
@Parent(property = "lpu", parentForm = MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkFunction")
@Setter

public class GroupWorkFunctionForm extends WorkFunctionForm {

    /**
     * Направлять анализы по умолчанию в этот кабинет
     */
    @Comment("Направлять анализы по умолчанию в этот кабинет")
    @Persist
    public Boolean getIsDefaultLabCabinet() {
        return isDefaultLabCabinet;
    }

    /**
     * Направлять анализы по умолчанию в этот кабинет
     */
    private Boolean isDefaultLabCabinet;

    /**
     * Есть обслуживающий персонал
     */
    @Comment("Есть обслуживающий персонал")
    @Persist
    public Boolean getHasServiceStuff() {
        return hasServiceStuff;
    }

    /**
     * Есть обслуживающий персонал
     */
    private Boolean hasServiceStuff;

    /**
     * Наименование
     */
    @Comment("Наименование")
    @Persist
    public String getName() {
        return name;
    }

    /**
     * ЛПУ
     */
    @Comment("ЛПУ")
    @Persist
    @Required
    public Long getLpu() {
        return lpu;
    }

    /**
     * Название группы
     */
    @Comment("Название группы")
    @Persist
    @Required
    @DoUpperCase
    public String getGroupName() {
        return groupName;
    }

    /**
     * Название группы
     */
    private String groupName;
    /**
     * ЛПУ
     */
    private Long lpu;
    /**
     * Наименование
     */
    private String name;

    /**
     * Разрешено создавать направление без указания услуг
     */
    @Comment("Разрешено создавать направление без указания услуг")
    @Persist
    public Boolean getIsCreateDIrectionWithoutService() {
        return isCreateDIrectionWithoutService;
    }

    /**
     * Разрешено создавать направление без указания услуг
     */
    private Boolean isCreateDIrectionWithoutService = false;
}
