package ru.ecom.jaas.ejb.form;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.jaas.ejb.domain.SecPolicy;
import ru.ecom.jaas.ejb.form.interceptor.SecPolicySaveInterceptor;
import ru.ecom.jaas.ejb.form.interceptor.SecPolicyViewInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Политика безопасности
 */
@EntityForm
@EntityFormPersistance(clazz= SecPolicy.class)
@Comment("Политика безопасности")
@Parent(property = "parentSecPolicy", parentForm=SecPolicyForm.class, orderBy="name,key")
@EntityFormSecurityPrefix("/Policy/Jaas/SecPolicy")
@WebTrail(comment="", nameProperties="name", view="entityParentView-secpolicy.do")
@ASaveInterceptors(
       @AEntityFormInterceptor(SecPolicySaveInterceptor.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(SecPolicyViewInterceptor.class)
)
@ACreateInterceptors( {
	@AEntityFormInterceptor(SecPolicySaveInterceptor.class)
	
})
@Setter
public class SecPolicyForm  extends IdEntityForm    {


    /** Ключ */
    @Persist
    @Required
    public String getKey() { return key ; }

    /** Название */
    @Persist
    @Required
    public String getName() { return name ; }

    /** Комментарий */
    @Persist
    public String getComment() { return comment ; }

    /** Родительская политика безопасности */
    @Persist
    @Required
    public Long getParentSecPolicy() { return parentSecPolicy ; }

    /** Роли, в которые входит данная политика */
	@Comment("Роли, в которые входит данная политика")
	public String getRoleList() {return roleList;}

	/** Роли, в которые входит данная политика */
	private String roleList;
    /** Родительская политика безопасности */
    private Long parentSecPolicy ;
    /** Комментарий */
    private String comment ;
    /** Название */
    private String name ;
    /** Ключ */
    private String key ;
    
    /** Создание роли */
	@Comment("Создание роли")
	public Boolean getIsCreateRole() {return isCreateRole;}

	/** Создание роли */
	private Boolean isCreateRole;
	
	/** Роль */
	@Comment("Роль")
	public SecRoleForm getRoleForm() {return roleForm;}

	/** Роль */
	private SecRoleForm roleForm = new SecRoleForm() ;

}
