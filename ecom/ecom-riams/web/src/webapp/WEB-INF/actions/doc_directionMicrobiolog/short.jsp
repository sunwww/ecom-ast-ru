<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoView-doc_directionMicrobiolog.do" defaultField="idc10" >
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:panel>
      	<msh:row>
      		<msh:autoComplete property="idc10" horizontalFill="true" vocName="vocIdc10" fieldColSpan="8"/>
      	</msh:row>
      	<msh:row>
      		<msh:textField property="dateFrom" label="Дата заболевания"/>
      	</msh:row>
      	<msh:row>
      		<msh:autoComplete property="materialAnalysis" horizontalFill="true" label="Материал" vocName="vocMaterialBiologAnalysis" fieldColSpan="8"/>
      	</msh:row>
      	<msh:row>
      		<msh:autoComplete property="analysis" horizontalFill="true" label="Наименование исследования" vocName="vocBiologAnalysis" fieldColSpan="8"/>
      	</msh:row>
      	<msh:row>
      		<msh:autoComplete property="objectAnalysis" horizontalFill="true" label="Цель исследования" vocName="vocObjectBiologAnalysis" fieldColSpan="8"/>
      	</msh:row>


        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Poly" beginForm="doc_directionMicrobiologForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsView formName="doc_directionMicrobiologForm">
  <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		window.location.href = "print-documentDirection.do?next=entityParentView-smo_visit.do__id="+$('medCase').value+"&s=VisitPrintService&m=printDocument&id=${param.id}" ;
      	}
  }
    //printDocument();
    </script>
  </msh:ifInRole>
  <msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		window.location.href = "print-documentDirection.do?next=entityParentView-smo_visit.do__id="+$('medCase').value+"&s=VisitPrintService&m=printDocument&id=${param.id}" ;
      	}
      }
    //printDocument();
    </script>
  </msh:ifNotInRole>
  </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="doc_directionMicrobiologForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-doc_directionMicrobiolog" name="Изменить" roles="/Policy/Mis/MedCase/Document/Internal/DirectionMicrobiolog/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-doc_directionMicrobiolog" name="Удалить" roles="/Policy/Mis/MedCase/Document/Internal/DirectionMicrobiolog/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

