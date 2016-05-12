package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Медицинская услуга")
@Entity
@Table(schema="SQLUser")
public class MedServiceGroup extends MedService {

}