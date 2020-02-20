package ru.ecom.mis.web.dwr.contract;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.mis.ejb.service.contract.IContractService;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.tags.helper.RolesHelper;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.List;


public class ContractServiceJs {
	
	public static final Logger LOG = Logger.getLogger(ContractServiceJs.class);

	public void changeIsPaymentTerminal(Long aContractAccountOperationId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		String sql = "update contractaccountoperation set ispaymentterminal = case when ispaymentterminal='1' then false else true end where id="+aContractAccountOperationId;
		service.executeUpdateNativeSql(sql);
		sql = "insert into AdminChangeJournal (cType, createDate, createTime, createUsername, annulRecord) " +
				"values ('CHANGEPAYMENTTYPE',current_date, current_time, '"+username+"', 'Инвертирован метод оплаты по счету с ИД №"+aContractAccountOperationId+"'); ";
		service.executeUpdateNativeSql(sql);
	}

	/**Замена обслуживаемой персоны по всем счетам договора */
	public void changeServedPerson(Long aConractId, Long aPersonId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "update servedPerson set person_id="+aPersonId+" where contract_id="+aConractId;
		service.executeUpdateNativeSql(sql);
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername() ;
		sql = "insert into AdminChangeJournal (cType, createDate, createTime, createUsername, annulRecord) " +
				"values ('CHANGESERVEDPERSON',current_date, current_time, '"+username+"', 'Обслуживаемая персона по договору "+aConractId+" изменена на "+aPersonId+"'); ";
		service.executeUpdateNativeSql(sql);
	}

