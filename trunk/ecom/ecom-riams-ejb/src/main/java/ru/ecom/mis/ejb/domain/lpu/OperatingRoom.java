package ru.ecom.mis.ejb.domain.lpu;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes(
		{
			@AIndex(unique= false, properties = {"lpu"})
		}
	)
public class OperatingRoom extends BaseEntity {
	/** Название */
	@Comment("Название")
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}

	/** Лечебное учреждение */
	@Comment("Лечебное учреждение")
	@ManyToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}

	/** Лечебное учреждение */
	private MisLpu theLpu;
	/** Название */
	private String theName;

}
