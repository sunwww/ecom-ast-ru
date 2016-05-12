package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Существующие номера стат. карт")
@Entity
@AIndexes({
    @AIndex(properties="medCase",table="StatisticStub")
}
	)
@Table(schema="SQLUser")
public class StatisticStubExist extends StatisticStub {

}
