<%@page import="java.text.SimpleDateFormat"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="ru.ecom.poly.web.action.ticket.JournalBySpecialistForm"%>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Просмотр данных по Форме 039/у-02 </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="report039"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
  	String typeAge =ActionUtil.updateParameter("Form039Action","typeAge","2", request) ;
  	String typeReestr =ActionUtil.updateParameter("Form039Action","typeReestr","2", request) ;
  	String typeGroup =ActionUtil.updateParameter("Form039Action","typeGroup","2", request) ;
	String typeView =ActionUtil.updateParameter("Form039Action","typeView","1", request) ;
	String typeAgeWork =ActionUtil.updateParameter("Form039Action","typeAgeWork","2", request) ;
	String typeDtype =ActionUtil.updateParameter("Form039Action","typeDtype","3", request) ;
	String typeDate =ActionUtil.updateParameter("Form039Action","typeDate","2", request) ;
	String typeEmergency =ActionUtil.updateParameter("Form039Action","typeEmergency","3", request) ;
	String typeDiag =ActionUtil.updateParameter("Form039Action","typeDiag","2", request) ;

  %>
    <msh:form action="/visit_f039_list.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <input type="hidden" name="id" id="id"/>
    <input type="hidden" name="ticketIs" id="ticketIs" value="0"/>
    <input type="hidden" name="typeReestr" id="typeReestr" value="2"/>
    <input type="hidden" name="person" id="person" value="${param.person}"/>
    <%if (request.getParameter("short")==null ||request.getParameter("short").equals(""))  {%>
    <msh:panel>
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="9" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        	<msh:textField property="beginDate" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        	<msh:textField property="finishDate" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="workFunction" vocName="vocWorkFunction" 
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="additionStatus" vocName="vocAdditionStatus" 
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="person" vocName="patient" 
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="specialist" vocName="workFunction" 
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="lpu" vocName="lpu"
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" vocName="vocServiceStream"
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="workPlaceType" vocName="vocWorkPlaceType"
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>        
        <msh:row>
        	<msh:autoComplete property="visitReason" label="Цель визита" vocName="vocReason"
        		horizontalFill="true" fieldColSpan="9" size="70"/>
        </msh:row>        
      <msh:row>
        	<msh:textField property="ageFrom" label="Возраст с" />
        	<msh:textField property="ageTo" label="по" />
        </msh:row>
        <msh:row>
	        <td class="label" title="Группировака (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировка по:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="1">  датам
	        </td>
	        
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="2"> ЛПУ
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="3"> врачам
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="4">  сотрудникам
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="5">  специальностям
	        </td>
        </msh:row>
        <msh:row>
        	<td></td>
	        <td  onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="6">Поток обслуж.  
	        </td>
	        <td colspan="2" onclick="this.childNodes[1].checked='checked';" style="text-align: left">
	        	<input type="radio" name="typeGroup" value="7">Место обслуж.  
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="8">Соц. статус  
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="9">по месяцам  
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeGroup" value="10">по доп.статусу  
	        </td>
        </msh:row>
        <msh:row>
	        <td class="label" title="Трудоспособный возраст считать с (typeAgeWork)" colspan="1"><label for="typeAgeWorkName" id="typeAgeWorkLabel">Трудоспособный возраст с:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeAgeWork" value="1">  16 лет
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeAgeWork" value="2" >  18 лет
	        </td>

        </msh:row>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1">  039 форма
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="2" >  039 по возрастам - заб.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="3">  039 cons
	        </td>

        </msh:row>
        <msh:row>
	        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="4">  30 форма
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="5">  30 - ст. трудосп.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="6">  62 форма
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="7">  свод по пациентам
	        </td>

        </msh:row>
        <msh:row>
	        <td class="label" title="Дата (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Дата:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="1">  закрытия СПО
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="2">  посещения
	        </td>

        </msh:row>
        <msh:row>
	        <td class="label" title="Показания (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="1">  Экстренные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeEmergency" value="2" >  Плановые
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="3">  Все
	        </td>
	    </msh:row>
        <msh:row>
	        <td class="label" title="Отображать диагностические службы (typeDiag)" colspan="1"><label for="typeDiagName" id="typeEmergencyLabel">Диагностические службы:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDiag" value="1">  только их
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDiag" value="2" >  не отображать
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDiag" value="3">  все данные
	        </td>
	    </msh:row>
        <msh:row>
	        <td class="label" title="База (typeDtype)" colspan="1"><label for="typeDtypeName" id="typeDtypeLabel">Версия:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDtype" value="1">  Визит.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeDtype" value="2" >  Талон.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDtype" value="3">  Все
	        </td>
	        <td colspan="2">
	        	<input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;visit_f039_list.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
	        	
	        </td>
        </msh:row>
        <msh:row>
        <td colspan="5" class="buttons">
		</td>
        
        </msh:row>

    </msh:panel>
    <%} %>
    </msh:form>
    
    <%
    	if (request.getParameter("beginDate")!=null  
    			&& !request.getParameter("beginDate").equals("") 
    			&& request.getParameter("finishDate")!=null
    			&& !request.getParameter("finishDate").equals("")) {
    		request.setAttribute("isReportBase", ActionUtil.isReportBase(request.getParameter("beginDate"),request.getParameter("finishDate"),request));
    		ActionUtil.setParameterFilterSql("workFunction","wf.workFunction_id", request) ;
    		ActionUtil.setParameterFilterSql("specialist","smo.workFunctionExecute_id", request) ;
    		ActionUtil.setParameterFilterSql("lpu","w.lpu_id", request) ;
    		ActionUtil.setParameterFilterSql("serviceStream","smo.serviceStream_id", request) ;
    		ActionUtil.setParameterFilterSql("workPlaceType","smo.workPlaceType_id", request) ;
    		ActionUtil.setParameterFilterSql("socialStatus","pvss.id", request) ;
    		ActionUtil.setParameterFilterSql("additionStatus","vas.id", request) ;
    		ActionUtil.setParameterFilterSql("person","wp.id", request) ;
    		ActionUtil.setParameterFilterSql("visitReason","smo.visitReason_id", request) ;
    		String age =null;
    		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
			String ageTo = request.getParameter("ageTo") ;
			String ageFrom = request.getParameter("ageFrom") ;
			if (ageFrom!=null && !ageFrom.equals("")) {
				if (ageTo!=null && !ageTo.equals("")) {
					age=" between "+ageFrom+" and "+ageTo ;
				} else {
					age=">="+ageFrom ;
				}
			} else if (ageTo!=null && !ageTo.equals("")) {
				age="<="+ageTo ;
			}
			if (age!=null) {
				StringBuilder as = new StringBuilder() ;
				as.append(" and cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)") ;
				as.append(" +(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)") ;
				as.append(" +(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)") ;
				as.append(" then -1 else 0 end) ");
				as.append(age) ;
				request.setAttribute("ageSql", as.toString()) ;
			}
    		if (typeDtype.equals("1")) {
    			request.setAttribute("dtypeSql", "smo.dtype='Visit'") ;
    		} else if (typeDtype.equals("2")) {
    			request.setAttribute("dtypeSql", "smo.dtype='ShortMedCase'") ;
    		} else {
    			request.setAttribute("dtypeSql", "(smo.dtype='ShortMedCase' or smo.dtype='Visit')") ;
    		}
    		if (typeDiag.equals("1")) {
    			request.setAttribute("is039Sql", " and vwf.isNo039='1'") ;
    		} else if (typeDiag.equals("2")) {
    			request.setAttribute("is039Sql", " and (vwf.isNo039='0' or vwf.isNo039 is null)") ;
    		} 
    		if (typeAgeWork.equals("1")) {
    			request.setAttribute("typeAgeWorkId", "16") ;
    		} else {
    			request.setAttribute("typeAgeWorkId", "18") ;
    		}
    		if (typeEmergency.equals("1")) {
    			request.setAttribute("emergencySql", " and smo.emergency='1'") ;
    		} else if (typeEmergency.equals("2")) {
    			request.setAttribute("emergencySql", " and (smo.emergency='0' or smo.emergency is null)") ;
    		}
    		if (typeDate.equals("1")) {
    			request.setAttribute("dateSql", "spo.dateFinish") ;
    		} else {
    			request.setAttribute("dateSql", "smo.dateStart") ;
    		}
    		if (typeGroup.equals("1")) {
    			request.setAttribute("printPrefix", "_date");
    			// Группировка по дате
    			if (typeDate.equals("2")) {
	       			request.setAttribute("groupSql", "to_char(smo.dateStart,'dd.mm.yyyy')") ;
	       			request.setAttribute("groupSqlId", "'&beginDate='||to_char(smo.dateStart,'dd.mm.yyyy')||'&finishDate='||to_char(smo.dateStart,'dd.mm.yyyy')") ;
	       			request.setAttribute("groupName", "Дата посещения") ;
	       			request.setAttribute("groupGroup", "smo.dateStart") ;
	       			request.setAttribute("groupOrder", "smo.dateStart") ;
    			} else {
	       			request.setAttribute("groupSql", "to_char(spo.dateFinish,'dd.mm.yyyy')") ;
	       			request.setAttribute("groupSqlId", "'&beginDate='||to_char(spo.dateFinish,'dd.mm.yyyy')||'&finishDate='||to_char(spo.dateFinish,'dd.mm.yyyy')") ;
	       			request.setAttribute("groupName", "Дата закрытия СПО") ;
	       			request.setAttribute("groupGroup", "spo.dateFinish") ;
	       			request.setAttribute("groupOrder", "spo.dateFinish") ;
    			}
    		} else if (typeGroup.equals("2")) {
    			// Группировка по ЛПУ
       			request.setAttribute("groupSql", "lpu.name") ;
       			request.setAttribute("groupSqlId", "'&lpu='||w.lpu_id") ;
       			request.setAttribute("groupName", "ЛПУ") ;
       			request.setAttribute("groupGroup", "w.lpu_id,lpu.name") ;
       			request.setAttribute("groupOrder", "lpu.name") ;
    		} else if (typeGroup.equals("4")) {
    			// Группировка по врачам 
       			request.setAttribute("groupSql", "wp.lastname||' '||wp.firstname||' '||wp.middlename") ;
       			request.setAttribute("groupSqlId", "'&person='||wp.id") ;
       			request.setAttribute("groupName", "Врач") ;
       			request.setAttribute("groupGroup", "wp.id,wp.lastname,wp.firstname,wp.middlename") ;
       			request.setAttribute("groupOrder", "wp.lastname,wp.firstname,wp.middlename") ;
    		} else if (typeGroup.equals("3")) {
    			// Группировка по сотрудникам 
       			request.setAttribute("groupSql", "vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename") ;
       			request.setAttribute("groupSqlId", "'&specialist='||wf.id") ;
       			request.setAttribute("groupName", "Сотрудник") ;
       			request.setAttribute("groupGroup", "wf.id,vwf.name,wp.lastname,wp.firstname,wp.middlename") ;
       			request.setAttribute("groupOrder", "vwf.name,wp.lastname,wp.firstname,wp.middlename") ;
    		} else if (typeGroup.equals("5")) {
    			// Группировка по специальностям
       			request.setAttribute("groupSql", "vwf.name") ;
       			request.setAttribute("groupSqlId", "'&workFunction='||vwf.id") ;
       			request.setAttribute("groupName", "Рабочая функция") ;
       			request.setAttribute("groupGroup", "vwf.id,vwf.name") ;
       			request.setAttribute("groupOrder", "vwf.name") ;
    		} else if (typeGroup.equals("6")) {
    			// Группировка по потоку обслуживания
       			request.setAttribute("groupSql", "vss.name") ;
       			request.setAttribute("groupSqlId", "'&serviceStream='||smo.serviceStream_id") ;
       			request.setAttribute("groupName", "Поток обслуживания") ;
       			request.setAttribute("groupGroup", "smo.serviceStream_id,vss.name") ;
       			request.setAttribute("groupOrder", "vss.name") ;
    		} else if (typeGroup.equals("7")) {
    			// Группировка по месту обслуживания
       			request.setAttribute("groupSql", "vwpt.name") ;
       			request.setAttribute("groupSqlId", "'&workPlaceType='||smo.workPlaceType_id") ;
       			request.setAttribute("groupName", "Место обслуживания") ;
       			request.setAttribute("groupGroup", "smo.workPlaceType_id,vwpt.name") ;
       			request.setAttribute("groupOrder", "vwpt.name") ;
    		} else if (typeGroup.equals("8")) {
    			// Группировка по социальному статусу
       			request.setAttribute("groupSql", "pvss.name") ;
       			request.setAttribute("groupSqlId", "'&socialStatus='||pvss.id") ;
       			request.setAttribute("groupName", "Социальный статус") ;
       			request.setAttribute("groupGroup", "pvss.id,pvss.name") ;
       			request.setAttribute("groupOrder", "pvss.name") ;
    		} else if (typeGroup.equals("9")) {
    			// Группировка по месяцам
       			request.setAttribute("groupSql", "to_char(smo.dateStart,'mm.yyyy')") ;
       			request.setAttribute("groupSqlId", "''") ;
       			request.setAttribute("groupName", "Период") ;
       			request.setAttribute("groupGroup", "to_char(smo.dateStart,'mm.yyyy')") ;
       			request.setAttribute("groupOrder", "to_char(smo.dateStart,'mm.yyyy')") ;
    		} else if (typeGroup.equals("10")) {
    			// Группировка по доп.статусу
       			request.setAttribute("groupSql", "vas.name") ;
       			request.setAttribute("groupSqlId", "'&additionStatus='||vas.id") ;
       			request.setAttribute("groupName", "Доп.статус") ;
       			request.setAttribute("groupGroup", "vas.id,vas.name") ;
       			request.setAttribute("groupOrder", "vas.name") ;
    		}
    		if (typeReestr!=null && (typeReestr.equals("1"))) {
    			
    			String homeVisit = request.getParameter("homeAge");
    			String pay = request.getParameter("pay");
    			StringBuilder sqlAppend = new StringBuilder();
    			if (pay!=null) {
    				if (pay.equals("DMS")){sqlAppend.append(" and vss.code='PRIVATEINSURANCE'");} 
    				else if (pay.equals("Budget")) {sqlAppend.append(" and vss.code='BUDGET'");}
    				else if (pay.equals("Charge")) {sqlAppend.append(" and vss.code='CHARGED'");}
    				else if (pay.equals("OMS")) {sqlAppend.append(" and vss.code='OBLIGATORYINSURANCE'");}
    			}
    			if (homeVisit!=null) {
    				if (homeVisit.equals("-17")) {sqlAppend.append(
        					"and (vr.code='ILLNESS' or vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') "+
        		    				"and (cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int) "+
        		    				"+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int) "+
        		    				"+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0) "+
        		    				"then -1 else 0 end)<18)" );}
    				else if (homeVisit.equals("0-1")) {sqlAppend.append(
        					"and (vr.code='ILLNESS' or vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') "+
        		    				"and (cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int) "+
        		    				"+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int) "+
        		    				"+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0) "+
        		    				"then -1 else 0 end)<2)" );}
    				else if (homeVisit.equals("60")) {sqlAppend.append(
        					"and (vr.code='ILLNESS' or vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') "+
        		    				"and (cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int) "+
        		    				"+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int) "+
        		    				"+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0) "+
        		    				"then -1 else 0 end)>=60)" );}
    				else if (homeVisit.equals("prof-17")) {sqlAppend.append(
        					"and vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') "+
        		    				"and (cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int) "+
				    				"+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int) "+
				    				"+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0) "+
				    				"then -1 else 0 end)<18)" );}
    				else if (homeVisit.equals("prof0-1")) {sqlAppend.append(
    					"and vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') "+
	    				"and (cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int) "+
	    				"+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int) "+
	    				"+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0) "+
	    				"then -1 else 0 end)<2)" );}
    				else if (homeVisit.equals("homeIll")) {sqlAppend.append(
    					"and (vr.code='ILLNESS' or vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') ");}
    				else if (homeVisit.equals("homeAll")) {sqlAppend.append(
    					"and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') ");}
    				else if (homeVisit.equals("prof")) {sqlAppend.append(
    					"and vr.code='PROFYLACTIC'  and vwpt.code='POLYCLINIC' ");}
    				else if (homeVisit.equals("polIll60")) {
    					sqlAppend.append("and (vr.code='ILLNESS' or vr.code='CONSULTATION') and vwpt.code='POLYCLINIC' "+
    							"and (cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)"+
    							"+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)"+
    							"+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) "+
    							"then -1 else 0 end)<0)then -1 else 0 end)>=60)");}
					else if (homeVisit.equals("polIll-17")) {
						sqlAppend.append("and (vr.code='ILLNESS' or vr.code='CONSULTATION')  and vwpt.code='POLYCLINIC' "+
    							"and (cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)"+
    							"+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)"+
    							"+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) "+
    							"then -1 else 0 end)<0) then -1 else 0 end)<18)");}
					else if (homeVisit.equals("polIllAll")) {
						sqlAppend.append(" and (vr.code='ILLNESS' or vr.code='CONSULTATION') and vwpt.code='POLYCLINIC'");}
    				else if (homeVisit.equals("pol60")) {
    					sqlAppend.append(" and vwpt.code='POLYCLINIC' and "+
    							"(cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)"+
    							"+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)"+
    							"+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) "+
    							"then -1 else 0 end)<0)then -1 else 0 end)>=60)");}
    				else if (homeVisit.equals("pol-17")) {
    					sqlAppend.append(" and vwpt.code='POLYCLINIC' and "+
    							"(cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)"+
    							"+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)"+
    							"+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) "+
    							"then -1 else 0 end)<0)then -1 else 0 end)<18)");}
    				else if (homeVisit.equals("polVil")) {
    					sqlAppend.append(" and vwpt.code='POLYCLINIC' and (ad1.addressIsVillage='1') ");}
    				else if (homeVisit.equals("polAll")) {
    					sqlAppend.append(" and vwpt.code='POLYCLINIC' ");}
    				}
    			request.setAttribute("appendSQL", sqlAppend.toString() );
    			
    			
    	%>
    
    <msh:section>
${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" maxResult="1500" name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select smo.id as name
,smo.dateStart as nameFld
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,p.birthday as birthday
,	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end) as age
,case when (ad1.addressisvillage='1') then 'сел' else null end as cntVil
,vr.name as vrname
,vwpt.name as vwptname 
,vss.name ||' '|| case when vss.code='HOSPITAL'or vss.code='OTHER' then 
coalesce(' - '||(select 
list(case when sls.deniedHospitalizating_id is null then slsml.name||' '||
to_char(sls.dateStart,'dd.mm.yyyy')||'-'||coalesce(to_char(sls.dateFinish,'dd.mm.yyyy'),
'по тек.мом.') else ' ОТКАЗ '||slsml.name ||' '||to_char(sls.dateStart,'dd.mm.yyyy') end)
from medcase sls 
left join mislpu slsml on slsml.id=sls.department_id
where sls.patient_id=smo.patient_id and sls.dtype='HospitalMedCase'
and (
sls.deniedHospitalizating_id is null and smo.datestart between sls.dateStart and coalesce(sls.datefinish,current_date)
or
sls.deniedHospitalizating_id is not null and smo.datestart between sls.dateStart and sls.datestart+1
)
),'нет данных') 
else '' end as vssname
,list(mkb.code) as mkblist,list(ms.code||' '||ms.name) as servecilist
,olpu.name as olpuname
,ovwf.name||' '||owp.lastname||' '||owp.firstname||' '||owp.middlename||' ('||owflpu.name||')' as owfinfo
,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename||' ('||lpu.name||')' as wfinfo
FROM MedCase smo  
left join MedCase spo on spo.id=smo.parent_id
LEFT JOIN Patient p ON p.id=smo.patient_id 
left join VocAdditionStatus vas on vas.id=p.additionStatus_id
LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId 
LEFT JOIN VocReason vr on vr.id=smo.visitReason_id 
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=smo.workPlaceType_id 
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id 
LEFT JOIN VocSocialStatus pvss on pvss.id=p.socialStatus_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id 
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id 
LEFT JOIN Worker w on w.id=wf.worker_id 
LEFT JOIN Patient wp on wp.id=w.person_id 
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id 
left join diagnosis diag on diag.medcase_id=smo.id
left join vocidc10 mkb on mkb.id=diag.idc10_id
left join medcase mssmo on mssmo.parent_id=smo.id and mssmo.dtype='ServiceMedCase'
left join medservice ms on ms.id=mssmo.medservice_id
left join mislpu olpu on olpu.id=smo.orderLpu_id
LEFT JOIN WorkFunction owf on owf.id=smo.orderWorkFunction_id 
LEFT JOIN VocWorkFunction ovwf on ovwf.id=owf.workFunction_id 
LEFT JOIN Worker ow on ow.id=owf.worker_id 
left join mislpu owflpu on owflpu.id=ow.lpu_id
LEFT JOIN Patient owp on owp.id=ow.person_id 

WHERE  ${dtypeSql} 
and ${dateSql} BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')  
${specialistSql} ${is039Sql} ${workFunctionSql} ${lpuSql} ${serviceStreamSql} ${visitReasonSql} ${workPlaceTypeSql} ${additionStatusSql} ${socialStatusSql}
${personSql}  and smo.dateStart is not null ${emergencySql} ${ageSql} ${appendSQL}
group by ${groupOrder},smo.id,smo.dateStart,p.lastname,p.middlename,p.firstname,p.birthday,ad1.addressisvillage,vr.name,vwpt.name,vss.name
,olpu.name,ovwf.name,owp.lastname,owp.firstname,owp.middlename,smo.patient_id,vss.code,owflpu.name
,vwf.name,wp.lastname,wp.firstname,wp.middlename,lpu.name
ORDER BY ${groupOrder},p.lastname,p.firstname,p.middlename
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
    <msh:sectionTitle>
        <form action="print-f039_reestr.do" method="post" target="_blank">
    Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_ticket_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
    <input type='hidden' name="m" id="m" value="printNativeQuery${printPrefix}">
    <input type='hidden' name="date1" id="date1" value="${beginDate}">
    <input type='hidden' name="date2" id="date2" value="${finishDate}">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>

    <msh:sectionContent>
        <msh:table
         name="journal_ticket" action="entitySubclassView-mis_medCase.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Дата посещения" property="2"/>            
            <msh:tableColumn columnName="ФИО пациента" property="3"/>
            <msh:tableColumn columnName="Дата рождения" property="4"/>
            <msh:tableColumn columnName="Возраст" property="5"/>
            <msh:tableColumn columnName="Житель" property="6"/>
            <msh:tableColumn columnName="цель визита" property="7"/>
            <msh:tableColumn columnName="место" property="8"/>
            <msh:tableColumn columnName="поток обсл." property="9"/>
            <msh:tableColumn columnName="диагноз" property="10"/>
            <msh:tableColumn columnName="услуга" property="11"/>
            <msh:tableColumn columnName="напр. ЛПУ" property="12"/>
            <msh:tableColumn columnName="напр. внутр." property="13"/>
            <msh:tableColumn columnName="специалист" property="14"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% } else {
    	if (typeView!=null && (typeView.equals("1"))) {
    
    	%>
    	
    <msh:section>
${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select
''||${groupSqlId}||${workFunctionSqlId}||${additionStatusSqlId}||${visitReasonSqlId}||${specialistSqlId}||${lpuSqlId}||${serviceStreamSqlId}||${workPlaceTypeSqlId}||${socialStatusSqlId}||'&beginDate=${beginDate}&finishDate=${finishDate}' as name
,${groupSql} as nameFld
,count(*) as cntAll
,count(case when (vwpt.code='POLYCLINIC') then 1 else null end) as cntAllPoly
,count(case when vwpt.code='POLYCLINIC' and (ad1.addressIsVillage='1') then 1 else null end) as cntVil
,count(case when vwpt.code='POLYCLINIC' and cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)<18 
	then 1 else null end) as cntAll17
,count(case when vwpt.code='POLYCLINIC' and 
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)>=60 then 1 else null end) as cntAll60
,count(case when (vr.code='ILLNESS' or vr.code='CONSULTATION') and vwpt.code='POLYCLINIC' then 1 else null end) as cntIllnessAll
,count(case when (vr.code='ILLNESS' or vr.code='CONSULTATION')  and vwpt.code='POLYCLINIC'
	and (
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)<18
	) then 1 else null end) as cntIllnes17
,count(case when (vr.code='ILLNESS' or vr.code='CONSULTATION')  and vwpt.code='POLYCLINIC' and 
	(
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>=60
	) then 1 else null end) as cntIllnes60
,count(case when vr.code='PROFYLACTIC'  and vwpt.code='POLYCLINIC' then 1 else null end) as cntProfAll
,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHomeAll
,count(case when (vr.code='ILLNESS' or vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntIllnesHomeAll
,count(case when (vr.code='ILLNESS' or vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<18
	) then 1 else null end) as cntIllnesHome17
,count(case when (vr.code='ILLNESS' or vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<2
	) then 1 else null end) as cntIllnesHome01
,count(case when (vr.code='ILLNESS' or vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>=60
	) then 1 else null end) as cntIllnesHome60
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<18
	) then 1 else null end) as cntProfHome17
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') 
	and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<2
	) then 1 else null end) as cntProfHome01
