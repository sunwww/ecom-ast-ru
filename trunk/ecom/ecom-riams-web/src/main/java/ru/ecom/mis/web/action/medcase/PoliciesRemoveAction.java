package ru.ecom.mis.web.action.medcase;

import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;

public class PoliciesRemoveAction extends PoliciesAddAction {
 void serviceDo(IHospitalMedCaseService aService, long aSslId, long [] aPolicies) {
	aService.removePolicies(aSslId, aPolicies);
}


}
