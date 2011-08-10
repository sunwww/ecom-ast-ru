package ru.nuzmsh.commons.auth;

/**
 * Информация о пользователе
 */
public interface ILoginInfo {

    /** Имя пользователя при входе в систему */
    public String getUsername() ;

    /** Полное имя пользователя */
    public String getFullname() ;

    /** Комментарий  */
    public String getComment() ;

    /** Есть ли у пользователя роль, группа или политика */
    public boolean isUserInRole(String aRole) ;

}
