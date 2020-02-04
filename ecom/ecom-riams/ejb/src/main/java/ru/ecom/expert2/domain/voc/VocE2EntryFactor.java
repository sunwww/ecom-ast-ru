package ru.ecom.expert2.domain.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expert2.domain.E2Entry;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class VocE2EntryFactor extends VocBaseEntity {

    /** Запись */
    @Comment("Запись")
    @ManyToMany
    @JoinTable(name = "e2entry_factor", joinColumns = @JoinColumn(name="factor_id")
    ,inverseJoinColumns = @JoinColumn(name = "entry_id"))
    public List<E2Entry> getEntries() {return theEntries;}
    public void setEntries(List<E2Entry> aEntries) {theEntries = aEntries;}
    private List<E2Entry> theEntries ;
}
