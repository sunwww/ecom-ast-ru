package ru.ecom.address.ejb.domain.address;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 * Тип адреса: улица, город, область, переулок и т.д.
 */
@Entity
@Comment("Тип адреса")
@AIndexes(@AIndex(unique = true, properties = "shortName"))
@Table(schema="SQLUser")
@Setter
@Getter
public class AddressType extends BaseEntity {

    /** Код в ОМС */
    private String omcCode ;
    /** Сокращенное наименование */
    private String shortName ;
    /** Наименование */
    private String name ;
}
