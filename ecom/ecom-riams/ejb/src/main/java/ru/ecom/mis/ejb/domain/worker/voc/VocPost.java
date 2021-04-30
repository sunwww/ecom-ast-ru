package ru.ecom.mis.ejb.domain.worker.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

/**
 *  Справочник должностей
 */
@Entity
@Comment("Справочник должностей")
@Table(schema="SQLUser")
@Getter
@Setter
public class VocPost extends VocIdName {
	
    /** Код профиля отделения для стационара или специалиста для поликлиники */
	@Comment("Код профиля отделения для стационара или специалиста для поликлиники")
	@OneToOne
	public VocOmcDepType getOmcDepType() {
		return omcDepType;
	}
	/** Код профиля отделения для стационара или специалиста для поликлиники */
	private VocOmcDepType omcDepType;
	
	/** Врачебная должность по ОМС */
	@Comment("Врачебная должность по ОМС")
	@OneToOne
	public VocOmcDoctorPost getOmcDoctorPost() {
		return omcDoctorPost;
	}
	private VocOmcDoctorPost omcDoctorPost;
	
	/** Рабочие функции */
	@Comment("Рабочие функции")
	@OneToMany(mappedBy="vocPost", cascade=CascadeType.ALL)
	public List<VocWorkFunction> getVocWorkFunctions() {
		return vocWorkFunctions;
	}
	/** Рабочие функции */
	private List<VocWorkFunction> vocWorkFunctions;
}
