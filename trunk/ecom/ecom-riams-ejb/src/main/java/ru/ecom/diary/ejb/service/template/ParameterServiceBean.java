package ru.ecom.diary.ejb.service.template;

import java.util.List;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.diary.ejb.domain.protocol.parameter.Parameter;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import sun.awt.windows.ThemeReader;

@Stateless
@Remote(IParameterService.class)
public class ParameterServiceBean implements IParameterService{
	// Получить список сортированных параметров по списку
	public String getParameterByIndex(Long aTemplateProtocol) {
		//List<Parameter> list = theManager.createQuery("from ParameterByForm where")
		return "" ;
	}
    @EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext
    EntityManager theManager ;
}
