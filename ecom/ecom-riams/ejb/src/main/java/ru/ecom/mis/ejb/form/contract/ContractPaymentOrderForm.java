package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractPaymentOrder;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ContractPaymentOrder.class)
@Comment("Платежное поручение по договору")
@WebTrail(comment = "Платежное поручение по договору", nameProperties= "id", list="entityParentList-contract_contractPaymentOrder.do", view="entityParentView-contract_contractPaymentOrder.do")
@Parent(property="contract", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractPaymentOrder")
public class ContractPaymentOrderForm extends ContractGuaranteeForm {
}
