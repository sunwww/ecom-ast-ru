package ru.ecom.jaas.ejb.form;

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
public class SecPolicyForm  extends IdEntityForm    {


    /** Ключ */
    @Persist
    @Required
    public String getKey() { return theKey ; }
    public void setKey(String aKey) { theKey = aKey ; }

    /** Название */
    @Persist
    @Required
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Комментарий */
    @Persist
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Родительская политика безопасности */
    @Persist
    @Required
    public Long getParentSecPolicy() { return theParentSecPolicy ; }
    public void setParentSecPolicy(Long aParentSecPolicy) { theParentSecPolicy = aParentSecPolicy ; }

    /** Роли, в которые входит данная политика */
	@Comment("Роли, в которые входит данная политика")
	public String getRoleList() {return theRoleList;}
	public void setRoleList(String aRoleList) {theRoleList = aRoleList;}

	/** Роли, в которые входит данная политика */
	private String theRoleList;
    /** Родительская политика безопасности */
    private Long theParentSecPolicy ;
    /** Комментарий */
    private String theComment ;
    /** Название */
    private String theName ;
    /** Ключ */
    private String theKey ;
    
    /** Создание роли */
	@Comment("Создание роли")
	public Boolean getIsCreateRole() {return theIsCreateRole;}
	public void setIsCreateRole(Boolean aIsCreateRole) {theIsCreateRole = aIsCreateRole;}

	/** Создание роли */
	private Boolean theIsCreateRole;
	
	/** Роль */
	@Comment("Роль")
	public SecRoleForm getRoleForm() {return theRoleForm;}
	public void setRoleForm(SecRoleForm aRoleForm) {theRoleForm = aRoleForm;}

	/** Роль */
	private SecRoleForm theRoleForm = new SecRoleForm() ;

}
