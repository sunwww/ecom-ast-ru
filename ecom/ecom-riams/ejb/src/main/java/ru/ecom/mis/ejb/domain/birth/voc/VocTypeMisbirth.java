package ru.ecom.mis.ejb.domain.birth.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Milamesher on 21.12.2018.
 * Типы выкидыша
 */
@Comment("Справочник типов выкидыша")
@Entity
@Table(schema="SQLUser")
public class VocTypeMisbirth extends VocBaseEntity {
}