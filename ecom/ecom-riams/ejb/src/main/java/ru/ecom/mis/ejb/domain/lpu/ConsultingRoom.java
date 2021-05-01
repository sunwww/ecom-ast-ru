package ru.ecom.mis.ejb.domain.lpu;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class ConsultingRoom extends WorkPlace {
	/** Специалист */
	@Comment("Специалист")
	@ManyToMany
	public List<WorkFunction> getWorkFunctions() {return workFunctions;}
	/** Специалист */
	private List<WorkFunction> workFunctions;

}
