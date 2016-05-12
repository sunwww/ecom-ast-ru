package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.JournalPatternCalendar;
import ru.ecom.mis.ejb.form.workcalendar.WorkCalendarPatternForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz= JournalPatternCalendar.class)
@Comment("Журнал шаблона календаря")
@WebTrail(comment = "Журнал шаблона  календаря", nameProperties= "info", view="entityParentView-cal_journalPatternCalendar.do")
@Parent(property="pattern", parentForm=WorkCalendarPatternForm.class) 
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/JournalPattern")
public class JournalPatternCalendarByPatternForm extends JournalPatternCalendarForm{

}
