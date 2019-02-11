package ru.ecom.ejb.services.entityform;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 21.11.2006
 * Time: 7:31:35
 * To change this template use File | Settings | File Templates.
 */
public interface ILocalEntityFormService {

    public <T extends IEntityForm> T loadForm(Class<T> aFormClass, Object aEntity) throws EntityFormException ;

}
