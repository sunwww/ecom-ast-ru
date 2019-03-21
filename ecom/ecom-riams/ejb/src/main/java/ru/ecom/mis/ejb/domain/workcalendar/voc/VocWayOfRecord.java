package ru.ecom.mis.ejb.domain.workcalendar.voc;

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
public class VocWayOfRecord extends VocBaseEntity {
    /** Может быть указан регистратором */
    @Comment("Может быть указан регистратором")
    public Boolean getIsAvailableForRecorder() {return theIsAvailableForRecorder;}
    public void setIsAvailableForRecorder(Boolean aIsAvailableForRecorder) {theIsAvailableForRecorder = aIsAvailableForRecorder;}
    /** Может быть указан регистратором */
    private Boolean theIsAvailableForRecorder;
}