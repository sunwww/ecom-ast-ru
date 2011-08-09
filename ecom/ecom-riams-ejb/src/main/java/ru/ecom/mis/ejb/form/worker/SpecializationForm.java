package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.Qualification;
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
@EntityFormPersistance(clazz= Qualification.class)
@Comment("Аттестация")
@WebTrail(comment = "Аттестат", nameProperties= "numberCertif", view="entityView-mis_specialization.do")
@Parent(property="worker", parentForm=WorkerForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/Specialization")
public class SpecializationForm extends IdEntityForm {
    /** Основание */
    @Persist
    @Comment("Основание")
    @Required
    public Long getBase() {    return theBase ;}
    public void setBase(Long aBase ) {  theBase = aBase ; }

    /** Сотрудник */
    @Persist
    public Long getWorker() {    return theWorker ;}
    public void setWorker(Long aWorker ) {  theWorker = aWorker ; }

    /** Номер сертификата */
    @Persist
    @Comment("Номер сертификата")
    public String getNumberCertif() {    return theNumberCertif ;}
    public void setNumberCertif(String aNumberCertif ) {  theNumberCertif = aNumberCertif ; }

    /** Дата начала */
    @Persist
    @Comment("Дата начала")
    @DateString @DoDateString
    public String getDateStart() {    return theDateStart ;}
    public void setDateStart(String aDateStart ) {  theDateStart = aDateStart ; }

    /** Дата окончания */
    @Persist
    @Comment("Дата окончания")
    @DateString @DoDateString
    public String getDateFinish() {    return theDateFinish ;}
    public void setDateFinish(String aDateFinish ) {  theDateFinish = aDateFinish ; }

    /** Дата выдачи сертификата */
    @Persist
    @Comment("Дата выдачи сертификата")
    @DateString @DoDateString
    public String getDateGetCertif() {    return theDateGetCertif ;}
    public void setDateGetCertif(String aDateGetCertif ) {  theDateGetCertif = aDateGetCertif ; }

    /** Организация */
    @Persist
    @Comment("Организация")
    @Required
    public Long getInstitut() {    return theInstitut ;}
    public void setInstitut(Long aInstitut ) {  theInstitut = aInstitut ; }

    /** Наименование организации */
    @Persist
    @Comment("Наименование организации")
    public String getNameInstitut() {    return theNameInstitut ;}
    public void setNameInstitut(String aNameInstitut ) {  theNameInstitut = aNameInstitut ; }

    /** Специализация */
    @Persist
    @Comment("Специализация")
    public Long getSpec() {    return theSpec ;}
    public void setSpec(Long aSpec ) {  theSpec = aSpec ; }

    /** Специализация */
    @Persist
    @Comment("Специализация")
    public String getNameSpec() {    return theNameSpec ;}
    public void setNameSpec(String aNameSpec ) {  theNameSpec = aNameSpec ; }

    /** Присвоенное звание */
    @Persist
    @Comment("Присвоенное звание")
    public Long getAcademicStatus() {    return theAcademicStatus ;}
    public void setAcademicStatus(Long aAcademicStatus ) {  theAcademicStatus = aAcademicStatus ; }

    /** Присвоенная степень */
    @Persist
    @Comment("Присвоенная степень")
    public Long getAcademicDegree() {    return theAcademicDegree ;}
    public void setAcademicDegree(Long aAcademicDegree ) {  theAcademicDegree = aAcademicDegree ; }

    /** Присвоенная категория */
    @Persist
    @Comment("Присвоенная категория")
    public Long getCategory() {    return theCategory ;}
    public void setCategory(Long aCategory ) {  theCategory = aCategory ; }

    /** Дата присвоения категории */
    @Persist
    @Comment("Дата присвоения категории")
    @DateString @DoDateString
    public String getDateCategory() { return theDateCategory ; }
    public void setDateCategory(String aDateCategory) { theDateCategory = aDateCategory ; }

    /** Дата присвоения категории */
    private String theDateCategory ;
    /** Присвоенная категория */
    private Long theCategory ;
    /** Присвоенная степень */
    private Long theAcademicDegree ;
    /** Присвоенное звание */
    private Long theAcademicStatus ;
    /** Специализация */
    private String theNameSpec ;
    /** Специализация */
    private Long theSpec ;
    /** Наименование организации */
    private String theNameInstitut ;
    /** Организация */
    private Long theInstitut ;
    /** Дата выдачи сертификата */
    private String theDateGetCertif ;
    /** Дата окончания */
    private String theDateFinish ;
    /** Дата начала */
    private String theDateStart ;
    /** Номер сертификата */
    private String theNumberCertif ;
    /** Основание */
    private Long theBase ;
    /** Сотрудник */
    private Long theWorker ;
}
