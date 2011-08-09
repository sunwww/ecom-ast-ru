package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Видеокарта
	 */
	@Comment("Видеокарта")
@Entity
@Table(schema="SQLUser")
public class VideoCard extends Component{
}
