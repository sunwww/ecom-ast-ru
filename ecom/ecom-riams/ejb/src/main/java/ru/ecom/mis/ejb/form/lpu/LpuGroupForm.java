package ru.ecom.mis.ejb.form.lpu;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.lpu.LpuGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.commons.formpersistence.annotation.Persist;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@Comment("Группа ЛПУ")
@EntityForm
@EntityFormPersistance(clazz = LpuGroup.class)
@WebTrail(comment = "Список ЛПУ в группе", nameProperties = "id", view = "entityParentView-mis_groupLpu.do")
@Parent(property = "groupLpu", parentForm = MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MisLpu")
public class LpuGroupForm extends IdEntityForm  {
	/** Центр */
	@Comment("Центр")
	@Persist
	public Long getGroupLpu() {return theGroupLpu;}
	public void setGroupLpu(Long aGroupLpu) {	theGroupLpu = aGroupLpu;}
	/** Центр */
	private Long theGroupLpu;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@Persist
	public Long getLpu() {return theLpu;}
	public void setLpu(Long aLpu) {theLpu = aLpu;}
	/** ЛПУ */
	private Long theLpu;
}
