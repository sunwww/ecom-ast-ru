package ru.ecom.mis.ejb.domain.directory;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class Department extends BaseEntity{
    
    
    /** Отделение */
    @Comment("Отделение")
    @OneToOne
    public MisLpu getDepartment() {return department;}
    private MisLpu department;
    
    /** Корпус */
    @Comment("Корпус")
    @OneToOne
    public VocBuilding getBuilding() {return building;}
    private VocBuilding building;
    
    /** Этаж */
    @Comment("Этаж")
    @OneToOne
    public VocBuildingLevel getBuildingLevel() {return buildingLevel;}
    private VocBuildingLevel buildingLevel;
}