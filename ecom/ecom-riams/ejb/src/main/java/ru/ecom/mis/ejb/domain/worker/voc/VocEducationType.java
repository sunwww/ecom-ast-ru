package ru.ecom.mis.ejb.domain.worker.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип обучения 
 * (высшее-1, незаконченное высшее-2,  среднее специальное-3, среднее общее-4, неполное среднее-5,
 * начальное и ниже-6, неизвестно-7)
 * @author azviagin
 *
 */
@Comment("Тип обучения ")
@Entity
@Table(schema="SQLUser")
public class VocEducationType extends VocBaseEntity{

}
