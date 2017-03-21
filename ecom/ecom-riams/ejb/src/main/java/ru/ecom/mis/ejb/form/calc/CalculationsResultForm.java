package ru.ecom.mis.ejb.form.calc;

import javax.persistence.OneToOne;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.calc.CalculationsResult;
import ru.ecom.mis.ejb.domain.calc.Calculator;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispAppointment;
import ru.ecom.mis.ejb.form.medcase.hospital.DepartmentMedCaseForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;


@EntityForm
@EntityFormPersistance(clazz = CalculationsResult.class)
@Comment("Калькулятор человека")
@WebTrail(comment = "Калькулятор человека"
, nameProperties= "id", list="entityParentList-calc_calculationsResult.do", view="entityParentView-calc_calculationsResult.do")
@Parent(property="departmentMedCase", parentForm=DepartmentMedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/ExtDisp/Card")
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
        
	/** Результат */
	@Comment("Результат")
	@Persist
	public String getResult(){return theResult;}
	public void setResult(String aResult){theResult = aResult;}
	private String theResult ;
	
}
