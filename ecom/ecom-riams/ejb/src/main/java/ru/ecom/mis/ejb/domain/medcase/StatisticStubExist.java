package ru.ecom.mis.ejb.domain.medcase;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;

@Comment("Существующие номера стат. карт")
@Entity
@AIndexes({
    @AIndex(properties="medCase",table="StatisticStub")
}
	)
public class StatisticStubExist extends StatisticStub {

}
