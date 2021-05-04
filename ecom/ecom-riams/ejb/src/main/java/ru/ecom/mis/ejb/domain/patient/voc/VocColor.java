package ru.ecom.mis.ejb.domain.patient.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Milamesher on 06.05.2019.
 * Справочник цветов (или картинки вместо цвета).
 */
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocColor extends VocBaseEntity {
    /** Название картинки, которую выводить в браслет */
    private String picture;
}