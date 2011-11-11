package ru.ecom.mis.web.action.patient;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.web.action.psych.AreaReportForm;
import ru.ecom.mis.web.action.util.ActionUtil;
import ru.nuzmsh.web.struts.BaseAction;

public class PsychCareCardByAreaAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		//String typePat =ActionUtil.updateParameter("PsychCareCardByArea","typePatient","3", aRequest) ;
		ActionUtil.updateParameter("PsychCareCardByArea","period","2", aRequest) ;
		String typeDate = ActionUtil.updateParameter("PsychCareCardByArea","typeDate","2", aRequest) ;
		String typeFirst = ActionUtil.updateParameter("PsychCareCardByArea","typeFirst","1", aRequest) ;
		String typeInv = ActionUtil.updateParameter("PsychCareCardByArea","typeInv","1", aRequest) ;
		AreaReportForm form = (AreaReportForm)aForm ;
		
		boolean isDt = (form!=null) ;
		/*
		if (typePat.equals("2")) {
			aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)>0") ;
			aRequest.setAttribute("infoTypePat", "Поиск по иногородним") ;
		} else if (typePat.equals("1")){
			aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
			aRequest.setAttribute("infoTypePat", "Поиск по региональным") ;
		} else {
			aRequest.setAttribute("add", "") ;
			aRequest.setAttribute("infoTypePat", "Поиск по всем") ;
		}
		*/
		//Date datEnd = DateFormat.parseDate(form.getDateEnd(),"yyyy-MM-dd") ;
		//Calendar cal = Calendar.getInstance() ;
		//cal.setTime(datEnd) ;
		//cal.add(Calendar.DATE, -1) ;
		
		if (typeDate.equals("1")) {
			if(isDt) {
				aRequest.setAttribute("dateT"," and  area.startDate<='"
						+form.getDateEnd()					
						+"' and (area.finishDate is null or area.finishDate >= '"
						+form.getDateEnd()
						+"' )") ;
			} 
			aRequest.setAttribute("dateInfo", "Поиск по состоящим") ;
		} else if (typeDate.equals("2")) {
			if(isDt)aRequest.setAttribute("dateT"," and area.finishDate between '"
					+form.getDateBegin()
					+"' and '"
					+form.getDateEnd()
					+"' ") ;
			aRequest.setAttribute("dateInfo", "Поиск по выбывшим") ;
		} else {
			if(isDt)aRequest.setAttribute("dateT"," and area.startDate between '"
					+form.getDateBegin()
					+"' and '"
					+form.getDateEnd()
					+"'") ;
			aRequest.setAttribute("dateInfo", "Поиск по поступившим") ;
		}
		
		if (typeFirst.equals("1")) {
			if(isDt) {
				aRequest.setAttribute("dateF"," ") ;
			} 
			aRequest.setAttribute("dateFInfo", " ") ;
		} else if (typeFirst.equals("2")) {
			aRequest.setAttribute("dateF"," and  (select count(*) from LpuAreaPsychCareCard l1 where l1.careCard_id =pcc.id and l1.startDate<area.startDate)>0 ") ;
			aRequest.setAttribute("dateFInfo", " Впервые в жизни взятые на учет: нет") ;
		} else {
			aRequest.setAttribute("dateF"," and  (select count(*) from LpuAreaPsychCareCard l1 where l1.careCard_id =pcc.id and l1.startDate<area.startDate)=0 ") ;
			aRequest.setAttribute("dateFInfo", " Впервые в жизни взятые на учет: да") ;
		}
		if (typeInv.equals("1")) {
			if(isDt) {
				aRequest.setAttribute("typeI"," ") ;
			} 
			aRequest.setAttribute("typeInvInfo", " ") ;
		} else if (typeInv.equals("2")) {
			aRequest.setAttribute("typeI"," and  (select count(*) from Invalidity inv where inv.patient_id =pcc.patient_id and inv.dateFrom<area.startDate )=0 ") ;
			aRequest.setAttribute("typeInvInfo", " Инвалидность: нет") ;
		} else {
			StringBuilder str = new StringBuilder() ;
			String str1 = "" ;
			if (form.getGroupInv()!=null &&form.getGroupInv()>0) {
				str.append(" and inv.group_id=").append(form.getGroupInv()) ;
				str1 = new StringBuilder().append(" группа " ).append(form.getGroupInv()).toString() ;
			}
			if (typeInv.equals("4")) {				
				str.append(" and inv.primary=1") ;
			} else if (typeInv.equals("5")) {
				str.append(" and (inv.primary is null or inv.primary=0)") ;
			}
			aRequest.setAttribute("typeI"," and  (select count(*) from Invalidity inv where inv.patient_id =pcc.patient_id and inv.dateFrom<area.startDate "+str+")>0 ") ;
			aRequest.setAttribute("typeInvInfo", " Инвалидность: есть "+str1) ;
		}
		if (form.getCompTreatment()!=null&&
				!form.getCompTreatment().equals(Long.valueOf(0))) {
			StringBuilder compTreat = new StringBuilder() ;
			compTreat.append(" and (ct.decisionDate <=isnull(area.finishDate,current_date) or ct.dateReplace>=isnull(area.startDate,current_date)) and ct.kind_id='").append(form.getCompTreatment()).append("'") ;
			aRequest.setAttribute("compTreatName", "принудительное лечение: "+form.getCompTreatment()) ;
			aRequest.setAttribute("compTreat", compTreat.toString()) ;
		} else {
			aRequest.setAttribute("compTreatName", "") ;
			aRequest.setAttribute("compTreat", "") ;
		}
		
		aRequest.setAttribute("group", "") ;
		aRequest.setAttribute("lpo", "") ;
		if (form.getAmbulatoryCare()!=null &&!form.getAmbulatoryCare().equals(Long.valueOf(0))){
			SimpleDateFormat FORMAT_JDBC = new SimpleDateFormat("yyyy-MM-dd");
			StringBuilder gr = new StringBuilder() ;
			aRequest.setAttribute("lpo", "left join PsychiaticObservation po on po.careCard_id=pcc.id") ;
			gr.append(" and po.startDate between (cast(area.startDate as integer)-1) and cast(COALESCE(area.finishDate,CURRENT_DATE) as integer)  ") ;
			gr.append(" and po.ambulatoryCare_id = ").append(form.getAmbulatoryCare()) ;
			if (form.getGroup()!=null &&!form.getGroup().equals(Long.valueOf(0))) {
				gr.append(" and po.dispensaryGroup_id = ").append(form.getGroup());
			}
			aRequest.setAttribute("group", gr.toString()) ;
		}

		
		return aMapping.findForward("success");
	}
}