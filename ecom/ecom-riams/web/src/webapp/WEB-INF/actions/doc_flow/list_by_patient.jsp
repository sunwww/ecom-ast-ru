<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
  <%
  String pat = request.getParameter("patient") ;
  String medcard = request.getParameter("medcard") ;
  String medcase = request.getParameter("medcase") ;
  if (pat!=null) {
	  request.setAttribute("frm","mis_patient");
  } else if (medcard!=null) {
	  request.setAttribute("frm","poly_medcard");
  } else if (medcase!=null) {
	  request.setAttribute("frm","mis_medCase");
  }
  %>
    <ecom:titleTrail beginForm="${frm}Form" mainMenu="Patient" title="Список перемещений" guid="40efbd1b-4177-47a8-9aad-1971732f3f98" />
  </tiles:put>
  <tiles:put name="side" type="string">
  <tags:style_currentMenu currentAction='inform'/>
    <msh:sideMenu title="Добавить">
    	<msh:sideLink name="Диагноз" params="id" roles="/Policy/Mis/MedCase/Diagnosis/Create" key="ALT+2" action="/entityParentPrepareCreate-mis_diagnosis.do"/>
    </msh:sideMenu>
    <msh:sideMenu guid="helloSideMenu-123">
      <msh:sideLink params="id" guid="Перейти к пациенту" action="/entityView-mis_patient" name="Пациент" />
        <msh:sideLink params="id" action="/js-smo_visit-infoByPatient" name="Информация по визитам" title="Показать информацию посещений по пациенту" guid="dd2ad6a3-5fb2-4586-a24e-1a0f1b796397" roles="/Policy/Mis/MedCase/Spo/View" />
        <msh:sideLink styleId="inform" params="id" action="/js-smo_diagnosis-infoByPatient" name="Диагнозы" title="Показать все диагнозы" guid="68b36632-8d07-4a87-b469-6695694b2bab" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        <msh:sideLink params="id" action="/js-smo_visitProtocol-infoByPatient" name="Заключения" title="Показать все заключения" guid="68b36632-8d07-4a87-b469-6695694b2bab" roles="/Policy/Mis/MedCase/Diagnosis/View" />
        
   </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  String pat = request.getParameter("patient") ;
  String medcard = request.getParameter("medcard") ;
  String medcase = request.getParameter("medcase") ;
  if (pat!=null) {
	  request.setAttribute("fld","patient");
  } else if (medcard!=null) {
	  request.setAttribute("fld","medcard");
  } else if (medcase!=null) {
	  request.setAttribute("fld","medcase");
  }
  %>
    <ecom:webQuery name="list" nativeSql="select fd.id as fdid,vfdt.name as vfdtname
 ,coalesce('ИБ№'||ss.code,'МК№'||mc.number,'') as infodoc 
,vfdp1.name as vfdp1name,vfdp2.name as vfdp2name
,to_char(fd.createdate,'dd.mm.yyyy') as createdate,fd.createusername 
,to_char(fd.posteddate,'dd.mm.yyyy') as posteddate,fd.postedusername 
,to_char(fd.receiptdate,'dd.mm.yyyy') as receitdate,fd.receiptusername 

from FlowDocument fd  left join medcase sls on sls.id=fd.medcase  
left join statisticstub ss on ss.id=sls.statisticstub_id  left join medcard mc on mc.id=fd.medcard  
left join patient pat on pat.id=fd.patient  left join VocFlowDocumentType vfdt on vfdt.id=fd.type_id  
left join VocFlowDocmentPlace vfdp1 on vfdp1.id=fd.placeFrom_id  
left join VocFlowDocmentPlace vfdp2 on vfdp2.id=fd.placeTo_id 
where fd.${fld}='${param.id}'  
order by fd.createdate,vfdt.name
    " guid="2d59a9bf-327f-4f4f-8336-531458b6caed" />
    <msh:table name="list" action="javascript:void(0)" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="№" property="sn" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4-1" />
      <msh:tableColumn columnName="Вид" property="2" />
      <msh:tableColumn columnName="Документ" property="3" />
      <msh:tableColumn columnName="Отправлен из" property="4" />
      <msh:tableColumn columnName="Отправлен в" property="5" />
      <msh:tableColumn columnName="Дата создания" property="6" />
      <msh:tableColumn columnName="пользователь" property="7" />
      <msh:tableColumn columnName="Дата отправки" property="8" />
      <msh:tableColumn columnName="пользователь" property="9" />
      <msh:tableColumn columnName="Дата получения" property="10" />
      <msh:tableColumn columnName="пользователь" property="11" />
     
    </msh:table>
  </tiles:put>
</tiles:insert>