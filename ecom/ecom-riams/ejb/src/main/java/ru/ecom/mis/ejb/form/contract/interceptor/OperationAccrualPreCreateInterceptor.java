package ru.ecom.mis.ejb.form.contract.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.contract.ContractAccount;
import ru.ecom.mis.ejb.domain.contract.MedContract;
import ru.ecom.mis.ejb.form.contract.OperationAccrualForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.List;

public class OperationAccrualPreCreateInterceptor implements IParentFormInterceptor {
    @Override
    public void intercept(IEntityForm aForm, Object aEntity, Object aParentId, InterceptorContext aContext) {
        EntityManager manager = aContext.getEntityManager();
        //Если уже было начисление по счету - не даем создать новое начисление!
        List accruals = manager.createNativeQuery("select id from contractaccountoperation where account_id = :accountId" +
                " and dtype='OperationAccrual' and (isDeleted is null or isDeleted='0')").setParameter("accountId",aParentId).getResultList();
        if (!accruals.isEmpty()) {
            throw new IllegalStateException("По данному счету уже оформлено начисление, создание нового начисления невозможно");
        }
        OperationAccrualForm form = (OperationAccrualForm) aForm;
        ContractAccount account = manager.find(ContractAccount.class,Long.valueOf(aParentId.toString()));
        MedContract contract = account.getContract();
        form.setContractNumber(contract.getContractNumber());
        form.setContractDate(DateFormat.formatToDate(contract.getDateFrom()));
        form.setContractCustomer(contract.getCustomer().getInformation());

    }
}
