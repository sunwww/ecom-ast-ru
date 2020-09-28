package ru.ecom.mis.ejb.domain.birth.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Milamesher on 10.12.2018.
 * Классификация Робсона
 */
@Comment("Справочник классификация Робсона")
@Entity
@Table(schema="SQLUser")
public class VocRobsonClass extends VocBaseEntity {
    /** Соответствие группа-подгруппа */
    @Comment("Соответствие группа-подгруппа")
    @ManyToMany
    public List<VocSubRobson> getSubs() {return theSubs;}
    public void setSubs(List<VocSubRobson> aSubs) {theSubs = aSubs;}

    /** Соответствие группа-подгруппа */
    private List<VocSubRobson> theSubs;
}