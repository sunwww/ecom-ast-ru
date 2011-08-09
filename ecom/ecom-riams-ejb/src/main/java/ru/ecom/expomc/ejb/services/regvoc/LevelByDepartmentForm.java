package ru.ecom.expomc.ejb.services.regvoc;

import ru.ecom.expomc.ejb.domain.regvoc.LevelByDepartment;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

/**
 * Уровни отделений
 */
@EntityForm
@EntityFormPersistance(clazz= LevelByDepartment.class)
@Comment("Уровни отделений")
public class LevelByDepartmentForm {

    /** Идентфикатор */
    @Persist
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** Тип отделения */
    @Persist
    public String getDepType() { return theDepType ; }
    public void setDepType(String aDepType) { theDepType = aDepType ; }

    /** Уровень */
    @Persist
    public int getLevel() { return theLevel ; }
    public void setLevel(int aLevel) { theLevel = aLevel ; }

    /** Уровень */
    private int theLevel ;
    /** Тип отделения */
    private String theDepType ;
    /** Идентфикатор */
    private long theId ;
}
