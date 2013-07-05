package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractAccountOperationByService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ContractAccountOperationByService.class)
@Comment("Конкретизация операции договорного счета по услуе")
@WebTrail(comment = "Операция договорного счета", nameProperties= "id", list="entityParentList-contract_accountOperationService.do", view="entityParentView-contract_accountOperationService.do")
@Parent(property="accountOperation", parentForm=ContractAccountOperationForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation")
public class ContractAccountOperationByServiceForm extends IdEntityForm {

}
