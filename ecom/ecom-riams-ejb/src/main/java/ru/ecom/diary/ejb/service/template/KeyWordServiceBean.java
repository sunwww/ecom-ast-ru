package ru.ecom.diary.ejb.service.template;

import java.util.List;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.diary.ejb.domain.protocol.template.TemplateWord;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 22.12.2006
 * Time: 12:26:02
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@Remote(IKeyWordService.class)
public class KeyWordServiceBean implements IKeyWordService {
    public String getDecryption(String aReduction) {
        aReduction = aReduction.toUpperCase() ;
        if (aReduction!=null) {
            List<TemplateWord> keyword = theManager.createQuery("from TemplateWord where reduction = :reduction").setParameter("reduction",aReduction).setMaxResults(1).getResultList() ;
            if (keyword!=null && !keyword.isEmpty()) {
                return keyword.get(0).getDecryption() ;
            }
        }
        return "" ;
    }
    @EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext
    EntityManager theManager ;
}
