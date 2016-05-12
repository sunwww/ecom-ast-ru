package ru.ecom.mis.ejb.service.diet;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import ru.ecom.ejb.services.entityform.EntityFormException;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.mis.ejb.domain.diet.voc.VocFoodStuff;
import ru.ecom.mis.ejb.form.diet.voc.VocFoodStuffForm;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;


/**
 * Сервис продукта питания
 */
@Stateless
@Remote(IFoodStuffService.class)
@Local(IFoodStuffService.class)
public class FoodStuffServiceBean implements IFoodStuffService {

	private final static Logger LOG = Logger.getLogger(FoodStuffServiceBean.class);

	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

	/**
	 * Поиск продукта питания
	 */
	public List<VocFoodStuffForm> findFoodStuff(String aName) {
		
		if (CAN_DEBUG) {
			LOG.debug("findFoodStuff()  aName = " 
					+ aName);
		}
	QueryClauseBuilder builder = new QueryClauseBuilder();
	builder.addLike("name", aName +"%");
	
	Query query = builder.build(theManager, "from VocFoodStuff where",
			" order by name");
	
	List<VocFoodStuffForm> ret = new LinkedList<VocFoodStuffForm>();
	appendToList(query, ret);
	return ret;
	}

	
	@SuppressWarnings("unchecked")
	private List<VocFoodStuffForm> appendToList(Query aQuery, List<VocFoodStuffForm> ret) {
		List<VocFoodStuff> list = aQuery.setMaxResults(50).getResultList();

		for (VocFoodStuff foodStuff : list) {
			try {
				ret.add(theEntityFormService.loadForm(VocFoodStuffForm.class, foodStuff));
			} catch (EntityFormException e) {
				throw new IllegalStateException(e);
			}
		}
		return ret;
	}
	
	
	
	private @EJB ILocalEntityFormService theEntityFormService;

	private @PersistenceContext EntityManager theManager;

	public void updateFoodStuff(VocFoodStuff aFoodStuffId) {
		// TODO Auto-generated method stub
		
	}


	}



