<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="13 форма"/>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  	String noViewForm = request.getParameter("noViewForm") ;

  	String typeAbort=ActionUtil.updateParameter("Report13","typeAbort","1", request) ;
  	if (typeAbort!=null &&typeAbort.equals("2")) {
  		request.setAttribute("report", "13AB_ADD") ;
  		request.setAttribute("reportInfo", "22-27 недель") ;
  	} else {
  		request.setAttribute("report", "13AB") ;
  		request.setAttribute("reportInfo", "до 21 недель (включительно)") ;
  	}
  	
  	if (noViewForm!=null && noViewForm.equals("1")) {
  	} else {
  		
  %>
    <msh:form action="/stac_report_13.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="s" id="s" value="HospitalPrintReport" />
    <input type="hidden" name="m" id="m" value="printReport13" />
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Просмотр данных (typeAbort)" colspan="1"><label for="typeAbortName" id="typeAbortLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAbort" value="1">  до 21 недель
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeAbort" value="2"  >  22-27 недель
        </td>
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
           checkFieldUpdate('typeAbort','${typeAbort}',1) ;

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
    	frm.action='journal_doc_externalMedService.do' ;
    }
   
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printHistology" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_histology.do' ;
    	$('id').value = $('dateBegin').value+":"
    		+$('dateBegin').value+":"
    		+$('department').value;
    }/*
    function printJournal() {
    	var frm = document.forms[0] ;
    	frm.m.value="printJournalByDay" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_journal001.do' ;
    	$('id').value = 
    		$('dateBegin').value+":"
    		
    		+$('department').value;
    }
    */
    /**/
    if ($('dateBegin').value=="") {
    	$('dateBegin').value=getCurrentDate() ;
    }

			 
    </script>
    <%
    
    String date = request.getParameter("dateBegin") ;
    String dateEnd = request.getParameter("dateEnd") ;
    String period = request.getParameter("period") ;
    String strcode =request.getParameter("strcode") ;
    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
    
    if (date!=null && !date.equals("")) {
    	request.setAttribute("isReportBase", ActionUtil.isReportBase(date, dateEnd,request));
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Свод по возрастам ${reportInfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="true" name="report13swod" nativeSql="
    select vrspt.id||'&strcode='||vrspt.id as vrsptid,vrspt.name,vrspt.strCode ,vrspt.code as vrsptcode,count(distinct so.id) as cntAll
,count(distinct case when (
cast(to_char(sls.dateFinish,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.dateFinish, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.dateFinish,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end))<=14 then so.id else null end ) as age14
,count(distinct case when (cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 15 and 19
then so.id else null end) as age19
,count(distinct case when (cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end)) between 15 and 17 then so.id else null end) as age17
,count(distinct 
case when (
cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end)) between 20 and 24 then so.id else null end) as age24
,count(distinct case when (cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 25 and 29 then so.id else null end) as age29
,count(distinct case when (cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 30 and 34 then so.id else null end) as age34
,count(distinct case when (cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0)
then -1 else 0 end)) between 35 and 39 then so.id else null end) as age39
,count(distinct case when (cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0) then -1 else 0 end)) between 40 and 44 then so.id else null end) as age44
,count(distinct case when (cast(to_char(sls.dateFinish,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(sls.dateFinish, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
when (cast(to_char(sls.dateFinish,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
((cast(to_char(sls.dateFinish, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) between 45 and 49 then so.id else null end) as age49
,count(distinct case when (cast(to_char(sls.dateFinish,'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int)
+case when (cast(to_char(sls.dateFinish, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)) <0 then -1 
when (cast(to_char(sls.dateFinish,'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) and 
((cast(to_char(sls.dateFinish, 'mm') as int)-cast(to_char(p.birthday, 'mm') as int)-1)<0)  then -1 else 0 end
) >=50 then so.id else null end) as age50
from SurgicalOperation so
left join VocAbortion va on so.abortion_id=va.id
left join ReportSetTYpeParameterType rspt on va.code=rspt.codefrom
left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
left join MedCase slo on slo.id=so.medCase_id
left join patient p on p.id=slo.patient_id
left join MedCase sls on sls.id=slo.parent_id
where vrspt.classname='${report}' and
sls.dtype='HospitalMedCase' and sls.dateFinish  between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
group by vrspt.id,vrspt.name,vrspt.strCode,vrspt.code
order by vrspt.strCode
" />
    <msh:table printToExcelButton="Сохранить в excel" name="report13swod"
    viewUrl="stac_report_13.do?typeAbort=${typeAbort}&noViewForm=1&short=Short&period=${dateBegin}-${dateEnd}" 
     action="stac_report_13.do?typeAbort=${typeAbort}&noViewForm=1&period=${dateBegin}-${dateEnd}" idField="1">
      <msh:tableColumn columnName="Наименование" property="2" />
      <msh:tableColumn columnName="№ строки" property="3" />
      <msh:tableColumn columnName="Код МКБ10" property="4" />
      <msh:tableColumn columnName="Кол-во" property="5"/>
      <msh:tableColumn columnName="до 14 лет" property="6"/>
      <msh:tableColumn columnName="15-19" property="7"/>
      <msh:tableColumn columnName="15-17" property="8"/>
      <msh:tableColumn columnName="20-24" property="9"/>
      <msh:tableColumn columnName="25-29" property="10"/>
      <msh:tableColumn columnName="30-34" property="11"/>
      <msh:tableColumn columnName="35-39" property="12"/>
      <msh:tableColumn columnName="40-44" property="13"/>
      <msh:tableColumn columnName="45-49" property="14"/>
      <msh:tableColumn columnName="больше 50 лет" property="15"/>
    </msh:table>
    
    </msh:sectionContent>
    </msh:section>
    <%} else if (period!=null && !period.equals("") 
    && strcode!=null && !strcode.equals("")) {
    	
    	String[] obj = period.split("-") ;
    	String dateBegin=obj[0] ;
    	dateEnd=obj[1];
    	request.setAttribute("dateBegin", dateBegin);
    	request.setAttribute("dateEnd", dateEnd);
    	
    		%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Список пациентов ${param.strname}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="true" name="journal_surOperation" nativeSql="
    select 
so.id as soid
,list(vrspt1.strCode)
,ss.code as sscode
,p.lastname||' '||p.firstname||' '||p.middlename
,sls.dateStart,sls.dateFinish
,cast(to_char(sls.dateFinish,'yyyy') as int)
-cast(to_char(p.birthday,'yyyy') as int)
+(case when (cast(to_char(sls.dateFinish, 'mm') as int)
-cast(to_char(p.birthday, 'mm') as int)
+(case when (cast(to_char(sls.dateFinish,'dd') as int) 
- cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)
<0)
then -1 else 0 end)
 as age
,va.code as vacode,va.name as vaname
,vsp.name as f10_vspName
from SurgicalOperation so
left join vocsurgicalprofile vsp on vsp.id=so.profile_id
left join VocAbortion va on so.abortion_id=va.id
left join ReportSetTYpeParameterType rspt on va.code=rspt.codefrom
left join VocReportSetParameterType vrspt on rspt.parameterType_id=vrspt.id
left join ReportSetTYpeParameterType rspt1 on va.code=rspt1.codefrom
left join VocReportSetParameterType vrspt1 on rspt1.parameterType_id=vrspt1.id
left join MedCase slo on slo.id=so.medCase_id
left join patient p on p.id=slo.patient_id
left join MedCase sls on sls.id=slo.parent_id
left join StatisticStub ss on ss.id=sls.statisticStub_id
where sls.dtype='HospitalMedCase' and sls.dateFinish between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
and vrspt.id='${param.strcode}'
and vrspt1.classname='${report}'
group by so.id
,ss.code,p.lastname,p.firstname,p.middlename,p.birthday,sls.dateStart,sls.dateFinish
,va.code ,va.name,vsp.name
order by p.lastname,p.firstname,p.middlename " />
    <msh:table printToExcelButton="Сохранить в excel" name="journal_surOperation"
    viewUrl="entityShortView-stac_surOperation.do" 
     action="entityView-stac_surOperation.do" idField="1">
      <msh:tableColumn columnName="##" property="sn" />
      <msh:tableColumn columnName="Строки отчета" property="2" />
      <msh:tableColumn columnName="№стат. карт" property="3" />
      <msh:tableColumn columnName="Дата поступления" property="5"/>
      <msh:tableColumn columnName="Дата выписки" property="6"/>
      <msh:tableColumn columnName="Код аборта" property="8"/>
      <msh:tableColumn columnName="Тип аборта" property="9"/>
      <msh:tableColumn columnName="Профиль" property="10"/>
      <msh:tableColumn columnName="ФИО пациента" property="4" />
      <msh:tableColumn columnName="Возраст" property="7" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		
    		<%
    	} else {%>
    	<i>Нет данных </i>
    	<% } %>
    
  </tiles:put>
</tiles:insert>