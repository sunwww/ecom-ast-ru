package ru.ecom.mis.ejb.domain.extdispplan;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * План доп. диспансеризации для населения
  */
@Entity
public class ExtDispPlanPopulation extends BaseEntity {

    /** Год плана*/
    @Comment("Год плана")
    public Long getYear() {return theYear;}
    public void setYear(Long aYear) {theYear = aYear;}
    /** Год плана */
    private Long theYear ;
}
