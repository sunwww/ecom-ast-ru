package ru.ecom.mis.ejb.domain.lpu;

import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.worker.GroupWorkFunction;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema="SQLUser")
@AIndexes(
		{
//			@AIndex(unique= false, properties = {"lpu"})
		}
	)
public class OperatingRoom extends GroupWorkFunction {


}
