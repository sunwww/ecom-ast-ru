package ru.ecom.mis.ejb.domain.lpu;

import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class ConsultingRoom extends WorkPlace {
	/** Специалист */
	@Comment("Специалист")
	@ManyToMany
	public List<WorkFunction> getWorkFunctions() {return theWorkFunctions;}
	public void setWorkFunctions(List<WorkFunction> aWorkFunction) {theWorkFunctions = aWorkFunction;}
	/** Специалист */
	private List<WorkFunction> theWorkFunctions;

}
