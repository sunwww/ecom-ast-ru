package ru.ecom.expomc.ejb.domain.regvoc;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.expomc.ejb.domain.impdoc.IImportData;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Уровни по отделениям
 */
@Entity
@Table(name="LEVEL_BY_DEP",schema="SQLUser")
@Comment("Уровни по отделениям")
public class LevelByDepartment extends BaseEntity implements IImportData, Serializable {

    /** Время импорта */
    @Comment("Время импорта")
    public long getTime() { return theTime ; }
    public void setTime(long aTime) { theTime = aTime ; }

    /** Тип отделения */
    @Comment("Тип отделения")
    public String getDepType() { return theDepType ; }
    public void setDepType(String aDepType) { theDepType = aDepType ; }

    /** Уровень */
    @Comment("Уровень")
    public int getLevel() { return theLevel ; }
    public void setLevel(int aLevel) { theLevel = aLevel ; }

    /** Уровень */
    private int theLevel ;
    /** Тип отделения */
    private String theDepType ;
    /** Время импорта */
    private long theTime ;
}
