package ru.ecom.mis.web.dwr.prescription;

import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.jdom.IllegalDataException;

import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.prescription.IPrescriptionService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;

/**
 * Сервис назначений
 * @author STkacheva
 */
public class PrescriptionServiceJs {
	public String cancelService(String aPrescript,Long aReasonId,String aReason,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		java.util.Date date = new java.util.Date() ;
		SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat formatT = new SimpleDateFormat("hh:mm") ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ; 
		sql.append("update Prescription set cancelReason_id='"+aReasonId+"', cancelReasonText='").append(aReason).append("', cancelDate=to_date('").append(formatD.format(date)).append("','dd.mm.yyyy'),cancelTime=cast('").append(formatT.format(date)).append("' as time),cancelUsername='").append(username).append("' where id in (").append(aPrescript).append(")");
		service.executeUpdateNativeSql(sql.toString()) ;
		return "1" ;
	}
	/**Возвращает ID листа назначений, если он существует в заданном Medcase 
	 * 	
	 * @param aMedcase - ИД СЛО
	 * @param aRequest
	 * @return - ИД листа назначений (самый ранний)
	 * @throws NamingException
	 */
	public String isPrescriptListExists(String aMedcase, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String req = "Select pl.id from prescriptionlist pl where pl.medcase_id='"+aMedcase+"' order by id ";
		Collection <WebQueryResult> wrt = service.executeNativeSql(req, 1);
		
		if (wrt.size()>0) {
			WebQueryResult obj = wrt.iterator().next();
			//System.out.println("---------------------in isPrescriptListExists, id="+obj.get1().toString());
			return obj.get1().toString();
		}
		return "";
	}
	/**
	 * Поиск СЛО по ИД листа назначения
	 * @param aPrescList - ИД листа назначения
	 * @param aRequest
	 * @return ИД MedCase
	 * @throws NamingException
	 */
	public Long getMedcaseByPrescriptionList(String aPrescList, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String req = "Select pl.medcase_id from prescriptionList pl where pl.id='"+aPrescList+"' ";
		Collection<WebQueryResult> list = service.executeNativeSql(req,1) ;
		if (!list.isEmpty()) {
			WebQueryResult obj = list.iterator().next() ; 
		//	System.out.println("res.get1 ================================ "+obj.get1());
				return ConvertSql.parseLong(obj.get1());
			
		} 
		return Long.valueOf(0);
	}
	/**
	 * Проверка назначений на наличие дублей (имеются назначения на такие же 
	 * исследования в том же СЛО, в котором создается назначение)
	 * **** upd 12.12.2014 - Поиск осуществляется во всех СЛО, которые относятся к тому же СЛС, что и указаное СЛО.
	 */
	
	public String getDuplicatePrescriptions(String aMedCase, String aData, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder req = new StringBuilder();
		req.append("select distinct p.medservice_id ");
		req.append(",ms.code ||' ' || ms.name as labName ");
		//req.append(",count(p.id) as cnt1 ");
		req.append("from medcase mc ");
		req.append("left join medcase mc2 on mc2.parent_id = mc.parent_id ");
		req.append("left join prescriptionList pl on pl.medcase_id = mc2.id ");
		req.append("left join prescription p on p.prescriptionList_id = pl.id ");
	//	req.append("left join patient pat on pat.id = mc.patient_id ");
		req.append("left join medservice ms on ms.id = p.medservice_id ");
		req.append("where mc.id ='").append(aMedCase).append("' ");
		req.append("and p.dtype='ServicePrescription' ");
		req.append("and p.medservice_id is not null ");
		req.append("and p.fulfilmentstate_id is null ");
		req.append("and p.canceldate is null ");
	//	req.append("group by p.medservice_id, labName ");
		//req.append("having count(p.id)>1 ");
	//	System.out.println("--------------------getDuplicatePrescriptions Request is = "+req.toString());
		Collection<WebQueryResult> list = service.executeNativeSql(req.toString().toString()) ;
	//	System.out.println("-------------in PS - start working!!!");
		StringBuilder res = new StringBuilder();
		if (list.size()>0) {
	//		System.out.println("-------------list_size>0"+list.size());
			String[] aDataArr  = aData.split(":");
			for (WebQueryResult obj: list) {
				for (int i=0; i<aDataArr.length;i++) {
					if (obj.get1().toString().equals(aDataArr[i])) {
						res.append(obj.get1().toString()).append(":").append(obj.get2().toString()).append("#");
					}
				}
			}
		}
	//	System.out.println("in getDuplicatePrescriptions Result is = "+res.toString());
		return res.length()>0?res.substring(0,res.length()-1):"";
	}
	
