<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Журнал внешних исследований"/>
  </tiles:put>
  <tiles:put name="side" type="string">

  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  	String noViewForm = request.getParameter("noViewForm") ;
  	String pShort = request.getParameter("short") ;
     if (pShort!=null && pShort.equals("Short")) { 
    	request.setAttribute("url2","<span onclick=\"getDefinition('journal_doc_externalMedService.do?short=Short&period="+request.getParameter("period")+"&typeList="+request.getParameter("typeList")+"&noViewForm="+request.getParameter("noViewForm")+"&id="+request.getParameter("lpuid")+"',event); \"><img width=14 height=14 title='Просмотр записи' alt='Просмотр записи' src='/skin/images/main/view1.png' > просмотр списка специалистов</span>") ;
	 } else { 
		request.setAttribute("url1","<a href='journal_doc_externalMedService.do'> выбрать другие параметры </a>") ;
		request.setAttribute("url2","<a href='journal_doc_externalMedService.do?period="+request.getParameter("period")+"&typeList="+request.getParameter("typeList")+"&noViewForm="+request.getParameter("noViewForm")+"&id="+request.getParameter("lpuid")+"'> выбрать другого специалиста </a>") ;
 	} 

  	ActionUtil.updateParameter("ExternalMedservice","typeList","2", request) ;
  	String typeList = (String)request.getAttribute("typeList") ;
	if (typeList.equals("1")) {
		request.setAttribute("listSql", " and em.patient_id is null ") ;
		request.setAttribute("having", "HAVING COUNT(CASE WHEN patient_id is null THEN ID ELSE NULL END)>0") ;
	} else if (typeList.equals("2")) {
		request.setAttribute("listSql", " and em.patient_id is not null ") ;    		
		request.setAttribute("having", "HAVING COUNT(CASE WHEN patient_id is not null THEN ID ELSE NULL END)>0") ;
	} else {
		
	}
  	if (noViewForm!=null && noViewForm.equals("1")) {
  	} else {
  		
  %>
    <msh:form action="/journal_doc_externalMedService.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="s" id="s" value="HospitalPrintReport" />
    <input type="hidden" name="m" id="m" value="printReport007" />
    <input type="hidden" name="id" id="id" value=""/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Отображать (typeList)" colspan="1"><label for="typeListName" id="typeListLabel">Отобразить:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeList" value="1">  дефекты записи
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeList" value="2">  реестр
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeList" value="3">  все
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
           checkFieldUpdate('typeList','${typeList}',1) ;

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
    function setPatient(aNumberDoc,aOrderDate, aPatient,aTypeList,aPeriod,aLpu,aSpec) {
    	if (confirm('Вы точно хотите установить соответствие с этим пациентом?')) HospitalMedCaseService.setPatientByExternalMedservice(aNumberDoc, aOrderDate, aPatient, {
    		callback: function(aResult) {
    			getDefinition('journal_doc_externalMedService.do?short=Short&typeList='+aTypeList+'&noViewForm=1&period='+aPeriod+'&lpuid='+aLpu+'&id='+aSpec); 
    		}
    	});

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
    
    String date = (String)request.getParameter("dateBegin") ;
    String dateEnd = (String)request.getParameter("dateEnd") ;
    String id = (String)request.getParameter("id") ;
    String period = (String)request.getParameter("period") ;
    
    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
    
    String view = (String)request.getAttribute("typeView") ;
    
    if (date!=null && !date.equals("")) {
        
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Разбивка по отделениям</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="true" name="journal_surOperation" nativeSql="
    select orderlpu,count(*) AS CNTALL,COUNT(CASE WHEN patient_id is null THEN ID ELSE NULL END) AS DEFECTRECORD
,COUNT(CASE WHEN patient_id is null THEN ID ELSE NULL END)*100/COUNT(*)
 from document where 
orderdate between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
group by orderlpu
${having} order by orderlpu " />
    <msh:table name="journal_surOperation" 
    viewUrl="journal_doc_externalMedService.do?short=Short&typeList=${typeList}&noViewForm=1&period=${dateBegin}-${dateEnd}" 
     action="journal_doc_externalMedService.do?noViewForm=1&typeList=${typeList}&period=${dateBegin}-${dateEnd}" idField="1">
      <msh:tableColumn columnName="Направитель ЛПУ" property="1" />
      <msh:tableColumn columnName="Кол-во направлений" property="2" />
      <msh:tableColumn columnName="Кол-во дефектных направлений" property="3" />
      <msh:tableColumn columnName="% дефектных направлений" property="4"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%} 
    
    if (period!=null && !period.equals("")) {
    	String lpuid = (String)request.getParameter("lpuid") ;
    	String[] obj = period.split("-") ;
    	String dateBegin=obj[0] ;
    	dateEnd=obj[1];
    	request.setAttribute("dateBegin", dateBegin);
    	request.setAttribute("dateEnd", dateEnd);
    	
    	if (lpuid==null || lpuid.equals("")) {
    		%>
    <msh:section>
    <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
    </msh:section>
   
    <msh:section>
    <msh:sectionTitle>Разбивка по специалистам ${url1}
    
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery isReportBase="true" name="journal_surOperation" nativeSql="
    select orderer,count(*) AS CNTALL,COUNT(CASE WHEN patient_id is null THEN ID ELSE NULL END) AS DEFECTRECORD
,COUNT(CASE WHEN patient_id is null THEN ID ELSE NULL END)*100/COUNT(*)
 from document where 
orderdate between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy')
    and orderLpu='${param.id}'
group by orderer
${having}
order by orderer " />
    <msh:table name="journal_surOperation" 
    viewUrl="journal_doc_externalMedService.do?short=Short&typeList=${typeList}&noViewForm=1&period=${param.period}&lpuid=${param.id}" 
     action="journal_doc_externalMedService.do?noViewForm=1&typeList=${typeList}&period=${param.period}&lpuid=${param.id}" idField="1">
      <msh:tableColumn columnName="Специалист" property="1" />
      <msh:tableColumn columnName="Кол-во направлений" property="2" />
      <msh:tableColumn columnName="Кол-во дефектных направлений" property="3" />
      <msh:tableColumn columnName="% дефектных направлений" property="4"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>    		
    		<%
    	} else {
    		
    		
    %>
		<msh:section>
		<msh:sectionTitle >
		    Список лабораторных исследований ${url1} ${url2}
		</msh:sectionTitle>
			<ecom:webQuery isReportBase="true" name="list"
			nativeSql="select em.id
			,' Фамилия <b>'||coalesce(em.PatientLastname,'')
			||'</b> \nимя <b>'||coalesce(em.PatientFirstname,'')||'</b> \nотчество <b>'
			||coalesce(em.PatientMiddlename,'')
		||'</b> \nдата рождения <b>'||coalesce(to_char(em.PatientBirthday,'dd.mm.yyyy'),'')||'</b>' as fio,	
		'ЛПУ: '||em.OrderLpu
		||'\nВрач: '||em.Orderer
		||'\nНомер направления: '||em.NumberDoc
		||'\nДата направления: '||to_char(em.OrderDate,'dd.mm.yyyy')
		||'\nВремя направления: '||cast(em.OrderTime as varchar(5))		
		||'\nДата получения результата: '||to_char(em.CreateDate,'dd.mm.yyyy')
		||'\nВремя получения результата: '||cast(em.CreateTime as varchar(5))
			
			as orderInfo
			,em.comment  as emcomment
			,case when em.patient_id is null then (select list('<a href=''javascript:void(0)'' onClick=''setPatient(&quot;'||em.numberDoc||'&quot;,&quot;'||to_char(em.orderDate,'dd.mm.yyyy')||'&quot;,&quot;'||p.id||'&quot;,&quot;${typeList}&quot;,&quot;${param.period}&quot;,&quot;${param.lpuid}&quot;,&quot;${param.id}&quot;);''>'||ss.code||' '||p.lastname||' '||p.firstname||' '||p.middlename||' '||to_char(p.birthday,'dd.mm.yyyy')||'</a>')
	
from medcase sls
left join patient p on p.id=sls.patient_id
left join statisticstub ss on ss.id=sls.statisticStub_id
where sls.dtype='HospitalMedCase' 
and em.orderDate between sls.dateStart and coalesce(sls.dateFinish,current_date)
and substring(p.lastname,1,1)=substring(em.patientlastname,1,1)
and substring(p.firstname,1,1)=substring(em.patientFirstname,1,1)
and substring(p.middlename,1,1)=substring(em.patientmiddlename,1,1)
and sls.deniedHospitalizating_id is null
) 	else 

' Фамилия <b>'||coalesce(p.lastname,'')
			||'</b> \nимя <b>'||coalesce(p.firstname,'')||'</b> \nотчество <b>'
			||coalesce(p.middlename,'')
		||'</b> \nдата рождения <b>'||coalesce(to_char(p.birthday,'dd.mm.yyyy'),'')||'</b>'
 end as infobypatient 
			from Document em 
			left join patient p on p.id=em.patient_id
			where 
			em.dtype='ExternalMedservice' and em.orderdate between to_date('${dateBegin}','dd.mm.yyyy') 
    and to_date('${dateEnd}','dd.mm.yyyy') and em.orderlpu='${param.lpuid}'
    and em.orderer='${param.id}'
				${listSql}
				
				order by em.patientLastname, em.patientFirstname, em.patientMiddlename
				
				" />
			<msh:table name="list" action="javascript:void(0)" idField="1">
				<msh:tableColumn columnName="#" property="sn" />
				<msh:tableColumn columnName="ИД" property="1" />
				<msh:tableColumn property="2" columnName="ФИО пациента" cssClass="preCell"/>
				<msh:tableColumn property="5" columnName="Возможный" cssClass="preCell"/>
				<msh:tableColumn property="3" columnName="Направление" cssClass="preCell"/>
				<msh:tableColumn property="4" columnName="Описание" cssClass="preCell"/>
			</msh:table>
		</msh:section>    
   <% 
    	}
   } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    
  </tiles:put>
</tiles:insert>