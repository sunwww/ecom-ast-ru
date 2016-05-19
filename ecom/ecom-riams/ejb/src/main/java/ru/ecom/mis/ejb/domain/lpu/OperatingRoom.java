package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.worker.GroupWorkFunction;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes(
		{
//			@AIndex(unique= false, properties = {"lpu"})
		}
	)
public class OperatingRoom extends GroupWorkFunction {


}