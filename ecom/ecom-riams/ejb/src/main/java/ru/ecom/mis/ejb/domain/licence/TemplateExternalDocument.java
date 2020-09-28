package ru.ecom.mis.ejb.domain.licence;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Entity
@Comment("Шаблоны выходных форм для протоколов")
public class TemplateExternalDocument extends ExternalDocument{

}
