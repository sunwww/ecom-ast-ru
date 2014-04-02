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
    public String getDecryption(String aReduction, String aUsername) {
        aReduction = aReduction.toUpperCase() ;
        if (aReduction!=null) {
            List<Object[]> keyword = theManager.createNativeQuery("select tw.id,tw.descriptionfrom TemplateWord tw left join templateword_secgroup twsg on twsg.templateword_id=tw.id left join secgroup_secuser sgsu on sgsu.group_id=twsg.secgroups_id left join secuser su on su.id=sgsu.secusers_id  where tw.reduction = :reduction and (tw.createusername='"+aUsername+"' or su.login='"+aUsername+"')").setParameter("reduction",aReduction).setMaxResults(1).getResultList() ;
            if (keyword!=null && !keyword.isEmpty()) {
                return ""+keyword.get(0)[1] ;
            }
        }
        return "" ;
    }
    @EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext
    EntityManager theManager ;
}
