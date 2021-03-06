<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoView-doc_discharge.do" defaultField="dateFrom" guid="05d29ef5-3f3c-43b5-bc22-e5d5494c5762">
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
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Poly" beginForm="doc_dischargeForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="doc_dischargeForm" guid="22417d8b-beb9-42c6-aa27-14f794d73b32">
      <msh:sideMenu guid="32ef99d6-ea77-41c6-93bb-aeffa8ce9d55">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-doc_discharge" name="Изменить" roles="/Policy/Mis/Disability/Case/Document/Record/Edit" guid="609c81cf-05e5-4e07-90b7-87b38863114c" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-doc_discharge" name="Удалить" roles="/Policy/Mis/Disability/Case/Document/Record/Delete" guid="1a3265b4-cebb-4536-a471-c79003ccf548" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsView formName="doc_dischargeForm">
  <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		window.location.href = "print-documentDischarge.do?next=entityParentView-smo_visit.do__id="+$('medCase').value+"&s=VisitPrintService&m=printDocument&id=${param.id}" ;
      	}
  }
    </script>
  </msh:ifInRole>
  <msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		window.location.href = "print-documentDischarge.do?next=entityParentView-smo_visit.do__id="+$('medCase').value+"&s=VisitPrintService&m=printDocument&id=${param.id}" ;
      	}
      }
    </script>
  </msh:ifNotInRole>
  </msh:ifFormTypeIsView>
  </tiles:put>
  
</tiles:insert>

