package ru.ecom.mis.ejb.uc.privilege.form;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.ACreateInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.ASaveInterceptors;
import ru.ecom.ejb.services.entityform.interceptors.AViewInterceptors;
import ru.ecom.mis.ejb.uc.privilege.domain.DrugClassificator;
import ru.ecom.mis.ejb.uc.privilege.form.interceptor.ClassificatorSave;
import ru.ecom.mis.ejb.uc.privilege.form.interceptor.ClassificatorView;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= DrugClassificator.class)
@WebTrail(comment = "Классификатор лекарственных средств", nameProperties= {"name"}, view="entityParentView-drug_classificator.do")
@Parent(property="parent", parentForm=DrugClassificatorForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Drug/Classificator")
@ASaveInterceptors(
        @AEntityFormInterceptor(ClassificatorSave.class)
)
@AViewInterceptors(
        @AEntityFormInterceptor(ClassificatorView.class)
)
@ACreateInterceptors( {
	@AEntityFormInterceptor(ClassificatorSave.class)
	
})
@Setter
public class DrugClassificatorForm extends IdEntityForm {
	/** Наименование */
	@Comment("Наименование")
	@Persist @Required
	public String getName() {return name;}

	/** Родитель */
	@Comment("Родитель")
	@Persist
	public Long getParent() {return parent;}

	/** Пользователь */
	@Comment("Пользователь")
	@Persist
	public String getUsername() {return username;}

	/** Дата создания */
	@Comment("Дата создания")
	@Persist @DoDateString @DateString
	public String getCreateDate() {return createDate;}

	/** Список лек. средств */
	@Comment("Список лек. средств")
	public String getDrugList() {return drugList;}

	/** Список лек. средств */
	private String drugList;
	/** Дата создания */
	private String createDate;
	/** Пользователь */
	private String username;
	/** Наименование */
	private String name;
	/** Родитель */
	private Long parent;
}
