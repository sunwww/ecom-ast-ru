package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.OperationReturn;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = OperationReturn.class)
@Comment("Операция договорного счета. Возврат")
@WebTrail(comment = "Операция договорного счета. Возврат", nameProperties= "id", list="entityParentList-contract_accountOperation.do", view="entityParentView-contract_accountOperation.do")
@Parent(property="account", parentForm=ContractAccountForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation")
public class OperationReturnForm extends ContractAccountOperationForm {

}
