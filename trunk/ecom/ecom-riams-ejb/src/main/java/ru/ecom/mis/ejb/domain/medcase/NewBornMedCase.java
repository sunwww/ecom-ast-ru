package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.mis.ejb.domain.birth.NewBorn;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Случай лечения в отделении по новорожденному")
@Entity
@Table(schema="SQLUser")
public class NewBornMedCase extends DepartmentMedCase {
	/** Случай мед.обслуживания новорожденного */
	@Comment("Случай мед.обслуживания новорожденного")
	@OneToOne
	public NewBorn getNewBorn() {return theNewBorn;}
	public void setNewBorn(NewBorn aNewBorn) {theNewBorn = aNewBorn;}

	@Transient
	public String getStatCardBySLS() {
		if (getNewBorn()!=null && getNewBorn().getChildBirth()!=null && getNewBorn().getChildBirth().getMedCase()!=null) {
			if (getNewBorn().getChildBirth().getMedCase() instanceof DepartmentMedCase)  {
				DepartmentMedCase dep = (DepartmentMedCase) getNewBorn().getChildBirth().getMedCase() ;
				return dep.getStatCardBySLS();	
			}
			return "" ;
			
		}
		return "" ;
	}
	/** Случай мед.обслуживания новорожденного */
	private NewBorn theNewBorn;

}
