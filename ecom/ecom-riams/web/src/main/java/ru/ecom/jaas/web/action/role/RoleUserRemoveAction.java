package ru.ecom.jaas.web.action.role;

import ru.ecom.jaas.ejb.service.ISecRoleService;

public class RoleUserRemoveAction extends RoleUserAddAction {
    void serviceDo(ISecRoleService service, long aRoleId, long[] aUsers) {
        service.removeUsersFromRole(aRoleId, aUsers);
    }
}
