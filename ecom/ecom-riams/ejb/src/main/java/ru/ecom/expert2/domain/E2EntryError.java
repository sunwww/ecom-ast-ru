package ru.ecom.expert2.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

@Entity
@AIndexes({
        @AIndex(properties = {"listEntry"})
        , @AIndex(properties = {"errorCode"})
        , @AIndex(properties = {"entry"})
})
@Getter
@Setter
public class E2EntryError extends BaseEntity {

    /**
     * Заполнение с ошибкой
     */
    @OneToOne
    public E2ListEntry getListEntry() {
        return listEntry;
    }

    private E2ListEntry listEntry;

    /**
     * Случай
     */
    @ManyToOne
    public E2Entry getEntry() {
        return entry;
    }

    private E2Entry entry;

    /**
     * Удаленная запись
     */
    private Boolean isDeleted;

    @PrePersist
    void onPrePesist() {
        if (isDeleted == null) {
            isDeleted = false;
        }
    }

    /**
     * Код ошибки
     */
    private String errorCode;

    public E2EntryError() {
    }

    public E2EntryError(E2Entry aEntry, String aCode) {
        this.entry = aEntry;
        this.errorCode = aCode;
        this.listEntry = aEntry.getListEntry();
    }

    public E2EntryError(E2Entry aEntry, String aCode, String aComment) {
        this.entry = aEntry;
        this.errorCode = aCode;
        this.listEntry = aEntry.getListEntry();
        comment = aComment;
    }

    /**
     * Примечание
     */
    private String comment;
}