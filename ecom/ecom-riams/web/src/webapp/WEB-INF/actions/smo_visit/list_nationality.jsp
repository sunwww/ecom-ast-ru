<%@page import="ru.ecom.mis.ejb.service.patient.HospitalLibrary"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Report">Просмотр отчета по гражданству </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <%
	String typeEmergency =ActionUtil.updateParameter("Report_nationality","typeEmergency","3", request) ;
	String typePatient =ActionUtil.updateParameter("Report_nationality","typePatient","1", request) ;
	String typeGroup =ActionUtil.updateParameter("Report_nationality","typeGroup","2", request) ;
	String typeView =ActionUtil.updateParameter("Report_nationality","typeView","3", request) ;

  %>
    <msh:form action="/journal_nationality_smo.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET">
    <input type="hidden" name="m" id="m" value="categoryForeignNationals"/>
    <input type="hidden" name="s" id="s" value="VisitPrintService"/>
    <input type="hidden" name="id" id="id"/>
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
        <msh:row styleId="noswod">
	        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="1">  экстренные
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="2"  >  плановые
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeEmergency" value="3">  все
	        </td>
        </msh:row>
      <msh:row>
        <td class="label" title="Поиск по пациентам (typeGroup)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeGroup" value="1">  по отделениям
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeGroup" value="2">  по гражданству
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeGroup" value="3">  по потоку обслуживания
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Отображать (typeView)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Отображать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1">  реестр обращений
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="2">  реестр пациентов
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="3">  свод
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  иностранцы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  соотечественники
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  иногородние
        </td>
      </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="4">  без адреса
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typePatient" value="5">  иностранцы+соотечественники
        </td>
      </msh:row>
       <msh:row>
       	<msh:autoComplete property="department" fieldColSpan="4"
       	label="Отделение" horizontalFill="true" vocName="lpu"/>
       </msh:row>
        <msh:row>
        	<msh:autoComplete property="nationality" fieldColSpan="4"
        	label="Гражданство" horizontalFill="true" vocName="omcOksm"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="serviceStream" fieldColSpan="4"
        	label="Поток облуживания" horizontalFill="true" vocName="vocServiceStream"/>
        </msh:row>
      <msh:row>
        	<msh:textField property="beginDate"  label="Период с" />
        	<msh:textField property="finishDate" fieldColSpan="7" label="по" />
        </msh:row>
        <msh:row>
        <td colspan="3" class="buttons">
			<input type="button" title="Найти" onclick="this.value=&quot;Поиск...&quot;;  this.form.action=&quot;journal_nationality_smo.do&quot;;this.form.target=&quot;&quot; ; this.form.submit(); return true ;" value="Найти" class="default" id="submitButton" autocomplete="off">
 		</td>
        
        </msh:row>

    </msh:panel>
    </msh:form>
    
    <%
    	if (request.getParameter("beginDate")!=null && request.getParameter("finishDate")!=null
    	 || request.getParameter("id")!=null && !request.getParameter("id").equals("")
    	) {
            request.setAttribute("isReportBase", ActionUtil.isReportBase(request.getParameter("beginDate"),request.getParameter("finishDate"),request));
        	if (typeEmergency!=null && typeEmergency.equals("1")) {
        		request.setAttribute("emergencySql", " and m.emergency='1' ") ;
        		request.setAttribute("emergencyInfo", ", поступивших по экстренным показаниям") ;
        		request.setAttribute("emergencyTicketSql", " and t.emergency='1' ") ;
        	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
        		request.setAttribute("emergencySql", " and (m.emergency is null or m.emergency='0') ") ;
        		request.setAttribute("emergencyInfo", ", поступивших по плановым показаниям") ;
        		request.setAttribute("emergencyTicketSql", " and (t.emergency is null or t.emergency='0') ") ;
        	} 
        	if (typePatient.equals("1")) {
    			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
    			request.setAttribute("patientSql", HospitalLibrary.getSqlGringo(true, "vn")) ;
    			request.setAttribute("infoTypePat", "Поиск по иностранцам") ;
        	} else if (typePatient.equals("2")) {
    			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
    			request.setAttribute("patientSql", " and (p.isCompatriot='1' or vn.isCompatriot='1')") ;
    			request.setAttribute("infoTypePat", "Поиск по соотечественникам") ;
        	} else if (typePatient.equals("3")) {
    			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
    			request.setAttribute("patientSql", " and a.kladr not like '30%' and (vn.id is null or vn.voc_code='643')") ;
    			request.setAttribute("infoTypePat", "Поиск по иногородним") ;
        	} else if (typePatient.equals("4")) {
    			//aRequest.setAttribute("add", "and $$isForeignPatient^ZExpCheck(m.patient_id,m.dateStart)=0") ;
    			request.setAttribute("patientSql", " and a.kladr is null and (vn.id is null or vn.voc_code='643')") ;
    			request.setAttribute("infoTypePat", "Поиск по иногородним") ;
    		} else {
    			request.setAttribute("patientSql", "") ;
    			request.setAttribute("infoTypePat", "Поиск по всем") ;
    		}
        	
        	if (typeGroup!=null&&typeGroup.equals("1")) {
    			request.setAttribute("groupSql", "coalesce(mlV.name,ml.name)") ;
    			request.setAttribute("groupSqlId", "coalesce(mlV.id,ml.id)") ;
    			request.setAttribute("groupId", "'&department='||coalesce(mlV.id,ml.id)") ;
    			request.setAttribute("groupName", "Наименование отделения") ;
    			request.setAttribute("group1Sql", "ml.name") ;
    			request.setAttribute("group1SqlId", "ml.id") ;
    			request.setAttribute("group1Id", "'&department='||ml.id") ;
        	} else if (typeGroup!=null&&typeGroup.equals("3")) {
    			request.setAttribute("groupSql", "vss.name") ;
    			request.setAttribute("groupSqlId", "vss.id") ;
    			request.setAttribute("groupId", "'&serviceStream='||vss.id") ;
    			request.setAttribute("groupName", "Поток обслуживания") ;
    			request.setAttribute("group1Sql", "vss.name") ;
    			request.setAttribute("group1SqlId", "vss.id") ;
    			request.setAttribute("group1Id", "'&serviceStream='||vss.id") ;
        	} else {
        		if (!typePatient.equals("3")) {
	    			request.setAttribute("groupSql", "vn.name") ;
	    			request.setAttribute("groupSqlId", "coalesce(p.nationality_id,0)") ;
	    			request.setAttribute("groupId", "'&nationality='||coalesce(p.nationality_id,0)") ;
	    			request.setAttribute("group1Sql", "vn.name") ;
	    			request.setAttribute("group1SqlId", "coalesce(p.nationality_id,0)") ;
	    			request.setAttribute("group1Id", "'&nationality='||coalesce(p.nationality_id,0)") ;
	    			request.setAttribute("groupName", "Гражданство") ;
        		} else {
	    			request.setAttribute("groupSql", "ar.name") ;
	    			request.setAttribute("groupSqlId", "coalesce(a.region_addressid,0)") ;
	    			request.setAttribute("groupId", "'&region='||coalesce(a.region_addressid,0)") ;
	    			request.setAttribute("group1Sql", "ar.name") ;
	    			request.setAttribute("group1SqlId", "coalesce(ap.region_addressid,0)") ;
	    			request.setAttribute("group1Id", "'&region='||coalesce(a.region_addressid,0)") ;
	    			request.setAttribute("groupName", "Район") ;
	    			request.setAttribute("groupSqlAdd", "left join Address2 ar on ar.addressid=a.region_addressid") ;
        		}
        	}
        	ActionUtil.setParameterFilterSql("serviceStream","vss.id", request) ;
        	ActionUtil.setParameterFilterSql("department","m.department_id", request) ;
        	ActionUtil.setParameterFilterSql("department","departmentD","m.department_id", request) ;
        	ActionUtil.setParameterFilterSql("department","departmentWF","we.lpu_id", request) ;
        	ActionUtil.setParameterFilterSql("nationality","p.nationality_id", request) ;
        	ActionUtil.setParameterFilterSql("region","a.region_addressid", request) ;
    		%>
   
    <% 
    //начало реестра
    
    if (typeView.equals("1")) {%>


  	<msh:section title="Поликлиника">

  	
	    <ecom:webQuery isReportBase="${isReportBase}" name="list_yes" nameFldSql="list_yes_sql" maxResult="100000" nativeSql="select m.id
	    
	    ,to_char(m.dateStart,'DD.MM.YYYY')||' '||cast(m.timeExecute as varchar(5)) as dateStart

	    ,p.lastname||' '||p.firstname||' '||p.middlename as fio,to_char(p.birthday,'DD.MM.YYYY') as birthday
	    ,vwfe.name||' '||pe.lastname as pefio
	    ,list(mkb.code) as mkbcode
	    ,vss.name
from medcase m 
left join patient p on p.id=m.patient_id
left join address2 a on a.addressid=p.address_addressid
left join Omc_Oksm vn on vn.id=p.nationality_id
left join WorkFunction wfe on wfe.id=m.workFunctionExecute_id
left join Worker we on we.id=wfe.worker_id
left join MisLpu ml on ml.id=we.lpu_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
left join VocVisitResult vvr on vvr.id=m.visitResult_id
left join VocServiceStream vss on vss.id=m.serviceStream_id
left join Diagnosis d on d.medcase_id=m.id
left join Vocidc10 mkb on mkb.id=d.idc10_id
${groupSqlAdd}
where  m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and (m.DTYPE='Visit' or m.DTYPE='ShortMedCase')  
and (m.noActuality is null or m.noActuality='0')
${emergencySql} ${departmentWFSql}
${serviceStreamSql}
 ${nationalitySql} ${regionSql} ${patientSql}
group by m.id,m.dateStart,m.timeExecute
	    ,p.lastname,p.firstname,p.middlename,p.birthday
	    ,vwfe.name,pe.lastname,vss.name
order by p.lastname,p.firstname,p.middlename"/>
<msh:table printToExcelButton="Сохранить в excel" viewUrl="entityView-mis_medCase.do?short=Short" name="list_yes" action="entitySubclassView-mis_medCase.do"
	idField="1">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Пациент" property="3" />
	      <msh:tableColumn columnName="Дата рождения" property="4" />
	      <msh:tableColumn columnName="Дата обращения" identificator="false" property="2" />
	      <msh:tableColumn columnName="Диагноз" identificator="false" property="6" />
	      <msh:tableColumn columnName="Специалист" identificator="false" property="5" />
    	  <msh:tableColumn columnName="Поток обслуживания" property="7"/>
	    </msh:table>
  	</msh:section>
  	<msh:section title="Стационар">

  	
	    <ecom:webQuery name="list_stac" maxResult="100000" nativeSql="select smo.id
	    
	    ,to_char(smo.dateStart,'DD.MM.YYYY') as dateStart
	    ,to_char(smo.dateFinish,'DD.MM.YYYY') as dateFinish
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,ss.code as sscode 
	    ,ml.name as mlname,vss.name as vssname
from medcase m 
left join medcase smo on smo.id=m.parent_id
left join patient p on p.id=m.patient_id
left join address2 a on a.addressid=p.address_addressid
${groupSqlAdd}
left join Omc_Oksm vn on vn.id=p.nationality_id
left join statisticstub ss on ss.id=smo.statisticStub_id
left join mislpu ml on ml.id=m.department_id
left join VocServiceStream vss on vss.id=smo.serviceStream_id
where  
m.DTYPE='DepartmentMedCase' and m.dateFinish between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and (m.noActuality is null or m.noActuality='0')
and smo.deniedHospitalizating_id is null
${emergencySql} ${departmentSql} 
${serviceStreamSql}
${nationalitySql} ${regionSql} ${patientSql}
order by p.lastname,p.firstname,p.middlename"/>
<msh:table printToExcelButton="Сохранить в excel" viewUrl="entityShortView-stac_ssl.do"
 name="list_stac"
 action="entityView-stac_ssl.do" idField="1" >
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Номер стат.карты" property="5" />
	      <msh:tableColumn columnName="Пациент" property="4" />
	      <msh:tableColumn columnName="Дата поступления" property="2" />
	      <msh:tableColumn columnName="Дата выписки" identificator="false" property="3" />
	      <msh:tableColumn property="6" columnName="Отделение"/>
	      <msh:tableColumn property="7" columnName="Поток обслуживания"/>
	    </msh:table>
  	</msh:section>
  	<msh:section title="Отказы от госпитализаций">

  	
	    <ecom:webQuery isReportBase="${isReportBase}" name="list_stac1" maxResult="100000" nativeSql="select m.id
	    
	    ,to_char(m.dateStart,'DD.MM.YYYY') as dateStart
	    ,to_char(m.dateFinish,'DD.MM.YYYY') as dateFinish
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,ss.code as sscode
	    ,vss.name as f6_serviceStream

from medcase m 
left join patient p on p.id=m.patient_id
left join address2 a on a.addressid=p.address_addressid
${groupSqlAdd}
left join Omc_Oksm vn on vn.id=p.nationality_id
left join statisticstub ss on ss.id=m.statisticStub_id
left join MisLpu ml on ml.id=m.department_id
left join VocServiceStream vss on vss.id=m.serviceStream_id
where  m.DTYPE='HospitalMedCase'
and m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and (m.noActuality is null or m.noActuality='0')
and m.deniedHospitalizating_id is not null
${emergencySql} ${departmentSql} 
${serviceStreamSql}
${nationalitySql} ${regionSql} ${patientSql}
order by p.lastname,p.firstname,p.middlename"/>
<msh:table printToExcelButton="Сохранить в excel" viewUrl="entityShortView-stac_ssl.do"
 name="list_stac1"
 action="entityView-stac_ssl.do" idField="1" >
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Пациент" property="4" />
	      <msh:tableColumn columnName="Дата обращения" property="2" />
	      <msh:tableColumn columnName="Поток обслуживания" property="5" />
	    </msh:table>
  	</msh:section>  	



    <% 
    //окончание реестра
    } else if (typeView.equals("2")) {
	%>

      	<msh:section title="Поликлиника">

      	
    	    <ecom:webQuery isReportBase="${isReportBase}" name="list_yes" maxResult="100000" nativeSql="select
    	    p.id as pid
    	    ,count(distinct m.id)

    	    ,p.lastname||' '||p.firstname||' '||p.middlename as fio,to_char(p.birthday,'DD.MM.YYYY') as birthday
    	    ,vwfe.name||' '||pe.lastname as pefio
    	    ,vn.name as vnname
    	    ,a.fullname
    	    ,list(distinct mkb.code) as mkbcode
    	    ,vss.name
    from medcase m 
    left join patient p on p.id=m.patient_id
    left join address2 a on a.addressid=p.address_addressid
${groupSqlAdd}
    left join Omc_Oksm vn on vn.id=p.nationality_id
    left join WorkFunction wfe on wfe.id=m.workFunctionExecute_id
    left join Worker we on we.id=wfe.worker_id
    left join MisLpu ml on ml.id=we.lpu_id
    left join Patient pe on pe.id=we.person_id
    left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
    left join VocVisitResult vvr on vvr.id=m.visitResult_id
    left join VocServiceStream vss on vss.id=m.serviceStream_id
    left join Diagnosis d on d.medcase_id=m.id
    left join Vocidc10 mkb on mkb.id=d.idc10_id
    where  m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
    and (m.DTYPE='Visit' or m.DTYPE='ShortMedCase')  
    and (m.noActuality is null or m.noActuality='0')
    ${emergencySql} ${departmentWFSql}
    ${serviceStreamSql}
     ${nationalitySql} ${regionSql} ${patientSql}
    group by p.id,p.lastname,p.firstname,p.middlename,p.birthday
    	    ,vwfe.name,pe.lastname , vn.name,a.fullname,vss.name
    order by p.lastname,p.firstname,p.middlename"/>
    <msh:table printToExcelButton="Сохранить в excel" name="list_yes" action="entityView-mis_patient.do"
    	viewUrl="entityShortView-mis_patient.do" 
    	idField="1">
    	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
    	      <msh:tableColumn columnName="Пациент" property="3" />
    	      <msh:tableColumn columnName="Дата рождения" property="4" />
    	      <msh:tableColumn columnName="Кол-во обращения" property="2" />
    	      <msh:tableColumn columnName="Диагноз" property="8" />
    	      <msh:tableColumn columnName="Гражданство" identificator="false" property="6" />
    	      <msh:tableColumn columnName="Адрес проживания" identificator="false" property="7" />
    	      <msh:tableColumn columnName="Специалист" identificator="false" property="5" />
    	      <msh:tableColumn columnName="Поток обслуживания" property="9"/>
    	    </msh:table>
      	</msh:section>
      	<msh:section title="Стационар">

      	
    	    <ecom:webQuery isReportBase="${isReportBase}" name="list_stac" maxResult="100000" nativeSql="select
    	    p.id as pid
    	    ,p.lastname||' '||p.firstname||' '||p.middlename as fio
    	    ,to_char(p.birthday,'DD.MM.YYYY') as birthday
    	    
    	    ,list(ml.name) as mlname
    	    ,list(ss.code) as sscode
    	    ,list(vss.name) as vssname
    	    ,vn.name as vnname
    	    ,a.fullname as afullname
    from medcase m 
    left join medcase smo on smo.id=m.parent_id
    left join patient p on p.id=m.patient_id
    left join address2 a on a.addressid=p.address_addressid
    ${groupSqlAdd}
    left join Omc_Oksm vn on vn.id=p.nationality_id
    left join statisticstub ss on ss.id=smo.statisticStub_id
    left join mislpu ml on ml.id=m.department_id
    left join VocServiceStream vss on vss.id=smo.serviceStream_id
    where  
    m.DTYPE='DepartmentMedCase' and m.dateFinish between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
    and (m.noActuality is null or m.noActuality='0')
    and smo.deniedHospitalizating_id is null
    ${emergencySql} ${departmentSql} 
    ${serviceStreamSql}
    ${nationalitySql} ${regionSql} ${patientSql}
    group by p.id,p.lastname,p.firstname,p.middlename 
    ,p.birthday,vn.name ,a.fullname
    order by p.lastname,p.firstname,p.middlename"/>
    <msh:table printToExcelButton="Сохранить в excel" viewUrl="entityShortView-stac_ssl.do"
     name="list_stac"
     action="entityView-mis_patient.do" idField="1" >
    	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
    	      <msh:tableColumn columnName="№№ стат.карт" property="5" />
    	      <msh:tableColumn columnName="Пациент" property="2" />
    	      <msh:tableColumn columnName="Дата рождения" property="3" />
    	      <msh:tableColumn columnName="Гражданство" identificator="false" property="7" />
    	      <msh:tableColumn columnName="Адрес проживания" identificator="false" property="8" />
    	      <msh:tableColumn columnName="Отделения" identificator="false" property="4" />
    	      <msh:tableColumn property="6" columnName="Потоки обслуживания"/>
    	    </msh:table>
      	</msh:section>
      	<msh:section title="Отказы от госпитализаций">

      	
    	    <ecom:webQuery isReportBase="${isReportBase}" name="list_stac1" maxResult="100000" nativeSql="select p.id
    	    ,p.lastname||' '||p.firstname||' '||p.middlename as fio
    	    ,to_char(p.birthday,'DD.MM.YYYY') as birthday
    	    ,vn.name as vnname
    	    ,a.fullname as afullname
    	    ,list(to_char(m.datestart,'dd.mm.yyyy')||vdh.name) as denhosp
    	    ,list(vss.name) as f7_ss
    	    
    from medcase m 
    left join patient p on p.id=m.patient_id
    left join address2 a on a.addressid=p.address_addressid
    ${groupSqlAdd}
    left join Omc_Oksm vn on vn.id=p.nationality_id
    left join statisticstub ss on ss.id=m.statisticStub_id
    left join MisLpu ml on ml.id=m.department_id
    left join VocServiceStream vss on vss.id=m.serviceStream_id
    left join VocDeniedHospitalizating vdh on vdh.id=m.deniedHospitalizating_id
    where  m.DTYPE='HospitalMedCase'
    and m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
    and (m.noActuality is null or m.noActuality='0')
    and m.deniedHospitalizating_id is not null
    ${emergencySql} ${departmentSql} 
    ${serviceStreamSql}
    ${nationalitySql} ${regionSql} ${patientSql}
    group by p.id,p.lastname,p.firstname,p.middlename
    	    ,p.birthday ,vn.name ,a.fullname
    order by p.lastname,p.firstname,p.middlename"/>
    <msh:table printToExcelButton="Сохранить в excel" viewUrl="entityShortView-mis_patient.do"
     name="list_stac1" 
     action="entityView-mis_patient.do" idField="1" >
    	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
    	      <msh:tableColumn columnName="Пациент" property="2" />
    	      <msh:tableColumn columnName="Дата рождения" property="3" />
    	      <msh:tableColumn columnName="Гражданство" property="4" />
    	      <msh:tableColumn columnName="Адрес" property="5" />
    	      <msh:tableColumn columnName="Дата и причина отказов" property="6" />
    	      <msh:tableColumn columnName="Поток обслуживания" property="7" />
    	    </msh:table>
      	</msh:section>  	
	<%    	
    } else {
    	// начало свода
    	%>
    	
    <msh:section>
<ecom:webQuery isReportBase="${isReportBase}" nameFldSql="sql_journal_swod" name="journal_swod" nativeSql="
select ${groupId}||${departmentSqlId}||${nationalitySqlId}||${serviceStreamSqlId} as idparam,${groupSql} as vnname
,count(*) as cntAll
,count(distinct case when (m.dtype='Visit' or m.dtype='ShortMedCase') then m.id else null end) as polic
,count(distinct case when (m.dtype='Visit' or m.dtype='ShortMedCase') and vss.code='CHARGED' then m.id else null end) as policCh
,count(distinct case when (m.dtype='Visit' or m.dtype='ShortMedCase') and vss.code!='CHARGED' then m.id else null end) as policOther
,list(distinct case when (m.dtype='Visit' or m.dtype='ShortMedCase') and vss.code!='CHARGED' then vss.name else null end) as policOtherServiceStream

,count(distinct case when m.dtype='DepartmentMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') then m.id else null end) as hospitAll
,sum(case when m.dtype='DepartmentMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') then case when smo.dateFinish=smo.dateStart then 1 else smo.dateFinish-smo.dateStart end else null end) as hospitDaysAll
,count(distinct case when m.dtype='DepartmentMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code='CHARGED' then m.id else null end) as hospitAllCh
,sum( case when m.dtype='DepartmentMedCase' and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code='CHARGED' then case when smo.dateFinish=smo.dateStart then 1 else smo.dateFinish-smo.dateStart end else null end) as hospitAllDaysCh
,count(distinct case when m.dtype='DepartmentMedCase' 
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then m.id else null end) as cntNoChan
,list(distinct case when m.dtype='DepartmentMedCase'  
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then vss.name else null end) as listVssNoChan
,case when 
count(distinct case when m.dtype='DepartmentMedCase' 
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then m.id else null end)>0
then 
cast(round(1.0*sum(case when m.dtype='DepartmentMedCase' 
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then smo.dateFinish-smo.dateStart+1 else null end)
/
count(distinct case when m.dtype='DepartmentMedCase'  
and (m.hospType_id is null or vht.code='ALLTIMEHOSP') and vss.code!='CHARGED' then m.id else null end)
,2) as numeric)
else null 
end as srDaysNoCh
,count(distinct case when m.dtype='DepartmentMedCase' and vht.code='DAYTIMEHOSP' then m.id else null end) as hospitDn
,sum(case when m.dtype='DepartmentMedCase' and vht.code='DAYTIMEHOSP' then smo.dateFinish-smo.dateStart+1 else null end) as hospitDaysDn
,count(distinct case when m.dtype='DepartmentMedCase' and vht.code='DAYTIMEHOSP'and vss.code='CHARGED' then m.id else null end) as hospitDnCh
,sum(case when m.dtype='DepartmentMedCase' and vht.code='DAYTIMEHOSP'and vss.code='CHARGED' then smo.dateFinish-smo.dateStart+1 else null end) as hospitDnDaysCh
,count(distinct case when m.dtype='HospitalMedCase' and m.deniedHospitalizating_id is not null then m.id else null end) as hospitDenied
,count(distinct smo.patient_id) as CountPatient
from medcase m
left join medcase smo on smo.id=m.parent_id
left join patient p on p.id=m.patient_id
left join address2 a on a.addressid=p.address_addressid
left join Omc_Oksm vn on vn.id=p.nationality_id
left join VocHospType vht on vht.id=m.hospType_id
left join VocServiceStream vss on vss.id=m.serviceStream_id
${groupSqlAdd}
left join WorkFunction wfe on wfe.id=m.workFunctionExecute_id 
left join Worker we on we.id=wfe.worker_id
left join MisLpu mlV on mlV.id=we.lpu_id
left join MisLpu ml on ml.id=m.department_id

where ((m.dtype='Visit' or m.dtype='ShortMedCase') 
and m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
${departmentWFSql}

or m.dtype='DepartmentMedCase' ${departmentSql} 
and m.dateFinish between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')

or m.dtype='HospitalMedCase' ${departmentSql} 
and m.dateStart between to_date('${param.beginDate}','dd.mm.yyyy') and to_date('${param.finishDate}','dd.mm.yyyy')
and m.deniedHospitalizating_id is not null
) 
and (m.noActuality is null or m.noActuality='0') ${emergencySql}
 ${serviceStreamSql} ${patientSql} ${nationalitySql} ${regionSql} 
group by ${groupSqlId},${groupSql}

" />


    <msh:sectionTitle>Период с ${param.beginDate} по ${param.finishDate}${emergencyInfo}
    <form action="print-report_categoryForeignNationals.do" method="post" target="_blank">
    <input type='hidden' name="sqlText1" id="sqlText1" value="${sql_journal_swod}"> 
    <input type='hidden' name="sqlText2" id="sqlText2" value="${sql_journal_swod1}">
    <input type='hidden' name="sqlCount" id="sqlCount" value="1">
    <input type='hidden' name="sqlInfo1" id="sqlInfo1" value="${param.beginDate}-${param.finishDate}${emergencyInfo}.">
    <input type='hidden' name="sqlInfo2" id="sqlInfo2" value="${groupName}">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printManyNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
        <msh:table printToExcelButton="Сохранить в excel"
         name="journal_swod" action="journal_nationality_smo.do?beginDate=${param.beginDate}&finishDate=${param.finishDate}&typeView=1&typeGroup=${typeGroup}&typePatient=${typePatient}&typeEmergency=${typeEmergency}" idField="1" noDataMessage="Не найдено">
            <msh:tableNotEmpty>
              <tr>
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="1" />
                <th colspan="4" class="rightBold">Амбулаторно-поликлиническая помощь</th>
                <th colspan="7" class="rightBold">Стационарная медицинская помощь</th>
                <th colspan="4" class="rightBold">Стационарно-замещающая медицинская помощь</th>
                <th colspan="1" />
                <th colspan="1" />
              </tr>
            </msh:tableNotEmpty>            
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="${groupName}" property="2"/>            
            <msh:tableColumn columnName="Общее кол-во" property="3" isCalcAmount="true"/>
             <msh:tableColumn columnName="общее кол-во пациентов" property="20" isCalcAmount="true"/>
            <msh:tableColumn columnName="всего" property="4" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. платно" property="5" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. др. потоки" property="6" isCalcAmount="true"/>
            <msh:tableColumn columnName="потоки обс." property="7"/>
            <msh:tableColumn columnName="всего" property="8" isCalcAmount="true"/>
            <msh:tableColumn columnName="к.дней" property="9" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. платно" property="10" isCalcAmount="true"/>
            <msh:tableColumn columnName="к.дней" property="11" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. др. потоки" property="12" isCalcAmount="true"/>
            <msh:tableColumn columnName="потоки обс." property="13"/>
            <msh:tableColumn columnName="сред. к.дней" property="14"/>
            <msh:tableColumn columnName="всего" property="15" isCalcAmount="true"/>
            <msh:tableColumn columnName="к.дней" property="16" isCalcAmount="true"/>
            <msh:tableColumn columnName="в т.ч. платно" property="17" isCalcAmount="true"/>
            <msh:tableColumn columnName="к.дней" property="18" isCalcAmount="true"/>
            <msh:tableColumn columnName="отказы от госп." property="19" isCalcAmount="true"/> 
           
        </msh:table>
    </msh:sectionContent>
    
    </msh:section>    	
    	<%
    	//окончание свода
    }
    	} else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
  	checkFieldUpdate('typeView','${typeView}',2) ;
  	checkFieldUpdate('typeGroup','${typeGroup}',2) ;
  	//checkFieldUpdate('typeDate','${typeDate}',2) ;
  	//checkFieldUpdate('typeUser','${typeUser}',3) ;
    checkFieldUpdate('typePatient','${typePatient}',1) ;
    checkFieldUpdate('typeEmergency','${typeEmergency}',4) ;

  	function checkFieldUpdate(aField,aValue,aDefault) {
  		aValue=+aValue ;
    	eval('var chk =  document.forms[0].'+aField) ;
    	max = chk.length ;
    	if (aValue<1) aValue=+aDefault ;
    	if (aValue>max) {
    		if (aDefault>max) {
    			chk[max-1].checked='checked' ;
    		} else {
    			chk[aDefault-1].checked='checked' ;
    		}
    	} else {
    		chk[aValue-1].checked='checked' ;
    	}
    }
    
  	function getId(aBis) {
		 
		
	}
  	function getCheckedValue(radioGrp) {
  		var radioValue ;
  		for(i=0; i < radioGrp.length; i++){
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