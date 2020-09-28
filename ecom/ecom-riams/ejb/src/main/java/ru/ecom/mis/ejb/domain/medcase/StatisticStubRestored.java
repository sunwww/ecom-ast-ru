package ru.ecom.mis.ejb.domain.medcase;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Comment("Восстановленные стат.номера")
@Entity
public class StatisticStubRestored extends StatisticStub {

}
