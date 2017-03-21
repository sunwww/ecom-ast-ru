package ru.ecom.mis.web.dwr.calculator;

import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.web.util.Injection;

/**
 * Сервис калькулятора
 * @author rkurbanov
 */
public class CalculateServiceJs {
    
   
    
    
    public void SetCalculateResultEdit(String aId,String aResult,HttpServletRequest aRequest) throws NamingException
    {
	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
	StringBuilder sql = new StringBuilder();
	sql.append("Update calculationsresult  set result='"+aResult+"' where id='"+aId+"'");
	System.out.println("=== Debug: "+sql.toString());
	service.executeUpdateNativeSql(sql.toString()) ;
	
    }
    public void SetCalculateResultCreate(String aId,String aResult,HttpServletRequest aRequest) throws NamingException
    {
	IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
	StringBuilder sql = new StringBuilder();
	sql.append("INSERT INTO calculationsresult  (result,departmentmedcase_id,calculator_id)")
	.append("values ('"+aResult+"','"+aId+"','1')");
	
	System.out.println("=== Debug: "+sql.toString());
	service.executeUpdateNativeSql(sql.toString()) ;
	
    }
    
    
         public String GetGenderAndAge(Long aId, HttpServletRequest aRequest) throws NamingException{
	
	 IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
	 StringBuilder sb = new StringBuilder();
	 String sql =" SELECT (cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int) "+ 
	 		"+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)  "+
	 		"+(case when (cast(to_char(smo.dateStart, 'dd') as int)-cast(to_char(p.birthday, 'dd') as int)<0) "+
	 		 "then -1 else 0 end)<0) then -1 else 0 end)) as age ,  vs.id as sex "+
                        " from medcase smo "+
                        " left join patient p on p.id = smo.patient_id "+
                        " left join vocsex vs on p.sex_id = vs.id "+
                        "  where smo.id ="+aId;
	 Collection <WebQueryResult> res = service.executeNativeSql(sql);
	 for (WebQueryResult wqr: res) {
		sb.append(""+wqr.get1());
		sb.append("|"+wqr.get2());
	 }
	 
	
	 return sb.toString();
	}
    
}