package ru.ecom.mis.ejb.domain.prescription.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Comment("Справочник причин отмены назначений на операцию")
@Table(schema = "SQLUser")
public class VocOperationCancelReason extends VocBaseEntity {
}