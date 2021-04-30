package ru.ecom.mis.ejb.domain.userdocument.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
/*Справочник динамических документов*/

@Getter
@Setter
public class VocDynamicDocument extends VocBaseEntity {
    /** Содержимое формы */
    @Comment("Содержимое формы")
    @Column(length= ColumnConstants.TEXT_MAXLENGHT)
    public String getContent() {return content;}
    private String content ;
}