package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.mis.ejb.domain.contract.OperationAccrual;
import ru.ecom.mis.ejb.form.contract.interceptor.OperationAccrualPreCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = OperationAccrual.class)
@Comment("Операция договорного счета. Начисление")
@WebTrail(comment = "Операция договорного счета. Начисление", nameProperties= "id", list="entityParentList-contract_accountOperation.do", view="entityParentView-contract_accountOperation.do")
@Parent(property="account", parentForm=ContractAccountForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ServedPerson/ContractAccount/ContractAccountOperation")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(OperationAccrualPreCreateInterceptor.class)
)
public class OperationAccrualForm extends ContractAccountOperationForm {

}
