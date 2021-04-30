package ru.ecom.mis.ejb.domain.extdisp;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispAppointment;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocKindSurvey;
import ru.ecom.mis.ejb.domain.worker.voc.VocOmcDepType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/** Назначения для диспансеризации */
@Comment("Назначения для диспансеризации")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class ExtDispAppointment extends BaseEntity{
			
		/** Назначение */
		@Comment("Назначение")
		@OneToOne
		public VocExtDispAppointment getAppointment(){return appointment;}
		private VocExtDispAppointment appointment ;
		
		/** Вид обследования */
		@Comment("Вид обследования")
		@OneToOne
		public VocKindSurvey getKindSurvey(){return kindSurvey;}
		private VocKindSurvey kindSurvey;
		
		/** Профиль */
		@Comment("Профиль")
		@OneToOne
		public VocOmcDepType getProfile(){return profile;}
		private VocOmcDepType profile ;
		
		/** Дисп. карта */
		@Comment("Дисп. карта")
		@OneToOne
		public ExtDispCard getDispCard(){return dispCard;}
		private ExtDispCard dispCard ;
		
}
