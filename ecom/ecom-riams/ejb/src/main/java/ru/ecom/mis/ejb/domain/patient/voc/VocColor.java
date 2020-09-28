package ru.ecom.mis.ejb.domain.patient.voc;

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
public class VocColor extends VocBaseEntity {
    /** Название картинки, которую выводить в браслет */
    @Comment("Название картинки, которую выводить в браслет")
    public String getPicture() {return thePicture;}
    public void setPicture(String aPicture) {thePicture = aPicture;}

    /** Название картинки, которую выводить в браслет */
    private String thePicture;
}