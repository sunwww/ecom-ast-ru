package ru.ecom.mis.web.action.patient;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.web.action.psych.AreaReportForm;
import ru.ecom.mis.web.action.util.ActionUtil;
import ru.nuzmsh.util.date.AgeUtil;
import ru.nuzmsh.web.struts.BaseAction;

public class PsychCareCardByAreaAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		//String typePat =ActionUtil.updateParameter("PsychCareCardByArea","typePatient","3", aRequest) ;
		//ActionUtil.updateParameter("PsychCareCardByArea","period","2", aRequest) ;
		String typeDate = ActionUtil.updateParameter("PsychCareCardByArea","typeDate","2", aRequest) ;
		//String typeFirst = ActionUtil.updateParameter("PsychCareCardByArea","typeFirst","1", aRequest) ;
		String typeInv = ActionUtil.updateParameter("PsychCareCardByArea","typeInv","1", aRequest) ;
		String typeAge = ActionUtil.updateParameter("PsychCareCardByArea","typeAge","2", aRequest) ;
		String typeSuicide = ActionUtil.updateParameter("PsychCareCardByArea","typeSuicide","1", aRequest) ;
		String typeCare = ActionUtil.updateParameter("PsychCareCardByArea","typeCare","1", aRequest) ;
		AreaReportForm form = (AreaReportForm)aForm ;
		if (form.getAmbulatoryCare()!=null && form.getAmbulatoryCare()>Long.valueOf(0)
				&& form.getLpuArea()!=null && form.getLpuArea()>Long.valueOf(0)) {
			aRequest.setAttribute("lpuArea", form.getLpuArea()) ;
		} else {
			aRequest.setAttribute("lpuArea", null) ;
		}
		
		boolean isDt = (form!=null) ;
		
		if (form.getSex()!=null && form.getSex()>Long.valueOf(0)) {
			aRequest.setAttribute("sexT", new StringBuilder().append(" and p.sex_id='").append(form.getSex()).append("'").toString()) ;
			aRequest.setAttribute("sexInfo", " по полу") ;
		}
		if (typeAge.equals("1")) {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
			Integer ageTo = form.getAgeTo() ;
			if (ageTo.equals(Integer.valueOf(0))&& ageTo<form.getAgeFrom()) ageTo = null ; 
			if (form.getAgeFrom()!=null) {
				Calendar cal = Calendar.getInstance() ;
				
				cal.add(Calendar.YEAR, form.getAgeFrom().intValue()*(-1)) ;
				aRequest.setAttribute("ageFrom", " and p.birthday < to_date('"+format.format(cal.getTime())+"','dd.mm.yyyy') ") ;
				
			}
			if (ageTo!=null) {
				Calendar cal = Calendar.getInstance() ;
				
				cal.add(Calendar.YEAR, (ageTo.intValue()*(-1)-1)) ;
				aRequest.setAttribute("ageTo", " and p.birthday >=to_date('"+format.format(cal.getTime())+"','dd.mm.yyyy') ") ;
				
			}
			aRequest.setAttribute("ageInfo", "возраст: "
					+(form.getAgeFrom()!=null?form.getAgeFrom():"?")
					+"-"
					+(ageTo!=null?ageTo:"?" )
			) ;
		}
		String dateEnd = form.getDateEnd() ;
		if (form.getDateEnd()==null || form.getDateEnd().equals("")) {
			dateEnd = form.getDateBegin() ;
		}
		dateEnd = "to_date('"+dateEnd+"','dd.mm.yyyy')" ;
		String dateBegin = "to_date('"+form.getDateBegin()+"','dd.mm.yyyy')" ;
		
		String careDate = null ;
		String careInfo = null ;
		if ((form.getGroup()==null||form.getGroup().equals(Long.valueOf(0)))
					&&(form.getCompTreatment()==null||form.getCompTreatment().equals(Long.valueOf(0)))) {} 
		else if (typeCare.equals("2")) {
			careDate = " and  fldDateStart <="
					+dateEnd					
					+" and (fldDateFinish is null or fldDateFinish >= "
					+dateEnd
					+" )" ;
			careInfo=" (состоящие)"; 
		} else if (typeCare.equals("3")) {
			careDate = " and  fldDateStart between "
					+dateBegin+" and "
					+dateEnd+" " ;
			careInfo=" (взятые)" ;
		} else if (typeCare.equals("4")) {
			careDate = " and  fldDateFinish between "
					+dateBegin+" and "
					+dateEnd+" " ;
			careInfo=" (снятые)" ;
		}
		
		
		
		if (typeSuicide.equals("2")) {
			StringBuilder sui  ;
			sui = new StringBuilder().append(" and sui.fulfilmentDate between ")
			.append(dateBegin).append(" and ")
			.append(dateEnd).append(" ") ;
			if (form.getNatureSuicide()!=null && form.getNatureSuicide()>Long.valueOf(0)) {
				sui.append(" and sui.nature_id='").append(form.getNatureSuicide()).append("'") ;
			}
			aRequest.setAttribute("suicide", sui.toString());
			aRequest.setAttribute("suicideInfo", " суицид ") ;
		} else if (typeSuicide.equals("3")) {
			StringBuilder sui  ;
			sui = new StringBuilder().append(" and sui.fulfilmentDate between ")
					.append(dateBegin).append(" and ")
					.append(dateEnd).append(" and sui.isFinished='1' ") ;
			if (form.getNatureSuicide()!=null && form.getNatureSuicide()>Long.valueOf(0)) {
				sui.append(" and sui.nature_id='").append(form.getNatureSuicide()).append("'") ;
			}
			aRequest.setAttribute("suicide", sui.toString());
			aRequest.setAttribute("suicideInfo", " суицид завершенный ") ;
		}

		if (typeDate.equals("1")) {
			if(isDt) {
				aRequest.setAttribute("dateT"," and  area.startDate<="
						+dateEnd					
						+" and (area.finishDate is null or area.finishDate >= "
						+dateEnd
						+" ) and (area.transferDate is null or area.transferDate >= "
						+dateEnd
						+" )") ;
			} 
			aRequest.setAttribute("dateInfo", "Поиск по состоящим") ;
		} else if (typeDate.equals("2")) {
			StringBuilder dateT = new StringBuilder() ;
			dateT.append(" and (area.finishDate between ")
					.append(dateBegin).append(" and ").append(dateEnd)
					.append(" or area.transferDate between ")
					.append(dateBegin).append(" and ").append(dateEnd).append(") ");

			aRequest.setAttribute("dateT",dateT.toString()) ;
			aRequest.setAttribute("dateInfo", "Поиск по снятым и переведенным") ;
		} else if (typeDate.equals("3")) {
			StringBuilder dateT = new StringBuilder() ;
			
			if(isDt) {
				dateT.append(" and area.startDate between ")
						.append(dateBegin).append(" and ")
						.append(dateEnd).append("") ;
				if (form.getReasonBegin()!=null && form.getReasonBegin()>Long.valueOf(0)) {
					dateT.append(" and area.observationReason_id='")
						.append(form.getReasonBegin()).append("'") ;
				}
				aRequest.setAttribute("dateT",dateT.toString()) ;
				aRequest.setAttribute("dateInfo", "Поиск по взятым на учет") ;
			}
		} else if (typeDate.equals("4")) {
			StringBuilder dateT = new StringBuilder() ;
			dateT.append(" and (area.finishDate between ")
					.append(dateBegin).append(" and ").append(dateEnd)
					.append(" or area.transferDate between ")
					.append(dateBegin).append(" and ").append(dateEnd).append(") and area.stikeOffReason_id is null ");

			aRequest.setAttribute("dateT",dateT.toString()) ;
			if (form.getReasonTransfer()!=null && form.getReasonTransfer()>Long.valueOf(0)) {
				dateT.append(" and area.transferReason_id='")
					.append(form.getReasonTransfer()).append("'") ;
			}
			aRequest.setAttribute("dateInfo", "Поиск по переведенным") ;
		} else if (typeDate.equals("5")) {
			StringBuilder dateT = new StringBuilder() ;
			dateT.append(" and (area.finishDate between ")
					.append(dateBegin).append(" and ").append(dateEnd)
					.append(" or area.transferDate between ")
					.append(dateBegin).append(" and ").append(dateEnd).append(") and area.transferReason_id is null ");

			if (form.getReasonEnd()!=null && form.getReasonEnd()>Long.valueOf(0)) {
				dateT.append(" and area.stikeOffReason_id='")
					.append(form.getReasonEnd()).append("'") ;
			}
			aRequest.setAttribute("dateT",dateT.toString()) ;
			aRequest.setAttribute("dateInfo", "Поиск по снятым") ;
		}
		
		if (typeInv.equals("1")) {
			if(isDt) {
				aRequest.setAttribute("typeI"," ") ;
			} 
			aRequest.setAttribute("typeInvInfo", " ") ;
		//} else if (typeInv.equals("2")) {
		//	aRequest.setAttribute("typeI"," and  (select count(*) from Invalidity inv where inv.patient_id =pcc.patient_id and inv.dateFrom<area.startDate )=0 ") ;
		//	aRequest.setAttribute("typeInvInfo", " Инвалидность: нет") ;
		} else if (typeInv.equals("2")) {
			aRequest.setAttribute("typeI"," and p.incapable='1' ") ;
			aRequest.setAttribute("typeInvInfo", " недееспособные ") ;
		} else {
		
			StringBuilder str = new StringBuilder() ;
			String str1 = "" ;
			if (form.getGroupInv()!=null &&form.getGroupInv()>Long.valueOf(0)) {
				str.append(" and inv.group_id=").append(form.getGroupInv()) ;
				str1 = new StringBuilder().append(" группа " ).append(form.getGroupInv()).toString() ;
			}
			if (typeInv.equals("4")) {				
				str.append(" and inv.dateFrom between ").append(dateBegin).append(" and ").append(dateEnd) ;
				str1=str1+"первичные" ;
			} 
			if (typeInv.equals("5")) {
				str.append(" and inv.incapable='1' ") ;
				str1 = str1+"недееспособные" ;
			}
			aRequest.setAttribute("typeI",
					//" and  inv.dateFrom<area.startDate "
					//+
					" and (inv.withoutExam='1' or inv.nextRevisionDate>=coalesce(area.finishDate,area.transferDate,"+dateBegin+")) "+str+" ") ;
			aRequest.setAttribute("typeInvInfo", " инвалиды "+str1) ;
		}
		if (form.getCompTreatment()!=null&&
				form.getCompTreatment()>(Long.valueOf(0)) && careDate!=null) {
			StringBuilder compTreat = new StringBuilder() ;
			compTreat.append(careDate.replaceAll("fldDateStart", "ct.decisionDate").replaceAll("fldDateFinish", "ct.dateReplace")).append(" and ct.kind_id='").append(form.getCompTreatment()).append("'") ;
			aRequest.setAttribute("compTreatName", "принудительное лечение: "+form.getCompTreatment()+careInfo) ;
			aRequest.setAttribute("compTreat", compTreat.toString()) ;
		} else {
			aRequest.setAttribute("compTreatName", "") ;
			aRequest.setAttribute("compTreat", "") ;
		}
		
		aRequest.setAttribute("group", "") ;
		//System.out.println(careDate.replaceAll("fldDateStart", "ct.decisionDate").replaceAll("fldDateFinish", "ct.dateReplace")) ;
		//System.out.println(careDate.replaceAll("fldDateFinish", "ct.dateReplace")) ;
		//System.out.println(careDate.replaceAll("fldDateStart", "ct.decisionDate")) ;
		if (form.getAmbulatoryCare()!=null &&form.getAmbulatoryCare()>Long.valueOf(0)){
			StringBuilder gr = new StringBuilder() ;
			//aRequest.setAttribute("lpo", "left join PsychiaticObservation po on po.careCard_id=pcc.id") ;
			gr.append(" and po.startDate between (cast(area.startDate as integer)-1) and ifnull(area.finishDate,cast(CURRENT_DATE as integer),(cast(area.finishDate as integer)-1))  ") ;
			gr.append(" and po.ambulatoryCare_id = ").append(form.getAmbulatoryCare()) ;
			if (form.getGroup()!=null &&!form.getGroup().equals(Long.valueOf(0))&&careDate!=null) {
				gr.append(careDate.replaceAll("fldDateStart", "po1.startDate").replaceAll("fldDateFinish", "po1.finishDate")+" and po1.dispensaryGroup_id = ").append(form.getGroup());

				aRequest.setAttribute("groupDopJoin"," left join PsychiaticObservation po1 on po1.careCard_id=pcc.id"); 
				aRequest.setAttribute("groupDopSql"," and po1.lpuAreaPsychCareCard_id is null") ;
				} else if(typeDate.equals("3")) {
				//gr.append(" and po.dispensaryGroup_id is null ");
			

			}
			aRequest.setAttribute("group", gr.toString()) ;
		}

		
		return aMapping.findForward("success");
	}
}