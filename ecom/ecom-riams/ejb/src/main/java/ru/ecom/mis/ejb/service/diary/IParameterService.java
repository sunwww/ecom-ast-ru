package ru.ecom.mis.ejb.service.diary;

import org.apache.struts.action.ActionErrors;
import ru.ecom.diary.ejb.form.protocol.parameter.ParameterForm;
import ru.ecom.diary.ejb.service.protocol.ParameterPage;
import ru.ecom.diary.ejb.service.protocol.ParameterType;

import java.io.IOException;
import java.util.List;

/**
 * Сервис для работы с параметрами
 *
 * @author stkacheva
 */
public interface IParameterService {
    String checkOrCreateCode(String aCode, String aId);

    List<ParameterType> loadParameterType();

    ParameterPage loadParameter(ParameterForm aParameterForm, Long aId);

    ParameterPage loadParameter(ParameterForm aParameterForm, Long aId, ActionErrors aErrors);

    String getActionByDocument(Long aId,
                               String aDocument) throws IOException;
}
