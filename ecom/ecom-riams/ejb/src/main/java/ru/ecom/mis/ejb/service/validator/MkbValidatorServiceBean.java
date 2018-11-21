package ru.ecom.mis.ejb.service.validator;

import ru.ecom.ejb.services.util.ConvertSql;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Remote(IMkbValidatorService.class)
public class MkbValidatorServiceBean  implements IMkbValidatorService{

	public Boolean validatePoint(String aId, String aClass, String aField) {
		//System.out.println("Ой, заработало");
		//System.out.println("id="+aId);
		//System.out.println("class="+aClass);
		StringBuilder sql = new StringBuilder() ;
		sql.append("select ")
			.append(aField)
			.append(" from ")
			.append(aClass)
			.append(" where id='")
			.append(aId)
			.append("'") ;
		String mkb = (String) theManager.createNativeQuery(sql.toString()).getSingleResult() ;
		sql = new StringBuilder() ;
		if (mkb.startsWith("F")) {
			if (mkb.startsWith("F06") ||mkb.startsWith("F02")||mkb.startsWith("F10") || mkb.startsWith("F3")) {
				sql.append("select Count(*) from ")
					.append(aClass)
					.append(" where ")
					.append(aField)
					.append(" like '").append(mkb).append("%' and ").append(aField)
					.append(" != '").append(mkb).append("' and (noActuality is null or noActuality='0') ");
			}
		} 
		if (sql.length()==0) {
			sql.append("select Count(*) from ")
			.append(aClass)
			.append(" where ")
			.append(aField)
			.append(" like '").append(mkb).append(".%' and (noActuality is null or noActuality='0') ");
		}
		Object cntUtoch = theManager.createNativeQuery(sql.toString()).getSingleResult() ;
		if (cntUtoch!=null && ConvertSql.parseLong(cntUtoch).intValue()>0) return false ;
		return true;
	}
//	@EJB ILocalEntityFormService theEntityFormService ;
    @PersistenceContext EntityManager theManager ;
 //   @Resource SessionContext theContext ;

}
