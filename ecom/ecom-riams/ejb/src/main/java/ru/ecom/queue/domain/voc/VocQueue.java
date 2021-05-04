package ru.ecom.queue.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**Виды очередей*/
@Entity
@Getter
@Setter
public class VocQueue extends VocBaseEntity {
    /** Не используется */
    private Boolean isArchival=false ;

    /** Префикс для талона */
    private String prefix ;
}
