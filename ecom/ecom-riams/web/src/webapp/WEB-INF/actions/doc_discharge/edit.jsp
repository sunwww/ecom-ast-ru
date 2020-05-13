<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoView-doc_discharge.do" defaultField="diagnosis">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:panel>
        <msh:row>
          <msh:textArea property="diagnosis" label="Диагноз:"
               size="100" rows="5" fieldColSpan="8" />
               
        </msh:row>
        <msh:row>
          <msh:textArea property="history" label="Анамнез:"
               size="100" rows="30" fieldColSpan="8" />
                    
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Poly" beginForm="doc_dischargeForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="doc_dischargeForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-doc_discharge" name="Изменить" roles="/Policy/Mis/MedCase/Document/Internal/Discharge/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-doc_discharge" name="Удалить" roles="/Policy/Mis/MedCase/Document/Internal/Discharge/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsView formName="doc_dischargeForm">
  <ecom:webQuery name="vssPrint_sql" nativeSql="select 
case 
when vssV.isDoublePrintPolic='1' then '_2c'
when vssV.code='HOSPITAL' then (
    select list(distinct case when vssSls.isDoublePrintPolic='1' then '_2c' else '' end)
    from medcase sls 
    left join VocServiceStream vssSls on vssSls.id=sls.serviceStream_id
    where sls.patient_id=vis.patient_id and sls.dtype='HospitalMedCase'
    and ( vis.dateStart between 
        sls.dateStart and 
        case when sls.deniedHospitalizating_id is not null then sls.dateStart else coalesce(sls.dateFinish,current_date) end 
        )
    )
    else ''
end as typeprint,vssV.code
from document doc
left join medcase vis on vis.id=doc.medcase_id
left join vocservicestream vssV on vssV.id=vis.serviceStream_id
where doc.id=${param.id}"/>
</msh:ifFormTypeIsView>
<%
if (request.getAttribute("vssPrint_sql")!=null) {ActionUtil.getValueByList("vssPrint_sql", "vssPrint", request);}
%>

<msh:ifFormTypeIsView formName="doc_dischargeForm">
<script type='text/javascript' src='./dwr/interface/TicketService.js'></script>
  <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		var addParam = "" ;
      		window.location.href = "print-documentDischarge${vssPrint}.do?next=entityParentView-smo_visit.do__id="+$('medCase').value+"&s=VisitPrintService&m=printDocument&id=${param.id}" ;
      		
      	}
  }
    printDocument();
    </script>
  </msh:ifInRole>
  <msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
          		window.location.href = "print-documentDischarge${vssPrint}.do?s=VisitPrintService&m=printDocument&id=${param.id}" ;
      	}
      }
    printDocument();
    </script>
  </msh:ifNotInRole>
  </msh:ifFormTypeIsView>
  </tiles:put>
  
</tiles:insert>

