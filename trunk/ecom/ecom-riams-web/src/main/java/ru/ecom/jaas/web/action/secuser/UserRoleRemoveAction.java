package ru.ecom.jaas.web.action.secuser;

import ru.ecom.jaas.ejb.service.ISecUserService;

/**
 */
public class UserRoleRemoveAction extends UserRoleAddAction {
    void serviceDo(ISecUserService service, long aUserId, long[] aRoles) {
        service.removeRoles(aUserId, aRoles);
    }
}
