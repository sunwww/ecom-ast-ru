package ru.ecom.mis.ejb.form.contract;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.PriceGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = PriceGroup.class)
@Comment("Группа")
@WebTrail(comment = "Группа", nameProperties= "name", list="entityParentList-contract_priceGroup.do", view="entityParentView-contract_priceGroup.do")
@Parent(property="parent", parentForm=PriceGroupForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/PriceList/PriceGroup")
@Setter
public class PriceGroupForm  extends PricePositionForm {
	/** Прейскурант */
	@Comment("Прейскурант")
	@Persist @Required
	public Long getPriceList() {return priceList;}

	/** Наименование */
	@Comment("Наименование")
	@Persist
	public String getName() {return name;}

	/** Код */
	@Comment("Код")
	@Persist
	public String getCode() {return code;}


	/** Родитель */
	@Comment("Родитель")
	@Persist
	public Long getParent() {return parent;}

	/** Комментарий */
	@Comment("Комментарий")
	@Persist
	public String getComment() {return comment;}

	/** Комментарий */
	private String comment;
	/** Родитель */
	private Long parent;
	/** Код */
	private String code;
	/** Наименование */
	private String name;
	/** Прейскурант */
	private Long priceList;
	
	/** Сразу открывать */
	@Comment("Сразу открывать")
	@Persist
	public Boolean getIsOnceView() {return isOnceView;}

	/** Сразу открывать */
	private Boolean isOnceView;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return lpu;}

	/** ЛПУ */
	private Long lpu;
	/** Тип услуги */
	@Comment("Тип услуги")
	@Persist
	public Long getPositionType() {return positionType;}

	/** Тип услуги */
	private Long positionType;

	
}
