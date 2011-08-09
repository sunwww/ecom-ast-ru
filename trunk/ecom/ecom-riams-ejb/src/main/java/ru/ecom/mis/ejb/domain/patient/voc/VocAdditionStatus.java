package ru.ecom.mis.ejb.domain.patient.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Дополнительный социальный статус ... работники ГДА")
@Entity
@Table(schema="SQLUser")
public class VocAdditionStatus extends VocIdName {
}

