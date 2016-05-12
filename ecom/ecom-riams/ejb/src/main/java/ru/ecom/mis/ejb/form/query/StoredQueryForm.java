package ru.ecom.mis.ejb.form.query;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.query.StoredQuery;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 * Запрос
 */
@EntityForm
@EntityFormPersistance(clazz = StoredQuery.class)
@Comment("Запрос")
@WebTrail(comment = "Запрос", nameProperties = "key", view = "entityView-mis_query.do")
@EntityFormSecurityPrefix("/Policy/Mis/StoredQuery")
public class StoredQueryForm extends IdEntityForm {
    /** Ключ */
    @Persist
    @Comment("Ключ")
    @Required
    public String getKey() {    return theKey ;}

    /** Документ */
    @Persist
    @Comment("Документ")
    public Long getDocument() {    return theDocument ;}

    /** Документ */
    public void setDocument(Long aDocument ) {  theDocument = aDocument ; }

    /** Документ */
    private Long theDocument ;

    /** Ключ */
    public void setKey(String aKey ) {  theKey = aKey ; }

    /** Ключ */
    private String theKey ;

}
