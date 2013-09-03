package ru.medos.ejb.persdata.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.medos.ejb.persdata.domain.DataOperation;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Пользователь
	 */
	@Comment("Пользователь")
@Entity
@Table(schema="SQLUser")
public class SecUser extends BaseEntity{
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	public List<DataOperation> getDataOperations() {
		return theDataOperations;
	}
	public void setDataOperations(List<DataOperation> aDataOperations) {
		theDataOperations = aDataOperations;
	}
	/**
	 * Операции с данными
	 */
	private List<DataOperation> theDataOperations;
}
