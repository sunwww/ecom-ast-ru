package ru.ecom.diary.ejb.form;

import javax.persistence.Id;

import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Протоколы
 */
@EntityForm
@EntityFormPersistance(clazz= Diary.class)
@Comment("Протоколы")
@WebTrail(comment = "Протокол", nameProperties= "record", view="entityView-diary_protokol.do")
@EntityFormSecurityPrefix("/Policy/Diary/Diary")

public class DiaryForm extends IdEntityForm {
    /** Id */
    @Id
    @Comment("Id")
    public long getId() {    return theId ;}

    /** Дата создания */
    @Persist
    @Comment("Дата создания")
    public String getDate() {    return theDate ;}

    /** Запись дневника (протокола) */
    @Persist
    @Comment("Запись дневника (протокола)")
    public String getRecord() {    return theRecord ;}

    /** СЛО */
    @Persist
    @Comment("СЛО")
    public long getSloId() {    return theSloId ;}

    /** ID врача */
    @Persist
    @Comment("ID врача")
    public long getDoctorId() {    return theDoctorId ;}

    /** Диагноз */
    @Persist
    @Comment("Диагноз")
    public Long getDiagnosis() {    return theDiagnosis ;}

    /** Случай лечения */
    @Persist
    @Comment("Случай лечения")
    public long getSlsId() {    return theSlsId ;}


    /** Случай лечения */
    public void setSlsId(long aSlsId ) {  theSlsId = aSlsId ; }

    /** Случай лечения */
    private long theSlsId ;

    /** Диагноз */
    public void setDiagnosis(Long aDiagnosis ) {  theDiagnosis = aDiagnosis ; }

    /** Диагноз */
    private Long theDiagnosis ;

    /** ID врача */
    public void setDoctorId(long aDoctorId ) {  theDoctorId = aDoctorId ; }

    /** ID врача */
    private long theDoctorId ;

    /** СЛО */
    public void setSloId(long aSloId ) {  theSloId = aSloId ; }

    /** СЛО */
    private long theSloId ;

    /** Запись дневника (протокола) */
    public void setRecord(String aRecord ) {  theRecord = aRecord ; }

    /** Запись дневника (протокола) */
    private String theRecord ;

    /** Дата создания */
    public void setDate(String aDate ) {  theDate = aDate ; }

    /** Дата создания */
    private String theDate ;

    /** Id */
    public void setId(long aId ) {  theId = aId ; }

    /** Id */
    private long theId ;
}
