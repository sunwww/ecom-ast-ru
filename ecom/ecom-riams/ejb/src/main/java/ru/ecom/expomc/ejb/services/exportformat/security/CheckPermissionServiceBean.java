package ru.ecom.expomc.ejb.services.exportformat.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Проверка прав доступа пользователя к сущностям Hibernate
 *
 * @author ikouzmin 06.03.2007 13:14:22
 */
public class CheckPermissionServiceBean
        implements ICheckPermissionService {







    @PersistenceContext private EntityManager theManager;
}
