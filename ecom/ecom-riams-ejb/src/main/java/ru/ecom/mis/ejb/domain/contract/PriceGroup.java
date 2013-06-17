package ru.ecom.mis.ejb.domain.contract;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;

@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(unique= false, properties = {"name"})
	,@AIndex(unique= false, properties = {"priceList"})
	,@AIndex(unique= false, properties = {"parent"})
})
public class PriceGroup extends PricePosition {


}
