package ru.ecom.mis.ejb.domain.contract;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
	/**
	 * Договорная персона  @author azviagin
	 */
	@Comment("Договорная персона")
@Entity
@Table(schema="SQLUser")
public abstract class ContractPerson extends BaseEntity{
		@Transient
		public String getInformation() {
			return "" ;
		}
}
