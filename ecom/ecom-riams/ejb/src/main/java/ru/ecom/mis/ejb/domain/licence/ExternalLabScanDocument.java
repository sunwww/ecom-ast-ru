package ru.ecom.mis.ejb.domain.licence;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
@Comment("Внешние сканированные документы по лаборатории")
public class ExternalLabScanDocument extends ExternalDocument {

}
