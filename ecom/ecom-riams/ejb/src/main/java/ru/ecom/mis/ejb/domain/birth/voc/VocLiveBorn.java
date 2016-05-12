package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
@Entity
@Comment("Родился (живой -1,мертвый-2)")
@Table(schema="SQLUser")
public class VocLiveBorn extends VocBaseEntity{

}