	private void makeHttpPostRequest(String data, HttpServletRequest aRequest) throws NamingException {
		LOG.info("===Send to KKM. Data = "+data);
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		//Milamesher 11012019 #136 отправка на привязанный к wf ККМ
		String username = LoginInfo.find(aRequest.getSession(true)).getUsername();
		Collection<WebQueryResult> l = service.executeNativeSql("select eq.url from equipment eq" +
				" left join workfunction wf on wf.kkmequipmentdefault_id=eq.id" +
				" where secuser_id=(select id from secuser where login='" + username + "')");
		if (!l.isEmpty()) {
			try {
				//method by milamesher 15.03.2017
				//отправка пост-запроса на веб-сервис, управляющий печатью ккм
				URL url = new URL(l.iterator().next().get1().toString());
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				StringBuilder answerString = new StringBuilder();
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Accept", "application/json");
				connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8")){
					writer.write(data);
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()== null ? connection.getInputStream() : connection.getErrorStream()));
				String line;
				while ((line = br.readLine()) != null) {
					answerString.append(line);
				}
				br.close();
				connection.disconnect();
				LOG.info("KKM return: "+answerString);
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
		} else {
			LOG.error("Нет настройки ККМ по умолчанию для рабочей функции пользователя "+username+", работа с ККМ невозможна");
		}
	}

	//Печать K, Z отчета
	private String printKKMReport(String aType, HttpServletRequest aRequest) {
		if ("Z".equals(aType) || "X".equals(aType)) {
			try {
				JSONObject root = new JSONObject();
				root.put("function", "print"+aType+"Report");
				makeHttpPostRequest(root.toString(),aRequest);
				return  aType+" отчет успешно отправлен на печать";
			}
			catch (Exception e) {
				LOG.error(e);
				return e.getMessage();
			}

		}
		return "Неизвестный тип отчета";
	}

	/**
	 * Отправить запрос на ККМ.
	 * @param aFunction  - команда ККМ
	 * @param aRequest
	 * @return ответ
	 * @throws JspException
	 */
	public String sendKKMRequest(String aFunction, HttpServletRequest aRequest) throws JspException {
		if (RolesHelper.checkRoles(" /Policy/Config/KKMWork", aRequest)) {
            if ("makePayment".equals(aFunction) || "makeRefund".equals(aFunction)) {
                return "Запрещено к выполнению!!!";
            } else if ("printZReport".equals(aFunction)){
                return printKKMReport("Z", aRequest);
            } else if ("printXReport".equals(aFunction)){
                return printKKMReport("X",aRequest);
            } else if ("printLastOrder".equals(aFunction)) {
                return printLastOrder(aRequest);
            } else {
                return "Неизвестная функция: "+aFunction;
            }
		} else {
			return "Нет прав для работы с ККМ";
		}
	}

	/**
	 * Печать последнего чека (в случае неудачи его первой печати
	 * @param aRequest
	 * @return
	 */
	private String printLastOrder(HttpServletRequest aRequest)  {
		try {
			JSONObject root = new JSONObject();
			root.put("function", "continuePrint");
			makeHttpPostRequest(root.toString(), aRequest);
			return "Чек отправлен на печать";
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			return "Произошла ошибка: "+e;
		}
	}

	/**
	 * Получаем информацию об остатке денег по гарантийному письму
	private JSONObject getGuaranteeMoneyInfo(Long aGuaranteeId, IWebQueryService aService) throws NamingException {
		String sql = "select cg.id as guaranteeid, cg.limitmoney as limitmoney" +
				",sum(cost*coalesce(countmedservice,1)) as spent" +
				", case when  cg.isnolimit ='1' then false else true end as islimit" +
				" from contractguarantee cg" +
				" left join contractaccountmedservice cams on cams.guarantee_id = cg.id"+
				" where cg.id="+aGuaranteeId+" and cams.account_id is null"+
				" group by cg.id, cg.limitmoney";
		return new JSONObject(aService.executeSqlGetJson(sql));
	}
*/
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
	public String checkIfDogovorIsNeeded (Long aPatient, Long aServiceStreamId, String aDate, Long aDatePlanId,String aMedhelpType, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		Collection<WebQueryResult> l = service.executeNativeSql("select id from vocserviceStream where id="+aServiceStreamId+" and isCalcDogovor='1'");
		if (!l.isEmpty()) { //требуется гарантийное письмо
			if ("POLYCLINIC".equals(aMedhelpType)) {
				aMedhelpType = "AMB";
			} else if ("HOSPITAL".equals(aMedhelpType)) {
				aMedhelpType = "PLAN_HOSP";
			} else {
				return new JSONObject().put("status","error").put("errorName","Неизвестный вид помощи").toString();
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("select cg.id as id,cg.numberdoc as number, to_char(cg.issueDate,'dd.MM.yyyy') as issueDate, mc.contractnumber as contractNumber")
					.append(",cg.limitMoney, case when cg.isnolimit ='1' then true else false end as f6_noLimit")
					.append(",coalesce(sum(cams.cost*coalesce(cams.countmedservice,1)),0) as f7_spent ")
					.append(",cg.limitMoney - coalesce(sum(cams.cost*coalesce(cams.countmedservice,1)),0) as f8_ostatok ")
					.append(" from contractguarantee cg")
					.append(" left join contractperson cp on cp.id=cg.contractperson_id")
					.append(" left join medcontract mc on mc.id=cg.contract_id")
					.append(" left join contractperson cpCustomer on cpCustomer.id=mc.customer_id")
					.append(" left join vocguaranteekindhelp vgkh on vgkh.id=cg.kindhelp_id")
					.append(" left join medpolicy mp on mp.patient_id=cp.patient_id and mp.company_id=cpCustomer.regcompany_id")
					.append(" left join contractaccountmedservice cams on cams.guarantee_id = cg.id and cams.account_id is null ")
					.append(" where cp.patient_id=").append(aPatient).append(" and (mc.servicestream_id is null or mc.servicestream_id=").append(aServiceStreamId).append(")")
					.append(" and vgkh.code='").append(aMedhelpType).append("'");
			if (aDate!=null&&!aDate.equals("")) {
				sb.append(" and (cg.actiondateto is null or cg.actiondateto >=to_date('").append(aDate).append("','dd.MM.yyyy'))");
			} else if (aDatePlanId!=null && aDatePlanId>0L){
				sb.append(" and (cg.actiondateto is null or cg.actiondateto >=(select calendardate from workcalendarday where id=").append(aDatePlanId).append("))");
			} else {
				sb.append(" and (cg.actiondateto is null or cg.actiondateto >=current_date)");
			}
			sb.append(" and ((cpCustomer.regcompany_id is not null and mp.id is not null) or (cpCustomer.regcompany_id is null ))")
					.append(" group by cg.id, cg.limitmoney,mc.contractnumber");
			l = service.executeNativeSql(sb.toString());
			if (!l.isEmpty()) {
				WebQueryResult r = l.iterator().next();

				JSONObject ret = new JSONObject();
				ret.put("status","ok");
				ret.put("noLimit",r.get6());
				ret.put("limit",r.get5());
				ret.put("spent",r.get7());
				ret.put("ostatok",r.get8());
				ret.put("guaranteeInfo","№"+r.get2()+" от "+r.get3()+", договор №"+r.get4()+(r.get6().toString().equals("true") ? ". Без лимита" :". Остаток: "+r.get8()));
				return ret.toString();
			} else {
				return new JSONObject().put("status","error").put("errorName","Не найдено гарантийное письмо").toString();
			}
		}
		
		return new JSONObject().put("status","ok").toString();
	}
	/**
	 * Функция нахождения стоимости случая лечения ( визита либо случая лечения в стационаре)
	 * @param aMedcaseId - id случая лечения
	 * @param aPriceListId - id прайс-листа
	 * @param aRequest
	 * @return Стоимость случая
	 * @throws NamingException
	 */
	private BigDecimal calculateMedCaseCost(Long aMedcaseId, Long aPriceListId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql;
		if (aPriceListId==null) {
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
					LOG.warn("Не удалось вычислить прайс-лист для расчета цены случая " +aMedcaseId);
					return BigDecimal.ZERO;
				}
			}
		}
		LOG.debug("Находим информацию по пацинету, СМО="+aMedcaseId);
		sql = "select m.patient_id,to_char(m.datestart,'dd.mm.yyyy') as dstart ,to_char(coalesce(m.datefinish,current_date),'dd.mm.yyyy') as dfinish,m.serviceStream_id as servstream, m.dtype as dtype from medcase m where m.id="+aMedcaseId;
		BigDecimal sum = BigDecimal.ZERO;
		Collection<WebQueryResult> l = service.executeNativeSql(sql);
		if (!l.isEmpty()) {
			WebQueryResult slsInfo = l.iterator().next();
			String dtype = ""+slsInfo.get5();
			String patientId = ""+slsInfo.get1();
			String dateStart = ""+slsInfo.get2();
			String dateFinish = ""+slsInfo.get3();
			String serviceStreamId = ""+slsInfo.get4();
			String bedType = "11";
			if ("HOSPITALMEDCASE".equalsIgnoreCase(dtype)) {
				LOG.debug("Высчитываем стоимость госпитализации c id="+aMedcaseId);
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
				LOG.debug("Запрос для поиска койко дней: "+sql);
				l= service.executeNativeSql(sql);
				if (!l.isEmpty()) {
					Object o = l.iterator().next().get1();
					BigDecimal cost = (o!=null && !o.toString().equals("")) ? BigDecimal.valueOf(Double.parseDouble(o.toString())) : BigDecimal.ZERO;
					LOG.debug("Сумма за койко дни СЛС№"+aMedcaseId+" = "+cost);
					sum =sum.add(cost);
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
					BigDecimal cost = (o!=null && !o.toString().equals("")) ? BigDecimal.valueOf(Double.parseDouble(o.toString())) : BigDecimal.ZERO;
					LOG.debug("Найдена сумма по диагностическим визитам при нахождении в стационаре ("+aMedcaseId+"), сумма = "+cost);
					sum=sum.add(cost);
				}
				
				LOG.debug("Поиск лабораторных исследований");
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
						BigDecimal cost = (o!=null && !o.toString().equals("")) ? BigDecimal.valueOf(Double.parseDouble(o.toString())) : BigDecimal.ZERO;
						LOG.debug("Найдена сумма за лабораторные анализы ("+aMedcaseId+"), сумма = "+cost);
						sum=sum.add(cost);
					}
					
					LOG.debug("Поиск цен за операции");
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
							BigDecimal cost = (o!=null && !o.toString().equals("")) ? BigDecimal.valueOf(Double.parseDouble(o.toString())) : BigDecimal.ZERO;
							LOG.debug("Найдена сумма за операции ("+aMedcaseId+"), сумма = "+cost);
							sum=sum.add(cost);
						}
						
						LOG.debug("Поиск цен за анастезию");
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
								BigDecimal cost = (o!=null && !o.toString().equals("")) ? BigDecimal.valueOf(Double.parseDouble(o.toString())) : BigDecimal.ZERO;
								LOG.debug("Найдена сумма за анастезию ("+aMedcaseId+"), сумма = "+cost);
								sum=sum.add(cost);
							}
							
							LOG.debug("Поиск цен за доп. услуги");
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
									BigDecimal cost = (o!=null && !o.toString().equals("")) ? BigDecimal.valueOf(Double.parseDouble(o.toString())) : BigDecimal.ZERO;
									LOG.debug("Найдена сумма за доп. услуги ("+aMedcaseId+"), сумма = "+cost);
									sum=sum.add(cost);
								}
			} else if ("VISIT".equalsIgnoreCase(dtype)) {
				LOG.debug("Поиск стоимости случая по визиту №"+aMedcaseId);
				sql = "select sum(pp.cost) from medcase smc " +
						" left join medservice ms on ms.id=smc.medservice_id" +
						" left join pricemedservice pms on pms.medservice_id=smc.medservice_id" +
						" left join priceposition pp on pp.id=pms.priceposition_id" +
						" where smc.dtype='ServiceMedCase' and smc.parent_id ="+aMedcaseId+" and pp.pricelist_id ="+aPriceListId;
				l = service.executeNativeSql(sql);
				if (!l.isEmpty()) {
					Object o = l.iterator().next().get1();
					BigDecimal cost = (o!=null && !o.toString().equals("")) ? BigDecimal.valueOf(Double.parseDouble(o.toString())) : BigDecimal.ZERO;
					LOG.debug("Найдена сумма по визиту №"+aMedcaseId+", сумма = "+cost);
					sum=sum.add(cost);
				}
			}
			return sum;
		} else {
			return BigDecimal.ZERO;
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
			LOG.error(e);
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
		StringBuilder sql  = new StringBuilder() ;
		sql.append("select ca.id,mc.priceList_id from contractaccount ca left join MedContract mc on mc.id=ca.contract_id where ca.id=").append(aAccount).append(" and (ca.isfinished='0' or ca.isfinished is null)") ;
		List<Object[]> l = service.executeNativeSqlGetObj(sql.toString()) ; 
		if (!l.isEmpty()) {
			Long pms=serviceC.getPriceMedService(ConvertSql.parseLong(l.get(0)[1]), aMS) ;
			if (pms!=null) {
				sql = new StringBuilder() ;
				sql.append("select pms.id,pp.cost from pricemedservice pms left join priceposition pp on pp.id=pms.priceposition_id") ;
				l = service.executeNativeSqlGetObj(sql.toString()) ;
				Object cost = l.isEmpty() ? null : l.get(0)[1];
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
		if (aTable1.equalsIgnoreCase("MEDSERVICE")) {
			if (aTable.equalsIgnoreCase("VOCMEDSERVICE")) {
				sql.append("update MedService set isNoOmc=null,vocMedService_id='").append(aId).append("' where id=").append(aId1) ;
				service.executeUpdateNativeSql(sql.toString()) ;
			} else if (aTable.equalsIgnoreCase("PRICEPOSITION")) {
				aTable1="PRICEPOSITION" ;aTable="MEDSERVICE" ;
				String id = aId1 ;
				aId1=aId;aId=id;
			}	
		} 
		if (aTable1.equalsIgnoreCase("PRICEPOSITION")) {
			if (aTable.equalsIgnoreCase("MEDSERVICE")) {
				sql.append("select id from pricemedservice where medService_id='").append(aId).append("' and pricePosition_id='").append(aId1).append("'") ;
				Collection<WebQueryResult> col = service.executeNativeSql(sql.toString()) ;
				if (col.isEmpty()) {
					sql = new StringBuilder() ;
					sql.append("select id from pricemedservice where medService_id is null and pricePosition_id='").append(aId1).append("'") ;
					col = service.executeNativeSql(sql.toString()) ;
					sql = new StringBuilder() ;
					if (col.isEmpty()) {
						sql.append("insert into PriceMedService (medService_id,pricePosition_id) values ('").append(aId).append("','").append(aId1).append("') ") ;
					} else {
						sql.append("update PriceMedService set medService_id='").append(aId).append("' where id=").append(col.iterator().next().get1()) ;
					}
					service.executeUpdateNativeSql(sql.toString()) ;
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
					LOG.info("insert result = "+idMSG) ;
					sql = new StringBuilder() ;
					sql.append("select max(id) from medservice where dtype='MedServiceGroup' and code='PRICELISTADD'") ;
					Collection<WebQueryResult> lG = service.executeNativeSql(sql.toString()) ;
					if (lG.isEmpty()) {
						throw new IllegalArgumentException("НЕВОЗМОЖНО ОПРЕДЕЛИТЬ ГРУППУ ДЛЯ ДОБАВЛЕНИЯ УСЛУГИ С КОДОМ: PRICELISTADD") ;
					}
					ppInfo.set6(lG.iterator().next().get1()) ;
					LOG.info("insert result = "+ppInfo.get6()) ;
				}
				list = service.executeNativeSql(sql.toString()) ;
				if (list.isEmpty()) {
					sql = new StringBuilder() ;
					sql.append("insert into medservice (dtype,code,name,parent_id,startdate,isnoomc) (select 'MedService',pp.code,pp.name,'").append(ppInfo.get6()).append("',pp.datefrom,'1' from priceposition pp where pp.id='").append(ppInfo.get4()).append("')") ;
					int idc = service.executeUpdateNativeSql(sql.toString()) ;
					LOG.info("insert result = "+idc) ;
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
		String addWhereSql;
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		
		sql.append("select p.id as pid,p.code as pcode,p.name as pname from PricePosition p ")
			.append("where ") ;
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
		String table=aTable ;
		String addWhereSql = "" ;
		String addLeftSql="";
		if (aTable.trim().equalsIgnoreCase("MEDSERVICEOPERATION")) {
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
		Long sexOther = aSex!=null && aSex.equals(1L)?Long.valueOf("2"):Long.valueOf("1") ;
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
		} else if ("У".equals(aAction) || "+".equals(aAction)) {
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
		//String actionNext="" ;
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
		//	actionNext="-" ;
		} else if (aAction!=null && (aAction.equals("-"))) {
			service.executeUpdateNativeSql("delete "
					+" from ExtDispPlanService edps where edps.plan_id='"+aPlan+"' and edps.serviceType_id='"+aServiceId+"'") ;
	//		actionNext="+" ;
		}
		return aAction ;
	}

	public String settingAppropriateService(String aAppropriateService, HttpServletRequest aRequest) throws NamingException {
		
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String[] appService = aAppropriateService.split(",") ;
		for (String s : appService) {
			String[] vr = s.split(":");
			String aMedService = vr[1];
			String aPricePosition = vr[0];
			StringBuilder sql = new StringBuilder();
			sql.append("select min(case when pms.medService_id='").append(aMedService)
					.append("' then pms.id else null end) as pmsser")
					.append(", min(case when pms.medService_id is null then pms.id else null end) as pmsmin")
					.append(", min(case when pms.medService_id is not null and pms.medService_id='").append(aMedService)
					.append("' then pms.id else null end) as pmsother")
					.append(" from PriceMedService pms where pricePosition_id='").append(aPricePosition).append("'");
			Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(), 1);
			if (!list.isEmpty()) {
				WebQueryResult wqr = list.iterator().next();
				if (wqr.get1() != null) {

				} else if (wqr.get2() != null) {
					service.executeUpdateNativeSql("update PriceMedService set medService_id='" + aMedService
							+ "' where id='" + wqr.get2() + "'");
				} else if (wqr.get3() != null) {
					service.executeUpdateNativeSql("update PriceMedService set medService_id='" + aMedService
							+ "' where id='" + wqr.get3() + "'");
				} else {
					service.executeUpdateNativeSql(
							"insert into PriceMedService (medService_id,pricePosition_id) values ('"
									+ aMedService
									+ "','" + aPricePosition + "')");
				}
			}
		}
		return "Соответствие установлено" ;
	}
	public String getCostByPriceMedService(Long aPriceMedService, HttpServletRequest aRequest) throws Exception {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		StringBuilder sql = new StringBuilder() ;
		sql.append("select pp.cost,pp.id ") ;
		sql.append(" from PriceMedService pms left join PricePosition pp on pp.id=pms.pricePosition_id");
		sql.append(" where pms.id='")
		.append(aPriceMedService).append("' and pp.cost>0") ;
		Collection<WebQueryResult> list = service.executeNativeSql(sql.toString(),1) ;
		return list.isEmpty() ? "" : ""+list.iterator().next().get1() ;
	}

	/**
	 * Получить дополнительные шаблонов (для печати лаб. анализов в договоре).
	 *
	 * @param aMedServiceId MedService.id
	 * @param aRequest HttpServletRequest
	 * @return String дополнительные шаблоны
	 */
	public String getLabAnalysisExtra(String aMedServiceId, HttpServletRequest aRequest) throws NamingException {
		StringBuilder res=new StringBuilder();
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select ms.code||' '||ms.name,ms.id\n" +
				"from VocLabAnalysisExtraPrint vlaep\n" +
				"left join medservice ms on ms.code=vlaep.medservice\n" +
				"left join pricemedservice pms on pms.medservice_id=ms.id\n" +
				"left join ContractAccountMedService cams on cams.medservice_id=pms.id\n" +
				"left join ContractAccount ca on cams.account_id=ca.id\n" +
				"left join medcontract mc on ca.contract_id=mc.id\n" +
				"where mc.id="+ aMedServiceId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
		if (!list.isEmpty()) {
			for (WebQueryResult w : list) {
				res.append(w.get1()).append("#").append(w.get2()).append("!");
			}
		} else res.append("##");
		return res.toString();
	}

	/**
	 * Получить шаблон по коду услуги.
	 *
	 * @param aMedServiceId MedService.id
	 * @param aRequest HttpServletRequest
	 * @return String шаблон
	 */
	public String getUserTemplateDocForPrintByService(String aMedServiceId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		String sql = "select filename from userDocument where template="+aMedServiceId;
		Collection<WebQueryResult> list = service.executeNativeSql(sql);
		return list.isEmpty() ? "" : list.iterator().next().get1().toString() ;
	}

	/**
	 * Получить список шаблонов по списку услуг.
	 *
	 * @param aMedServiceSId Список услуг
	 * @param aRequest HttpServletRequest
	 * @return String шаблоны
	 */
	public String getAllUserTemplateDocForPrintByService(String[] aMedServiceSId, HttpServletRequest aRequest) throws NamingException {
		StringBuilder res=new StringBuilder();
		for (String s : aMedServiceSId) {
			String r = getUserTemplateDocForPrintByService(s, aRequest);
			if (r.equals(""))
				res.append(s).append("#").append("*").append("!"); //в имени файла не мб *
			else if (res.indexOf(r) == -1)
				res.append(s).append("#").append(r).append("!");
		}
		return res.toString();
	}

	/**
	 * Копировать шаблоны из одной услуги в другую #133.
	 *
	 * @param aMedServiceIdFrom MedService.id копировать из
	 * @param aMedServiceIdTo MedService.id в
	 * @param aRequest HttpServletRequest
	 * @return String сообщение
	 */
	public String copyTemplatesToMedService(String aMedServiceIdFrom, String aMedServiceIdTo, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		service.executeNativeSql("select copymedservicetemplates ("+aMedServiceIdFrom+","+aMedServiceIdTo+")");
		return "Шаблоны скопированы!" ;
	}

	/**
	 * Изменить родителя услуги.
	 *
	 * @param aMedServiceId MedService.id
	 * @param aMedServiceGroupId MedService.id группа
	 * @param aRequest HttpServletRequest
	 * @return String результат
	 */
	public String changeParentMedService(String aMedServiceId, String aMedServiceGroupId, HttpServletRequest aRequest) throws NamingException {
		IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
		service.executeUpdateNativeSql("update medservice set parent_id='"+aMedServiceGroupId+"' where id='"+aMedServiceId+"'");
		return "Услуга перенесена в группу!" ;
	}
}