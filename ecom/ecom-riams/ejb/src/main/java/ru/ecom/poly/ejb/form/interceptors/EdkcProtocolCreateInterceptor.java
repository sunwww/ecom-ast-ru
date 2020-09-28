package ru.ecom.poly.ejb.form.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.patient.ObservationSheet;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.patient.EdkcProtocolForm;
import ru.ecom.poly.ejb.domain.protocol.Protocol;

import java.util.List;

/**
 * Created by Milamesher on 08.08.2019.
 */
public class EdkcProtocolCreateInterceptor implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
        String username = aContext.getSessionContext().getCallerPrincipal().getName() ;
        List<WorkFunction> listwf =  aContext.getEntityManager().createQuery("from WorkFunction where secUser.login = :login")
                .setParameter("login", username).getResultList() ;
        if (listwf.isEmpty()) {
            throw new IllegalArgumentException(
                    "Обратитесь к администратору системы. Ваш профиль настроен неправильно. Нет соответствия между рабочей функцией и пользователем (WorkFunction и SecUser)"
            );
        } else {
            Protocol protocol=(Protocol)aEntity;
            protocol.setUsername(username);
            protocol.setSpecialist(listwf.get(0)) ;

            
            checkEditAfterClose(aEntity);
            checkFirstProtocol(aForm, aEntity, aContext);
            checkAfterFirstProtocol(aForm, aEntity, aContext);
        }
    }

    //проверка: первый протокол в листе наблюдения обязательно должен быть протоколом консультации
    private void checkFirstProtocol(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
        Object val = aContext.getEntityManager().createNativeQuery("select count(id)from diary where obssheet_id='"+((EdkcProtocolForm)aForm).getObsSheet()+"'").getSingleResult();
        if (val.toString().equals("0") && !((Protocol) aEntity).getType().getCode().equals("edkc_1"))
            throw new IllegalArgumentException("Первый протокол в листе наблюдения обязательно должен быть " +
                    "<a href='entityParentPrepareCreate-edkcProtocol.do?id="+((EdkcProtocolForm)aForm).getObsSheet() + "&type=edkc_1'>протоколом консультации!");
    }

    //проверка: в листе наблюдений может быть только один первый протокол консультации
    private void checkAfterFirstProtocol(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
        if (((Protocol) aEntity).getType().getCode().equals("edkc_1")) {
            Object val = aContext.getEntityManager().createNativeQuery("select case when count(p.id)>0 then '1' else '0' end " +
                    "from diary p left join voctypeprotocol vtp on vtp.id=p.type_id " +
                    "where p.obssheet_id='"+((EdkcProtocolForm)aForm).getObsSheet()+"' and vtp.code='edkc_1'").getSingleResult();
            if (val.toString().equals("1"))
                throw new IllegalArgumentException("В листе наблюдений может быть только один первый протокол консультации! " +
                        "Все остальные должны быть <a href='entityParentPrepareCreate-edkcProtocol.do?id=" +
                        ((EdkcProtocolForm)aForm).getObsSheet() + "&type=edkc_ev'>протоколами ежесуточного наблюдения.</a>");
        }
    }

    //проверка: нельзя редактировать после закрытия листа наблюдений
    private void checkEditAfterClose(Object aEntity) {
        ObservationSheet sh = ((Protocol)aEntity).getObsSheet();
        if (sh.getFinishDate()!=null)
            throw new IllegalArgumentException("Нельзя редактировать протоколы в закрытом листе наблюдения!");
    }
}