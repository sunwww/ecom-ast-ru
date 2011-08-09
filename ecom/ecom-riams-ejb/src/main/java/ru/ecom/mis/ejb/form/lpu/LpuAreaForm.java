package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.form.simple.IdNameCommentEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ADynamicParentSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ADynamicSecurityInterceptor;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.form.lpu.interceptors.LpuAreaDynamicSecurity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Участок
 */
@EntityForm
@EntityFormPersistance(clazz= LpuArea.class)
@WebTrail(comment = "Участок", nameProperties= "name", view="entityView-mis_lpuArea.do")
@Parent(property="lpu", parentForm=MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/LpuArea")
//@AParentPrepareCreateInterceptors(
//        @AParentEntityFormInterceptor(LpuAreaPrepareCreateInterceptor.class)
//)

@ADynamicSecurityInterceptor(LpuAreaDynamicSecurity.class)
@ADynamicParentSecurityInterceptor(LpuAreaDynamicSecurity.class)

public class LpuAreaForm extends IdNameCommentEntityForm {
    /** ЛПУ */
    @Persist
    public Long getLpu() { return theLpu ; }
    public void setLpu(Long aLpu) { theLpu = aLpu ; }

    @Comment("Название")
    public String getName() {
        return new StringBuilder().append(theTypeName).append(" № ").append(theNumber).toString() ;
    }

    /** Тип участка */
    @Comment("Тип участка")
    @Persist
    @Required
    public Long getType() { return theType ; }
    public void setType(Long aType) { theType = aType ; }

    /** Тип участка */
    private Long theType ;
    /** Номер участка */
    @Required
    @Persist
    public String getNumber() { return theNumber ; }
    public void setNumber(String aNumber) { theNumber = aNumber ; }

    /** Название типа участка */
    @Persist
    public String getTypeName() { return theTypeName ; }
    public void setTypeName(String aTypeName) { theTypeName = aTypeName ; }

    /** Название типа участка */
    private String theTypeName ;

    /** Номер участка */
    private String theNumber ;
    /** ЛПУ */
    private Long theLpu ;
}
