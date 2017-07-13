package ru.ecom.mis.ejb.domain.directory;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.directory.voc.VocBuilding;
import ru.ecom.mis.ejb.domain.directory.voc.VocBuildingLevel;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/** Отделения */
@Comment("Отделения")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class Department extends BaseEntity{
    
    
    /** Отделение */
    @Comment("Отделение")
    @OneToOne
    public MisLpu getDepartment() {return theDepartment;}
    public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}
    private MisLpu theDepartment;
    
    /** Корпус */
    @Comment("Корпус")
    @OneToOne
    public VocBuilding getBuilding() {return theBuilding;}
    public void setBuilding(VocBuilding aBuilding) {theBuilding = aBuilding;}
    private VocBuilding theBuilding;
    
    /** Этаж */
    @Comment("Этаж")
    @OneToOne
    public VocBuildingLevel getBuildingLevel() {return theBuildingLevel;}
    public void setBuildingLevel(VocBuildingLevel aBuildingLevel) {theBuildingLevel = aBuildingLevel;}
    private VocBuildingLevel theBuildingLevel;
}