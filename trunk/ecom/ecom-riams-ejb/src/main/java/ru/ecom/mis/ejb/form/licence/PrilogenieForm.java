package ru.ecom.mis.ejb.form.licence;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.mis.ejb.domain.licence.Prilogenie;
import ru.ecom.mis.ejb.domain.licence.voc.VocTypeWork;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;


/**
Приложения к лицензиям
 */

@EntityForm
@EntityFormPersistance(clazz= Prilogenie.class)
@Comment("Приложение к лицензии")
@WebTrail(comment = "Приложение №", nameProperties = "numberDoc", view="entityView-mis_prilogenie.do")
@Parent(property="licence", parentForm= LicenceForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Prilogenie")
public class PrilogenieForm extends IdEntityForm {

    /** Номер документа */
    @Persist
    @Comment("Номер документа")
    @Required
    public String getNumberDoc() {    return theNumberDoc ;}

    /** Связь с подразделениями */
//    @PersistOneToManyOneProperty(valueProperty="type", parentProperty="lpu")
    @Persist
    @Comment("Связь с подразделениями")
    @Required
    public Long getLpu() {    return theLpu ;}

    /** Связь с подразделениями */
    @Persist
    @Comment("Связь с подразделениями")
    public String getNameLpu() {    return theNameLpu ;}

    /** Связь с лицензией */
    @Persist
    @Comment("Связь с лицензией")
    public Long getLicence() {    return theLicence ;}

    /** Вид деятельности */
//    @PersistOneToManyOneProperty(valueProperty="name", parentProperty="vocTypeWork")
    @Persist
    @Comment("Вид деятельности")
    @PersistManyToManyOneProperty(collectionGenericType=VocTypeWork.class)
    public String getTypeWorks() {    return theTypeWorks ;}

     /** Наименование деятельности */
    @Persist
    @Comment("Наименование деятельности")
    public String getNameTypeWork() {    return theNameTypeWork ;}

    /** Наименование деятельности */
    public void setNameTypeWork(String aNameTypeWork ) {  theNameTypeWork = aNameTypeWork ; }

    /** Наименование деятельности */
    private String theNameTypeWork ;

    /** Вид деятельности */
    public void setTypeWorks(String aTypeWorks ) {  theTypeWorks = aTypeWorks ; }

    /** Вид деятельности */
    private String theTypeWorks ;

    /** Связь с лицензией */
    public void setLicence(Long aLicence ) {  theLicence = aLicence ; }

    /** Связь с лицензией */
    private Long theLicence ;

    /** Связь с подразделениями */
    public void setNameLpu(String aNameLpu ) {  theNameLpu = aNameLpu ; }

    /** Связь с подразделениями */
    private String theNameLpu ;

    /** Связь с подразделениями */
    public void setLpu(Long aLpu ) {  theLpu = aLpu ; }

    /** Связь с подразделениями */
    private Long theLpu ;

    /** Номер документа */
    public void setNumberDoc(String aNumberDoc ) {  theNumberDoc = aNumberDoc ; }

    /** Номер документа */
    private String theNumberDoc ;

}