package ru.ecom.mis.ejb.domain.userdocument.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
/*Справочник динамических документов*/
public class VocDynamicDocument extends VocBaseEntity {
    /** Содержимое формы */
    @Comment("Содержимое формы")
    @Column(length= ColumnConstants.TEXT_MAXLENGHT)
    public String getContent() {return theContent;}
    public void setContent(String aContent) {theContent = aContent;}
    private String theContent ;
}