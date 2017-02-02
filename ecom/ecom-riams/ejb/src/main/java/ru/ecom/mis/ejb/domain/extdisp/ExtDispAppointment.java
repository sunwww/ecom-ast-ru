package ru.ecom.mis.ejb.domain.extdisp;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDispAppointment;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocKindMedHelp;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocKindSurvey;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
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
		
		/** Специальнось врача */
		@Comment("Специальнось врача")
		@OneToOne
		public VocWorkFunction getWorkFunction(){return theWorkFunction ;}
		public void setWorkFunction (VocWorkFunction aWorkFunction ){theWorkFunction  = aWorkFunction ;}
		private VocWorkFunction theWorkFunction;
		
		/** Вид обследования */
		@Comment("Вид обследования")
		@OneToOne
		public VocKindSurvey getKindSurvey(){return theKindSurvey;}
		public void setKindSurvey(VocKindSurvey aKindSurvey){theKindSurvey = aKindSurvey;}
		private VocKindSurvey theKindSurvey;
			
		/** Профиль мед. помощи */
		@Comment("Профиль мед. помощи")
		@OneToOne
		public VocKindMedHelp getKindMedHelp(){return theKindMedHelp;}
		public void setKindMedHelp(VocKindMedHelp aKindMedHelp){theKindMedHelp = aKindMedHelp;}
		private VocKindMedHelp theKindMedHelp ;
		
		/** Профиль коек */
		@Comment("Профиль коек")
		@OneToOne
		public VocBedType getBedType(){return theBedType;}
		public void setBedType(VocBedType aBedType){theBedType = aBedType;}
		private VocBedType theBedType ;
		
		/** Дисп. карта */
		@Comment("Дисп. карта")
		@OneToOne
		public ExtDispCard getDispCard(){return theDispCard;}
		public void setDispCard(ExtDispCard aDispCard){theDispCard = aDispCard;}
		private ExtDispCard theDispCard ;


}
