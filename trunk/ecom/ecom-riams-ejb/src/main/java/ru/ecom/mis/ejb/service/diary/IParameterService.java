package ru.ecom.mis.ejb.service.diary;

import java.io.IOException;
import java.util.List;

import org.apache.struts.action.ActionErrors;

import ru.ecom.diary.ejb.form.protocol.parameter.ParameterForm;
import ru.ecom.diary.ejb.service.protocol.ParameterPage;
import ru.ecom.diary.ejb.service.protocol.ParameterType;

/**
 * Сервис для работы с параметрами
 * @author stkacheva
 */
public interface IParameterService {
	public List<ParameterType> loadParameterType()  ;
	public ParameterPage loadParameter(ParameterForm aParameterForm, Long aId) ;
	public ParameterPage loadParameter(ParameterForm aParameterForm, Long aId, ActionErrors aErrors) ;
	public String getActionByDocument(Long aId,
			String aDocument) throws IOException;
}
