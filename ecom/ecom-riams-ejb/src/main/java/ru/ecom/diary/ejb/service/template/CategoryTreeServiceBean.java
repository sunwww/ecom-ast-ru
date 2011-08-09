package ru.ecom.diary.ejb.service.template;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.diary.ejb.domain.protocol.template.TemplateClassif;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 26.01.2007
 */
@Stateless
@Remote(ICategoryTreeService.class)
public class CategoryTreeServiceBean implements ICategoryTreeService {
    public String getClazz(long aIdClassif) {
        TemplateClassif classif = theManager.find(TemplateClassif.class, aIdClassif) ;
        if(classif==null) throw new IllegalArgumentException("Нет  классификатора с ид "+aIdClassif) ;
        return classif.getClazz() ;
    }
    @EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext
    EntityManager theManager ;
}
