package ru.nuzmsh.commons.formpersistence;

/**
 * Контекст
 */
public interface IEntityFormManagerContext {

    /**
     * Есть ли у пользователя роль aRoleName
     * @param aRoleName роль
     * @return если есть true
     */
    boolean isUserInRole(String aRoleName) ;
}
