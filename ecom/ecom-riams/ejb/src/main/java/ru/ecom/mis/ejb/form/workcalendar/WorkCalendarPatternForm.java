package ru.ecom.mis.ejb.form.workcalendar;

import lombok.Setter;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.Subclasses;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarPattern;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.Required;

@EntityForm
@EntityFormPersistance(clazz= WorkCalendarPattern.class)
@Comment("Шаблон рабочего календаря")
@WebTrail(comment = "Шаблон рабочего календаря", nameProperties= "name", view="entitySublassView-cal_pattern.do",list= "entityList-cal_pattern.do")
@Subclasses(value = {WorkCalendarPatternBySpecialistForm.class })
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkCalendar/Pattern")
@Setter
public class WorkCalendarPatternForm extends IdEntityForm {
	/** Тип календаря */
	@Comment("Тип календаря")
	@Persist @Required
	public Long getCalendarType() {return calendarType;}
	private Long calendarType;

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist
	public Long getWorkFunction() {return workFunction;}
	private Long workFunction;

	/** Тип занятости*/
	@Comment("Тип занятости")
	@Persist 
	public Long getWorkBusy() {return workBusy;}
	private Long workBusy;

	/** Название
	 */
	@Comment("Название")
	@Persist @DoUpperCase @Required
	public String getName() {return name;}
	private String name;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return lpu;}
	private Long lpu;
	
	/** Проф.день */
	@Comment("Проф.день")
	public WorkCalendarProphDayAlgorithmForm getProphDayAlgorithmForm() {return prophDayAlgorithmForm;}
	private WorkCalendarProphDayAlgorithmForm prophDayAlgorithmForm = new WorkCalendarProphDayAlgorithmForm();
	
	/** Алгоритм на определенные дни */
	@Comment("Алгоритм на определенные дни")
	public WorkCalendarDatesAlgorithmForm getDatesAlgorithmForm() {return datesAlgorithmForm;}
	private WorkCalendarDatesAlgorithmForm datesAlgorithmForm = new WorkCalendarDatesAlgorithmForm();
	
	/** Алгоритм на недели */
	@Comment("Алгоритм на недели")
	public WorkCalendarWeekAlgorithmForm getWeekAlgorithmForm0() {return weekAlgorithmForm0;}
	private WorkCalendarWeekAlgorithmForm weekAlgorithmForm0 = new WorkCalendarWeekAlgorithmForm() ;
	
	/** Алгоритм на недели */
	@Comment("Алгоритм на недели")
	public WorkCalendarWeekAlgorithmForm getWeekAlgorithmForm1() {return weekAlgorithmForm1;}
	private WorkCalendarWeekAlgorithmForm weekAlgorithmForm1 = new WorkCalendarWeekAlgorithmForm() ;
	
	/** Алгоритм на недели */
	@Comment("Алгоритм на недели")
	public WorkCalendarWeekAlgorithmForm getWeekAlgorithmForm2() {return weekAlgorithmForm2;}
	private WorkCalendarWeekAlgorithmForm weekAlgorithmForm2 = new WorkCalendarWeekAlgorithmForm() ;
	
	/** Алгоритм по дням недели */
	@Comment("Алгоритм по дням недели")
	public WorkCalendarWeekDaysAlgorithmForm getWeekDaysAlgorithmForm0() {return weekDaysAlgorithmForm0;}
	private WorkCalendarWeekDaysAlgorithmForm weekDaysAlgorithmForm0 = new WorkCalendarWeekDaysAlgorithmForm();

	/** Алгоритм по дням недели */
	@Comment("Алгоритм по дням недели")
	public WorkCalendarWeekDaysAlgorithmForm getWeekDaysAlgorithmForm1() {return weekDaysAlgorithmForm1;}
	private WorkCalendarWeekDaysAlgorithmForm weekDaysAlgorithmForm1 = new WorkCalendarWeekDaysAlgorithmForm();

	/** Алгоритм по дням недели */
	@Comment("Алгоритм по дням недели")
	public WorkCalendarWeekDaysAlgorithmForm getWeekDaysAlgorithmForm2() {return weekDaysAlgorithmForm2;}
	private WorkCalendarWeekDaysAlgorithmForm weekDaysAlgorithmForm2 = new WorkCalendarWeekDaysAlgorithmForm();
	
	/** Рабочий день1 */
	@Comment("Рабочий день1")
	public WorkCalendarDayPatternForm getDayPattern1Form() {return dayPattern1Form;}
	private WorkCalendarDayPatternForm dayPattern1Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 2 */
	@Comment("Рабочий день 2")
	public WorkCalendarDayPatternForm getDayPattern2Form() {return dayPattern2Form;}
	private WorkCalendarDayPatternForm dayPattern2Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 3 */
	@Comment("Рабочий день 3")
	public WorkCalendarDayPatternForm getDayPattern3Form() {return dayPattern3Form;}
	private WorkCalendarDayPatternForm dayPattern3Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 4 */
	@Comment("Рабочий день 4")
	public WorkCalendarDayPatternForm getDayPattern4Form() {return dayPattern4Form;}
	private WorkCalendarDayPatternForm dayPattern4Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 5 */
	@Comment("Рабочий день 5")
	public WorkCalendarDayPatternForm getDayPattern5Form() {return dayPattern5Form;}
	private WorkCalendarDayPatternForm dayPattern5Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 6 */
	@Comment("Рабочий день 6")
	public WorkCalendarDayPatternForm getDayPattern6Form() {return dayPattern6Form;}
	private WorkCalendarDayPatternForm dayPattern6Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 7 */
	@Comment("Рабочий день 7")
	public WorkCalendarDayPatternForm getDayPattern7Form() {return dayPattern7Form;}
	private WorkCalendarDayPatternForm dayPattern7Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 8 */
	@Comment("Рабочий день 8")
	public WorkCalendarDayPatternForm getDayPattern8Form() {return dayPattern8Form;}
	private WorkCalendarDayPatternForm dayPattern8Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 9 */
	@Comment("Рабочий день 9")
	public WorkCalendarDayPatternForm getDayPattern9Form() {return dayPattern9Form;}
	private WorkCalendarDayPatternForm dayPattern9Form = new WorkCalendarDayPatternForm();
	
	/** Используется алгоритм профдня */
	@Comment("Используется алгоритм профдня")
	public Boolean getIsProfday() {return isProfday;}
    private Boolean isProfday;

	/** Используется алгоритм дней */
	@Comment("Используется алгоритм дней")
	public Boolean getIsDays() {return isDays;}
    private Boolean isDays;

	/** Используется алгоритм рабочей недели */
	@Comment("Используется алгоритм рабочей недели")
	public Boolean getIsWeek() {return isWeek;}
    private Boolean isWeek;

	/** Используется алгоритм рабочих дней недели */
	@Comment("Используется алгоритм рабочих дней недели")
	public Boolean getIsWeekDays() {return isWeekDays;}
	private Boolean isWeekDays;

	/** Тип профосмотра */
	@Comment("Тип профосмотра")
	public String getProfType() {return profType;}
	private String profType;
}
