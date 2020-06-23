package ru.ecom.mis.ejb.domain.medcase;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Comment("Медицинская услуга")
@Entity
public class MedServiceGroup extends MedService {

}