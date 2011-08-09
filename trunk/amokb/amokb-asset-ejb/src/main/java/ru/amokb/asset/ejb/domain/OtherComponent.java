package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Другой компонент
	 */
	@Comment("Другой компонент")
@Entity
@Table(schema="SQLUser")
public class OtherComponent extends Component{
}
