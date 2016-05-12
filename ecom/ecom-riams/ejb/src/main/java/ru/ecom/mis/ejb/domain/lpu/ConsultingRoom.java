package ru.ecom.mis.ejb.domain.lpu;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class ConsultingRoom extends WorkPlace {
	/** Специалист */
	@Comment("Специалист")
	@ManyToMany
	public List<WorkFunction> getWorkFunctions() {return theWorkFunctions;}
	public void setWorkFunctions(List<WorkFunction> aWorkFunction) {theWorkFunctions = aWorkFunction;}
	/** Специалист */
	private List<WorkFunction> theWorkFunctions;

}
