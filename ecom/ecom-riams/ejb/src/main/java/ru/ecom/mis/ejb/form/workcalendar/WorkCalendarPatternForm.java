package ru.ecom.mis.ejb.form.workcalendar;

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
public class WorkCalendarPatternForm extends IdEntityForm {
	/** Тип календаря */
	@Comment("Тип календаря")
	@Persist @Required
	public Long getCalendarType() {return theCalendarType;}
	public void setCalendarType(Long aCalendarType) {theCalendarType = aCalendarType;}
	private Long theCalendarType;

	/** Рабочая функция */
	@Comment("Рабочая функция")
	@Persist
	public Long getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(Long aWorkFunction) {theWorkFunction = aWorkFunction;}
	private Long theWorkFunction;

	/** Тип занятости*/
	@Comment("Тип занятости")
	@Persist 
	public Long getWorkBusy() {return theWorkBusy;}
	public void setWorkBusy(Long aWorkBusy) {theWorkBusy = aWorkBusy;}
	private Long theWorkBusy;

	/** Название
	 */
	@Comment("Название")
	@Persist @DoUpperCase @Required
	public String getName() {return theName;}
	public void setName(String aName) {theName = aName;}
	private String theName;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}
	private Long theLpu;
	
	/** Проф.день */
	@Comment("Проф.день")
	public WorkCalendarProphDayAlgorithmForm getProphDayAlgorithmForm() {return theProphDayAlgorithmForm;}
	public void setProphDayAlgorithmForm(WorkCalendarProphDayAlgorithmForm aProphDayAlgorithmForm) {theProphDayAlgorithmForm = aProphDayAlgorithmForm;}
	private WorkCalendarProphDayAlgorithmForm theProphDayAlgorithmForm = new WorkCalendarProphDayAlgorithmForm();
	
	/** Алгоритм на определенные дни */
	@Comment("Алгоритм на определенные дни")
	public WorkCalendarDatesAlgorithmForm getDatesAlgorithmForm() {return theDatesAlgorithmForm;}
	public void setDatesAlgorithmForm(WorkCalendarDatesAlgorithmForm aDatesAlgorithmForm) {theDatesAlgorithmForm = aDatesAlgorithmForm;}
	private WorkCalendarDatesAlgorithmForm theDatesAlgorithmForm = new WorkCalendarDatesAlgorithmForm();
	
	/** Алгоритм на недели */
	@Comment("Алгоритм на недели")
	public WorkCalendarWeekAlgorithmForm getWeekAlgorithmForm0() {return theWeekAlgorithmForm0;}
	public void setWeekAlgorithmForm0(WorkCalendarWeekAlgorithmForm aWeekAlgorithmForm) {theWeekAlgorithmForm0 = aWeekAlgorithmForm;}
	private WorkCalendarWeekAlgorithmForm theWeekAlgorithmForm0 = new WorkCalendarWeekAlgorithmForm() ;
	
	/** Алгоритм на недели */
	@Comment("Алгоритм на недели")
	public WorkCalendarWeekAlgorithmForm getWeekAlgorithmForm1() {return theWeekAlgorithmForm1;}
	public void setWeekAlgorithmForm1(WorkCalendarWeekAlgorithmForm aWeekAlgorithmForm) {theWeekAlgorithmForm1 = aWeekAlgorithmForm;}
	private WorkCalendarWeekAlgorithmForm theWeekAlgorithmForm1 = new WorkCalendarWeekAlgorithmForm() ;
	
	/** Алгоритм на недели */
	@Comment("Алгоритм на недели")
	public WorkCalendarWeekAlgorithmForm getWeekAlgorithmForm2() {return theWeekAlgorithmForm2;}
	public void setWeekAlgorithmForm2(WorkCalendarWeekAlgorithmForm aWeekAlgorithmForm) {theWeekAlgorithmForm2 = aWeekAlgorithmForm;}
	private WorkCalendarWeekAlgorithmForm theWeekAlgorithmForm2 = new WorkCalendarWeekAlgorithmForm() ;
	
	/** Алгоритм по дням недели */
	@Comment("Алгоритм по дням недели")
	public WorkCalendarWeekDaysAlgorithmForm getWeekDaysAlgorithmForm0() {return theWeekDaysAlgorithmForm0;}
	public void setWeekDaysAlgorithmForm0(WorkCalendarWeekDaysAlgorithmForm aWeekDaysAlgorithmForm) {theWeekDaysAlgorithmForm0 = aWeekDaysAlgorithmForm;}
	private WorkCalendarWeekDaysAlgorithmForm theWeekDaysAlgorithmForm0 = new WorkCalendarWeekDaysAlgorithmForm();

	/** Алгоритм по дням недели */
	@Comment("Алгоритм по дням недели")
	public WorkCalendarWeekDaysAlgorithmForm getWeekDaysAlgorithmForm1() {return theWeekDaysAlgorithmForm1;}
	public void setWeekDaysAlgorithmForm1(WorkCalendarWeekDaysAlgorithmForm aWeekDaysAlgorithmForm) {theWeekDaysAlgorithmForm1 = aWeekDaysAlgorithmForm;}
	private WorkCalendarWeekDaysAlgorithmForm theWeekDaysAlgorithmForm1 = new WorkCalendarWeekDaysAlgorithmForm();

	/** Алгоритм по дням недели */
	@Comment("Алгоритм по дням недели")
	public WorkCalendarWeekDaysAlgorithmForm getWeekDaysAlgorithmForm2() {return theWeekDaysAlgorithmForm2;}
	public void setWeekDaysAlgorithmForm2(WorkCalendarWeekDaysAlgorithmForm aWeekDaysAlgorithmForm) {theWeekDaysAlgorithmForm2 = aWeekDaysAlgorithmForm;}
	private WorkCalendarWeekDaysAlgorithmForm theWeekDaysAlgorithmForm2 = new WorkCalendarWeekDaysAlgorithmForm();
	
	/** Рабочий день1 */
	@Comment("Рабочий день1")
	public WorkCalendarDayPatternForm getDayPattern1Form() {return theDayPattern1Form;}
	public void setDayPattern1Form(WorkCalendarDayPatternForm aDayPattern1Form) {theDayPattern1Form = aDayPattern1Form;}
	private WorkCalendarDayPatternForm theDayPattern1Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 2 */
	@Comment("Рабочий день 2")
	public WorkCalendarDayPatternForm getDayPattern2Form() {return theDayPattern2Form;}
	public void setDayPattern2Form(WorkCalendarDayPatternForm aDayPattern2Form) {theDayPattern2Form = aDayPattern2Form;}
	private WorkCalendarDayPatternForm theDayPattern2Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 3 */
	@Comment("Рабочий день 3")
	public WorkCalendarDayPatternForm getDayPattern3Form() {return theDayPattern3Form;}
	public void setDayPattern3Form(WorkCalendarDayPatternForm aDayPattern3Form) {theDayPattern3Form = aDayPattern3Form;}
	private WorkCalendarDayPatternForm theDayPattern3Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 4 */
	@Comment("Рабочий день 4")
	public WorkCalendarDayPatternForm getDayPattern4Form() {return theDayPattern4Form;}
	public void setDayPattern4Form(WorkCalendarDayPatternForm aDayPattern4Form) {theDayPattern4Form = aDayPattern4Form;}
	private WorkCalendarDayPatternForm theDayPattern4Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 5 */
	@Comment("Рабочий день 5")
	public WorkCalendarDayPatternForm getDayPattern5Form() {return theDayPattern5Form;}
	public void setDayPattern5Form(WorkCalendarDayPatternForm aDayPattern5Form) {theDayPattern5Form = aDayPattern5Form;}
	private WorkCalendarDayPatternForm theDayPattern5Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 6 */
	@Comment("Рабочий день 6")
	public WorkCalendarDayPatternForm getDayPattern6Form() {return theDayPattern6Form;}
	public void setDayPattern6Form(WorkCalendarDayPatternForm aDayPattern6Form) {theDayPattern6Form = aDayPattern6Form;}
	private WorkCalendarDayPatternForm theDayPattern6Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 7 */
	@Comment("Рабочий день 7")
	public WorkCalendarDayPatternForm getDayPattern7Form() {return theDayPattern7Form;}
	public void setDayPattern7Form(WorkCalendarDayPatternForm aDayPattern7Form) {theDayPattern7Form = aDayPattern7Form;}
	private WorkCalendarDayPatternForm theDayPattern7Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 8 */
	@Comment("Рабочий день 8")
	public WorkCalendarDayPatternForm getDayPattern8Form() {return theDayPattern8Form;}
	public void setDayPattern8Form(WorkCalendarDayPatternForm aDayPattern8Form) {theDayPattern8Form = aDayPattern8Form;}
	private WorkCalendarDayPatternForm theDayPattern8Form = new WorkCalendarDayPatternForm();
	
	/** Рабочий день 9 */
	@Comment("Рабочий день 9")
	public WorkCalendarDayPatternForm getDayPattern9Form() {return theDayPattern9Form;}
	public void setDayPattern9Form(WorkCalendarDayPatternForm aDayPattern9Form) {theDayPattern9Form = aDayPattern9Form;}
	private WorkCalendarDayPatternForm theDayPattern9Form = new WorkCalendarDayPatternForm();
	
	/** Используется алгоритм профдня */
	@Comment("Используется алгоритм профдня")
	public Boolean getIsProfday() {return theIsProfday;}
	public void setIsProfday(Boolean aIsProfday) {theIsProfday = aIsProfday;}
    private Boolean theIsProfday;

	/** Используется алгоритм дней */
	@Comment("Используется алгоритм дней")
	public Boolean getIsDays() {return theIsDays;}
	public void setIsDays(Boolean aIsDays) {theIsDays = aIsDays;}
    private Boolean theIsDays;

	/** Используется алгоритм рабочей недели */
	@Comment("Используется алгоритм рабочей недели")
	public Boolean getIsWeek() {return theIsWeek;}
	public void setIsWeek(Boolean aIsWeek) {theIsWeek = aIsWeek;}
    private Boolean theIsWeek;

	/** Используется алгоритм рабочих дней недели */
	@Comment("Используется алгоритм рабочих дней недели")
	public Boolean getIsWeekDays() {return theIsWeekDays;}
	public void setIsWeekDays(Boolean aIsWeekDays) {theIsWeekDays = aIsWeekDays;}
	private Boolean theIsWeekDays;

	/** Тип профосмотра */
	@Comment("Тип профосмотра")
	public String getProfType() {return theProfType;}
	public void setProfType(String aProfType) {theProfType = aProfType;}
	private String theProfType;
}
