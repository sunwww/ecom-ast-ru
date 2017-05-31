package ru.ecom.mis.ejb.domain.disability.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

/**
 * Created by vtsybulin on 30.05.2017.
 */
@Comment("Статус экспорта больничного листа")
@Entity
public class VocDisabilityDocumentExportStatus extends VocBaseEntity {
}
