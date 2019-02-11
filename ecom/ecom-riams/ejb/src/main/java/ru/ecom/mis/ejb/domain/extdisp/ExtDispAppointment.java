package ru.ecom.mis.ejb.domain.extdisp;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
public class ExtDispAppointment extends BaseEntity{
			
		/** Назначение */
		@Comment("Назначение")
		@OneToOne
		public VocExtDispAppointment getAppointment(){return theAppointment;}
		public void setAppointment(VocExtDispAppointment aAppointment){theAppointment = aAppointment;}
		private VocExtDispAppointment theAppointment ;
		
		/** Вид обследования */
		@Comment("Вид обследования")
		@OneToOne
		public VocKindSurvey getKindSurvey(){return theKindSurvey;}
		public void setKindSurvey(VocKindSurvey aKindSurvey){theKindSurvey = aKindSurvey;}
		private VocKindSurvey theKindSurvey;
		
		/** Профиль */
		@Comment("Профиль")
		@OneToOne
		public VocOmcDepType getProfile(){return theProfile;}
		public void setProfile(VocOmcDepType aProfile){theProfile = aProfile;}
		private VocOmcDepType theProfile ;
		
		/** Дисп. карта */
		@Comment("Дисп. карта")
		@OneToOne
		public ExtDispCard getDispCard(){return theDispCard;}
		public void setDispCard(ExtDispCard aDispCard){theDispCard = aDispCard;}
		private ExtDispCard theDispCard ;
		
}
