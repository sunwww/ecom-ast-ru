package ru.amokb.asset.ejb.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * Дисковод гибких дисков
	 */
	@Comment("Дисковод гибких дисков")
@Entity
@Table(schema="SQLUser")
public class FloppyDrive extends Component{
}
