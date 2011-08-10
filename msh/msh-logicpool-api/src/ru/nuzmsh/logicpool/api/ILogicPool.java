package ru.nuzmsh.logicpool.api;

/**
 * Доступ к объектам ILogic, размещенных в пуле
 *
 * @author ESinev
 *         Date: 19.10.2005 9:13:18
 */
public interface ILogicPool {

    /**
     * Получение ILogic'а
     *
     * @return объект
     */
    public ILogic getLogic() throws LogicException;


    /**
     * Количество подключение к базе
     * @return
     */
    int getActiveConnections() ;

    /**
     * Закрывает все подключения к базе
     * @throws LogicException
     */
    void closeAllConnections() throws LogicException ;

    /**
     * Максимальное количество подключений к базе
     */
    public void setMaxConnections(int aMaxConnections);

    /**
     * Максимальное количество подключений к базе
     */
    public int getMaxConnections();

    /**
     * Строка подключения к базе данных
     */
    public void setUrl(String aUrl);

    /**
     * Строка подключения к базе данных
     */
    public String getUrl();

    /**
     * Имя пользователя
     */
    public void setUsername(String aUsername);

    /**
     * Имя пользователя
     */
    public String getUsername();

    /**
     * Пароль
     */
    public void setPassword(String aPassword);

    /**
     * Пароль
     */
    public String getPassword();

}
