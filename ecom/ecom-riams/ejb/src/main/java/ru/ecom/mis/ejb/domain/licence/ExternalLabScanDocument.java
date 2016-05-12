package ru.ecom.mis.ejb.domain.licence;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Внешние сканированные документы по лаборатории")
@Table(schema="SQLUser")
public class ExternalLabScanDocument extends ExternalDocument {

}