	/*
	 * Возвращаем тип исследование, его код и имя, код и имя кабинета
	 * в формате: medserviceID:msName:msType:date:cabinetName# 
	 * На входе берем список исследований в формате ID:date:cabinet#
	 */
	public String getPresLabTypes(String aPresIDs, HttpServletRequest aRequest) throws NamingException {
		System.out.println(aPresIDs);
		return getPresLabTypes(aPresIDs, 0,aRequest);
	}
	
	
	/*
	 * Проверяем полученные исследования на соответствие типу листа назначения 
	 * и возвращаем те, которые соответствуют типу ЛН.
	 */
	public String getPresLabTypes(String aPresIDs, int aPrescriptListType, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		aPresIDs = aPresIDs.substring(0,aPresIDs.length()-1); // Обрезаем #
		System.out.println("    presIds="+aPresIDs) ;
		StringBuilder sqlMS = new StringBuilder() ;
		StringBuilder sqlCab = new StringBuilder();
		StringBuilder res = new StringBuilder() ;
		String[] labListArr = aPresIDs.split("#");
		if (labListArr.length>0) {
			
			for (int i=0; i<labListArr.length;i++) {
				String[] param = labListArr[i].split(":");
			//	System.out.println("For="+i+" data ID= "+param[0]);
			//	System.out.println("For="+i+" RES "+res.toString());
				String msID = param.length>0&&param[0]!=null? param[0] : null;
				String date = param.length>1&&param[1]!=null ? param[1]: "";
				String cabID = param.length>2&&param[2]!=null? param[2] : null;
				String departmentID = param.length>3&&param[3]!=null? param[3] : null;
				if (msID!=null && msID!=""){
					sqlMS.setLength(0);
					sqlMS.append("select vst.code, ms.id, ms.code ||' ' ||ms.name from medservice ms ")
					.append("left join vocservicetype vst on vst.id = ms.servicetype_id ")
					.append("where ms.id='").append(msID).append("' ");
					/*if (aPrescriptListType>0) {
					sqlMS.append("and ms.servicesubtype_id='").append(aPrescriptListType).append("' ");
					}*/
					
					Collection<WebQueryResult> listMS = service.executeNativeSql(sqlMS.toString()) ;
					for (WebQueryResult wqr :listMS) {
						res.append(wqr.get1()).append(":")
						.append(wqr.get2()).append(":")
						.append(wqr.get3()).append(":")
						.append(date).append(":");
						
						if (cabID!=null && cabID !=""){
							sqlCab = new StringBuilder() ;
							sqlCab.append("Select wf.id,wf.groupname from workfunction wf where wf.id='").append(cabID).append("' ");
							Collection<WebQueryResult> listCab = service.executeNativeSql(sqlCab.toString(),1) ;
							for (WebQueryResult wqr2 :listCab) {
								res.append(wqr2.get1()).append(":");
								res.append(wqr2.get2()).append(":");
							}
						} else res.append("::");
						if (departmentID!=null && departmentID !=""){
							sqlCab = new StringBuilder() ;
							sqlCab.append("Select ml.id,ml.name from mislpu ml where ml.id='").append(departmentID).append("' ");
							Collection<WebQueryResult> listCab = service.executeNativeSql(sqlCab.toString(),1) ;
							for (WebQueryResult wqr2 :listCab) {
								res.append(wqr2.get1()).append(":");
								res.append(wqr2.get2()).append(":");
							}
						} else res.append("::");
						res.append("::" );
						res.append("#") ;
					}					
					
					
				}
			}
		}
		return res.length()>0?res.substring(0,res.length()-1):"";
	}
	public String getDescription(Long aIdTemplateList, HttpServletRequest aRequest) throws NamingException {
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		System.out.println("Получить описание шаблона: "+aIdTemplateList);
		return service.getDescription(aIdTemplateList) ;
	}
	public String getDefectByBiomaterial(String aPrescript, String aBiomaterialType,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select id,name,case when additionData='1' then additionData else null end as addData from VocPrescriptCancelReason where serviceType='LABSURVEY' and biomaterial='"+aBiomaterialType+"'") ;
		Collection<WebQueryResult> l = service.executeNativeSql(sql.toString()) ;
		StringBuilder ret = new StringBuilder() ;
		ret.append("<table>") ;
		for (WebQueryResult wqr:l) {			
			ret.append("<tr>") ;
			ret.append("<td onclick=\"this.childNodes[1].checked=\'checked\';this.childNodes[1].onchange()\" colspan=\"4\">");
    		ret.append("	<input name=\"typeDefect\" value=\"5\" type=\"radio\" onchange=\"cancelInLab('"+aPrescript+"','"+wqr.get1()+"','"+(wqr.get3()!=null?"1":"0")+"')\">  "+wqr.get2()); 
    		ret.append("</td>") ;
    		ret.append("</tr>") ;
		}
		ret.append("</table>") ;
		return ret.toString() ;
	}
	public String getLabCabinetByPres(String aListPrescript,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select pms.id as p1msid,pms.code||' '||pms.name as p2msname,wf.id as w3fid,wf.groupname as w4fgroup,replace(list(''||ms.id),' ','') as m5slist") ;
		sql.append(" from WorkFunction wf") ;
		sql.append(" left join WorkFunctionService wfs on wfs.workfunction_id=wf.id") ;
		sql.append(" left join MedService pms on pms.id=wfs.medservice_id") ;
		sql.append(" left join MedService ms on ms.parent_id=pms.id") ;
		sql.append(" left join Prescription p on ms.id=p.medService_id") ;
		sql.append(" where p.id in (").append(aListPrescript).append(") ") ;
		sql.append(" group by pms.id,pms.code,pms.name,wf.id,wf.groupname") ;
		sql.append(" order by pms.name,wf.groupname");
		StringBuilder ret = new StringBuilder() ;
		Collection<WebQueryResult> lwqr = service.executeNativeSql(sql.toString()) ;
		String oldi = "" ;
		StringBuilder listCab = new StringBuilder() ;
		ret.append("<form name='frmCabinet' id='frmCabinet'><table>") ;
		for (WebQueryResult wqr:lwqr) {
			String newi = String.valueOf(wqr.get1()) ;
			if (!newi.equals(oldi)) {
				oldi=newi ;
				if (listCab.length()>0)listCab.append(",") ;
				listCab.append(newi) ;
				ret.append("<tr>") ;
				ret.append("<th colspan='2'>").append(wqr.get2()).append("</th>") ;
				ret.append("</tr>") ;
			}
			ret.append("<tr>") ;
			ret.append("<td onclick=\"this.childNodes[1].checked=\'checked\';\" colspan=\"4\">");
    		ret.append("	<input name=\"typeCabinet").append(wqr.get1()).append("\" id=\"typeCabinet").append(wqr.get1()).append("\" value=\"").append(wqr.get1()).append("#").append(wqr.get3()).append("#").append(aListPrescript).append("#").append(wqr.get5()).append("\" type=\"radio\" />  "+wqr.get4()); 
    		ret.append("</td>") ;
			ret.append("</tr>") ;

		}
		ret.append("<tr>") ;
		ret.append("<td onclick=\"this.childNodes[1].checked=\'checked\';\" colspan=\"4\">");
		ret.append("	<input name=\"btnOk\" value=\"Принять\" type=\"button\" onclick=\"transferInLabCheck('").append(listCab).append("')\"/>" ); 
		ret.append("</td>") ;
		ret.append("</tr>") ;
		ret.append("</table></form>") ;
		return ret.toString() ;
	}
	public String checkLabAnalyzed(Long aPrescript,HttpServletRequest aRequest) throws NamingException {
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ; 
		service.checkLabAnalyzed(aPrescript,username) ;
		return "1" ;
	}
	public String checkTransferService(String aListPrescript,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		java.util.Date date = new java.util.Date() ;
		SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat formatT = new SimpleDateFormat("HH:mm") ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		String[] l = aListPrescript.split(":") ;
		for (String r:l) {
			String[] cab = r.split("#") ;
			sql = new StringBuilder() ;
			sql.append("update Prescription set prescriptCabinet_id='"+cab[1]+"',transferDate=to_date('").append(formatD.format(date))
			.append("','dd.mm.yyyy'),transferTime=cast('").append(formatT.format(date)).append("' as time)")
			.append(",transferUsername='").append(username).append("' ")
			//.append(",intakeSpecial_id='").append(spec).append("' ")
			.append(" where id in (").append(cab[2]).append(") and medservice_id in (").append(cab[3]).append(")");
			service.executeUpdateNativeSql(sql.toString()) ;
		}
		return "1" ;
	}
	public String intakeService(String aListPrescript,String aDate,String aTime,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		/*java.util.Date date = new java.util.Date() ;
		SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat formatT = new SimpleDateFormat("hh:mm") ;*/
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		Long spec  = null ;
		Collection<WebQueryResult> specL = service.executeNativeSql("select wf.id from secuser su left join workFunction wf on wf.secuser_id=su.id where su.login='"+username+"'",1) ;
		if (!specL.isEmpty()) {
			spec = ConvertSql.parseLong(specL.iterator().next().get1()) ;
		}
		if (spec==null) new IllegalDataException("У пользователя "+username+" нет соответствия с рабочей функцией") ;
		sql.append("update Prescription set intakeDate=to_date('").append(aDate).append("','dd.mm.yyyy'),intakeTime=cast('").append(aTime).append("' as time)")
			.append(",intakeUsername='").append(username).append("' ")
			.append(",intakeSpecial_id='").append(spec).append("' ")
			.append(" where id in (").append(aListPrescript).append(")");
		service.executeUpdateNativeSql(sql.toString()) ;
		return "1" ;
	}
	public String receptionIntakeService(String aListPrescript,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		java.util.Date date = new java.util.Date() ;
		SimpleDateFormat formatD = new SimpleDateFormat("dd.MM.yyyy") ;
		SimpleDateFormat formatT = new SimpleDateFormat("hh:mm") ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ; 
		Long spec  = null ;
		Collection<WebQueryResult> specL = service.executeNativeSql("select wf.id from secuser su left join workFunciton wf on wf.secuser_id=su.id where su.login='"+username+"'",1) ;
		if (!specL.isEmpty()) {
			spec = ConvertSql.parseLong(specL.iterator().next().get1()) ;
		}
		if (spec==null) new IllegalDataException("У пользователя "+username+" нет соответствия с рабочей функцией") ;
		sql.append("update Prescription set transferSpecial_id='").append(spec).append("',transferDate=to_date('").append(formatD.format(date)).append("','dd.mm.yyyy'),transferTime=cast('").append(formatT.format(date)).append("' as time),transferUsername='").append(username).append("' where id in (").append(aListPrescript).append(")");
		service.executeUpdateNativeSql(sql.toString()) ;
		return "1" ;
	}
	public String intakeServiceRemove(String aListPrescript,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("update Prescription set intakeSpecial_id=null,intakeDate=null,intakeTime=null,intakeUsername=null where id in (").append(aListPrescript).append(")");
		service.executeUpdateNativeSql(sql.toString()) ;
		return "1" ;
	}
	public boolean checkMedCaseEmergency(Long aIdTemplateList, String idType, HttpServletRequest aRequest) throws NamingException {
		
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		return service.checkMedCaseEmergency(aIdTemplateList, idType) ;
	}
	
