package ru.ecom.mis.web.dwr.pharmacy;


import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.pharmacy.IPharmOperationService;
import ru.ecom.web.util.Injection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Сервис аптеки
 * Created by rkurbanov on 19.09.2017.
 */

public class PharmacyServiceJs {

    public void SQLupdate(HttpServletRequest aRequest, String SQLUpdateString) {

    }
//TODO deprecated
    public Integer endPrescription(Long prescriptionId, String username, HttpServletRequest aRequest) throws NamingException {
        IPharmOperationService service = Injection.find(aRequest).getService(IPharmOperationService.class);
        service.setFunctionEndPrescription();
        Integer bol = 1;
        try {
            IWebQueryService service2 = Injection.find(aRequest).getService(IWebQueryService.class);
            String sql = "select pharmEndPrescription (" + prescriptionId + ",'" + username + "');";

            Collection<WebQueryResult> res = service2.executeNativeSql(sql);
            for (WebQueryResult wqr : res) {
                bol = (Integer) wqr.get1();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return bol;
    }
}