,count(case when (vss.code='OBLIGATORYINSURANCE') then 1 else null end) as cntOMC
,count(case when (vss.code='BUDGET') then 1 else null end) as cntBudget
,count(case when (vss.code='CHARGED') then 1 else null end) as cntCharged
,count(case when (vss.code='PRIVATEINSURANCE') then 1 else null end) as cntPrivateIns

FROM MedCase smo  
left join MedCase spo on spo.id=smo.parent_id
LEFT JOIN Patient p ON p.id=smo.patient_id 
left join VocAdditionStatus vas on vas.id=p.additionStatus_id

LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId 
LEFT JOIN VocReason vr on vr.id=smo.visitReason_id 
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=smo.workPlaceType_id 
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id 
LEFT JOIN VocSocialStatus pvss on pvss.id=p.socialStatus_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id 
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id 
LEFT JOIN Worker w on w.id=wf.worker_id 
LEFT JOIN Patient wp on wp.id=w.person_id 
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id 
WHERE  ${dtypeSql} 
and ${dateSql} BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')
${specialistSql} ${is039Sql} ${workFunctionSql} ${lpuSql} ${serviceStreamSql} ${visitReasonSql} ${workPlaceTypeSql} ${additionStatusSql} ${socialStatusSql}
${personSql}  and smo.dateStart is not null ${emergencySql} ${ageSql}
GROUP BY ${groupGroup} ORDER BY ${groupOrder}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" /> 
    <msh:sectionTitle>
    <form action="print-f039_stand${printPrefix}.do" method="post" target="_blank">
    Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_ticket_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
    <input type='hidden' name="m" id="m" value="printNativeQuery${printPrefix}">
    <input type='hidden' name="date1" id="date1" value="${beginDate}">
    <input type='hidden' name="date2" id="date2" value="${finishDate}">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table cellFunction="true"
         name="journal_ticket" action="visit_f039_list.do?&short=Short&typeReestr=1&typeView=${typeView}&typeDiag=${typeDiag}&ageFrom=${param.ageFrom}&ageTo=${param.ageTo}&typeDtype=${typeDtype}&typeEmergency=${typeEmergency}&typeDate=${typeDate}&typeGroup=${typeGroup}" 
         idField="1" noDataMessage="Не найдено">
         <msh:tableNotEmpty>
         	<tr>
         		<th></th>
         		<th></th>
         		<th colspan="4">Число посещений (в поликлинику)</th>
         		<th colspan="3">Из общего числа посещений в пол-ку сделано по поводу заболеваний</th>
         		<th></th>
          		<th colspan="7">Число посещений врачами на дому</th>
         		<th colspan="4">Число посещений по видам оплаты</th>
         	</tr>
         </msh:tableNotEmpty>  
            <msh:tableColumn columnName="${groupName}" property="2" addParam="&10=00" />            
            <msh:tableColumn isCalcAmount="true" columnName="Общее кол-во посещ." property="3"/>
            
            <msh:tableColumn isCalcAmount="true" columnName="Всего" property="4" addParam="&homeAge=polAll"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего с.ж." property="5" addParam="&homeAge=polVil"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего до 17 лет" property="6" addParam="&homeAge=pol-17"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего старше 60 лет" property="7" addParam="&homeAge=pol60"/>
            
            <msh:tableColumn isCalcAmount="true" columnName="Кол-во" property="8" addParam="&homeAge=polIllAll"/>
            <msh:tableColumn isCalcAmount="true" columnName="числе до 17 лет" property="9" addParam="&homeAge=polIll-17"/>
            <msh:tableColumn isCalcAmount="true" columnName="числе старше 60 лет" property="10" addParam="&homeAge=polIll60"/>
            
            <msh:tableColumn isCalcAmount="true" columnName="Проф." property="11" addParam="&homeAge=prof"/>
            
            <msh:tableColumn isCalcAmount="true" columnName="Всего" property="12" addParam="&homeAge=homeAll" />
            <msh:tableColumn isCalcAmount="true" columnName="по забол." property="13" addParam="&homeAge=homeIll" />
            <msh:tableColumn isCalcAmount="true" columnName="до 17 лет" property="14" addParam="&homeAge=-17"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-1(вкл) лет" property="15" addParam="&homeAge=0-1"/>
            <msh:tableColumn isCalcAmount="true" columnName="старше 60 лет" property="16" addParam="&homeAge=60"/>
            <msh:tableColumn isCalcAmount="true" columnName="проф до 17 лет" property="17" addParam="&homeAge=prof-17"/>
            <msh:tableColumn isCalcAmount="true" columnName="проф 0-1(вкл) лет" property="18" addParam="&homeAge=prof0-1"/>
            <msh:tableColumn isCalcAmount="true" columnName="ОМС" property="19" addParam="&pay=OMS"/>
            <msh:tableColumn isCalcAmount="true" columnName="бюджет" property="20" addParam="&pay=Budget"/>
            <msh:tableColumn isCalcAmount="true" columnName="платные" property="21" addParam="&pay=Charge"/>
            <msh:tableColumn isCalcAmount="true" columnName="ДМС" property="22" addParam="&pay=DMS"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	
    	<%
    } else if (typeView!=null && (typeView.equals("2"))) {
    	%>
    <msh:section>
${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="journal_ticket" nativeSql="
select
''||${groupSqlId}||${workFunctionSqlId}||${additionStatusSqlId}||${visitReasonSqlId}||${specialistSqlId}||${lpuSqlId}||${serviceStreamSqlId}||${workPlaceTypeSqlId}||${socialStatusSqlId}||'&beginDate=${beginDate}&finishDate=${finishDate}' as name
,${groupSql} as nameFld
,count(*) as cntAll
,count(case when vwpt.code='POLYCLINIC' then 1 else null end) as cntPAll
,count(case when vwpt.code='POLYCLINIC' and (ad1.addressIsVillage='1') then 1 else null end) as cntPVil

,count(case when (vr.code='ILLNESS') and vwpt.code='POLYCLINIC' then 1 else null end) as cntPIllnessAll
,count(case when (vr.code='ILLNESS')  and vwpt.code='POLYCLINIC'
	and (
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)<15
	) then 1 else null end) as cntPIllnes14
,count(case when (vr.code='ILLNESS')  and vwpt.code='POLYCLINIC'
	and (
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end) between 15 and 17
	) then 1 else null end) as cntPIllnes17
