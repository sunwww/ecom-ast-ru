<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoView-doc_direction.do" defaultField="phonePatient">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:panel>
      	<msh:row>
        	<msh:textField property="phonePatient" label="Телефон" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="planDateFrom" label="Дата госп. с"/>
        	<msh:textField property="planDateTo" label="по"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="sentToLpu" label="ЛПУ куда направлен" fieldColSpan="3" horizontalFill="true" vocName="mainLpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" label="Отделение" fieldColSpan="3" horizontalFill="true" parentAutocomplete="sentToLpu" vocName="vocLpuHospOtd"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedType" label="Профиль коек" fieldColSpan="3" horizontalFill="true" vocName="vocBedType"/>
        </msh:row>
        
        <msh:row>
        	<msh:autoComplete property="bedSubType" label="Тип коек" fieldColSpan="3" horizontalFill="true" vocName="vocBedSubType"/>
        </msh:row>
        
        <msh:row>
      		<msh:autoComplete property="idc10" horizontalFill="true" vocName="vocIdc10" fieldColSpan="8"/>
      	</msh:row>
        <msh:row>
          <msh:textArea property="diagnosis" label="Обоснование:"
               size="100" rows="5" fieldColSpan="8" />
        </msh:row>
        <msh:row>
	       	<msh:checkBox property="isPlanOperation" label="Операция?"/>
	       	<msh:ifFormTypeIsCreate formName="doc_directionForm">
	       		<msh:checkBox property="isCreatePlanHosp" label="Создать пред.госпит.?"/>
	       	</msh:ifFormTypeIsCreate>
        </msh:row>
        


        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="doc_directionForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsView formName="doc_directionForm">
  <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		window.location.href = "print-documentDirection.do?next=entityParentView-smo_visit.do__id="+$('medCase').value+"&s=VisitPrintService&m=printDocument&id=${param.id}" ;
      	}
  }
    printDocument();
    </script>
  </msh:ifInRole>
  <msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		window.location.href = "print-documentDirection.do?next=entityParentView-smo_visit.do__id="+$('medCase').value+"&s=VisitPrintService&m=printDocument&id=${param.id}" ;
      	}
      }
    printDocument();
    </script>
  </msh:ifNotInRole>
  </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="doc_directionForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-doc_direction" name="Изменить" roles="/Policy/Mis/MedCase/Document/Internal/Direction/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-doc_direction" name="Удалить" roles="/Policy/Mis/MedCase/Document/Internal/Direction/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

