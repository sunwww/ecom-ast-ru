package ru.ecom.expert2.form;

import lombok.Setter;
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
@Setter
public class Expert2ConfigForm extends IdEntityForm {

    /** Значение параметра */
    @Comment("Значение параметра")
    @Persist
    public String getValue() {return value;}
    /** Значение параметра */
    private String value ;

    @Comment("Удалено")
    @Persist
    public Boolean getIsDeleted() {return isDeleted;}
    /** Удалено */
    private Boolean isDeleted ;

    /** Название */
    @Comment("Название")
    @Persist
    public String getName() {return name;}
    /** Название */
    private String name ;

    /** Код */
    @Comment("Код")
    @Persist
    public String getCode() {return code;}
    /** Код */
    private String code ;
}
