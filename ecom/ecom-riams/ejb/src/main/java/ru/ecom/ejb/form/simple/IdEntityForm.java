package ru.ecom.ejb.form.simple;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.forms.validator.BaseValidatorForm;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * ИД
 */
@EntityForm
@Setter
public abstract class IdEntityForm extends BaseValidatorForm
        implements IEntityForm, Serializable {
    /** Идентификатор */
    @Id @Comment("Идентификатор")
    public long getId() { return id ; }

    /** Порядковый номер */
	@Comment("Порядковый номер")
	public Integer getSn() {return sn;}

	/** Порядковый номер */
	private Integer sn;
    /** Идентификатор */
    private long id ;

}