,count(case when (vr.code='ILLNESS')  and vwpt.code='POLYCLINIC' and 
	(
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>17
	) then 1 else null end) as cntPIllnes60



,count(case when (vr.code='CONSULTATION') and vwpt.code='POLYCLINIC' then 1 else null end) as cntPConsAll
,count(case when (vr.code='CONSULTATION')  and vwpt.code='POLYCLINIC'
	and (
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)<15
	) then 1 else null end) as cntPCons14
,count(case when (vr.code='CONSULTATION')  and vwpt.code='POLYCLINIC'
	and (
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end) between 15 and 17
	) then 1 else null end) as cntPCons17
,count(case when (vr.code='CONSULTATION')  and vwpt.code='POLYCLINIC' and 
	(
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>17
	) then 1 else null end) as cntPCons60

,count(case when vr.code='PROFYLACTIC' and vwpt.code='POLYCLINIC' and vwpt.code!='HOME' and vwpt.code!='HOMEACTIVE' then 1 else null end) as cntPProfAll

,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHomeAll


,count(case when (vr.code='ILLNESS') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHIllnessAll
,count(case when (vr.code='ILLNESS')  and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE')
	and (
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)<15
	) then 1 else null end) as cntHIllnes14
,count(case when (vr.code='ILLNESS')  and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE')
	and (
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end) between 15 and 17
	) then 1 else null end) as cntHIllnes17
