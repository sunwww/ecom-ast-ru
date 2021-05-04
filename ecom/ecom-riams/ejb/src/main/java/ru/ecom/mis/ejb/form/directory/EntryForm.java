package ru.ecom.mis.ejb.form.directory;



import lombok.Setter;
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
@Setter
public class EntryForm extends IdEntityForm{
	
    /** Персона */
    @Comment("Персона")
    @Persist
    public Long getPerson() {return person;}
    private Long person;
    
    /** Отделение */
    @Comment("Отделение")
    @Persist
    public Long getDepartment() {return department;}
    private Long department;
    
    /** Комментарий */
    @Comment("Комментарий")
    @Persist
    public String getComment() {return comment;}
    private String comment;
    
    /** Внутренний номер */
    @Comment("Внутренний номер")
    @Persist
    public String getInsideNumber() {return insideNumber;}
    private String insideNumber;
    
    /** Тип Номера */
    @Comment("Тип Номера")
    public String getTypeNumber() {return typeNumber; }
    private String typeNumber;
    
    /** Номер */
    @Comment("Номер")
    public String getNumber() {return number;}
    private String number;
    
}
