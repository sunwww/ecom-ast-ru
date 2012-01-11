package ru.ecom.mis.ejb.domain.patient;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * @author  azviagin
 */
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties={"patient"})
	,@AIndex(properties={"lpu"})
	,@AIndex(properties={"area"})
})
public class LpuAttachedByDepartment extends BaseEntity {

	/** Участок */
	@Comment("Участок")
	@OneToOne
	public LpuArea getArea() {
		return theArea;
	}

	public void setArea(LpuArea aArea) {
		theArea = aArea;
	}

	/** Пациент */
	@Comment("Пациент")
	@ManyToOne
	public Patient getPatient() {
		return thePatient;
	}

	public void setPatient(Patient aPatient) {
		thePatient = aPatient;
	}

	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {
		return theLpu;
	}

	public void setLpu(MisLpu aLpu) {
		theLpu = aLpu;
	}

	@Transient
	public String getLpuFullname() {
		StringBuilder sb = new StringBuilder() ;
		MisLpu lpu = theLpu ;
		while (lpu!=null) {
			sb.insert(0, lpu.getName()) ;
			lpu = lpu.getParent() ;
			if(lpu!=null) sb.insert(0," / ") ;
		}
		return sb.toString() ;
	}
	public void setLpuFullname(String aLpuFullname) {}
	/** ЛПУ */
	private MisLpu theLpu;
	/** Участок */
	private LpuArea theArea;
	/** Пациент */
	private Patient thePatient;
}
