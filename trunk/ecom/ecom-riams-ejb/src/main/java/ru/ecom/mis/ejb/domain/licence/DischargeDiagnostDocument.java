package ru.ecom.mis.ejb.domain.licence;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Выписка диагностических служб")
@Table(schema="SQLUser")
public class DischargeDiagnostDocument extends InternalDocuments {

}