,count(case when (vr.code='ILLNESS')  and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and 
	(
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>17
	) then 1 else null end) as cntHIllnes60



,count(case when (vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHConsAll
,count(case when (vr.code='CONSULTATION')  and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE')
	and (
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)<15
	) then 1 else null end) as cntHCons14
,count(case when (vr.code='CONSULTATION')  and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE')
	and (
	cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end) between 15 and 17
	) then 1 else null end) as cntHCons17
,count(case when (vr.code='CONSULTATION')  and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and 
	(
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>17
	) then 1 else null end) as cntHCons60

,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHProfAll

FROM MedCase smo  
left join MedCase spo on spo.id=smo.parent_id
LEFT JOIN Patient p ON p.id=smo.patient_id 
left join VocAdditionStatus vas on vas.id=p.additionStatus_id

LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId 
LEFT JOIN VocReason vr on vr.id=smo.visitReason_id 
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=smo.workPlaceType_id 
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id 
LEFT JOIN VocSocialStatus pvss on pvss.id=p.socialStatus_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id 
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id 
LEFT JOIN Worker w on w.id=wf.worker_id 
LEFT JOIN Patient wp on wp.id=w.person_id 
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id 
WHERE  ${dtypeSql} 
and ${dateSql} BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')  
${specialistSql} ${is039Sql} ${workFunctionSql} ${lpuSql} ${serviceStreamSql} ${visitReasonSql} ${workPlaceTypeSql} ${additionStatusSql} ${socialStatusSql}
${personSql}  and smo.dateStart is not null ${emergencySql} ${ageSql}
GROUP BY ${groupGroup} ORDER BY ${groupOrder}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" nameFldSql="journal_ticket_sql" /> 
    <msh:sectionTitle>
    <form action="print-f039_bis${printPrefix}.do" method="post" target="_blank">
    Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_ticket_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
    <input type='hidden' name="m" id="m" value="printNativeQuery${printPrefix}">
    <input type='hidden' name="date1" id="date1" value="${beginDate}">
    <input type='hidden' name="date2" id="date2" value="${finishDate}">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table
         name="journal_ticket" action="visit_f039_list.do?typeReestr=1&typeView=${typeView}&typeGroup=${typeGroup}&typeDiag=${typeDiag}&ageFrom=${param.ageFrom}&ageTo=${param.ageTo}&typeDtype=${typeDtype}&typeEmergency=${typeEmergency}&typeDate=${typeDate}" idField="1" noDataMessage="Не найдено">
         <msh:tableNotEmpty>
         	<tr>
         		<th></th>
         		<th></th>         		
         		<th colspan="2">Число посещ. в пол-ку</th>		
         		<th colspan="4">Из посещ. в пол-ку по поводу заб.</th>	
         		<th colspan="4">Из посещ. в пол-ку по поводу конс.</th>
         		<th></th>
         		<th></th>
         		<th colspan="4">Из посещ. на дому по поводу заб.</th>	
         		<th colspan="4">Из посещ. на дому по поводу конс.</th>
         		<th></th>
         	</tr>
         </msh:tableNotEmpty>  
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn isCalcAmount="true" columnName="Всего" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="кол-во" property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="из них с.ж." property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="кол-во" property="6"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-14" property="7"/>
            <msh:tableColumn isCalcAmount="true" columnName="15-17" property="8"/>
            <msh:tableColumn isCalcAmount="true" columnName="от 18" property="9"/>
            <msh:tableColumn isCalcAmount="true" columnName="кол-во" property="10"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-14" property="11"/>
            <msh:tableColumn isCalcAmount="true" columnName="15-17" property="12"/>
            <msh:tableColumn isCalcAmount="true" columnName="от 18" property="13"/>
            <msh:tableColumn isCalcAmount="true" columnName="Проф." property="14"/>
            <msh:tableColumn isCalcAmount="true" columnName="На дому" property="15"/>
            <msh:tableColumn isCalcAmount="true" columnName="кол-во" property="16"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-14" property="17"/>
            <msh:tableColumn isCalcAmount="true" columnName="15-17" property="18"/>
            <msh:tableColumn isCalcAmount="true" columnName="от 18" property="19"/>
            <msh:tableColumn isCalcAmount="true" columnName="кол-во" property="20"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-14" property="21"/>
            <msh:tableColumn isCalcAmount="true" columnName="15-17" property="22"/>
            <msh:tableColumn isCalcAmount="true" columnName="от 18" property="23"/>
            <msh:tableColumn isCalcAmount="true" columnName="Проф. на дому" property="24"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	
    <%
    } else if (typeView!=null && (typeView.equals("3"))) {
    	%>
    <msh:section>
${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="journal_ticket" nativeSql="
select
''||${groupSqlId}||${workFunctionSqlId}||${additionStatusSqlId}||${visitReasonSqlId}||${specialistSqlId}||${lpuSqlId}||${serviceStreamSqlId}||${workPlaceTypeSqlId}||${socialStatusSqlId}||'&beginDate=${beginDate}&finishDate=${finishDate}' as name
,${groupSql} as nameFld

,count(*) as cntAll 
,count(case when 
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<15
then 1 else null end) as cntAll14 
,count(case when (ad1.addressIsVillage='1') and 
(
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<15
) then 1 else null end) as cntAll14V 
,count(case when (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 15 and 17
)	then 1 else null end) as cntAll17 
,count(case when (ad1.addressIsVillage='1') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 15 and 17
	)	then 1 else null end) as cntAll17V 
,count(case when (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>17
) then 1 else null end) as cntAllold 
,count(case when (ad1.addressIsVillage='1') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>17
) then 1 else null end) as cntAlloldV 
,count(case when vr.code='ILLNESS' and vwpt.code='POLYCLINIC' then 1 else null end) as cntIllness 
,count(case when vr.code='ILLNESS' and vwpt.code='POLYCLINIC' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 0 and 14
) then 1 else null end) as cntIllnes14
,count(case when vr.code='ILLNESS' and vwpt.code='POLYCLINIC' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 15 and 17
) then 1 else null end) as cntIllnes17 
,count(case when vr.code='ILLNESS' and vwpt.code='POLYCLINIC' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >17 
)then 1 else null end) as cntIllnesold 
,count(case when vr.code='PROFYLACTIC' and vwpt.code='POLYCLINIC' then 1 else null end) as cntProf 
,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHome 
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntIllnesHome 
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<15
) then 1 else null end) as cntIllnesHome14 
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 15 and 17
) then 1 else null end) as cntIllnesHome17 
,count(case when vr.code='ILLNESS' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>17
) then 1 else null end) as cntIllnesHomeold 
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntProfHome 
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)<15
) then 1 else null end) as cntProfHome14 
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 15 and 17
) then 1 else null end) as cntProfHome17 
,count(case when vr.code='PROFYLACTIC' and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end)>17
) then 1 else null end) as cntProfHomeold
FROM MedCase smo  
left join MedCase spo on spo.id=smo.parent_id
LEFT JOIN Patient p ON p.id=smo.patient_id 
left join VocAdditionStatus vas on vas.id=p.additionStatus_id

LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId 
LEFT JOIN VocReason vr on vr.id=smo.visitReason_id 
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=smo.workPlaceType_id 
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id 
LEFT JOIN VocSocialStatus pvss on pvss.id=p.socialStatus_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id 
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id 
LEFT JOIN Worker w on w.id=wf.worker_id 
LEFT JOIN Patient wp on wp.id=w.person_id 
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id 
WHERE  ${dtypeSql} 
and ${dateSql} BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')  
${specialistSql} ${is039Sql} ${workFunctionSql} ${lpuSql} ${serviceStreamSql} ${visitReasonSql} ${workPlaceTypeSql} ${additionStatusSql} ${socialStatusSql}
${personSql}  and smo.dateStart is not null ${emergencySql} ${ageSql}
GROUP BY ${groupGroup} ORDER BY ${groupOrder}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" nameFldSql="journal_ticket_sql"/> 
    <msh:sectionTitle>
    <form action="print-f039_bis${printPrefix}.do" method="post" target="_blank">
    Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_ticket_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
    <input type='hidden' name="m" id="m" value="printNativeQuery${printPrefix}">
    <input type='hidden' name="date1" id="date1" value="${beginDate}">
    <input type='hidden' name="date2" id="date2" value="${finishDate}">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table
         name="journal_ticket" action="visit_f039_list.do?typeReestr=1&typeView=${typeView}&typeGroup=${typeGroup}&typeDiag=${typeDiag}&ageFrom=${param.ageFrom}&ageTo=${param.ageTo}&typeDtype=${typeDtype}&typeEmergency=${typeEmergency}&typeDate=${typeDate}" idField="1" noDataMessage="Не найдено">
         <msh:tableNotEmpty>
         	<tr>
         		<th></th>
    		
         		<th colspan="7">Посещения на дому и поликлиника</th>		
         		<th colspan="4">Посещения по поводу заболевания(д) в пол-ке</th>	
         		<th colspan="1">Проф. посещ. в пол-ке</th>
         		<th colspan="1">Посещ. на дому</th>
         		<th colspan="4">Посещ. по поводу забол.(д) на дому</th>
         		<th colspan="4">Проф. посещ. на дому</th>
         	</tr>
         </msh:tableNotEmpty>          
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn isCalcAmount="true" columnName="кол-во" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-14 лет" property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-14 лет с.ж." property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="15-17 лет" property="6"/>
            <msh:tableColumn isCalcAmount="true" columnName="15-17 лет с.ж." property="7"/>
            <msh:tableColumn isCalcAmount="true" columnName=">=18 лет" property="8"/>
            <msh:tableColumn isCalcAmount="true" columnName=">=18 с.ж." property="9"/>
            <msh:tableColumn isCalcAmount="true" columnName="кол-во" property="10"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-14" property="11"/>
            <msh:tableColumn isCalcAmount="true" columnName="15-17" property="12"/>
            <msh:tableColumn isCalcAmount="true" columnName=">=18" property="13"/>
            <msh:tableColumn isCalcAmount="true" columnName="кол-во" property="14"/>
            <msh:tableColumn isCalcAmount="true" columnName="кол-во" property="15"/>
            
            <msh:tableColumn isCalcAmount="true" columnName="кол-во" property="16"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-14" property="17"/>
            <msh:tableColumn isCalcAmount="true" columnName="15-17" property="18"/>
            <msh:tableColumn isCalcAmount="true" columnName=">=18" property="19"/>
            
            <msh:tableColumn isCalcAmount="true" columnName="кол-во пд" property="20"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-14" property="21"/>
            <msh:tableColumn isCalcAmount="true" columnName="15-17" property="22"/>
            <msh:tableColumn isCalcAmount="true" columnName=">=18" property="23"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	
    <%
    } else if (typeView!=null && (typeView.equals("4"))) {
    	%>
    <msh:section>
${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="journal_ticket" nativeSql="
select
''||${groupSqlId}||${workFunctionSqlId}||${additionStatusSqlId}||${visitReasonSqlId}||${specialistSqlId}||${lpuSqlId}||${serviceStreamSqlId}||${workPlaceTypeSqlId}||${socialStatusSqlId}||'&beginDate=${beginDate}&finishDate=${finishDate}' as name
,${groupSql} as nameFld

,count(case when vwpt.code='POLYCLINIC' then 1 else null end) as cntAllPoly
,count(case when vwpt.code='POLYCLINIC' and (ad1.addressIsVillage='1') then 1 else null end) as cntAllPolyV 
,count(case when vwpt.code='POLYCLINIC' and
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 0 and 17
then 1 else null end) as cntAll17

,count(case when (vr.code='ILLNESS') and vwpt.code='POLYCLINIC' then 1 else null end) as cntIllness 
,count(case when (vr.code='ILLNESS') and vwpt.code='POLYCLINIC' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) > 17
) then 1 else null end) as cntIllnesOld
,count(case when (vr.code='ILLNESS') and vwpt.code='POLYCLINIC' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 0 and 17
) then 1 else null end) as cntIllnes17 

