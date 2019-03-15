package ru.ecom.mis.ejb.domain.patient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.voc.VocKinsmanRole;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;

/**
 * Родственник
 * @author azviagin
 *
 */

@Comment("Родственник")
@Entity
@AIndexes({
	@AIndex(properties={"person"})
	,@AIndex(properties={"kinsman"})
})
@Table(schema="SQLUser")
public class Kinsman extends BaseEntity{
	
	/** Персона */
	@Comment("Персона")
	@ManyToOne
	public Patient getPerson() {
		return thePerson;
	}
	

	public void setPerson(Patient aPerson) {
		thePerson = aPerson;
	}

	/** Персона */
	private Patient thePerson;
	
	/** Родственник */
	@Comment("Родственник")
	@ManyToOne
	public Patient getKinsman() {
		return theKinsman;
	}

	public void setKinsman(Patient aKinsman) {
		theKinsman = aKinsman;
	}

	/** Родственник */
	private Patient theKinsman;
	
	/** Родственная роль */
	@Comment("Родственная роль")
	@OneToOne
	public VocKinsmanRole getKinsmanRole() {
		return theKinsmanRole;
	}

	public void setKinsmanRole(VocKinsmanRole aKinsmanRole) {
		theKinsmanRole = aKinsmanRole;
	}

	/** Родственная роль */
	private VocKinsmanRole theKinsmanRole;
	
	@Transient
	public String getKinsmanInfo() {
		return theKinsman!=null ? theKinsman.getPatientInfo() :"";
	}
	
	@Transient
	public String getKinsmanRoleInfo() {
		return theKinsmanRole!=null ? theKinsmanRole.getName() : "" ;
	}
	@Transient
	public String getInfo() {
		return getKinsmanRoleInfo() + " " + getKinsmanInfo();
	}

}
