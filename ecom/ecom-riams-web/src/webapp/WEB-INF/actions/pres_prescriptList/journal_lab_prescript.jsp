<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Patient" guid="65127a6f-d6d3-4b8e-b436-c6aeeaea35ae" title="Забор биоматериала для лабораторных исследований" />
   
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Показать" guid="a47dfc0b-97d1-4cb5-b904-4ff717e612a7" />
    <msh:sideMenu title="Добавить" guid="60616958-11ef-48b0-bec7-f6b1d0b8463f">
      <msh:sideLink roles="/Policy/Mis/Prescription/Create" key="ALT+N" action="/entityParentPrepareCreate-pres_prescriptList" name="Сводный  лист назначений" guid="1faa5477-419b-4f77-8379-232e33a61922" params="id" />
      <msh:sideLink roles="/Policy/Mis/Prescription/Create" key="ALT+4" action=".javascript:shownewTemplatePrescription(1,&quot;.do&quot;)" name="ЛН из шаблона" guid="2a2c0ab6-4a46-41f7-8221-264de893815c" title="Добавить лист назначений из шаблона" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<%
  	String username = LoginInfo.find(request.getSession(true)).getUsername() ;
  	ActionUtil.getValueBySql("select lpu.id,lpu.name from mislpu lpu left join worker w on w.lpu_id=lpu.id left join workfunction wf on wf.worker_id=w.id left join secuser su on su.id=wf.secuser_id where su.login='"+username+"'", "lpu_id","lpu_name",request) ;
  	Object lpu = request.getAttribute("lpu_id") ;
  	request.setAttribute("beginDate", "01.01.2014") ;
  	request.setAttribute("endDate", "01.12.2014") ;
  	if (lpu!=null && !lpu.equals("")) {
  	%>
    <msh:section>
    <ecom:webQuery name="list" nativeSql="
    select w.lpu_id||'&service='||ms.id as id,ml.name as mlname,ms.name as msname,count(p.id) as cntService
    
    from prescription p
    left join PrescriptionList pl on pl.id=p.prescriptionList_id
    left join MedCase slo on slo.id=pl.medCase_id
    left join MedCase sls on sls.id=slo.parent_id
    left join StatisticStub ssSls on ssSls.id=sls.statisticstub_id
    left join StatisticStub ssSlo on ssSlo.id=slo.statisticstub_id
    left join Patient pat on pat.id=slo.patient_id
    left join MedService ms on ms.id=p.medService_id
    left join VocServiceType vst on vst.id=ms.serviceType_id
    left join VocServiceSubType vsst on vsst.id=ms.serviceSubType_id
    left join WorkFunction wf on wf.id=p.prescriptSpecial_id
    left join Worker w on w.id=wf.worker_id
    left join MisLpu ml on ml.id=w.lpu_id
    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
    where p.dtype='ServicePrescription'
    and p.planStartDate between to_date('${beginDate}','dd.mm.yyyy') 
    and to_date('${endDate}','dd.mm.yyyy')
    and w.lpu_id='${lpu_id}'
    group by w.lpu_id,ms.id,ml.name,ms.name
    
    order by ms.name,ml.name
    "/>
    <msh:sectionTitle>Список пациентов по отделению ${lpu_name}</msh:sectionTitle>
    <msh:sectionContent>
	    <msh:table name="list" action="entityParentView-pres_prescriptList.do" idField="id" guid="3c4adc65-cfce-4205-a2dd-91ba8ba87543">
	      <msh:tableColumn columnName="Назначил" property="workFunctionInfo" guid="44482100-2200-4c8b-9df5-4f5cc0e3fe68" />
	      <msh:tableColumn columnName="Комментарии" property="comments" guid="5c893448-9084-4b1a-b301-d7aca8f6307c" />
	      <msh:tableColumn columnName="Дата создания" property="date" guid="dbe4fc52-03f7-42af-9555-a4bee397a800" />
	      <msh:tableColumn columnName="Период актуальности" property="periodActual"/>
	    </msh:table>
    </msh:sectionContent>
    </msh:section>
  	
  	<%	
  	} else {
  	  	%>
<H1>НЕПРАВИЛЬНО НАСТРОЕН ПРОФИЛЬ ПОЛЬЗОВАТЕЛЯ!!! ОБРАТИТЕСЬ К АДМИНИСТРАТОРУ!!!!</H1>  	  	
  	  	<%	
  	}
  	
	%>
  </tiles:put>
</tiles:insert>

