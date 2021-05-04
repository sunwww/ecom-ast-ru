package ru.ecom.expert2.domain.voc.federal;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Справочник типов диспансеризации
 */
@Entity
@Getter
@Setter
public class VocE2FondV016 extends VocBaseFederal {

    /** Rule */
    private String rule ;

}
