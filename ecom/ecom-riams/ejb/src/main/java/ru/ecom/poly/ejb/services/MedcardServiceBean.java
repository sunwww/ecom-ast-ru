package ru.ecom.poly.ejb.services;

import org.apache.log4j.Logger;
import org.jboss.annotation.security.SecurityDomain;
import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;
import ru.ecom.poly.ejb.domain.Medcard;
import ru.ecom.poly.ejb.form.MedcardForm;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

/**
 * Работа с мед. картой
 */
@Stateless
@Remote(IMedcardService.class)
@Local(IMedcardService.class)
@SecurityDomain("other")
public class MedcardServiceBean implements IMedcardService{

    private static final Logger LOG = Logger.getLogger(MedcardServiceBean.class);

    /**
     * Поиск мед. карты по номеру
     */
    public List<MedcardForm> findMedCard(String aNumber, boolean aIsExactMatch) {
        QueryClauseBuilder builder = new QueryClauseBuilder();
        if (aIsExactMatch) {
        	builder.addLike("number", "%"+aNumber+"%");
        } else {
        	builder.add("number", aNumber);
        }
        
        Query query = builder.build(theManager, "from Medcard where", " order by Number");
        return createList(query);
    }

    private List<MedcardForm> createList(Query aQuery) {
        List<Medcard> list = aQuery.setMaxResults(50).getResultList();
        List<MedcardForm> ret = new LinkedList<>();
        for (Medcard medcard : list) {
            try {
                ret.add(theEntityFormService.loadForm(MedcardForm.class, medcard));
            } catch (EntityFormException e) {
                throw new IllegalStateException(e);
            }
        }
        return ret;
    }

    /**
     * Получение нового номера стат. карты
     * FIXME НЕ ПОДХОДИТ ДЛЯ МНОГОПОЛЬЗОВАТЕЛЬСКОЙ СРЕДЫ!!!
     */
	public String getNewMedcardNumber() {
		Query query = theManager.createNativeQuery("SELECT * from Medcard WHERE id = (SELECT max(id) from Medcard)", Medcard.class);
		List<Medcard> list = query.setMaxResults(1).getResultList();
		if(list.size() == 1){
			try {
				Medcard medcard = list.get(0);
				Long n = Long.valueOf( medcard.getNumber() );
				n += 1;
				return String.valueOf(n);
			} catch (Exception e) {
				return "";
			}			
		} else {
			return "1";
		}
	}

    private @EJB ILocalEntityFormService theEntityFormService;
    private @PersistenceContext EntityManager theManager;


}
