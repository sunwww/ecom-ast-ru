package ru.ecom.mis.ejb.domain.extdispplan;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * План доп. диспансеризации для населения
  */
@Entity
@Getter
@Setter
public class ExtDispPlanPopulation extends BaseEntity {

    /** Год плана */
    private Long year ;
}
