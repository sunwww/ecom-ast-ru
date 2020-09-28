<%@page import="ru.ecom.mis.ejb.service.patient.HospitalLibrary"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Poly" title="Реестр" property="worker" />
    
  </tiles:put>
    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="reportNationality"/>
    </tiles:put>
  <tiles:put name="body" type="string">
  	
  <%
	String typeEmergency =ActionUtil.updateParameter("Report_hospital_standart","typeEmergency","3", request) ;
	String typePatient =ActionUtil.updateParameter("Report_hospital_standart","typePatient","1", request) ;
  	String id = request.getParameter("id") ;
  	String[] params = id.split(":") ;
  	request.setAttribute("dateStart", params[1]) ;
  	request.setAttribute("dateEnd", params[2]) ;
  	request.setAttribute("nationality", params[0].equals("0")?" is null ":"='"+params[0]+"'") ;
  	if (typeEmergency!=null && typeEmergency.equals("1")) {
   		request.setAttribute("emergencySql", " and v.emergency='1' ") ;
   	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
   		request.setAttribute("emergencySql", " and (v.emergency is null or v.emergency='0') ") ;
   	} 
	%> 	
  	<msh:section title="Поликлиника">

  	
	    <ecom:webQuery name="list_yes" nativeSql="select v.id
	    
	    ,to_char(v.dateStart,'DD.MM.YYYY')||' '||cast(v.timeExecute as varchar(5)) as dateStart

	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,vwfe.name||' '||pe.lastname as pefio

from medcase v 
left join patient p on p.id=v.patient_id
left join WorkFunction wfe on wfe.id=v.workFunctionExecute_id
left join Worker we on we.id=wfe.worker_id
left join Patient pe on pe.id=we.person_id
left join VocWorkFunction vwfe on vwfe.id=wfe.workFunction_id
left join VocVisitResult vvr on vvr.id=v.visitResult_id
where  v.dateStart between between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
and (m.dtype='Visit' or m.dtype='ShortMedCase') and v.dateStart is not null
and (v.noActuality is null or v.noActuality='0')
and p.nationality_id ${nationality} ${emergencySql}
order by p.lastname,p.firstname,p.middlename"/>
<msh:table viewUrl="entitySubclassView-smo_visit.do" name="list_yes" action="entitySublassView-mis_medCase.do" idField="1">
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Пациент" property="3" />
	      <msh:tableColumn columnName="Дата" identificator="false" property="2" />
	      <msh:tableColumn columnName="Исполнитель" identificator="false" property="4" />
	    </msh:table>
  	</msh:section>
  	<msh:section title="Стационар">

  	
	    <ecom:webQuery name="list_stac" nativeSql="select v.id
	    
	    ,to_char(v.dateStart,'DD.MM.YYYY') as dateStart
	    ,to_char(v.dateFinish,'DD.MM.YYYY') as dateFinish
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,ss.code as sscode 
	    ,ml.name as mlname
from medcase v 
left join patient p on p.id=v.patient_id
left join statisticstub ss on ss.id=v.statisticStub_id
left join mislpu ml on ml.id=v.department_id
where  v.dateStart between between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
and v.DTYPE='HospitalMedCase' and v.dateStart is not null
and (v.noActuality is null or v.noActuality='0')
and v.deniedHospitalizating_id is null
and p.nationality_id ${nationality} ${emergencySql}
order by p.lastname,p.firstname,p.middlename"/>
<msh:table viewUrl="entityShortView-stac_ssl.do" 
 name="list_stac"
 action="entityView-stac_ssl.do" idField="1" >
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Номер стат.карты" property="5" />
	      <msh:tableColumn columnName="Пациент" property="4" />
	      <msh:tableColumn columnName="Дата поступления" property="2" />
	      <msh:tableColumn columnName="Дата выписки" identificator="false" property="3" />
	      <msh:tableColumn property="6" columnName="Отделение"/>
	    </msh:table>
  	</msh:section>
  	<msh:section title="Отказы от госпитализаций">

  	
	    <ecom:webQuery name="list_stac1" nativeSql="select v.id
	    
	    ,to_char(v.dateStart,'DD.MM.YYYY') as dateStart
	    ,to_char(v.dateFinish,'DD.MM.YYYY') as dateFinish
	    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'DD.MM.YYYY') as pfio
	    ,ss.code as sscode 

from medcase v 
left join patient p on p.id=v.patient_id
left join statisticstub ss on ss.id=v.statisticStub_id
where  v.dateStart between to_date('${beginDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
and v.DTYPE='HospitalMedCase' and v.dateStart is not null
and (v.noActuality is null or v.noActuality='0')
and v.deniedHospitalizating_id is not null
and p.nationality_id ${nationality} ${emergencySql}
order by p.lastname,p.firstname,p.middlename"/>
<msh:table viewUrl="entityShortView-stac_ssl.do" 
 name="list_stac1"
 action="entityView-stac_ssl.do" idField="1" >
	      <msh:tableColumn columnName="№" identificator="false" property="sn" />
	      <msh:tableColumn columnName="Пациент" property="4" />
	      <msh:tableColumn columnName="Дата обращения" property="2" />
	    </msh:table>
  	</msh:section>  	
  </tiles:put>
  
</tiles:insert>

