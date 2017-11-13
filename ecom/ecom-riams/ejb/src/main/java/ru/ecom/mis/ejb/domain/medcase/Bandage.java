package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * Created by Администратор on 13.09.2017.
 */
@Entity
@Comment("Перевязка")
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class Bandage extends MedicalManipulation {
}
