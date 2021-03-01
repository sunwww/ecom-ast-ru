package ru.nuzmsh.commons.auth;

/**
 * Информация о пользователе
 */
public interface ILoginInfo {

    /** Имя пользователя при входе в систему */
    String getUsername() ;

    /** Полное имя пользователя */
    String getFullname() ;

    /** Комментарий  */
    String getComment() ;

    /** Есть ли у пользователя роль, группа или политика */
    boolean isUserInRole(String aRole) ;

}
