package ru.ecom.mis.ejb.service.validator;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.services.entityform.ILocalEntityFormService;

@Stateless
@Remote(IMkbValidatorService.class)
public class MkbValidatorServiceBean  implements IMkbValidatorService{

	public Boolean validatePoint(String aId, String aClass, String aField) {
		//System.out.println("Ой, заработало");
		//System.out.println("id="+aId);
		//System.out.println("class="+aClass);
		StringBuilder sql = new StringBuilder() ;
		sql.append("select Count(*) from ")
			.append(aClass)
			.append(" where ")
			.append(aField)
			.append(" like (select ")
			.append(aField)
			.append(" from ")
			.append(aClass)
			.append(" where id='")
			.append(aId)
			.append("')_'.%'");
		//System.out.println("sql="+sql.toString());
		Integer cntUtoch = (Integer) theManager.createNativeQuery(sql.toString()).getSingleResult() ;
		if (cntUtoch!=null && cntUtoch>0) return false ;
		return true;
	}
	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
    @Resource SessionContext theContext ;

}
