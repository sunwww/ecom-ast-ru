package ru.ecom.mis.ejb.form.directory;



import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.directory.Entry;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz = Entry.class)
@Comment("Запись")
@WebTrail(comment = "Запись"
, nameProperties= "id", list="entityParentList-directory_entry.do", view="entityParentView-directory_entry.do")
@EntityFormSecurityPrefix("/Policy/Mis/Directory/Department")
public class EntryForm extends IdEntityForm{
	
    /** Персона */
    @Comment("Персона")
    @Persist
    public Long getPerson() {return thePerson;}
    public void setPerson(Long aPerson) {thePerson = aPerson;}
    private Long thePerson;
    
    /** Отделение */
    @Comment("Отделение")
    @Persist
    public Long getDepartment() {return theDepartment;}
    public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
    private Long theDepartment;
    
    /** Комментарий */
    @Comment("Комментарий")
    @Persist
    public String getComment() {return theComment;}
    public void setComment(String aComment) {theComment = aComment;}
    private String theComment;
    
    /** Внутренний номер */
    @Comment("Внутренний номер")
    @Persist
    public String getInsideNumber() {return theInsideNumber;}
    public void setInsideNumber(String aInsideNumber) {theInsideNumber = aInsideNumber;}
    private String theInsideNumber;
    
    /** Тип Номера */
    @Comment("Тип Номера")
    public String getTypeNumber() {return theTypeNumber; }
    public void setTypeNumber(String aTypeNumber) {theTypeNumber = aTypeNumber;}
    private String theTypeNumber;
    
    /** Номер */
    @Comment("Номер")
    public String getNumber() {return theNumber;}
    public void setNumber(String aNumber) {theNumber = aNumber;}
    private String theNumber;
    
}
