package ru.ecom.mis.ejb.form.lpu;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdNameCommentEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ADynamicParentSecurityInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ADynamicSecurityInterceptor;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.form.lpu.interceptors.LpuAreaDynamicSecurity;
import ru.nuzmsh.commons.formpersistence.annotation.*;
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
@ADynamicSecurityInterceptor(LpuAreaDynamicSecurity.class)
@ADynamicParentSecurityInterceptor(LpuAreaDynamicSecurity.class)
@Setter

public class LpuAreaForm extends IdNameCommentEntityForm {
    /** ЛПУ для изменения */
	@Comment("ЛПУ для изменения")
	public Long getChangeLpu() {return changeLpu;}
	private Long changeLpu;
	
	/** Участок для изменения */
	@Comment("Участок для изменения")
	public Long getChangeLpuArea() {return changeLpuArea;}
	private Long changeLpuArea;
	
	/** ЛПУ */
    @Persist
    public Long getLpu() { return lpu ; }

    @Comment("Название")
    public String getName() {
        return typeName + " № " + number;
    }

    /** Тип участка */
    @Comment("Тип участка")
    @Persist
    @Required
    public Long getType() { return type ; }

    /** Тип участка */
    private Long type ;
    /** Номер участка */
    @Required
    @Persist
    public String getNumber() { return number ; }

    /** Название типа участка */
    @Persist
    public String getTypeName() { return typeName ; }

    /** Название типа участка */
    private String typeName ;

    /** Номер участка */
    private String number ;
    /** ЛПУ */
    private Long lpu ;
    /** Участковый */
	@Comment("Участковый")
	@Persist @Required
	public Long getWorkFunction() {return workFunction;}

	/** Участковый */
	private Long workFunction;
	
	/** Код подразделения */
	@Comment("Код подразделения")
	@Persist @Required
	public String getCodeDepartment() {return codeDepartment;}

	/** Код подразделения */
	private String codeDepartment;
	
	/** Отображать в пациенте */
	@Comment("Отображать в пациенте")
	@Persist
	public Boolean getIsViewInfoPatient() {return isViewInfoPatient;}
	private Boolean isViewInfoPatient;

}
