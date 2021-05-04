package ru.ecom.mis.ejb.domain.medcase.voc;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Справочник операций")
@Table(schema="SQLUser")
@AIndexes
({@AIndex(properties = {"codeF"})})
@Getter
@Setter
public class VocOperation extends VocBaseEntity{

	/** Отделения */
	@Comment("Отделения")
	@ManyToMany
	public List<MisLpu> getDepartments() {return departments;}
	/** Отделения */
	private List<MisLpu> departments;
	/** Код федеральный */
	private String codeF;
	/** Уровонь сложности */
	private Long complexity;
	/** Дата окончания актуальности */
	private Date finishActualDate;
	/** Дата начала актуальности */
	private Date startActualDate;

}
