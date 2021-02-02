package ru.ecom.diary.ejb.service.protocol;

import ru.ecom.diary.ejb.service.protocol.tree.CheckNode;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 18.12.2006
 * Time: 12:29:42
 * To change this template use File | Settings | File Templates.
 */
public interface IDiaryService {

    CheckNode loadParametersByMedService(long aMedServiceId);

    CheckNode loadParametersByMedService(long aMedServiceId, String aUsername);

    CheckNode loadParametersByMedService(long aMedServiceId, String aUsername, String aField);

    void saveParametersByTemplateProtocol(String aIdFieldName, long aProtocolId, long[] aAdds, long[] aRemoves);

}
