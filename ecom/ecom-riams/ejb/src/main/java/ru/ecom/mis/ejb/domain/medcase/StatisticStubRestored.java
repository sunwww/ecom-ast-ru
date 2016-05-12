package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Восстановленные стат.номера")
@Entity
@Table(schema="SQLUser")
public class StatisticStubRestored extends StatisticStub {

}