	public String getPrescriptionTypes(boolean isEmergency, HttpServletRequest aRequest) throws NamingException {
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		return service.getPrescriptionTypes(isEmergency) ;
	}
	
	public String getLabListFromTemplate(Long aIdTemplateList, HttpServletRequest aRequest) throws NamingException {
		
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		return service.getLabListFromTemplate(aIdTemplateList) ;
	}
	
	public String savePrescription(Long aIdParent,Long aIdTemplateList, Long aFlag, HttpServletRequest aRequest) throws NamingException {
//		Long id = Long.valueOf(aIdParent);
//		Long flag = Long.valueOf(aFlag);
		System.out.println("Родитель: "+aIdParent);
		System.out.println("Шаблон: "+aIdTemplateList);
		System.out.println("Флаг: "+aFlag);
		
		IPrescriptionService service = Injection.find(aRequest).getService(IPrescriptionService.class) ;
		if (aFlag==1) return savePrescriptExists(aIdTemplateList,aIdParent, service) ;
		if (aFlag==2) return savePrescriptNew(aIdTemplateList,aIdParent, service) ;
		return "" ;
		
	}
	private String savePrescriptExists(Long aIdTemplateList, Long aIdPrescript, IPrescriptionService service) {
		String ret ="";
		try {
			if (service.savePrescriptExists(aIdTemplateList, aIdPrescript)) ret ="Сохранено в текущий лист назначений" ;
			else ret = "Ошибка при сохранении  в текущий лист назначений" ;
		} catch (Exception e) {
			ret = "Ошибка при сохранении  в текущий лист назначений"+e.getMessage() ;
		}
		
		return ret ;
	}
	
	private String savePrescriptNew(Long aTemplateList, Long aMedCase, IPrescriptionService service) {
		String ret ="";
		try {
			if (service.savePrescriptNew(aTemplateList, aMedCase)) ret ="Сохранено в новый лист назначений" ;
			else ret = "Ошибка при сохранении  в новый лист назначений" ;
		} catch (Exception e) {
			ret = "Ошибка при сохранении  в новый лист назначений:"+e.getMessage() ;
		}
		return ret;	}
	
}
