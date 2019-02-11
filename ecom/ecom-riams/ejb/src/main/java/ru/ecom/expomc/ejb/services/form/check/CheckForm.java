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
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Свойства */
    public String getProperties() { return theProperties ; }
    public void setProperties(String aProperties) { theProperties = aProperties ; }

    /** Свойства */
    private String theProperties ;
    /** Название */
    @Persist
    @Required
    public String getName() { return theName ; }
    public void setName(String aName) { theName = aName ; }

    /** Комментарий */
    @Persist
    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** Дата действия с */
    @DateString
    @Persist
    @Required
    public String getActualDateFrom() { return theActualDateFrom ; }
    public void setActualDateFrom(String aActualDateFrom) { theActualDateFrom = aActualDateFrom ; }

    /** Дата действия по */
    @DateString
    @Persist
    public String getActualDateTo() { return theActualDateTo ; }
    public void setActualDateTo(String aActualDateTo) { theActualDateTo = aActualDateTo ; }

    /** Отключен */
    @Persist
    public boolean getDisabled() { return theDisabled ; }
    public void setDisabled(boolean aDisabled) { theDisabled = aDisabled ; }

    /** Тип проверки */
    @Persist
    @Required
    public int getCheckType() { return theCheckType ; }
    public void setCheckType(int aCheckType) { theCheckType = aCheckType ; }

    /** Проверка */
    @Persist
    @Required
    public long getCheckId() { return theCheckId ; }
    public void setCheckId(long aCheckId) { theCheckId = aCheckId ; }

    /** Документ */
    @Persist
    public long getDocument() { return theDocument ; }
    public void setDocument(long aDocument) { theDocument = aDocument ; }

    /** Порядковый номер */
    @Persist
	public Integer getSn() {return theSn;	}
	public void setSn(Integer aSn) {theSn = aSn;	}

	/** Порядковый номер */
	private Integer theSn;
    
    /** Документ */
    private long theDocument ;
    /** Отключен */
    private boolean theDisabled ;
    /** Дата действия по */
    private String theActualDateTo ;
    /** Дата действия с */
    private String theActualDateFrom ;
    /** Комментарий */
    private String theComment ;
    /** Название */
    private String theName ;
    /** Идентификатор */
    private long theId ;
    /** Проверка */
    private long theCheckId ;
    /** Тип проверки */
    private int theCheckType ;

}
