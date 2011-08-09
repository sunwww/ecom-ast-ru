package ru.ecom.mis.ejb.domain.worker.voc;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.mis.ejb.domain.patient.voc.VocIdName;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 *  Справочник должностей
 */
@Entity
@Comment("Справочник должностей")
@Table(schema="SQLUser")
public class VocPost extends VocIdName {
	
    /** Код профиля отделения для стационара или специалиста для поликлиники */
	@Comment("Код профиля отделения для стационара или специалиста для поликлиники")
	@OneToOne
	public VocOmcDepType getOmcDepType() {
		return theOmcDepType;
	}

	public void setOmcDepType(VocOmcDepType aOmcDepType) {
		theOmcDepType = aOmcDepType;
	}
	/** Код профиля отделения для стационара или специалиста для поликлиники */
	private VocOmcDepType theOmcDepType;
	
	/** Врачебная должность по ОМС */
	@Comment("Врачебная должность по ОМС")
	@OneToOne
	public VocOmcDoctorPost getOmcDoctorPost() {
		return theOmcDoctorPost;
	}
	public void setOmcDoctorPost(VocOmcDoctorPost aOmcDoctorPost) {
		theOmcDoctorPost = aOmcDoctorPost;
	}
	/** Врачебная должность по ОМС */
	private VocOmcDoctorPost theOmcDoctorPost;
	
	/** Рабочие функции */
	@Comment("Рабочие функции")
	@OneToMany(mappedBy="vocPost", cascade=CascadeType.ALL)
	public List<VocWorkFunction> getVocWorkFunctions() {
		return theVocWorkFunctions;
	}

	public void setVocWorkFunctions(List<VocWorkFunction> aVocWorkFunctions) {
		theVocWorkFunctions = aVocWorkFunctions;
	}

	/** Рабочие функции */
	private List<VocWorkFunction> theVocWorkFunctions;
	
	 /** Тип рабочего места */
	@Comment("Тип рабочего места")
	@OneToOne
	public VocPostType getPostType() {
		return thePostType;
	}

	public void setPostType(VocPostType aPostType) {
		thePostType = aPostType;
	}

	/** Тип рабочего места */
	private VocPostType thePostType;
}
