package ru.ecom.mis.ejb.form.medcase.hospital;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.medcase.voc.VocOperation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz = VocOperation.class)
@Comment("Справочник хир.операций")
@WebTrail(comment = "Справочник хир.операций", nameProperties= "id", view="entityParentView-voc_operation.do")
@EntityFormSecurityPrefix("/Policy/Voc/VocOperation")
@Setter
public class VocSurgicalOperationForm extends IdEntityForm {
    /** Название */
    @Comment("Наименование")
    @Persist @Required
    public String getName() { return name ; }

    /** Внешний код */
	@Comment("Внешний код")
	@Persist @Required
	public String getCode() {return code;}

	/** Дата начала актуальности */
	@Comment("Дата начала актуальности")
	@Persist @DateString @DoDateString
	public String getStartActualDate() {return startActualDate;}

	/** Дата окончания актуальности */
	@Comment("Дата окончания актуальности")
	@Persist @DateString @DoDateString
	public String getFinishActualDate() {return finishActualDate;}
	/** Уровонь сложности */
	@Comment("Уровонь сложности")
	@Persist
	public Long getComplexity() {return complexity;}

	/** Код федеральный */
	@Comment("Код федеральный")
	@Persist
	public String getCodeF() {return codeF;}

	/** Отделения */
	@Comment("Отделения")
	@Persist @PersistManyToManyOneProperty(collectionGenericType=MisLpu.class)
	public String getDepartments() {return departments;}

	/** Отделения */
	private String departments;
	/** Код федеральный */
	private String codeF;
	/** Уровонь сложности */
	private Long complexity;
	/** Дата окончания актуальности */
	private String finishActualDate;
	/** Дата начала актуальности */
	private String startActualDate;
	/** Внешний код */
	private String code;
    /** Название */
    private String name ;
}