,count(case when (vr.code='CONSULTATION') and vwpt.code='POLYCLINIC' then 1 else null end) as cntCons 
,count(case when (vr.code='CONSULTATION') and vwpt.code='POLYCLINIC' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) > 17
) then 1 else null end) as cntConsOld
,count(case when (vr.code='CONSULTATION') and vwpt.code='POLYCLINIC' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 0 and 17
) then 1 else null end) as cntCons17 

,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHome 
,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (ad1.addressIsVillage='1') then 1 else null end) as cntHomeV 
,count(case when (vr.code='ILLNESS' or vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntIllnesHome 
,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 0 and 17
) then 1 else null end) as cntHome17
,count(case when (vr.code='ILLNESS' or vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 0 and 17
) then 1 else null end) as cntIllnesHome17 
FROM MedCase smo  
left join MedCase spo on spo.id=smo.parent_id
LEFT JOIN Patient p ON p.id=smo.patient_id 
left join VocAdditionStatus vas on vas.id=p.additionStatus_id

LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId  
LEFT JOIN VocReason vr on vr.id=smo.visitReason_id 
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=smo.workPlaceType_id 
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id 
LEFT JOIN VocSocialStatus pvss on pvss.id=p.socialStatus_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id 
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id 
LEFT JOIN Worker w on w.id=wf.worker_id 
LEFT JOIN Patient wp on wp.id=w.person_id 
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id 
WHERE  ${dtypeSql} 
and ${dateSql} BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')  
${specialistSql} ${is039Sql} ${workFunctionSql} ${lpuSql} ${serviceStreamSql} ${visitReasonSql} ${workPlaceTypeSql} ${additionStatusSql} ${socialStatusSql}
${personSql}  and smo.dateStart is not null ${emergencySql} ${ageSql}
GROUP BY ${groupGroup} ORDER BY ${groupOrder}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" nameFldSql="journal_ticket_sql"/> 
    <msh:sectionTitle>
    <form action="print-f039_30rep.do" method="post" target="_blank">
    Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_ticket_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type='hidden' name="date1" id="date1" value="${beginDate}">
    <input type='hidden' name="date2" id="date2" value="${finishDate}">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table
         name="journal_ticket" action="visit_f039_list.do?typeReestr=1&typeView=${typeView}&typeGroup=${typeGroup}&typeDiag=${typeDiag}&ageFrom=${param.ageFrom}&ageTo=${param.ageTo}&typeDtype=${typeDtype}&typeEmergency=${typeEmergency}&typeDate=${typeDate}" idField="1" noDataMessage="Не найдено">
         <msh:tableNotEmpty>
         	<tr>
         		<th></th>
         		<th colspan="3">Число посещений (в поликлинику)</th>
         		<th colspan="3">Из посещ. в пол-ку сделано по поводу заболеваний</th>
         		<th colspan="3">Из посещ. в пол-ку сделано по поводу консультаций</th>
         		<th colspan="5">Число посещ. врачами на дому</th>
         	</tr>
         </msh:tableNotEmpty>
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn isCalcAmount="true" columnName="Всего" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="из них сел. жит." property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего 0-17 лет" property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="всего" property="6"/>
            <msh:tableColumn isCalcAmount="true" columnName="взрослыми 18 лет и старше" property="7"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-17 лет" property="8"/>
            <msh:tableColumn isCalcAmount="true" columnName="всего" property="9"/>
            <msh:tableColumn isCalcAmount="true" columnName="взрослыми 18 лет и старше" property="10"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-17 лет" property="11"/>
            <msh:tableColumn isCalcAmount="true" columnName="Всего" property="12"/>
            <msh:tableColumn isCalcAmount="true" columnName="из них с.ж." property="13"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего по поводу заболеваний" property="14"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего 0-17" property="15"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего 0-17 по поводу заболеваний" property="16"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	
    <%
    } else if (typeView!=null && (typeView.equals("5"))) {
    	%>
    <msh:section>
${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="journal_ticket" nativeSql="
select
''||${groupSqlId}||${workFunctionSqlId}||${additionStatusSqlId}||${visitReasonSqlId}||${specialistSqlId}||${lpuSqlId}||${serviceStreamSqlId}||${workPlaceTypeSqlId}||${socialStatusSqlId}||'&beginDate=${beginDate}&finishDate=${finishDate}' as name
,${groupSql} as nameFld

,count(case when vwpt.code='POLYCLINIC' then 1 else null end) as cntAllPoly
,count(case when vwpt.code='POLYCLINIC' and (ad1.addressIsVillage='1') then 1 else null end) as cntAllPolyV 
,count(case when vwpt.code='POLYCLINIC' and
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >= case when vs.omcCode='2' then 55 else 60 end
then 1 else null end) as cntAll17

,count(case when (vr.code='ILLNESS') and vwpt.code='POLYCLINIC' then 1 else null end) as cntIllness 
,count(case when (vr.code='ILLNESS') and vwpt.code='POLYCLINIC' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between ${typeAgeWorkId} and case when vs.omcCode='2' then 54 else 59 end
) then 1 else null end) as cntIllnesOld
,count(case when (vr.code='ILLNESS') and vwpt.code='POLYCLINIC' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >= case when vs.omcCode='2' then 55 else 60 end
) then 1 else null end) as cntIllnes17 

