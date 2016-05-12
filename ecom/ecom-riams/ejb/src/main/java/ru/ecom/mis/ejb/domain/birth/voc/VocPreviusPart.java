package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник предлежащей части плода (голова, ягодицы, смешанное)
 * @author oegorova
 *
 */

@Comment("Справочник предлежащей части плода")
@Entity
@Table(schema="SQLUser")
public class VocPreviusPart extends VocBaseEntity{

}
