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
		StringBuilder sqlQ = new StringBuilder() ;
		sqlQ.append("select ")
			.append(aField)
			.append(" from ")
			.append(aClass)
			.append(" where id='")
			.append(aId)
			.append("'") ;
		String mkb = (String) theManager.createNativeQuery(sqlQ.toString()).getSingleResult() ;
		if (mkb.startsWith("F")) {
			if (!mkb.startsWith("F06") && !mkb.startsWith("F3")) {
				return true ;
			}
		}
		
		StringBuilder sql = new StringBuilder() ;
		sql.append("select Count(*) from ")
			.append(aClass)
			.append(" where ")
			.append(aField)
			.append(" like '").append(mkb).append(".%'");
		//System.out.println("sql="+sql.toString());
		
		Integer cntUtoch = (Integer) theManager.createNativeQuery(sql.toString()).getSingleResult() ;
		if (cntUtoch!=null && cntUtoch>0) return false ;
		return true;
	}
	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
    @Resource SessionContext theContext ;

}
