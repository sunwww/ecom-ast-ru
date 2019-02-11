package ru.ecom.ejb.form.simple;

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
public abstract class IdEntityForm extends BaseValidatorForm
        implements IEntityForm, Serializable {
    /** Идентификатор */
    @Id @Comment("Идентификатор")
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }
    
    /** Порядковый номер */
	@Comment("Порядковый номер")
	public Integer getSn() {return theSn;}
	public void setSn(Integer aSn) {theSn = aSn;}

	/** Порядковый номер */
	private Integer theSn;
    /** Идентификатор */
    private long theId ;

}
