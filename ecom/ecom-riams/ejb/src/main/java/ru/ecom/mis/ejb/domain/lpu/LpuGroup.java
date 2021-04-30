package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Список ЛПУ в группе")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class LpuGroup extends BaseEntity{
	/** Центр */
	private Long groupLpu;
	
	/** ЛПУ */
	private Long lpu;
}
