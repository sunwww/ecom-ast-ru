package ru.ecom.expert2.domain.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expert2.domain.E2Entry;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class VocE2EntryFactor extends VocBaseEntity {

    /** Запись */
    @Comment("Запись")
    @ManyToMany
    @JoinTable(name = "e2entry_factor", joinColumns = @JoinColumn(name="factor_id")
    ,inverseJoinColumns = @JoinColumn(name = "entry_id"))
    public List<E2Entry> getEntries() {return entries;}
    private List<E2Entry> entries ;
}
