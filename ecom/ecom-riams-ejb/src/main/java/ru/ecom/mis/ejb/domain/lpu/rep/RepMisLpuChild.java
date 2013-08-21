package ru.ecom.mis.ejb.domain.lpu.rep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/**
 * @author  azviagin
 */
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties={"lpu"})
})
public class RepMisLpuChild implements Serializable {
	
    /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }

    /** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {
		return theLpu;
	}

	public void setLpu(MisLpu aLpu) {
		theLpu = aLpu;
	}

	/** Потомок */
	@Comment("Потомок")
	@OneToOne
	public MisLpu getChildLpu() {
		return theChildLpu;
	}

	public void setChildLpu(MisLpu aChildLpu) {
		theChildLpu = aChildLpu;
	}

	/** Название */
	@Comment("Название")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getTrailName() {
		return theTrailName;
	}

	public void setTrailName(String aTrailName) {
		theTrailName = aTrailName;
	}

	/** Уровень */
	@Comment("Уровень")
	public int getLpuLevel() {
		return theLpuLevel;
	}

	public void setLpuLevel(int aLpuLevel) {
		theLpuLevel = aLpuLevel;
	}

	/** Уровень */
	private int theLpuLevel;
	/** Название */
	private String theTrailName;
	/** Потомок */
	private MisLpu theChildLpu;
	/** ЛПУ */
	private MisLpu theLpu;
    
    /** Идентификатор */
    private long theId ;
}
