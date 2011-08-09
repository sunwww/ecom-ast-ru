package ru.ecom.mis.ejb.form.contract;

import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.contract.ContractGuaranteeLetter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;

@EntityForm
@EntityFormPersistance(clazz = ContractGuaranteeLetter.class)
@Comment("Гарантийное письмо по договору")
@WebTrail(comment = "Гарантийное письмо по договору", nameProperties= "id", list="entityParentList-contract_contractGuaranteeLetter.do", view="entityParentView-contract_contractGuaranteeLetter.do")
@Parent(property="contract", parentForm=MedContractForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/Contract/MedContract/ContractGuarantee/ContractGuaranteeLetter")
public class ContractGuaranteeLetterForm extends ContractGuaranteeForm {
}
