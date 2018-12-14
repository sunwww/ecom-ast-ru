package ru.ecom.mis.ejb.form.calc;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.calc.CalculationsResult;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz = CalculationsResult.class)
@Comment("Результат калькулятора человека")
@WebTrail(comment = "Результат калькулятора человека"
, nameProperties= "id", list="entityParentList-calc_calculationsResult.do", view="entityParentView-calc_calculationsResult.do", shortView="entityShortView-calc_calculationsResult.do")
@Parent(property="departmentMedCase", parentForm=DepartmentMedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Calc/Calculation")
public class CalculationsResultForm extends IdEntityForm{
	
        /** СЛО */
	@Comment("СЛО")
	@Persist
	public Long getDepartmentMedCase(){return theDepartmentMedCase;}
	public void setDepartmentMedCase(Long aDepartmentMedCase){theDepartmentMedCase = aDepartmentMedCase;}
	private Long theDepartmentMedCase ;
	
	/** Калькулятор */
        @Comment("Калькулятор")
        @Persist
        public Long getCalculator() {return theCalculator;}
        public void setCalculator(Long aCalculator){theCalculator = aCalculator;}
        private Long theCalculator;
        
        /** Дата */
      	@Comment("Дата")
      	@Persist
      	public String getResDate(){return theResDate;}
      	public void setResDate(String aResDate){theResDate = aResDate;}
      	private String theResDate;
      	
	/** Результат */
	@Comment("Результат")
	@Persist
	public String getResult(){return theResult;}
	public void setResult(String aResult){theResult = aResult;}
	private String theResult ;
	
}
