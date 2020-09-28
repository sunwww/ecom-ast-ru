package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * Created by Администратор on 13.09.2017.
 */
@Entity
@Comment("Перевязка")
@EntityListeners(DeleteListener.class)
public class Bandage extends MedicalManipulation {
}
