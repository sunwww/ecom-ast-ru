package ru.ecom.mis.ejb.form.calc;

import lombok.Setter;
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
@Setter
public class CalculationsResultForm extends IdEntityForm{
	
        /** СЛО */
	@Comment("СЛО")
	@Persist
	public Long getDepartmentMedCase(){return departmentMedCase;}
	private Long departmentMedCase ;
	
	/** Калькулятор */
        @Comment("Калькулятор")
        @Persist
        public Long getCalculator() {return calculator;}
        private Long calculator;
        
        /** Дата */
      	@Comment("Дата")
      	@Persist
      	public String getResDate(){return resDate;}
      	private String resDate;
      	
	/** Результат */
	@Comment("Результат")
	@Persist
	public String getResult(){return result;}
	private String result ;
	
}
