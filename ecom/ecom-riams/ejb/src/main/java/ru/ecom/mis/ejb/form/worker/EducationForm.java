package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.Education;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
import ru.nuzmsh.forms.validator.validators.Required;

/**
 *
 */
@EntityForm
@EntityFormPersistance(clazz= Education.class)
@Comment("Образование")
@WebTrail(comment = "Образовательный институт", nameProperties= "nameInstitut", view="entityView-mis_education.do")
@Parent(property="worker", parentForm=WorkerForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/Education")
public class EducationForm extends IdEntityForm {
    /** Сотрудник */
    @Persist
    @Comment("Сотрудник")
    public Long getWorker() {    return theWorker ;}
    public void setWorker(Long aWorker ) {  theWorker = aWorker ; }

    /** Институт */
    @Persist
    @Comment("Институт")
    @Required
    public Long getInstitut() {    return theInstitut ;}
    public void setInstitut(Long aInstitut ) {  theInstitut = aInstitut ; }

    /** Наименование института */
    @Persist
    @Comment("Наименование института")
    public String getNameInstitut() {    return theNameInstitut ;}
    public void setNameInstitut(String aNameInstitut ) {  theNameInstitut = aNameInstitut ; }

    /** Дата начала обучения */
    @Persist
    @Comment("Дата начала обучения")
    @DateString @DoDateString
    @Required
    public String getDateStart() {    return theDateStart ;}
    public void setDateStart(String aDateStart ) {  theDateStart = aDateStart ; }

    /** Дата окончания обучения */
    @Persist
    @Comment("Дата окончания обучения")
    @DateString @DoDateString
    @Required
    public String getDateFinish() {    return theDateFinish ;}
    public void setDateFinish(String aDateFinish ) {  theDateFinish = aDateFinish ; }

    /** Номер диплома */
    @Persist
    @Comment("Номер диплома")
    @Required
    public String getNumberDiplom() {    return theNumberDiplom ;}
    public void setNumberDiplom(String aNumberDiplom ) {  theNumberDiplom = aNumberDiplom ; }

    /** Специальность по диплому */
    @Persist
    @Comment("Специальность по диплому")
    @Required
    public Long getSpec() {    return theSpec ;}
    public void setSpec(Long aSpec ) {  theSpec = aSpec ; }

    /** Специальность */
    @Persist
    @Comment("Специальность")
    public String getNameSpec() {    return theNameSpec ;}
    public void setNameSpec(String aNameSpec ) {  theNameSpec = aNameSpec ; }

    /** Квалификация */
    @Persist
    @Comment("Квалификация")
    @Required
    public Long getQualif() {    return theQualif ;}
    public void setNameQualif(String aNameQualif ) {  theNameQualif = aNameQualif ; }

    /** Квалификация */
    @Persist
    @Comment("Квалификация")
    public String getNameQualif() {    return theNameQualif ;}
    public void setQualif(Long aQualif ) {  theQualif = aQualif ; }

    /** Образование */
    @Persist
    @Comment("Образование")
    @Required
    public Long getEduc() {    return theEduc ;}
    public void setEduc(Long aEduc ) {  theEduc = aEduc ; }

    /** Образование */
    @Persist
    @Comment("Образование")
    public String getNameEduc() {    return theNameEduc ;}
    public void setNameEduc(String aNameEduc ) {  theNameEduc = aNameEduc ; }

    /** Образование */
    private String theNameEduc ;
    /** Образование */
    private Long theEduc ;
    /** Квалификация */
    private String theNameQualif ;
    /** Квалификация */
    private Long theQualif ;
    /** Специальность */
    private String theNameSpec ;
    /** Специальность по диплому */
    private Long theSpec ;
    /** Номер диплома */
    private String theNumberDiplom ;
    /** Дата окончания обучения */
    private String theDateFinish ;
    /** Дата начала обучения */
    private String theDateStart ;
    /** Наименование института */
    private String theNameInstitut ;
    /** Институт */
    private Long theInstitut ;
    /** Сотрудник */
    private Long theWorker ;
}
