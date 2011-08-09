package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@Comment("Справочник исходов хирургических операций: 1. без летального исхода, 2. летальный исход")
public class VocOperationOutcome extends VocBaseEntity{

}
