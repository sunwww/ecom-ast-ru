package ru.ecom.mis.ejb.domain.contract.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник меток для договоров
 */
@Comment("Справочник меток для договоров")
@Entity
@Table(schema="SQLUser")
public class VocContractLabel extends VocBaseEntity {

}
