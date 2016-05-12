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
@Deprecated
public class NewBornMedCase extends DepartmentMedCase {
	
}
