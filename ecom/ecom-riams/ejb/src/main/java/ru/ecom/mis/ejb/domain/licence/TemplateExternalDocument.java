package ru.ecom.mis.ejb.domain.licence;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Шаблоны выходных форм для протоколов")
@Table(schema="SQLUser")
public class TemplateExternalDocument extends ExternalDocument{

}
