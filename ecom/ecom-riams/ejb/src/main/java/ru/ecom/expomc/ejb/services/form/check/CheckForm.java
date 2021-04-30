package ru.ecom.expomc.ejb.services.form.check;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.util.FormAfterLoadInterceptor;
import ru.ecom.expomc.ejb.domain.check.Check;
import ru.ecom.expomc.ejb.services.form.impdoc.ImportDocumentForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

import javax.persistence.Id;

/**
 * Проверка
 */
@EntityForm
@EntityFormPersistance(clazz= Check.class)
@Comment("Проверка")
@Parent(property = "document", parentForm= ImportDocumentForm.class, orderBy="sn")
@FormAfterLoadInterceptor(AfterLoadInterceptor.class)
@EntityFormSecurityPrefix("/Policy/Exp/Check")
@WebTrail(comment="Проверка", nameProperties="name", view="entityParentView-exp_check.do")
public class CheckForm extends IdEntityForm {

    /** Идентификатор */
    @Id
    public long getId() { return id ; }
    public void setId(long aId) { id = aId ; }

    /** Свойства */
    public String getProperties() { return properties ; }
    public void setProperties(String aProperties) { properties = aProperties ; }

    /** Свойства */
    private String properties ;
    /** Название */
    @Persist
    @Required
    public String getName() { return name ; }
    public void setName(String aName) { name = aName ; }

    /** Комментарий */
    @Persist
    public String getComment() { return comment ; }
    public void setComment(String aComment) { comment = aComment ; }

    /** Дата действия с */
    @DateString
    @Persist
    @Required
    public String getActualDateFrom() { return actualDateFrom ; }
    public void setActualDateFrom(String aActualDateFrom) { actualDateFrom = aActualDateFrom ; }

    /** Дата действия по */
    @DateString
    @Persist
    public String getActualDateTo() { return actualDateTo ; }
    public void setActualDateTo(String aActualDateTo) { actualDateTo = aActualDateTo ; }

    /** Отключен */
    @Persist
    public boolean getDisabled() { return disabled ; }
    public void setDisabled(boolean aDisabled) { disabled = aDisabled ; }

    /** Тип проверки */
    @Persist
    @Required
    public int getCheckType() { return checkType ; }
    public void setCheckType(int aCheckType) { checkType = aCheckType ; }

    /** Проверка */
    @Persist
    @Required
    public long getCheckId() { return checkId ; }
    public void setCheckId(long aCheckId) { checkId = aCheckId ; }

    /** Документ */
    @Persist
    public long getDocument() { return document ; }
    public void setDocument(long aDocument) { document = aDocument ; }

    /** Порядковый номер */
    @Persist
	public Integer getSn() {return sn;	}
	public void setSn(Integer aSn) {sn = aSn;	}

	/** Порядковый номер */
	private Integer sn;
    
    /** Документ */
    private long document ;
    /** Отключен */
    private boolean disabled ;
    /** Дата действия по */
    private String actualDateTo ;
    /** Дата действия с */
    private String actualDateFrom ;
    /** Комментарий */
    private String comment ;
    /** Название */
    private String name ;
    /** Идентификатор */
    private long id ;
    /** Проверка */
    private long checkId ;
    /** Тип проверки */
    private int checkType ;

}