,count(case when (vr.code='CONSULTATION') and vwpt.code='POLYCLINIC' then 1 else null end) as cntCons 
,count(case when (vr.code='CONSULTATION') and vwpt.code='POLYCLINIC' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between ${typeAgeWorkId} and case when vs.omcCode='2' then 54 else 59 end
) then 1 else null end) as cntConsOld
,count(case when (vr.code='CONSULTATION') and vwpt.code='POLYCLINIC' and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >= case when vs.omcCode='2' then 55 else 60 end
) then 1 else null end) as cntCons17 

,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntHome 
,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (ad1.addressIsVillage='1') then 1 else null end) as cntHomeV 
,count(case when (vr.code='ILLNESS' or vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') then 1 else null end) as cntIllnesHome 
,count(case when (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >= case when vs.omcCode='2' then 55 else 60 end
) then 1 else null end) as cntHome17
,count(case when (vr.code='ILLNESS' or vr.code='CONSULTATION') and (vwpt.code='HOME' or vwpt.code='HOMEACTIVE') and (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >= case when vs.omcCode='2' then 55 else 60 end
) then 1 else null end) as cntIllnesHome17 
FROM MedCase smo  
left join MedCase spo on spo.id=smo.parent_id
LEFT JOIN Patient p ON p.id=smo.patient_id 
left join VocAdditionStatus vas on vas.id=p.additionStatus_id
LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId 
LEFT JOIN VocReason vr on vr.id=smo.visitReason_id 
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=smo.workPlaceType_id 
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id 
LEFT JOIN VocSocialStatus pvss on pvss.id=p.socialStatus_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id 
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id 
LEFT JOIN Worker w on w.id=wf.worker_id 
LEFT JOIN Patient wp on wp.id=w.person_id 
left join VocSex vs on vs.id=p.id
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id 
WHERE  ${dtypeSql} 
and ${dateSql} BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')  
${specialistSql} ${is039Sql} ${workFunctionSql} ${lpuSql} ${serviceStreamSql} ${visitReasonSql} ${workPlaceTypeSql} ${additionStatusSql} ${socialStatusSql}
${personSql}  and smo.dateStart is not null ${emergencySql} ${ageSql}
GROUP BY ${groupGroup} ORDER BY ${groupOrder}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" nameFldSql="journal_ticket_sql"/> 
    <msh:sectionTitle>
    <form action="print-f039_30rep_bis${printPrefix}.do" method="post" target="_blank">
    Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_ticket_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
    <input type='hidden' name="m" id="m" value="printNativeQuery${printPrefix}">
    <input type='hidden' name="date1" id="date1" value="${beginDate}">
    <input type='hidden' name="date2" id="date2" value="${finishDate}">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table
         name="journal_ticket" action="visit_f039_list.do?typeReestr=1&typeView=${typeView}&typeGroup=${typeGroup}&typeDiag=${typeDiag}&ageFrom=${param.ageFrom}&ageTo=${param.ageTo}&typeDtype=${typeDtype}&typeEmergency=${typeEmergency}&typeDate=${typeDate}" idField="1" noDataMessage="Не найдено">
         <msh:tableNotEmpty>
         	<tr>
         		<th></th>
         		<th colspan="3">Число посещений (в поликлинику)</th>
         		<th colspan="3">Из посещ. в пол-ку сделано по поводу заболеваний</th>
         		<th colspan="3">Из посещ. в пол-ку сделано по поводу консультаций</th>
         		<th colspan="5">Число посещ. врачами на дому</th>
         	</tr>
         </msh:tableNotEmpty>
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn isCalcAmount="true" columnName="Всего" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="из них сел. жит." property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего старше труд. возраста" property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="всего" property="6"/>
            <msh:tableColumn isCalcAmount="true" columnName="взрослыми труд. возраст" property="7"/>
            <msh:tableColumn isCalcAmount="true" columnName="старше труд. возраста" property="8"/>
            <msh:tableColumn isCalcAmount="true" columnName="всего" property="9"/>
            <msh:tableColumn isCalcAmount="true" columnName="взрослыми труд. возраст" property="10"/>
            <msh:tableColumn isCalcAmount="true" columnName="старше труд. возраста" property="11"/>
            <msh:tableColumn isCalcAmount="true" columnName="Всего" property="12"/>
            <msh:tableColumn isCalcAmount="true" columnName="из них с.ж." property="13"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего по поводу заболеваний" property="14"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего старше труд.возраста" property="15"/>
            <msh:tableColumn isCalcAmount="true" columnName="из всего старше труд.возраста по поводу заболеваний" property="16"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	
    <%
    } else if (typeView!=null && (typeView.equals("6"))) {
    	%>
    <msh:section>
${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="journal_ticket" nativeSql="
select
''||${groupSqlId}||${workFunctionSqlId}||${additionStatusSqlId}||${visitReasonSqlId}||${specialistSqlId}||${lpuSqlId}||${serviceStreamSqlId}||${workPlaceTypeSqlId}||${socialStatusSqlId}||'&beginDate=${beginDate}&finishDate=${finishDate}' as name
,${groupSql} as nameFld

,count(*) as cntAll 
,count(distinct case when vr.code='PROFYLACTIC' then smo.id else null end) as cntProf 
,count(distinct case when vr.code='ILLNESS' then smo.id else null end) as cntIllnessSmo
,count(distinct case when vr.code='ILLNESS' then smo.patient_id else null end) as cntIllnessPat
,count(distinct case when vr.code='ILLNESS' and spo.dateStart!=spo.dateFinish then spo.id else null end) as cntIllnessSpo
,count(distinct case when vr.code='ILLNESS' and spo.dateStart=spo.dateFinish then spo.id else null end) as cntIllnessSpo1
,count(distinct case when vr.code='CONSULTATION' then smo.id else null end) as cntConsSmo
,count(distinct case when vr.code='CONSULTATION' then smo.patient_id else null end) as cntConsPat
,count(distinct case when vr.code='CONSULTATION' and spo.dateStart!=spo.dateFinish then spo.id else null end) as cntConsSpo
,count(distinct case when vr.code='CONSULTATION' and spo.dateStart=spo.dateFinish then spo.id else null end) as cntConsSpo1
FROM MedCase smo  
left join MedCase spo on spo.id=smo.parent_id
LEFT JOIN Patient p ON p.id=smo.patient_id 
LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId 
left join VocAdditionStatus vas on vas.id=p.additionStatus_id
LEFT JOIN VocReason vr on vr.id=smo.visitReason_id 
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=smo.workPlaceType_id 
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id 
LEFT JOIN VocSocialStatus pvss on pvss.id=p.socialStatus_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id 
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id 
LEFT JOIN Worker w on w.id=wf.worker_id 
LEFT JOIN Patient wp on wp.id=w.person_id 
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id 
WHERE  ${dtypeSql} 
and ${dateSql} BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')  
${specialistSql} ${is039Sql} ${workFunctionSql} ${lpuSql} ${serviceStreamSql} ${visitReasonSql} ${workPlaceTypeSql} ${additionStatusSql} ${socialStatusSql}
${personSql} and smo.dateStart is not null ${emergencySql} ${ageSql}
GROUP BY ${groupGroup} ORDER BY ${groupOrder}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" nameFldSql="journal_ticket_sql"/> 
    <msh:sectionTitle>
    <form action="print-f039_62rep.do" method="post" target="_blank">
    Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_ticket_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type='hidden' name="date1" id="date1" value="${beginDate}">
    <input type='hidden' name="date2" id="date2" value="${finishDate}">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table
         name="journal_ticket" action="visit_f039_list.do?typeReestr=1&typeView=${typeView}&typeGroup=${typeGroup}&typeDiag=${typeDiag}&ageFrom=${param.ageFrom}&ageTo=${param.ageTo}&typeDtype=${typeDtype}&typeEmergency=${typeEmergency}&typeDate=${typeDate}" idField="1" noDataMessage="Не найдено">
                  <msh:tableNotEmpty>
         	<tr>
         		<th></th>
         		<th></th>
         		<th></th>
         		<th colspan="4">из всех посещ. в связи с заб.</th>
         		<th colspan="4">из всех посещ. в связи с конс.</th>
         	</tr>
         </msh:tableNotEmpty>
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn isCalcAmount="true" columnName="Всего посещений" property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="из них с проф. целью" property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="посещений" property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="пациентов" property="6"/>
            <msh:tableColumn isCalcAmount="true" columnName="обращений (более 1 дня)" property="7"/>
            <msh:tableColumn isCalcAmount="true" columnName="обращений (1 день)" property="8"/>
            <msh:tableColumn isCalcAmount="true" columnName="посещений" property="9"/>
            <msh:tableColumn isCalcAmount="true" columnName="пациентов" property="10"/>
            <msh:tableColumn isCalcAmount="true" columnName="обращений (более 1 дня)" property="11"/>
            <msh:tableColumn isCalcAmount="true" columnName="обращений (1 день)" property="12"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	
    <%} else if (typeView!=null && (typeView.equals("7"))) {
    	%>
    <msh:section>
${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" name="journal_ticket" nativeSql="
select
''||${groupSqlId}||${workFunctionSqlId}||${additionStatusSqlId}||${visitReasonSqlId}||${specialistSqlId}||${lpuSqlId}||${serviceStreamSqlId}||${workPlaceTypeSqlId}||${socialStatusSqlId}||'&beginDate=${beginDate}&finishDate=${finishDate}' as name
,${groupSql} as nameFld

,count(distinct smo.id) as cntSmoAll 
,count(distinct case when (oo.voc_code='643' or oo.id is null) and (ad1.addressid is null or ad1.kladr not like '30%') then smo.id else null end) as cntSmoInog 
,count(distinct case when oo.voc_code!='643' and oo.id is not null then smo.id else null end) as cntSmoInostr
,count(distinct smo.patient_id) as cntPatAll 
,count(distinct case when (oo.voc_code='643' or oo.id is null) and (ad1.addressid is null or ad1.kladr not like '30%') then smo.patient_id else null end) as cntPatInog 
,count(distinct case when oo.voc_code!='643' and oo.id is not null then smo.patient_id else null end) as cntPatInostr
,count(distinct case when (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >= 18
) then smo.patient_id else null end) as cntStr18 
,count(distinct case when (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >= case when vs.omcCode='2' then 55 else 60 end
) then smo.patient_id else null end) as cntStrTrud 
,count(distinct smo.patient_id)-count(distinct case when (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >= 18
) then smo.patient_id else null end) as cntStrD018

,count(distinct smo.patient_id)-count(distinct case when (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >= 15
) then smo.patient_id else null end) as cntStrD014
,count(distinct smo.patient_id)-(count(distinct smo.patient_id)-count(distinct case when (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >= 15
) then smo.patient_id else null end))-
count(distinct case when (
		cast(to_char(smo.dateStart,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(smo.dateStart, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(smo.dateStart,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) >= 18
) then smo.patient_id else null end)
 as cntStr15_17 

FROM MedCase smo  
left join MedCase spo on spo.id=smo.parent_id
LEFT JOIN Patient p ON p.id=smo.patient_id 
left join VocSex vs on vs.id=p.sex_id
LEFT JOIN Address2 ad1 on ad1.addressId=p.address_addressId 
left join VocAdditionStatus vas on vas.id=p.additionStatus_id
LEFT JOIN VocReason vr on vr.id=smo.visitReason_id 
LEFT JOIN vocWorkPlaceType vwpt on vwpt.id=smo.workPlaceType_id 
LEFT JOIN VocServiceStream vss on vss.id=smo.serviceStream_id 
LEFT JOIN VocSocialStatus pvss on pvss.id=p.socialStatus_id
LEFT JOIN WorkFunction wf on wf.id=smo.workFunctionExecute_id 
LEFT JOIN VocWorkFunction vwf on vwf.id=wf.workFunction_id 
LEFT JOIN Worker w on w.id=wf.worker_id 
LEFT JOIN Patient wp on wp.id=w.person_id 
LEFT JOIN MisLpu lpu on lpu.id=w.lpu_id 
Left join Omc_oksm oo on oo.id=p.nationality_id
WHERE  ${dtypeSql} 
and ${dateSql} BETWEEN TO_DATE('${beginDate}','dd.mm.yyyy') and TO_DATE('${finishDate}','dd.mm.yyyy') 
and (smo.noActuality is null or smo.noActuality='0')  
${specialistSql} ${is039Sql} ${workFunctionSql} ${lpuSql} ${serviceStreamSql} ${visitReasonSql} ${workPlaceTypeSql} ${additionStatusSql} ${socialStatusSql}
${personSql} and smo.dateStart is not null ${emergencySql} ${ageSql}
GROUP BY ${groupGroup} ORDER BY ${groupOrder}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" nameFldSql="journal_ticket_sql"/> 
    <msh:sectionTitle>
    <form action="print-f039_patient.do" method="post" target="_blank">
    Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_ticket_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${beginDate} по ${finishDate}. ${filterInfo} ${specInfo} ${workFunctionInfo} ${lpuInfo} ${serviceStreamInfo}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type='hidden' name="date1" id="date1" value="${beginDate}">
    <input type='hidden' name="date2" id="date2" value="${finishDate}">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table
         name="journal_ticket" action="visit_f039_list.do?typeReestr=1&typeView=${typeView}&typeGroup=${typeGroup}&typeDiag=${typeDiag}&ageFrom=${param.ageFrom}&ageTo=${param.ageTo}&typeDtype=${typeDtype}&typeEmergency=${typeEmergency}&typeDate=${typeDate}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn isCalcAmount="true" columnName="Всего посещ." property="3"/>
            <msh:tableColumn isCalcAmount="true" columnName="Иног. посещ." property="4"/>
            <msh:tableColumn isCalcAmount="true" columnName="Иностр. посещ." property="5"/>
            <msh:tableColumn isCalcAmount="true" columnName="Всего пац." property="6"/>
            <msh:tableColumn isCalcAmount="true" columnName="Иног.пац." property="7"/>
            <msh:tableColumn isCalcAmount="true" columnName="Иностр. пац." property="8"/>
            <msh:tableColumn isCalcAmount="true" columnName="18 лет и старше" property="9"/>
            <msh:tableColumn isCalcAmount="true" columnName="старше труд. возраста" property="10"/>
            <msh:tableColumn isCalcAmount="true" columnName="дети до 18 лет" property="11"/>
            <msh:tableColumn isCalcAmount="true" columnName="0-14" property="12"/>
            <msh:tableColumn isCalcAmount="true" columnName="15-17" property="13"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>    	
    <% }
    }
    		} else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">

    checkFieldUpdate('typeGroup','${typeGroup}',1) ;
    checkFieldUpdate('typeAgeWork','${typeAgeWork}',1) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
    checkFieldUpdate('typeDtype','${typeDtype}',3) ;
    checkFieldUpdate('typeDate','${typeDate}',2) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeDiag','${typeDiag}',3) ;
    
    
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
    
  	function getId(aBis) {
  		var typeGroup = document.forms[0].typeGroup ;
		var args=$('beginDate').value+":"+$('finishDate').value
 			+":"+getCheckedValue(typeGroup)
 			+":"+$('specialist').value
 			+":"+$('workFunction').value
 			+":"+$('lpu').value
 			+":"+$('serviceStream').value
 			+":"+$('workPlaceType').value
 			+":0";
		//aSpecialist, aWorkFunction, aLpu, aServiceStream
		//aGroupBy, aStartDate, aFinishDate
		//, aSpecialist, aWorkFunction, aLpu, aServiceStream 			
		$('id').value =args ; 
		if (+aBis>0) {
			$('m').value='f039add' ;
		} else {
			$('m').value='f039' ;
		}
		
	}
  	function getCheckedValue(radioGrp) {
  		var radioValue ;
  		for(i=0; i < radioGrp.length; i++) {
  		  if (radioGrp[i].checked == true){
  		    radioValue = radioGrp[i].value;
  		    break ;
  		  }
  		}
  		return radioValue ;
  	}
  		
  	</script>
  </tiles:put>

</tiles:insert>