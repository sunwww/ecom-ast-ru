package ru.ecom.diary.ejb.service.protocol;

import java.util.List;

import org.apache.struts.action.ActionErrors;

import ru.ecom.diary.ejb.form.protocol.parameter.ParameterForm;

/**
 * Сервис для работы с параметрами
 * @author stkacheva
 */
public interface IParameterService {
	public List<ParameterType> loadParameterType()  ;
	public ParameterPage loadParameter(ParameterForm aParameterForm, Long aId) ;
	public ParameterPage loadParameter(ParameterForm aParameterForm, Long aId, ActionErrors aErrors) ;
}
