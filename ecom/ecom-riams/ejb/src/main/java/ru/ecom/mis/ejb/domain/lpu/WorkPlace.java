package ru.ecom.mis.ejb.domain.lpu;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@AIndexes({
	@AIndex(properties={"parent"})
	,@AIndex(properties={"name"})
	,@AIndex(properties={"lpu"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class WorkPlace extends BaseEntity{

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@OneToOne
	public MisLpu getLpu() {return lpu;}

	/** Родитель */
	@Comment("Родитель")
	@OneToOne
	public WorkPlace getParent() {return parent;}



	/** Неактуален */
	private Boolean isNoActuality;
	/** Комментарий */
	private String comment;
	/** Родитель */
	private WorkPlace parent;
	/** Лечебное учреждение */
	private MisLpu lpu;
	/** Название */
	private String name;
	
	/**
	 * Номер телефона
	 */
	private String phoneNumber;
}
