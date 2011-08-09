package ru.amokb.asset.ejb.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.amokb.asset.ejb.domain.voc.VocHDD;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Жесткий диск
	 */
	@Comment("Жесткий диск")
@Entity
@Table(schema="SQLUser")
public class HDD extends Component{
	/**
	 * Емкость в ГБ
	 */
	@Comment("Емкость в ГБ")
	
	public BigDecimal getStorage() {
		return theStorage;
	}
	public void setStorage(BigDecimal aStorage) {
		theStorage = aStorage;
	}
	/**
	 * Емкость в ГБ
	 */
	private BigDecimal theStorage;
	/**
	 * Тип жесткого диска
	 */
	@Comment("Тип жесткого диска")
	@OneToOne
	public VocHDD getHddType() {
		return theHddType;
	}
	public void setHddType(VocHDD aHddType) {
		theHddType = aHddType;
	}
	/**
	 * Тип жесткого диска
	 */
	private VocHDD theHddType;
}
