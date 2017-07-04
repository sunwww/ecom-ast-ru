package ru.ecom.mis.web.dwr.contract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode; 
import java.net.URL; 
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;
import java.net.HttpURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.ecs.xhtml.a;
import org.apache.log4j.Logger;
import org.json.JSONArray; 
import org.json.JSONException;
import org.json.JSONObject;


import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.contract.IContractService;
import ru.ecom.web.util.Injection; 

import ru.nuzmsh.web.tags.helper.RolesHelper;


public class ContractServiceJs {
	
	public static Logger log = Logger.getLogger(ContractServiceJs.class);
	
private void makeHttpPostRequest(String data, HttpServletRequest aRequest) throws IOException, NamingException {
	
		log.info("===Send to KKM. Data = "+data);
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l = service.executeNativeSql("select keyvalue from  softconfig where key='KKM_WEB_SERVER'");
		if (!l.isEmpty()) {
			String address = l.iterator().next().get1().toString();
			
			log.info("KKM_server = "+address);
			//method by milamesher 15.03.2017
			//отправка пост-запроса на веб-сервис, управляющий печатью ккм  
			URL url = new URL(address); 
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		    connection.setDoInput(true);
		    connection.setDoOutput(true);
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Accept", "application/json");
		    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		    writer.write(data);
		    writer.close();
		    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		 //   StringBuffer answerString = new StringBuffer();
		 //   String line;
		 //   while ((line = br.readLine()) != null) {
		 //   	answerString.append(line);
		 //   }
		    br.close();
		    connection.disconnect();
		} else {
			log.error("Нет настройки 'KKM_WEB_SERVER', работа с ККМ невозможна");
		}
		
	}



//method by milamesher 15.03.2017
//отправляет на принтер команду печатать z-отчёт
public JSONObject makeKKMEmptyJson () {
	try {
		JSONObject root = new JSONObject();
		//root.put("function", "");
	//	root.put("totalPaymentSum", 0) ;
	//	root.put("totalTaxSum", 0) ;
	//	root.put("totalRefundSum", 0) ;
		//JSONObject par = new JSONObject();
		return root;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
	
}

//Печать K, Z отчета
public String printKKMReport(String aType, HttpServletRequest aRequest) {
	if (aType!=null&&(aType.equals("Z")||aType.equals("X"))) {
		try {
			JSONObject root = makeKKMEmptyJson();
			if (root!=null) {
				root.put("function", "print"+aType+"Report");
				makeHttpPostRequest(root.toString(),aRequest);
				return  aType+" отчет успешно отправлен на печать";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	} 
	return "Неизвестный тип отчета";
}

//method by milamesher 15.03.2017
//отправляет на принтер команду возврата (пока 3 копейки, чтобы продемонстрировать отказ возврата)

public String sendKKMRequest(String aFunction, Long aAccountId, String aDiscont, Boolean isTerminalPayment, HttpServletRequest aRequest) throws JspException {
	if (RolesHelper.checkRoles(" /Policy/Config/KKMWork", aRequest)) {
	if (aFunction!=null &&aFunction.equals("makePayment")) {
		return makeKKMPaymentOrRefund(aAccountId, aDiscont, false, isTerminalPayment,aRequest);
	} else if (aFunction!=null&&aFunction.equals("makeRefund")) {
		return makeKKMPaymentOrRefund(aAccountId, aDiscont, true, isTerminalPayment, aRequest);
	} else if (aFunction!=null&&aFunction.equals("printZReport")){
		return printKKMReport("Z", aRequest);
	} else if (aFunction!=null&&aFunction.equals("printXReport")){
		return printKKMReport("X",aRequest);
	}
	return "Неизвестная функция";
	} else {
		return "Нет прав для работы с ККМ";
	}
}
public String makeKKMPaymentOrRefund(Long aAccountId,String aDiscont, Boolean isRefund,Boolean isTerminalPayment, HttpServletRequest aRequest) {
	try {		
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		String discontSql = "cams.cost";
		if (aDiscont!=null&&!aDiscont.equals("")) {
			log.info("=== Send KKM, discont = "+aDiscont);
			discontSql = "round(cams.cost*(100-"+aDiscont+")/100,2)";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select pp.id, pp.code as f2_code, pp.name as f3_name, cams.countmedservice as f4_count, cast("+discontSql+" as varchar) as f5_cost, cast("+discontSql+"*cams.countmedservice as varchar) as f6_sum")
			.append(",case when pp.isVat='1' then 'Ставка налога НДС '||coalesce(vv.taxrate,0) ||'%' end as f7_taxName") 
			.append(",case when pp.isVat='1' then cast(round("+discontSql+"*cams.countmedservice*vv.taxrate/100,2) as varchar) end as f8_taxSum")
			.append(" from contractaccount  ca")
			.append(" left join contractaccountmedservice cams on cams.account_id=ca.id")
			.append(" left join pricemedservice pms on pms.id=cams.medservice_id")
			.append(" left join priceposition pp on pp.id=pms.priceposition_id")
			.append(" left join vocvat vv on vv.id=pp.tax_id")
			.append(" where ca.id=").append(aAccountId);
		List<Object[]> l = service.executeNativeSqlGetObj(sb.toString());
		//Collection<WebQueryResult> l = service.executeNativeSql(sb.toString());
		if (!l.isEmpty()) {
		//	System.out.println("not empty");
			Double totalSum = 0.00;
			Double taxSum = 0.00;
			JSONObject root = new JSONObject();
			root.put("function", isRefund?"makeRefund":"makePayment"); 
			JSONArray arr = new JSONArray();
			for (Object[] r: l) {
				
				Double sum = Double.valueOf(r[5].toString());
				Double tax = Double.valueOf(r[7]!=null?r[7].toString():"0.00");
				totalSum+=sum;
				taxSum+=tax;
				JSONObject record = new JSONObject();
				record.put("code", r[1]);
				record.put("name", r[2]);
				record.put("count", r[3]);
				record.put("price", r[4].toString());
				record.put("sum", r[5].toString());
			//	record.put("price", 0);
			//	record.put("sum", 0);
				if (r[6]!=null&&!(""+r[6]).equals("")) {
					record.put("taxName", "");
					record.put("taxSum", r[7]!=null?r[7].toString():"");
				}
				arr.put(record);
			}
			root.put("isTerminalPayment", isTerminalPayment);
			if (isRefund) {
				root.put("totalRefundSum", totalSum) ;
			} else {
				root.put("pos", arr) ;
				root.put("totalPaymentSum", ""+totalSum+"") ;
				if (taxSum>0) {
					root.put("totalTaxSum", ""+ new BigDecimal(taxSum).setScale(2, RoundingMode.HALF_EVEN).toString()+"") ;
				}
			}
			root.put("isTerminalPayment", isTerminalPayment);
			makeHttpPostRequest(root.toString(), aRequest);
			return "Чек отправлен на печать";
		} else {
			return "Произошла ошибка, обратитесь к программистам";
		}
	} catch (Exception e) {
		e.printStackTrace();
		return e.getMessage();
	}
}
	/**
	 * Проверяем, нужно ли гарантийное письмо для выбранного потока обслуживания. Если нужно - находим. 
	 * @param aPatient  - id пациента 
	 * @param aServiceStreamId id потока обслуживания
	 * @param aDate - дата оказания услуги
	 * @param aDatePlanId - id номера дня в календаре
	 * @param aMedhelpType - тип помощи (амбулаторная либо стационарная)
	 * @param aRequest
	 * @return Гарантийное письмо с остатком ден. средств
	 * @throws NamingException
	 */
	public String checkIfDogovorIsNeeded (String aPatient, String aServiceStreamId, String aDate, String aDatePlanId,String aMedhelpType, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l = service.executeNativeSql("select id from vocserviceStream where id="+aServiceStreamId+" and isCalcDogovor='1'");
		if (!l.isEmpty()) {
			if (aMedhelpType!=null&& aMedhelpType.equals("POLYCLINIC")) {
				aMedhelpType = "AMB";
			} else if (aMedhelpType!=null&&aMedhelpType.equals("HOSPITAL")) {
				aMedhelpType = "PLAN_HOSP";
			} else {
				return "0Неизвестный тип помощи";
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("select cg.id as id,cg.numberdoc, to_char(cg.issueDate,'dd.MM.yyyy') as guarDate, mc.contractnumber as contractNumber")
			.append(",cg.limitMoney, mc.pricelist_id as price")
			.append(" from contractguarantee  cg")
			.append(" left join contractperson cp on cp.id=cg.contractperson_id")
			.append(" left join medpolicy mp on mp.patient_id=cp.patient_id")
			.append(" left join medcontract mc on mc.id=cg.contract_id")
			.append(" left join contractperson cpCustomer on cpCustomer.id=mc.customer_id")
			.append(" left join vocguaranteekindhelp vgkh on vgkh.id=cg.kindhelp_id")
			.append(" where mc.servicestream_id='"+aServiceStreamId+"'") 
			.append(" and (cg.contractperson_id is null or cp.patient_id='"+aPatient+"') and vgkh.code='"+aMedhelpType+"'");
			if (aDate!=null&&!aDate.equals("")) {
				sb.append(" and (cg.actiondateto is null or cg.actiondateto >=to_date('"+aDate+"','dd.MM.yyyy'))");
			} else if (aDatePlanId!=null&&!aDatePlanId.equals("")){
				sb.append(" and (cg.actiondateto is null or cg.actiondateto >=(select calendardate from workcalendarday where id='"+aDatePlanId+"'))");
			} else {
				sb.append(" and (cg.actiondateto is null or cg.actiondateto >=current_date)");
			}
			sb.append(" and case when cpCustomer.regcompany_id is not null and mp.company_id=cpCustomer.regcompany_id then 1 else 0 end = 1");
		log.info("=== Ищем гар. письмо по пациенту. sql="+sb.toString());
			l = service.executeNativeSql(sb.toString());
			if (!l.isEmpty()) {
				log.info("Ищем уже исрасходованную сумму лечения");
				WebQueryResult r = l.iterator().next();
				float limit  = Float.parseFloat(r.get5().toString());
				String priceListId = r.get6().toString();
				String guaranteeId = r.get1().toString();
				l = service.executeNativeSql("select list(''||id) from medcase where guarantee_id="+guaranteeId);
				float spent = 0;
				for (WebQueryResult wqr: l) {
					String listId =wqr.get2()!=null?wqr.get2().toString():null;
					if (listId!=null) {
						String[] ids = listId.split(",");
						for (String id: ids) {
							spent +=calculateMedCaseCost(Long.valueOf(id), Long.valueOf(priceListId), aRequest);
						}
					}				
				}
				sb.setLength(0);
				sb.append(guaranteeId) //id письма
				.append("|гар. письмо № ").append(r.get2()) //номер письма
				.append(" от ").append(r.get3()) //Дата письма
				.append(" Остаток средств: "+(limit - spent)+" руб. (Договор №").append(r.get4()).append(")");
				return "1"+sb.toString();
			} else {
				return "0Не найдено гарантийное письмо";
			}
		}
		
		return null;
	}
	/**
	 * Функция нахождения стоимости случая лечения ( визита либо случая лечения в стационаре)
	 * @param aMedcaseId - id случая лечения
	 * @param aPriceListId - id прайс-листа
	 * @param aRequest
	 * @return Стоимость случая
	 * @throws NamingException
	 */
public Double calculateMedCaseCost(Long aMedcaseId, Long aPriceListId, HttpServletRequest aRequest) throws NamingException {
		
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "";
		if (aPriceListId==null) {
			log.debug("Ищем прайс лист по СМО, либо по умолчанию");
			sql = "select pl.id as priceBySLS" +
				" , (select max(id) from pricelist where isdefault='1') as defaultPrice" +
				" from medcase mc" +
				" left join contractguarantee cg on cg.id=mc.guarantee_id" +
				" left join medcontract mcon on mcon.id=cg.contract_id" +
				" left join pricelist pl on pl.id=mcon.pricelist_id" +
				" where mc.id ="+aMedcaseId;
			Collection<WebQueryResult> l =	service.executeNativeSql(sql);
			if (!l.isEmpty()) {
				WebQueryResult r = l.iterator().next();
				if (r.get1()!=null&&!(""+r.get1()).equals("")) { //Используем прайс лист по гар. письму, если есть
					aPriceListId = Long.valueOf(r.get1().toString());
				} else if (r.get2()!=null&&!(""+r.get2()).equals("")) { //Используем прайс-лист по умолчанию 
					aPriceListId = Long.valueOf(r.get2().toString());
				} else {
					log.info("Не удалось вычислить прайс-лист для расчета цены случая " +aMedcaseId);
					return 0.00;
				}
			}
		}
		log.debug("Находим информацию по пацинету, СМО="+aMedcaseId);
		sql = "select m.patient_id,to_char(m.datestart,'dd.mm.yyyy') as dstart ,to_char(coalesce(m.datefinish,current_date),'dd.mm.yyyy') as dfinish,m.serviceStream_id as servstream, m.dtype as dtype from medcase m where m.id="+aMedcaseId;
		Double sum = 0.00;
		Collection<WebQueryResult> l = service.executeNativeSql(sql);
		if (!l.isEmpty()) {
			WebQueryResult slsInfo = l.iterator().next();
			String dtype = ""+slsInfo.get5();
			String patientId = ""+slsInfo.get1();
			String dateStart = ""+slsInfo.get2();
			String dateFinish = ""+slsInfo.get3();
			String serviceStreamId = ""+slsInfo.get4();
			String bedType = "11";
			if (dtype!=null&&dtype.toUpperCase().equals("HOSPITALMEDCASE")) {
				log.debug("Высчитываем стоимость госпитализации c id="+aMedcaseId);
				sql = " select list(''||( case when coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart=0 then '1'" +
						"       else coalesce(slo.datefinish,slo.transferdate,current_date)-slo.datestart+case when vbst.code='1' then 0 else 1 end end" +
						"       * pp.cost)) as ppsum" +
						"       from medcase slo" +
						"       left join medcase sls on sls.id=slo.parent_id" +
						" left join Vochosptype vht on vht.id=sls.hosptype_id" +
						" left join statisticstub ss on ss.id=sls.statisticStub_id" +
						" left join bedfund bf on bf.id=slo.bedfund_id" +
						" left join vocbedtype vbt on vbt.id=bf.bedtype_id" +
						" left join vocbedsubtype vbst on vbst.id=bf.bedsubtype_id" +
						" left join workPlace wp on wp.id=slo.roomNumber_id" +
						" left join Patient pat on pat.id=slo.patient_id" +
						" left join VocRoomType vrt on vrt.id=wp.roomType_id" +
						" left join mislpu ml on ml.id=slo.department_id" +
						" left join workfunctionservice wfs on wfs.lpu_id=slo.department_id" +
						"     and bf.bedtype_id=wfs.bedtype_id and bf.bedsubtype_id=wfs.bedsubtype_id" +
						"     and wfs.roomType_id=wp.roomType_id" +
						" left join medservice ms on ms.id=wfs.medservice_id" +
						" left join pricemedservice pms on pms.medservice_id=wfs.medservice_id" +
						" left join priceposition pp on pp.id=pms.priceposition_id" +
						" and (pp.isvat is null or pp.isvat='0')" +
						" where slo.parent_id='"+aMedcaseId+"'" +
						"  and ms.servicetype_id='"+bedType+"' and pp.priceList_id='"+aPriceListId+"'";
				log.debug("Запрос для поиска койко дней: "+sql);
				l= service.executeNativeSql(sql);
				
				if (l.size()>0) {
					Object o = l.iterator().next().get1();
					Double cost = (o!=null&&!o.toString().equals(""))?Double.valueOf(o.toString()):0.00;
					log.debug("Сумма за койко дни СЛС№"+aMedcaseId+" = "+cost);
					sum +=cost;
				}
				sql = "select sum (pp.cost) as ppcost " +
					" from medcase vis" +
					" left join workfunction wf on wf.id=vis.workfunctionexecute_id" +
					" left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
					" left join worker w on w.id=wf.worker_id" +
					" left join patient wp on wp.id=w.person_id" +
					" left join vocservicestream vss on vss.id=vis.servicestream_id" +
					" left join medcase smc on smc.parent_id=vis.id and upper(smc.dtype)='SERVICEMEDCASE'" +
					" left join medservice ms on ms.id=smc.medservice_id" +
					" left join pricemedservice pms on pms.medservice_id=smc.medservice_id" +
					" left join priceposition pp on pp.id=pms.priceposition_id" +
					" where vis.patient_id='"+patientId+"' and (vis.datestart between to_date('"+dateStart+"','dd.mm.yyyy') and to_date('"+dateFinish+"','dd.mm.yyyy')" +
					" and upper(vis.dtype)='VISIT' and (vss.code='HOSPITAL' or vss.id='"+serviceStreamId+"' or vss.code='OTHER')" +
					" or vis.datestart-to_date('"+dateStart+"','dd.mm.yyyy') = -1 and upper(vis.dtype)='VISIT' and ( vss.id='"+serviceStreamId+"' ))" +
					" and pp.priceList_id='"+aPriceListId+"' and (vis.noActuality='0' or vis.noActuality is null)";
				
				l = service.executeNativeSql(sql);
				if (!l.isEmpty()) {
					Object o = l.iterator().next().get1();
					Double cost = (o!=null&&!o.toString().equals(""))?Double.valueOf(o.toString()):0.00;
					log.debug("Найдена сумма по диагностическим визитам при нахождении в стационаре ("+aMedcaseId+"), сумма = "+cost);
					sum+=cost;
				}
				
				log.debug("Поиск лабораторных исследований");
				sql = "select sum (pp.cost) as ppcost " +
						" from medcase vis" +
						" left join workfunction wf on wf.id=vis.workfunctionexecute_id" +
						" left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
						" left join worker w on w.id=wf.worker_id" +
						" left join patient wp on wp.id=w.person_id" +
						" left join vocservicestream vss on vss.id=vis.servicestream_id" +
						" left join medcase smc on smc.parent_id=vis.id and upper(smc.dtype)='SERVICEMEDCASE'" +
						" left join medservice ms on ms.id=smc.medservice_id" +
						" left join pricemedservice pms on pms.medservice_id=smc.medservice_id" +
						" left join priceposition pp on pp.id=pms.priceposition_id" +
						" where vis.parent_id='"+aMedcaseId+"'" +
						" and vis.datestart between to_date('"+dateStart+"','dd.mm.yyyy') and to_date('"+dateFinish+"','dd.mm.yyyy')" +
						" and upper(vis.dtype)='VISIT'" +
						" and (vss.code='HOSPITAL' or vss.id is null)" +
						" and (vis.noActuality='0' or vis.noActuality is null) and pp.id is not null and pp.pricelist_id="+aPriceListId;
						
					l = service.executeNativeSql(sql);
					if (!l.isEmpty()) {
						Object o = l.iterator().next().get1();
						Double cost = (o!=null&&!o.toString().equals(""))?Double.valueOf(o.toString()):0.00;
						log.debug("Найдена сумма за лабораторные анализы ("+aMedcaseId+"), сумма = "+cost);
						sum+=cost;
					}
					
					log.debug("Поиск цен за операции");
					sql = "select sum (pp.cost) as ppcost " +
						" from SurgicalOperation so"+
						" left join workfunction wf on wf.id=so.surgeon_id"+
						" left join vocworkfunction vwf on vwf.id=wf.workfunction_id"+
						" left join worker w on w.id=wf.worker_id"+
						" left join patient wp on wp.id=w.person_id"+
						" left join medcase slo on slo.id=so.medcase_id"+
						" left join vocservicestream vss on vss.id=so.servicestream_id"+
						" left join medservice ms on ms.id=so.medservice_id"+
						" left join pricemedservice pms on pms.medservice_id=so.medservice_id"+
						" left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='"+aPriceListId+"'"+
						" where (slo.parent_id='"+aMedcaseId+"' or slo.id='"+aMedcaseId+"') "+
						" and pp.id is not null";
							
						l = service.executeNativeSql(sql);
						if (!l.isEmpty()) {
							Object o = l.iterator().next().get1();
							Double cost = (o!=null&&!o.toString().equals(""))?Double.valueOf(o.toString()):0.00;
							log.debug("Найдена сумма за операции ("+aMedcaseId+"), сумма = "+cost);
							sum+=cost;
						}
						
						log.debug("Поиск цен за анастезию");
						sql = "select coalesce(sum (pp.cost),0) as ppcost " +
							" from Anesthesia aso " +
							"       left join VocAnesthesiaMethod vam on vam.id=aso.method_id" +
							"     left join VocAnesthesia va on va.id=aso.type_id" +
							"     left join SurgicalOperation so on so.id=aso.surgicalOperation_id" +
							"     left join workfunction wf on wf.id=aso.anesthesist_id" +
							"     left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
							"     left join worker w on w.id=wf.worker_id" +
							"     left join patient wp on wp.id=w.person_id" +
							"     left join medcase slo on slo.id=so.medcase_id" +
							"     left join vocservicestream vss on vss.id=so.servicestream_id" +
							"     left join medservice ms on ms.id=aso.medservice_id" +
							"   left join pricemedservice pms on pms.medservice_id=aso.medservice_id" +
							"   left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='"+aPriceListId+"'" +
							" where (slo.parent_id='"+aMedcaseId+"' or slo.id='"+aMedcaseId+"') ";
								
							l = service.executeNativeSql(sql);
							if (!l.isEmpty()) {
								Object o = l.iterator().next().get1();
								Double cost = (o!=null&&!o.toString().equals(""))?Double.valueOf(o.toString()):0.00;
								log.debug("Найдена сумма за анастезию ("+aMedcaseId+"), сумма = "+cost);
								sum+=cost;
							}
							
							log.debug("Поиск цен за доп. услуги");
							sql = "select sum (pp.cost*coalesce(so.medserviceamount,'1'))"+
								 " from MedCase so"+
								 "      left join VocIdc10 mkb on mkb.id=so.idc10_id"+
								 "    left join workfunction wf on wf.id=so.workFunctionExecute_id"+
								 "    left join vocworkfunction vwf on vwf.id=wf.workfunction_id"+
								 "    left join worker w on w.id=wf.worker_id"+
								 "    left join patient wp on wp.id=w.person_id"+
								 "    left join medcase slo on slo.id=so.parent_id"+
								 "    left join vocservicestream vss on vss.id=so.servicestream_id"+
								 "    left join medservice ms on ms.id=so.medservice_id"+
								 "  left join pricemedservice pms on pms.medservice_id=so.medservice_id"+
								 "  left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id='"+aPriceListId+"'"+
								 "    where (slo.parent_id='"+aMedcaseId+"' or slo.id='"+aMedcaseId+"')"+
								 "    and upper(so.dtype)='SERVICEMEDCASE' and upper(slo.dtype)!='VISIT'"+
								 "    and pp.id is not null";
									
								l = service.executeNativeSql(sql);
								if (!l.isEmpty()) {
									Object o = l.iterator().next().get1();
									Double cost = (o!=null&&!o.toString().equals(""))?Double.valueOf(o.toString()):0.00;
									log.debug("Найдена сумма за доп. услуги ("+aMedcaseId+"), сумма = "+cost);
									sum+=cost;
								}
			} else if (dtype!=null&&dtype.toUpperCase().equals("VISIT")) {
				log.debug("Поиск стоимости случая по визиту №"+aMedcaseId);
				sql = "select sum(pp.cost) from medcase smc " +
						" left join medservice ms on ms.id=smc.medservice_id" +
						" left join pricemedservice pms on pms.medservice_id=smc.medservice_id" +
						" left join priceposition pp on pp.id=pms.priceposition_id" +
						" where smc.dtype='ServiceMedCase' and smc.parent_id ="+aMedcaseId+" and pp.pricelist_id ="+aPriceListId;
				l = service.executeNativeSql(sql);
				if (!l.isEmpty()) {
					Object o = l.iterator().next().get1();
					Double cost = (o!=null&&!o.toString().equals(""))?Double.valueOf(o.toString()):0.00;
					log.debug("Найдена сумма по визиту №"+aMedcaseId+", сумма = "+cost);
					sum+=cost;
				}
			}
			
				
			return sum;
			
		} else {
			return 0.00;
		}
		
	}
	public String deleteCAMS(String aIds, HttpServletRequest aRequest) throws NamingException {
		String ret = "";
		if (aIds!=null&&!aIds.equals("")) {
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
			ret = "Удалено " +service.executeUpdateNativeSql("update contractaccountmedservice set isdelete='1' where id in ("+aIds+")")+" записей";
		}
		return ret;
	}
	
	public String getCostByPriceMedService(String aPriceMedServiceId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String ret = "";
		try {
			ret = service.executeNativeSql("select pp.cost from pricemedservice pms left join priceposition pp on pp.id=pms.priceposition_id where pms.id="+aPriceMedServiceId).iterator().next().get1().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	public String updateCAMSinAccountNew(Long aCAMS, Long aAccountNew, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("update ContractAccountMedService set account_id=").append(aAccountNew).append(" where id=").append(aCAMS) ;
		service.executeUpdateNativeSql(sql.toString()) ;
		return "Перенесено" ;
	}
	public String setPMSbyCAMS(Long aPricePosition, Long aCAMS, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select pms.id,pp.cost from pricemedservice pms left join priceposition pp on pp.id=pms.priceposition_id where pp.id=").append(aPricePosition) ;
		List<Object[]> l = service.executeNativeSqlGetObj(sql.toString()) ;
		if (!l.isEmpty()) {
			
			Object[] obj =l.get(0) ;
			sql = new StringBuilder() ;
			sql.append("select ca.id from contractaccount ca left join ContractAccountMedService cams on cams.account_id=ca.id where cams.id=").append(aCAMS).append(" and (ca.isfinished='1')") ;
			if (service.executeNativeSql(sql.toString()).isEmpty()) {
				sql = new StringBuilder() ;
				sql.append("update ContractAccountMedService set cost='").append(obj[1]).append("', medservice_id='").append(obj[0]).append("' where id=").append(aCAMS) ;
				service.executeUpdateNativeSql(sql.toString()) ;
				return "Обновлено";
			} else {
				return "Счет закрыт" ;
			}
		} else {
			return "нет соответствия с внут.мед.услугой" ;
		}
	}
	public String moveNoCheckedCAMSinOtherAccount(Long aAccountOld, Long aAccountNew, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select ca.id from contractaccount ca left join ContractAccountMedService cams on cams.account_id=ca.id where cams.id=").append(aAccountNew).append(" and (ca.isfinished='1')") ;
		if (!service.executeNativeSql(sql.toString()).isEmpty()) {return "Счет закрыт" ;}
		sql = new StringBuilder() ;
		sql.append("select ca.id from contractaccount ca left join ContractAccountMedService cams on cams.account_id=ca.id where cams.id=").append(aAccountOld).append(" and (ca.isfinished='1')") ;
		if (service.executeNativeSql(sql.toString()).isEmpty()) {
			sql = new StringBuilder() ;
			sql.append("update ContractAccountMedService set account_id='").append(aAccountNew).append("' where account_id='").append(aAccountOld).append("' and (isCheck='0' or isCheck is null)") ;
			service.executeUpdateNativeSql(sql.toString()) ;
			return "Обновлено";
		} else {
			return "Счет закрыт" ;
		}
		
	}
	public String isChecked(Long aCAMS, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select ca.id from contractaccount ca left join ContractAccountMedService cams on cams.account_id=ca.id where cams.id=").append(aCAMS).append(" and (ca.isfinished='1')") ;
		if (service.executeNativeSql(sql.toString()).isEmpty()) {
			sql = new StringBuilder() ;
			sql.append("update ContractAccountMedService set isCheck='1' where id=").append(aCAMS) ;
			service.executeUpdateNativeSql(sql.toString()) ;
			return "Обновлено";
		} else {
			return "Счет закрыт" ;
		}
		
	}
	public String isDelete(Long aCAMS, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select ca.id from contractaccount ca left join ContractAccountMedService cams on cams.account_id=ca.id where cams.id=").append(aCAMS).append(" and (ca.isfinished='1')") ;
		if (service.executeNativeSql(sql.toString()).isEmpty()) {
				sql = new StringBuilder() ;
				sql.append("update ContractAccountMedService set isDelete='1' where id=").append(aCAMS) ;
				service.executeUpdateNativeSql(sql.toString()) ;
				return "Обновлено";
		} else {
				return "Счет закрыт" ;
		}
		
	}
	
	public String updatePMSbyMSinACCOUNT(Long aAccount, Long aMS, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		IContractService serviceC = Injection.find(aRequest).getService(IContractService.class) ;
		StringBuilder sql = new StringBuilder() ;
			 
		sql = new StringBuilder() ;
		sql.append("select ca.id,mc.priceList_id from contractaccount ca left join MedContract mc on mc.id=ca.contract_id where ca.id=").append(aAccount).append(" and (ca.isfinished='0' or ca.isfinished is null)") ;
		List<Object[]> l = service.executeNativeSqlGetObj(sql.toString()) ; 
		if (l.isEmpty()) {
			sql = new StringBuilder() ;
			Long pms=serviceC.getPriceMedService(ConvertSql.parseLong(l.get(0)[1]), aMS) ;
			if (pms!=null) {
				sql.append("select pms.id,pp.cost from pricemedservice pms left join priceposition pp on pp.id=pms.priceposition_id") ;
				l = service.executeNativeSqlGetObj(sql.toString()) ;
				Object cost = l.isEmpty()?null:l.get(0)[1];
				sql.append("update ContractAccountMedService set cost='").append(cost).append("', medservice='").append(pms).append("' where account_id=").append(aAccount).append(" and servicein=").append(aMS) ;
				service.executeUpdateNativeSql(sql.toString()) ;
				return "Обновлено";
			} else {
				return "Нет соответствия ";
			}
		} else {
			return "Счет закрыт" ;
		}
	}
		
	
	
	public String deleteLpuContractGroup (String aId, HttpServletRequest aRequest) throws NamingException {
		String sql = "delete from lpucontractnosologygroup where id="+aId;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
		return ""+service.executeUpdateNativeSql(sql);
	}
	public String createLpuContractGroup (String aContractId, String aDiagnosisRuleId, HttpServletRequest aRequest) throws NamingException {
		String sql = "insert into lpucontractnosologygroup (lpudiagnosisrule, nosologygroup) values ("+aDiagnosisRuleId+","+aContractId+")";
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		return "" +service.executeUpdateNativeSql(sql);
	}

	public String saveWorkFunctionService(
 			String aMedServiciesId, String aVWF,String aWF,String aLpu,String aBedType,String aBedSubType
      		 ,String aRoomType,String aPrescriptType,String aNoActiveByPrescript,HttpServletRequest aRequest) throws NamingException {
		StringBuilder sql = new StringBuilder() ;
		String[][] fld = new String[9][2] ;
		fld[0][1] = "medService_id" ;
		fld[1][0]= aVWF; fld[1][1] = "vocWorkFunction_id" ;
		fld[2][0]= aWF; fld[2][1] = "workFunction_id" ;
		fld[3][0]= aLpu; fld[3][1] = "lpu_id" ;
		fld[4][0]= aBedType; fld[4][1] = "bedType_id" ;
		fld[5][0]= aBedSubType; fld[5][1] = "bedSubType_id" ;
		fld[6][0]= aRoomType; fld[6][1] = "roomType_id" ;
		fld[7][0]= aPrescriptType; fld[7][1] = "prescriptType_id" ;
		fld[8][0]= aNoActiveByPrescript; fld[8][1] = "noActiveByPrescript" ;
		String[] ids = aMedServiciesId.split(",") ;
		for (int i=0;i<fld.length-1;i++) {
			if (fld[i][0]==null||fld[i][0].equals("0")||fld[i][0].equals("")) {fld[i][0]=null;}
		}
		for (String medService:ids) {
			fld[0][0]= medService;
			sql.append("select id from workfunctionservice where");
			for (int i=0;i<fld.length-1;i++) {
				if (i>0) sql.append(" and ") ;
				sql.append(" ").append(fld[i][1]).append(" ") ;if (fld[i][0]!=null) {sql.append(" = ").append(fld[i][0]);} else {sql.append(" is null") ;}
			}
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
			Collection<WebQueryResult> col = service.executeNativeSql(sql.toString()) ;
			if (col.isEmpty()) {
				sql = new StringBuilder() ;
				sql.append("insert into workfunctionservice (");
				for (int i=0;i<fld.length;i++) {
					if (i>0) sql.append(",") ;
					sql.append(fld[i][1]).append(" ") ;
				}
				sql.append(") values (") ;
				for (int i=0;i<fld.length;i++) {
					if (i>0) sql.append(",") ;
					if (fld[i][0]!=null) {
						sql.append("'").append(fld[i][0]).append("' ") ;
					} else {
						sql.append("null") ;
					}
				}
				sql.append(")") ;
				service.executeUpdateNativeSql(sql.toString()) ;
			} else {
				sql = new StringBuilder() ;
				sql.append("update workfunctionservice set noActiveByPrescript='").append(aNoActiveByPrescript).append("' where id=").append(col.iterator().next().get1());
				service.executeUpdateNativeSql(sql.toString()) ;
			}
		}
		return "" ;
	}

	public String addContractPerson(
   		 Long aIdPat,String aField,HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		StringBuilder res = new StringBuilder() ;
		res.append(aIdPat) ;
		res.append("#") ;
		service.executeUpdateNativeSql("insert into ContractPerson (patient_id,dtype) values ('"
				+aIdPat+"','NaturalPerson')") ;
		Collection<WebQueryResult> col = service.executeNativeSql("select id from ContractPerson where patient_id="+aIdPat) ;
		if (col.isEmpty()) {
			throw new IllegalArgumentException("ОШИБКА ПРИ ОБРАБОТКА ДАННЫХ!!!") ;
		} else {
			res.append(col.iterator().next().get1()) ;
		}
		res.append("#") ;
		res.append(aField) ;
		return res.toString();
		
	}
	public String updateService(String aTable, String aId, String aTable1, String aId1
			, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		if (aTable1.toUpperCase().equals("MEDSERVICE")) {
			if (aTable.toUpperCase().equals("VOCMEDSERVICE")) {
				sql.append("update MedService set isNoOmc=null,vocMedService_id='").append(aId).append("' where id=").append(aId1) ;
				service.executeUpdateNativeSql(sql.toString()) ;
			} else if (aTable.toUpperCase().equals("PRICEPOSITION")) {
				aTable1="PRICEPOSITION" ;aTable="MEDSERVICE" ;
				String id = aId1 ;
				aId1=aId;aId=id;
			}	
		} 
		if (aTable1.toUpperCase().equals("PRICEPOSITION")) {
			if (aTable.toUpperCase().equals("MEDSERVICE")) {
				sql.append("select id from pricemedservice where medService_id='").append(aId).append("' and pricePosition_id='").append(aId1).append("'") ;
				Collection<WebQueryResult> col = service.executeNativeSql(sql.toString()) ;
				if (col.isEmpty()) {
					sql = new StringBuilder() ;
					sql.append("select id from pricemedservice where medService_id is null and pricePosition_id='").append(aId1).append("'") ;
					col.clear() ;
					col = service.executeNativeSql(sql.toString()) ;
					if (col.isEmpty()) {
						sql = new StringBuilder() ;
						sql.append("insert into PriceMedService (medService_id,pricePosition_id) values ('").append(aId).append("','").append(aId1).append("') ") ;
						service.executeUpdateNativeSql(sql.toString()) ;
					} else {
						sql = new StringBuilder() ;
						sql.append("update PriceMedService set medService_id='").append(aId).append("' where id=").append(col.iterator().next().get1()) ;
						service.executeUpdateNativeSql(sql.toString()) ;
						
					}
				}
			}
		}
		return "" ;
	}
	public String createMedServiceByPMS(Long aPMS, HttpServletRequest aRequest) throws NamingException {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select pms.medService_id,ms.code,ms.name,pms.pricePosition_id")
			.append(",(select max(ms.id) from medservice ms where ms.code=pp.code and replace(upper(ms.name),' ','')=replace(upper(pp.name),' ','') and ms.finishdate is null and ms.dtype='MedService') as msinfo")
			.append(",(select max(ms.id) from medservice ms where ms.code='PRICELISTADD' and ms.dtype='MedServiceGroup') as msgroupinfo")
			.append(",pp.code as ppcode,pp.name as ppname")
			.append(" from pricemedservice pms ")
			.append(" left join medService ms on ms.id=pms.medService_id")
			.append(" left join priceposition pp on pp.id=pms.priceposition_id")
			.append(" where pms.id='").append(aPMS).append("'") ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString()) ;
		
		if (list.isEmpty()) {
			WebQueryResult ppInfo = list.iterator().next() ;
			if (ppInfo.get4()==null) {
				throw new IllegalArgumentException("НЕОПРЕДЕЛЕНА ПОЗИЦИЯ ПРЕЙСКУРАНТА ДЛЯ PRICEMEDSERVICE С ID="+aPMS) ;
			}

			if (ppInfo.get1()!=null) {
				return new StringBuilder().append(ppInfo.get1()).append("@#@")
						.append(ppInfo.get2()).append(" ").append(ppInfo.get3()).toString() ;
			} else {
				if (ppInfo.get6()==null) {
					sql = new StringBuilder() ;
					sql.append("insert into medservice (dtype,code,name) values (select 'MedServiceGroup','PRICELISTADD','УСЛУГИ, ДОБАВЛЕННЫЕ ИЗ ПРЕЙСКУРАНТА')") ;
					int idMSG = service.executeUpdateNativeSql(sql.toString()) ;
					System.out.println("insert result = "+idMSG) ;
					sql = new StringBuilder() ;
					sql.append("select max(id) from medservice where dtype='MedServiceGroup' and code='PRICELISTADD'") ;
					Collection<WebQueryResult> lG = service.executeNativeSql(sql.toString()) ;
					if (lG.isEmpty()) {
						throw new IllegalArgumentException("НЕВОЗМОЖНО ОПРЕДЕЛИТЬ ГРУППУ ДЛЯ ДОБАВЛЕНИЯ УСЛУГИ С КОДОМ: PRICELISTADD") ;
					}
					ppInfo.set6(lG.iterator().next().get1()) ;
					System.out.println("insert result = "+ppInfo.get6()) ;
				}
				list = service.executeNativeSql(sql.toString()) ;
				if (list.isEmpty()) {
					sql = new StringBuilder() ;
					sql.append("insert into medservice (dtype,code,name,parent_id,startdate,isnoomc) (select 'MedService',pp.code,pp.name,'").append(ppInfo.get6()).append("',pp.datefrom,'1' from priceposition pp where pp.id='").append(ppInfo.get4()).append("')") ;
					int idc = service.executeUpdateNativeSql(sql.toString()) ;
					System.out.println("insert result = "+idc) ;
					sql = new StringBuilder() ;
					sql.append("select max(id) from medservice where dtype='MedService' and code='").append(ppInfo.get7()).append("'") ;
					Collection<WebQueryResult> lG = service.executeNativeSql(sql.toString()) ;
					ppInfo.set1(lG.iterator().next().get1()) ;
					sql = new StringBuilder() ;
					sql.append("update pricemedservice set medService_id='").append(ppInfo.get1()).append("' where id='").append(aPMS).append("'") ;
					return createMedServiceByPMS(aPMS, aRequest) ;
				}
			}
		} else {
			throw new IllegalArgumentException("НЕ НАЙДЕН PRICEMEDSERVICE С ID="+aPMS) ;
		}
		return "" ;
	}
	public String findMedServiceByPricePosition( Long aPP, String aJavascript,String aJavaScriptCreate, HttpServletRequest aRequest) throws NamingException {
		StringBuilder sql = new StringBuilder() ;
		sql.append("select pms.medService_id,ms.code,ms.name,pms.id from pricemedservice pms ")
			.append(" left join medService ms on ms.id=pms.medService_id")
			.append(" where pms.pricePosition_id='").append(aPP).append("'") ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString()) ;
		StringBuilder res = new StringBuilder() ;
		if (list.isEmpty()) return "НЕТ ДАННЫХ" ;
		res.append("<ll>" ) ;
		for (WebQueryResult wqr:list) {
			res.append("<li><a href=\"").append(wqr.get2()==null?aJavaScriptCreate:aJavascript).append("('");
			if (wqr.get2()==null) {
				res.append(wqr.get4()!=null?wqr.get4():"") ;
			} else {
				res.append("','");
				res.append(wqr.get1()!=null?wqr.get1():"") ;
				res.append("','");
				//res.append(aTable1) ;
				res.append("','");
				//res.append(aServiceId) ;
				res.append("','");
				res.append(wqr.get2()).append(" ").append(wqr.get3()) ;
			}
			//res.append(aTable) ;
			
			res.append("')\">").append(wqr.get2()).append(" ").append(wqr.get3()).append("</a></li>");
		}
		res.append("</ll>" ) ;
		return res.toString() ;
		
	}
	public String findServiceByPriceList( Long aPriceList, String aCode, String aName, String aDivName
			, String aJavascript, HttpServletRequest aRequest) throws NamingException {
		StringBuilder sql = new StringBuilder() ;
		String addWhereSql = "" ;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		sql.append("select p.id as pid,p.code as pcode,p.name as pname from PricePosition p ")
			.append("").append("where ") ;
		addWhereSql=" p.priceList_id='"+aPriceList+"' and " ;
		sql.append(addWhereSql) ;
	
		boolean isNext = false;
		if (aCode!=null && !aCode.equals("")) {
			sql.append(" upper(p.code) like '%").append(aCode.toUpperCase()).append("%'") ;
			isNext=true ;
		}
		if (aName!=null && !aName.equals("")) {
			if (isNext) sql.append(" and ") ;
			sql.append(" upper(p.name) like '%").append(aName.toUpperCase()).append("%'") ;
		}
		
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString()) ;
		StringBuilder res = new StringBuilder() ;
		if (list.isEmpty()) return "НЕТ ДАННЫХ" ;
		res.append("<ll>" ) ;
		for (WebQueryResult wqr:list) {
			res.append("<li><a href=\"").append(aJavascript).append("('");
			res.append(wqr.get1());
			res.append("')\">").append(wqr.get2()).append(" ").append(wqr.get3()).append("</a><div id='").append(aDivName).append(wqr.get1()).append("'></div></li>");
		}
		res.append("</ll>" ) ;
		return res.toString() ;
	}
	public String findService(String aTable, String aServiceId, String aTable1, Long aPriceList, String aCode, String aName
			, String aJavascript, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		String table=aTable ;String addWhereSql = "" ;String addLeftSql="";
		if (aTable.toUpperCase().trim().equals("MEDSERVICEOPERATION")) {
			table="MedService" ;
			addWhereSql = "p.dtype='MedService' and vmt.code in ('OPERATION','SURVEY') and p.finishDate is null and " ;
			addLeftSql = "left join VocServiceType vmt on vmt.id=p.serviceType_id ";
		} else if (aTable.toUpperCase().trim().equals("PRICEPOSITION")) {
			addWhereSql=" p.priceList_id='"+aPriceList+"' and " ;
		}
		sql.append("select p.id as pid,p.code as pcode,p.name as pname from ").append(table).append(" p ").append(addLeftSql).append("where ") ;
			sql.append(addWhereSql) ;
		
		boolean isNext = false;
		if (aCode!=null && !aCode.equals("")) {
			sql.append(" (p.code like '%").append(aCode.toUpperCase()).append("%' or replace(p.code,'.','') like '%").append(aCode.toUpperCase()).append("%')") ;
			isNext=true ;
		}
		if (aName!=null && !aName.equals("")) {
			if (isNext) sql.append(" and ") ;
			sql.append(" upper(p.name) like '%").append(aName.toUpperCase()).append("%'") ;
		}
		
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString()) ;
		StringBuilder res = new StringBuilder() ;
		if (list.isEmpty()) return "НЕТ ДАННЫХ" ;
		res.append("<ll>" ) ;
		for (WebQueryResult wqr:list) {
			res.append("<li><a href=\"").append(aJavascript).append("('");
			res.append(aTable) ;
			res.append("','");
			res.append(wqr.get1()!=null?wqr.get1():"") ;
			res.append("','");
			res.append(aTable1) ;
			res.append("','");
			res.append(aServiceId) ;
			res.append("','");
			res.append(wqr.get2()).append(" ").append(wqr.get3()) ;
			res.append("')\">").append(wqr.get2()).append(" ").append(wqr.get3()).append("</a></li>");
		}
		res.append("</ll>" ) ;
		return res.toString() ;
	}
	public String findPerson(String aLastname, String aFirstname, String aMiddlename
			, String aBirthday, String aJavascript, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select p.id as pid,cp.id as cpid,p.lastname||' '||p.firstname||' '||p.middlename||' г.р. '||to_char(p.birthday,'dd.mm.yyyy') from patient p left join ContractPerson cp on cp.patient_id=p.id where ") ;
		if (aLastname!=null && !aLastname.equals("")) {
			sql.append(" p.lastname like '").append(aLastname.toUpperCase()).append("%'") ;
		}
		if (aFirstname!=null && !aFirstname.equals("")) {
			sql.append(" and p.firstname like '").append(aFirstname.toUpperCase()).append("%'") ;
		}
		if (aMiddlename!=null && !aMiddlename.equals("")) {
			sql.append(" and p.middlename like '").append(aMiddlename.toUpperCase()).append("%'") ;
		}
		if (aBirthday!=null && !aBirthday.equals("")) {
			sql.append(" and p.birthday = to_date('").append(aBirthday).append("','dd.mm.yyyy')") ;
		}
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString()) ;
		StringBuilder res = new StringBuilder() ;
		if (list.isEmpty()) return "НЕТ ДАННЫХ" ;
		res.append("<ll>" ) ;
		for (WebQueryResult wqr:list) {
			res.append("<li><a href=\"").append(aJavascript).append("('");
			res.append(wqr.get1()!=null?wqr.get1():"") ;
			res.append("','");
			res.append(wqr.get2()!=null?wqr.get2():"") ;
			res.append("','");
			res.append(wqr.get3()!=null?wqr.get3():"") ;
			res.append("')\">").append(wqr.get3()).append("</a></li>");
		}
		res.append("</ll>" ) ;
		return res.toString() ;
	}
	
	public String updateExtDispPlanService(Long aPlan, String aAction, Long aServiceId, Long aAgeGroup, Long aSex, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String actionNext="" ;
		Long sexOther = aSex!=null && aSex.equals(Long.valueOf(1))?Long.valueOf("2"):Long.valueOf("1") ;
		System.out.println("action="+aAction+"=") ;
		if (aAction!=null && (aAction.equals("Д") || aAction.equals("-"))) {
			Collection<WebQueryResult> list = service.executeNativeSql("select edps.id,edps.sex_id "
				+"from ExtDispPlanService edps left join vocsex vs on vs.id=edps.sex_id where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
				+"' and edps.ageGroup_id='"+aAgeGroup+"' and (vs.omcCode='"+sexOther+"' or vs.id is null)") ;
			if (!list.isEmpty()) {
				list = service.executeNativeSql("select edps.id,edps.sex_id "
						+"from ExtDispPlanService edps left join vocsex vs on vs.id=edps.sex_id where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
						+"' and edps.ageGroup_id='"+aAgeGroup+"' and (vs.omcCode='"+sexOther+"')") ;
				if (!list.isEmpty()) {
					service.executeUpdateNativeSql("update "
							+" ExtDispPlanService edps "
							+" set sex_id=null"
							+" where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
							+"' and edps.ageGroup_id='"+aAgeGroup+"'") ;
				}
			} else {
				try {
					Collection<WebQueryResult> wqrSex = service.executeNativeSql("select vs.id,vs.name from vocsex vs where vs.omcCode='"+aSex+"'",1) ;
					if (wqrSex.isEmpty()) {
						service.executeUpdateNativeSql("insert into "
								+" ExtDispPlanService "
								+" (sex_id,plan_id,serviceType_id,ageGroup_id) values ((select min(vs.id) from vocsex vs where vs.omcCode='"+aSex+"'),'"+aPlan+"','"+aServiceId+"','"+aAgeGroup+"')"
								) ;
					} else {
						service.executeUpdateNativeSql("insert into "
								+" ExtDispPlanService "
								+" (sex_id,plan_id,serviceType_id,ageGroup_id) values ('"+wqrSex.iterator().next().get1()+"','"+aPlan+"','"+aServiceId+"','"+aAgeGroup+"')"
								) ;
					}
				} catch (Exception e) {
					service.executeUpdateNativeSql("alter table extdispplanservice alter column id set default nextval('extdispplanservice_sequence')") ;
					service.executeUpdateNativeSql("insert into "
							+" ExtDispPlanService "
							+" (sex_id,plan_id,serviceType_id,ageGroup_id) values ((select min(vs.id) from vocsex vs where vs.omcCode='"+aSex+"'),'"+aPlan+"','"+aServiceId+"','"+aAgeGroup+"')"
							) ;
				}
			}
			
			actionNext=aAction.equals("Д")?"У":"+" ;
		} else if (aAction!=null && (aAction.equals("У")||aAction.equals("+"))) {
			service.executeUpdateNativeSql("delete "
					+" from ExtDispPlanService edps where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
					+"' and edps.ageGroup_id='"+aAgeGroup+"' and (select vs.omcCode from vocsex vs where vs.id=edps.sex_id)='"+aSex+"'") ;
			service.executeUpdateNativeSql("update "
					+" ExtDispPlanService edps "
					+" set sex_id=(select min(vs.id) from vocsex vs where vs.omcCode='"+sexOther+"')"
					+" where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
					+"' and edps.ageGroup_id='"+aAgeGroup+"' and edps.sex_id is null") ;
			actionNext=aAction.equals("У")?"Д":"-" ;
		} 
		return actionNext ;
	}

	public String updateExtDispPlanServiceAllAge(Long aPlan, String aAction, Long aServiceId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String actionNext="" ;
		System.out.println("action="+aAction+"=") ;
		Collection<WebQueryResult> listAge = service.executeNativeSql("select veda.id,veda.name from VocExtDispAgeGroup veda left join ExtDispPlan edp on edp.dispType_id=veda.dispType_id where edp.id="+aPlan) ;
		if (aAction!=null && (aAction.equals("+"))) {
			for (WebQueryResult wqrAge:listAge) {
			String aAgeGroup=""+wqrAge.get1() ;
			
				Collection<WebQueryResult> list = service.executeNativeSql("select edps.id,edps.sex_id "
					+"from ExtDispPlanService edps left join vocsex vs on vs.id=edps.sex_id where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
					+"' and edps.ageGroup_id='"+aAgeGroup+"'") ;
				if (!list.isEmpty()) {
					service.executeUpdateNativeSql("update "
								+" ExtDispPlanService edps "
								+" set sex_id=null"
								+" where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId
								+"' and edps.ageGroup_id='"+aAgeGroup+"'") ;
				} else {
					try {
						service.executeUpdateNativeSql("insert into "
								+" ExtDispPlanService "
								+" (plan_id,serviceType_id,ageGroup_id) values ('"+aPlan+"','"+aServiceId+"','"+aAgeGroup+"')"
								) ;
					} catch (Exception e) {
						service.executeUpdateNativeSql("alter table extdispplanservice alter column id set default nextval('extdispplanservice_sequence')") ;
						service.executeUpdateNativeSql("insert into "
								+" ExtDispPlanService "
								+" (plan_id,serviceType_id,ageGroup_id) values ('"+aPlan+"','"+aServiceId+"','"+aAgeGroup+"')"
								) ;
					}
				}
			} 
			actionNext="-" ;
		} else if (aAction!=null && (aAction.equals("-"))) {
			service.executeUpdateNativeSql("delete "
					+" from ExtDispPlanService edps where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId+"'") ;
			actionNext="+" ;
		}
		return aAction ;
	}

	public String settingAppropriateService(String aAppropriateService, HttpServletRequest aRequest) throws NamingException {
		
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String[] appService = aAppropriateService.split(",") ;
		for (int i=0;i<appService.length;i++) {
			String[] vr = appService[i].split(":") ;
			String aMedService = vr[1] ;
			String aPricePosition = vr[0] ;
			StringBuilder sql = new StringBuilder() ;
			sql.append("select min(case when pms.medService_id='").append(aMedService)
				.append("' then pms.id else null end) as pmsser")
				.append(", min(case when pms.medService_id is null then pms.id else null end) as pmsmin")
				.append(", min(case when pms.medService_id is not null and pms.medService_id='").append(aMedService)
				.append("' then pms.id else null end) as pmsother")
				.append(" from PriceMedService pms where pricePosition_id='").append(aPricePosition).append("'");
			Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1) ;
			if (list.isEmpty()) {
				
			} else {
				WebQueryResult wqr = list.iterator().next() ;
				if (wqr.get1()!=null) {
					
				} else if (wqr.get2()!=null) {
					service.executeUpdateNativeSql("update PriceMedService set medService_id='"+aMedService
							+"' where id='"+wqr.get2()+"'") ;
				} else if (wqr.get3()!=null) {
					service.executeUpdateNativeSql("update PriceMedService set medService_id='"+aMedService
							+"' where id='"+wqr.get3()+"'") ;
				} else {
					service.executeUpdateNativeSql(
							"insert into PriceMedService (medService_id,pricePosition_id) values ('"
							+aMedService
							+"','"+aPricePosition+"')") ;
				}
			}
		}
		return "Соответствие установлено" ;
	}
	public String getCostByPriceMedService(Long aPriceMedService, HttpServletRequest aRequest) throws Exception {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		//IContractService service = Injection.find(aRequest).getService(IContractService.class) ;
		
		StringBuilder sql = new StringBuilder() ;
		sql.append("") ;
		sql.append("select pp.cost,pp.id ") ;
		sql.append(" from PriceMedService pms left join PricePosition pp on pp.id=pms.pricePosition_id");
		sql.append(" where pms.id='")
		.append(aPriceMedService).append("' and pp.cost>0") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1) ;
		
		return list.isEmpty()?"":""+list.iterator().next().get1() ;
	}

}
