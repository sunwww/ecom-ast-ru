package ru.ecom.mis.ejb.domain.workcalendar.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Тип способа обращения
 * @author Milamesher
 *
 */
@Comment("Тип способа обращения")
@Entity
@Getter
@Setter
public class VocWayOfRecord extends VocBaseEntity {
    /** Может быть указан регистратором */
    private Boolean isAvailableForRecorder;
}