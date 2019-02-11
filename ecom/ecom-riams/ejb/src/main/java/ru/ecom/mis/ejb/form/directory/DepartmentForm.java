package ru.ecom.mis.ejb.form.directory;



import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.directory.Department;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz = Department.class)
@Comment("Форма калькуляции")
@WebTrail(comment = "Форма калькуляции"
, nameProperties= "id", list="entityParentList-directory_department.do", view="entityParentView-directory_department.do")
@EntityFormSecurityPrefix("/Policy/Mis/Directory/Department")

public class DepartmentForm extends IdEntityForm{
	
	
	 /** Отделение */
	    @Comment("Отделение")
	    @Persist
	    public Long getDepartment() {return theDepartment;}
	    public void setDepartment(Long aDepartment) {theDepartment = aDepartment;}
	    private Long theDepartment;
	    
	    /** Корпус */
	    @Comment("Корпус")
	    @Persist
	    public Long getBuilding() {return theBuilding;}
	    public void setBuilding(Long aBuilding) {theBuilding = aBuilding;}
	    private Long theBuilding;
	    
	    /** Этаж */
	    @Comment("Этаж")
	    @Persist
	    public Long getBuildingLevel() {return theBuildingLevel;}
	    public void setBuildingLevel(Long aBuildingLevel) {theBuildingLevel = aBuildingLevel;}
	    private Long theBuildingLevel;
}
