package ru.ecom.diary.ejb.service.protocol;

import java.util.List;

import ru.ecom.diary.ejb.form.DiaryForm;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNode;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 18.12.2006
 * Time: 12:29:42
 * To change this template use File | Settings | File Templates.
 */
public interface IDiaryService {
    public List<DiaryForm> findProtocol(long aSloId) ;
    
    public CheckNode loadParametersByMedService(long aMedServiceId) ;
    
    public void saveParametersByTemplateProtocol(long aProtocolId, long[] aAdds, long[] aRemoves) ;
    public List<Object[]> loadParameterTableByMedService(long aTemplateId) ;
}
