package ru.ecom.expert2.form;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.expert2.domain.Expert2Config;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Справочник настроек экспертизы
 */
@EntityForm
@EntityFormPersistance(clazz = Expert2Config.class)
@Comment("Справочник настроек экспертизы")
@WebTrail(comment = "Справочник настроек экспертизы", nameProperties = "id", view = "entityView-e2_config.do")
@EntityFormSecurityPrefix("/Policy/E2")
public class Expert2ConfigForm extends IdEntityForm {

    /** Значение параметра */
    @Comment("Значение параметра")
    @Persist
    public String getValue() {return theValue;}
    public void setValue(String aValue) {theValue = aValue;}
    /** Значение параметра */
    private String theValue ;

    @Comment("Удалено")
    @Persist
    public Boolean getIsDeleted() {return theIsDeleted;}
    public void setIsDeleted(Boolean aIsDeleted) {theIsDeleted = aIsDeleted;}
    /** Удалено */
    private Boolean theIsDeleted ;

    /** Название */
    @Comment("Название")
    @Persist
    public String getName() {return theName;}
    public void setName(String aName) {theName = aName;}
    /** Название */
    private String theName ;

    /** Код */
    @Comment("Код")
    @Persist
    public String getCode() {return theCode;}
    public void setCode(String aCode) {theCode = aCode;}
    /** Код */
    private String theCode ;
}
