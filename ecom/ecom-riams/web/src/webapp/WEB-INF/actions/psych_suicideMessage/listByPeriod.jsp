
<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="PolyJournal" title="Сводная информация о случаях суицидальных попытках"/>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  	String noViewForm = request.getParameter("noViewForm") ;
  	String typeDateSuicide=ActionUtil.updateParameter("ReportMesSuicide","typeDateSuicide","1", request) ;
  	String typeView=ActionUtil.updateParameter("ReportMesSuicide","typeView","2", request) ;
  	String typeAgeView=ActionUtil.updateParameter("ReportMesSuicide","typeAgeView","1", request) ;
  	String typeIsFinish=ActionUtil.updateParameter("ReportMesSuicide","typeIsFinish","3", request) ;
  	
  	StringBuilder paramSql= new StringBuilder() ;
  	StringBuilder paramHref= new StringBuilder() ;
  	
	//ActionUtil.setParameterFilterSql("reportStr", "vrspt.id", request) ;
  	//ActionUtil.setParameterFilterSql("department","department", "ahr.department", request) ;
  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("sex", "p.sex_id", request)) ;
  	paramSql.append(" ").append(ActionUtil.setParameterFilterSql("department", "sm.regOtherLpu_id", request)) ;
  	//paramHref.append("sex=").append(request.getParameter("sex")!=null?request.getParameter("sex"):"") ;
  	paramHref.append("department=").append(request.getParameter("department")!=null?request.getParameter("department"):"") ;
  	paramHref.append("&typeDateSuicide=").append(typeDateSuicide) ;
  	request.setAttribute("paramSql", paramSql.toString()) ;
  	request.setAttribute("paramHref", paramHref.toString()) ;
  	//request.setAttribute("diag_typeReg_cl", "4") ;
  	//request.setAttribute("diag_typeReg_pat", "5") ;
  	//request.setAttribute("diag_priority_m", "1") ;
  	
  	if (noViewForm!=null && noViewForm.equals("1")) {
  	} else {
  		
  %>
    <msh:form action="/psych_suicideMessage_by_period.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="id" id="id" value=""/>
    <input type="hidden" name="refreshType" id="refreshType" value="REFRESH_36"/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      
      <msh:row>
        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1"> по реестр
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="2"> свод по полу
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="3"> общий свод
        </td>

       </msh:row>
      <msh:row>
        <td class="label" title="Просмотр данных (typeAgeView)" colspan="1"><label for="typeAgeViewName" id="typeAgeViewLabel">Разбивка по возрастам:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAgeView" value="1"> дети + взрослые
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAgeView" value="2"> дети
        </td>


       </msh:row>
      <msh:row>
        <td class="label" title="Просмотр данных (typeDateSuicide)" colspan="1"><label for="typeDateSuicideName" id="typeDateSuicideLabel">Поиск по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDateSuicide" value="1"> дате суицида
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeDateSuicide" value="2"> дате регистрации в ОКПБ
        </td>

       </msh:row>
     
      <msh:row>
        <td class="label" title="Просмотр данных (typeIsFinish)" colspan="1"><label for="typeIsFinishName" id="typeIsFinishLabel">Отобразить суиц.попытки:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeIsFinish" value="1"> завершенные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeIsFinish" value="2"> незавершенные
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeIsFinish" value="3"> все
        </td>

       </msh:row>
      
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="ЛПУ" vocName="mainLpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="type" fieldColSpan="4" horizontalFill="true" label="Вид попытки" vocName="vocSuicideMesType"/>
        </msh:row>
       
      <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="submit" value="Найти" />
           
          </td>
      </msh:row>
    </msh:panel>
    </msh:form>
    
    <%} %>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
           <script type='text/javascript'>
          
           checkFieldUpdate('typeAgeView','${typeAgeView}',1) ;
           checkFieldUpdate('typeView','${typeView}',1) ;
           checkFieldUpdate('typeIsFinish','${typeIsFinish}',1) ;
           checkFieldUpdate('typeDateSuicide','${typeDateSuicide}',1) ;
           

   function checkFieldUpdate(aField,aValue,aDefaultValue) {
   	eval('var chk =  document.forms[0].'+aField) ;
   	var aMax=chk.length ;
   	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
   		chk[+aDefaultValue-1].checked='checked' ;
   	} else {
   		chk[+aValue-1].checked='checked' ;
   	}
   }
			 
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='psych_suicideMessage_by_period.do' ;
    }
    
    
    if ($('dateBegin').value=="") {
    	$('dateBegin').value=getCurrentDate() ;
    }

			 
    </script>
    <%
    
    String date = request.getParameter("dateBegin") ;
    if (date!=null) {
    String dateEnd = request.getParameter("dateEnd") ;
    
    //Str
    String view = (String)request.getAttribute("typeView") ;
    if (date!=null) {
    	if (typeDateSuicide.equals("1")) {
    		request.setAttribute("dateSuiFld", "sm.suicideDate") ;
    	} else {
    		request.setAttribute("dateSuiFld", "sm.regDate") ;
    	}
    	if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
        request.setAttribute("dateBegin", date) ;
        request.setAttribute("dateEnd", dateEnd) ;
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    	StringBuilder sql = new StringBuilder() ;
    	if (typeIsFinish!=null&&typeIsFinish.equals("1")) {
    		sql.append(" and sm.IsFinished='1'") ;
    	} else if (typeIsFinish!=null&&typeIsFinish.equals("2")) {
    		sql.append(" and (sm.IsFinished='0' or sm.IsFinished is null)") ;
    	}
    	request.setAttribute("paramSql", sql) ;
    	ActionUtil.setParameterFilterSql("sex","vs.id", request) ;
    	ActionUtil.setParameterFilterSql("type","vsmt.id", request) ;

    if (view.equals("1")) { 
    
    String typeAddress = request.getParameter("typeAddress") ;
	if (typeAddress!=null&&typeAddress.equals("1")) {
		sql.append(" and a.addressisCity='1'") ;
	} else if (typeAddress!=null&&typeAddress.equals("2")) {
		sql.append(" and a.addressisVillage='1'") ;
	}
    String typeAge = request.getParameter("typeAge") ;
	if (typeAge!=null&&typeAge.equals("1")) {
		sql.append(" and (cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)") ;
		sql.append("		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)") ;
		sql.append("		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)") ;
		sql.append("		then -1 else 0 end) between 0 and 17") ;
		sql.append(")") ;
	} else if (typeAge!=null&&typeAge.equals("2")) {
		sql.append(" and (cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)") ;
		sql.append("		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)") ;
		sql.append("		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)") ;
		sql.append("		then -1 else 0 end) between 18 and case when vs.omcCode='2' then 54 else 59 end") ;
		sql.append(")") ;
	} else if (typeAge!=null&&typeAge.equals("3")) {
		sql.append(" and (cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)") ;
		sql.append("		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)") ;
		sql.append("		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)") ;
		sql.append("		then -1 else 0 end) > case when vs.omcCode='2' then 54 else 59 end") ;
		sql.append(")") ;
	} else if (typeAge!=null&&typeAge.equals("4")) {
		sql.append(" and (cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)") ;
		sql.append("		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)") ;
		sql.append("		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)") ;
		sql.append("		then -1 else 0 end) between 0 and 14") ;
		sql.append(")") ;
	} else if (typeAge!=null&&typeAge.equals("5")) {
		sql.append(" and (cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)") ;
		sql.append("		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)") ;
		sql.append("		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)") ;
		sql.append("		then -1 else 0 end) between 15 and 17") ;
		sql.append(")") ;
	}
	
	
	
    String intoxication = request.getParameter("intoxication") ;
	if (intoxication!=null&&intoxication.equals("1")) {
		sql.append(" and i.code='1'") ;
	}
    String helpSMP = request.getParameter("helpSMP") ;
	if (helpSMP!=null&&helpSMP.equals("1")) {
		sql.append(" and h.code='1'") ;
	}
	request.setAttribute("paramSql", sql) ;
    %>

    
      <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section><ecom:webQuery isReportBase="${isReportBase}" name="reestr" nameFldSql="reestr_sql" nativeSql="
select sm.id
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,vr.name as vname
,
		cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) as age
,vs.name as vsname
,lpu.name as lpuname
,to_char(sm.suicideDate,'dd.mm.yyyy') as smsuicideDate
,vsmt.name as vsmtname
,h.name as hname
,i.name as iname
,case when sm.isfinished='1' then 'Да' else 'Нет' end as isFinished
 from suicidemessage sm
 left join patient p on p.id=sm.patient_id
 left join vocsex vs on vs.id=p.sex_id
 left join vocrayon vr on vr.id=p.rayon_id
left join address2 a on a.addressid=p.address_addressid
left join vocSuicideMesType vsmt on vsmt.id=sm.type_id
left join vocyesno i on i.id=sm.intoxication_id
left join vocyesno h on h.id=sm.helpsmp_id
left join mislpu lpu on lpu.id=sm.regOtherLpu_id
where ${dateSuiFld} between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')

${paramSql}
${typeSql}
${sexSql}
order by p.lastname,p.firstname,p.middlename
" />
    <msh:sectionTitle>
    <form action="print-psych_suicideMessage_by_period_r.do" method="post" target="_blank">
    ${titleReestr}
    <input type='hidden' name="sqlText" id="sqlText" value="${reestr_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="${titleReestr}">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    
    </form>
    
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table name="reestr" 
    viewUrl="entityView-psych_suicideMessage.do" 
     action="entityView-psych_suicideMessage.do" idField="1">
      <msh:tableColumn columnName="##" property="sn" />
      <msh:tableColumn columnName="ФИО пациента" property="2" />
      <msh:tableColumn columnName="Район" property="3"/>
      <msh:tableColumn columnName="Возраст" property="4" />
      <msh:tableColumn columnName="Пол" property="5"/>
      <msh:tableColumn columnName="ЛПУ" property="6"/>
      <msh:tableColumn columnName="Дата суицида" property="7"/>
      <msh:tableColumn columnName="Вид" property="8"/>
      <msh:tableColumn columnName="Оказана мед помощь СМП" property="9"/>
      <msh:tableColumn columnName="Состояние опьянения" property="10"/>
      <msh:tableColumn columnName="Завершен" property="11"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		
   
     
        <% } else if (view.equals("2")||view.equals("3")) {
        	
        	
        	if (typeAgeView!=null && typeAgeView.equals("1")) {
        		StringBuilder p1 = new StringBuilder() ;
        		StringBuilder p2 = new StringBuilder() ;
        		p1.append("  (cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)") ;
        		p1.append("		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)") ;
        		p1.append("		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)") ;
        		p1.append("		then -1 else 0 end) between 18 and case when vs.omcCode='2' then 54 else 59 end") ;
        		p1.append(")") ;
        	
        		p2.append("  (cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)") ;
        		p2.append("		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)") ;
        		p2.append("		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)") ;
        		p2.append("		then -1 else 0 end) > case when vs.omcCode='2' then 54 else 59 end") ;
        		p2.append(")") ;
        		
        		request.setAttribute("param1", p1.toString()) ;
        		request.setAttribute("param2", p2.toString()) ;
        		request.setAttribute("paramAge1", "2") ;
        		request.setAttribute("paramAge2", "3") ;
        		request.setAttribute("paramTitle1", "Трудоспособный возраст (до 55-60)") ;
        		request.setAttribute("paramTitle2", "Старше трудоспособного возраста") ;
        	} else {
        		StringBuilder p1 = new StringBuilder() ;
        		StringBuilder p2 = new StringBuilder() ;
        		
        		p1.append("  (cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)") ;
        		p1.append("		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)") ;
        		p1.append("		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)") ;
        		p1.append("		then -1 else 0 end) between 0 and 14") ;
        		p1.append(")") ;
        	
        		p2.append("  (cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)") ;
        		p2.append("		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)") ;
        		p2.append("		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)") ;
        		p2.append("		then -1 else 0 end) between 15 and 17") ;
        		p2.append(")") ;
        		
        		request.setAttribute("param1", p1.toString()) ;
        		request.setAttribute("param2", p2.toString()) ;
        		request.setAttribute("paramAge1", "4") ;
        		request.setAttribute("paramAge2", "5") ;
        		request.setAttribute("paramTitle1", "0-14") ;
        		request.setAttribute("paramTitle2", "15-17") ;
        	}
        	
        	
        	if (view.equals("2")) {
        		request.setAttribute("groupBy", ",vs.id,vs.name") ;
        		request.setAttribute("orderBy", ",vs.name") ;
        		request.setAttribute("fldAdd", "||'&sex='||vs.id") ;
        		request.setAttribute("titleReestr","Сводная информация о случаях суицидальных попыток с разбивкой по полу за ") ;
        	} else {
        		request.setAttribute("titleReestr","Сводная информация о случаях суицидальных попыток за") ;
        		
        	}
        	
        	request.setAttribute("dateSql", sql) ;
    	%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section><ecom:webQuery isReportBase="${isReportBase}" name="Report36HOSPswod" 
    nameFldSql="Report36HOSPswod_sql" nativeSql="

select '&type='||vsmt.id${fldAdd} as id
,vsmt.name as vsmtname
,list(distinct vs.name) as vsname
,count(distinct sm.id) as cntAll
,count(distinct case when a.addressisCity='1' then sm.id else null end) as cntAllC
,count(distinct case when a.addressisVillage='1' then sm.id else null end) as cntAllV
,count(distinct case when h.code='1' then sm.id else null end) as cntAllH
,count(distinct case when i.code='1' then sm.id else null end) as cntAllI

,count(distinct case when  (
		cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 0 and 17
)then sm.id else null end) as cnt17
,count(distinct case when a.addressisCity='1' and (
		cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 0 and 17
) then sm.id else null end) as cnt17C
,count(distinct case when a.addressisVillage='1' and (
		cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 0 and 17
) then sm.id else null end) as cnt17V
,count(distinct case when h.code='1' and (
		cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 0 and 17
) then sm.id else null end) as cnt17H
,count(distinct case when i.code='1' and (
		cast(to_char(sm.suicideDate,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
		+(case when (cast(to_char(sm.suicideDate, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
		+(case when (cast(to_char(sm.suicideDate,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
		then -1 else 0 end) between 0 and 17
) then sm.id else null end) as cnt17I



,count(distinct case when  (
		${param1}
)then sm.id else null end) as cnt55
,count(distinct case when a.addressisCity='1' and (
		${param1}
) then sm.id else null end) as cnt55C
,count(distinct case when a.addressisVillage='1' and (
		${param1}
) then sm.id else null end) as cnt55V
,count(distinct case when h.code='1' and (
	${param1}
) then sm.id else null end) as cnt55H
,count(distinct case when i.code='1' and (
		${param1}
) then sm.id else null end) as cnt55I








,count(distinct case when  (
		${param2}
)then sm.id else null end) as cntSt55
,count(distinct case when a.addressisCity='1' and (
		${param2}
) then sm.id else null end) as cntSt55C
,count(distinct case when a.addressisVillage='1' and (
		${param2}
) then sm.id else null end) as cntSt55V
,count(distinct case when h.code='1' and (
		${param2}
) then sm.id else null end) as cntSt55H
,count(distinct case when i.code='1' and (
		${param2}
) then sm.id else null end) as cntSt55I



 from suicidemessage sm
left join patient p on p.id=sm.patient_id
left join vocsex vs on vs.id=p.sex_id
left join address2 a on a.addressid=p.address_addressid
left join vocSuicideMesType vsmt on vsmt.id=sm.type_id
left join vocyesno i on i.id=sm.intoxication_id
left join vocyesno h on h.id=sm.helpsmp_id
left join mislpu lpu on lpu.id=sm.regOtherLpu_id
where ${dateSuiFld} between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')

${paramSql}
${typeSql}
${sexSql}
group by vsmt.id,vsmt.name${groupBy}
order by vsmt.name${orderBy}
" />
    <msh:sectionTitle>
    
    	    <form action="print-psych_suicideMessage_by_period.do" method="post" target="_blank">
	    ${titleReestr} за ${param.dateBegin}-${dateEnd}.
	    <input type='hidden' name="sqlText" id="sqlText" value="${Report36HOSPswod_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="${titleReestr} за ${param.dateBegin}-${dateEnd}.">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="Report36HOSPswod" 
     
     action="psych_suicideMessage_by_period.do?${paramHref}&noViewForm=1&typeView=1&dateBegin=${dateBegin}&dateEnd=${dateEnd}" idField="1"
    cellFunction="true"  
     >
     <msh:tableNotEmpty>
     <tr>
     <th></th>
     <th></th>
     <th colspan="5">Всего</th>
     <th colspan="5">Дети (0-17)</th>
     <th colspan="5">${paramTitle1}</th>
     <th colspan="5">${paramTitle2}</th>
     </tr>
     </msh:tableNotEmpty>
      <msh:tableColumn columnName="Вид попытки" property="2" />
      <msh:tableColumn columnName="Пол" property="3" />
      
      <msh:tableColumn isCalcAmount="true" columnName="Всего" property="4" />
      <msh:tableColumn isCalcAmount="true" columnName="из них гор." property="5" addParam="&typeAddress=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="село" property="6" addParam="&typeAddress=2"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего оказана пом. СМП" property="7" addParam="&helpSMP=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего в опьянении" property="8" addParam="&intoxication=1"/>
      
      <msh:tableColumn isCalcAmount="true" columnName="Всего" property="9" addParam="&typeAge=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них гор." property="10" addParam="&typeAge=1&typeAddress=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="село" property="11" addParam="&typeAge=1&typeAddress=2"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего оказана пом. СМП" property="12" addParam="&typeAge=1&helpSMP=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего в опьянении" property="13" addParam="&typeAge=1&intoxication=1"/>
      
      <msh:tableColumn isCalcAmount="true" columnName="Всего" property="14" addParam="&typeAge=${paramAge1}"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них гор." property="15" addParam="&typeAge=${paramAge1}&typeAddress=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="село" property="16" addParam="&typeAge=${paramAge1}&typeAddress=2"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего оказана пом. СМП" property="17" addParam="&typeAge=${paramAge1}&helpSMP=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего в опьянении" property="18" addParam="&typeAge=${paramAge1}&intoxication=1"/>
      
      <msh:tableColumn isCalcAmount="true" columnName="Всего" property="19" addParam="&typeAge=${paramAge2}"/>
      <msh:tableColumn isCalcAmount="true" columnName="из них гор." property="20" addParam="&typeAge=${paramAge2}&typeAddress=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="село" property="21" addParam="&typeAge=${paramAge2}&typeAddress=2"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего оказана пом. СМП" property="22" addParam="&typeAge=${paramAge2}&helpSMP=1"/>
      <msh:tableColumn isCalcAmount="true" columnName="из всего в опьянении" property="23" addParam="&typeAge=${paramAge2}&intoxication=1"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		

    <%
    } 
    	
    }
    }
    	%>
    
  </tiles:put>
</tiles:insert>