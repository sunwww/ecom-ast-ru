package ru.ecom.mis.ejb.form.worker;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.worker.ExtPersonalWorkFunction;
import ru.ecom.mis.ejb.form.lpu.MisLpuForm;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz= ExtPersonalWorkFunction.class)
@Comment("Сотрудник другого ЛПУ")
@WebTrail(comment = "Сотрудник другого ЛПУ", nameProperties= "name"
, view="entityParentView-work_extPersonalWorkFunction.do"
,list= "entityParentList-work_extPersonalWorkFunction.do")
@Parent(property="lpu", parentForm= MisLpuForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Worker/WorkFunction")
public class ExtPersonalWorkFunctionForm extends GroupWorkFunctionForm {

}
