package ru.ecom.mis.ejb.domain.patient;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class Kinsman extends BaseEntity{
	
	/** Персона */
	@Comment("Персона")
	@ManyToOne
	public Patient getPerson() {
		return person;
	}
	
	/** Персона */
	private Patient person;
	
	/** Родственник */
	@Comment("Родственник")
	@ManyToOne
	public Patient getKinsman() {
		return kinsman;
	}

	/** Родственник */
	private Patient kinsman;
	
	/** Родственная роль */
	@Comment("Родственная роль")
	@OneToOne
	public VocKinsmanRole getKinsmanRole() {
		return kinsmanRole;
	}

	/** Родственная роль */
	private VocKinsmanRole kinsmanRole;
	
	@Transient
	public String getKinsmanInfo() {
		return kinsman!=null ? kinsman.getPatientInfo() :"";
	}
	
	@Transient
	public String getKinsmanRoleInfo() {
		return kinsmanRole!=null ? kinsmanRole.getName() : "" ;
	}
	@Transient
	public String getInfo() {
		return getKinsmanRoleInfo() + " " + getKinsmanInfo();
	}

}
