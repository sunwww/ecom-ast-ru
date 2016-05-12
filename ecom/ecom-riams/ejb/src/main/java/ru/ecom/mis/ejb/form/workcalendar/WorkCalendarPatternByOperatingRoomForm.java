package ru.ecom.mis.ejb.form.workcalendar;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalenPatternByOperRoom;
import ru.ecom.mis.ejb.form.lpu.OperatingRoomForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= WorkCalenPatternByOperRoom.class)
@Comment("Шаблон операционного календаря")
@WebTrail(comment = "Шаблон операционного календаря", nameProperties= "info", view="entityParentView-cal_workCalendarPatternByOperRoom.do",list= "entityParentList-cal_workCalendarPatternByOperRoom.do")
@Parent(property="operatingRoom", parentForm=OperatingRoomForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MisLpu/OperatingRoom/Pattern")
public class WorkCalendarPatternByOperatingRoomForm extends WorkCalendarPatternForm {
	/** Операционная */
	@Comment("Операционная")
	@Persist @Required
	public Long getOperatingRoom() {
		return theOperatingRoom;
	}

	public void setOperatingRoom(Long aOperatingRoom) {
		theOperatingRoom = aOperatingRoom;
	}

	/** Операционная */
	private Long theOperatingRoom;

}
