<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoView-doc_baseMedicalExamination.do" defaultField="dateIssued" >
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:panel>
      	<msh:row>
      		<msh:textField property="dateIssued" label="Дата оформления"/>
      	</msh:row>
      	<msh:row>
      		<msh:autoComplete property="departmentWork" horizontalFill="true" label="Отделение" vocName="lpu" fieldColSpan="8"/>
      	</msh:row>
      	<msh:row>
      		<msh:autoComplete property="profession" horizontalFill="true" label="Профессия" vocName="vocDocumentProfession" fieldColSpan="8"/>
      	</msh:row>
        <msh:row>
          <msh:textArea property="recommendations" label="Произ. факторы"
               size="100" rows="5" fieldColSpan="8" />
                    
        </msh:row>      	
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Poly" beginForm="doc_baseMedicalExaminationForm" guid="fbc3d5c0-2bf8-4584-a23f-1e2389d03646" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsNotView formName="doc_baseMedicalExaminationForm">
  <script type="text/javascript" src="./dwr/interface/PatientService.js">/**/</script>
  <script type="text/javascript">
	professionAutocomplete.addOnChangeCallback(function() {
		PatientService.getFactorByProfession($('profession').value
  				,{
  			callback: function(aResult) {
  					$('recommendations').value = aResult ; 
   			}
  		}) ;
	    });
	</script>
  </msh:ifFormTypeIsNotView>
  <msh:ifFormTypeIsView formName="doc_baseMedicalExaminationForm">
  <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
   <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		if (confirm('Распечатать полностью?')) {
	      		window.location.href = "print-documentBaseMedicalExamination.do?next=entityParentView-smo_visit.do__id="+$('medCase').value+"&s=VisitPrintService&m=printDocument&id=${param.id}" ;
      		} else {
      			window.location.href = "print-documentBaseMedicalExamination1.do?next=entityParentView-smo_visit.do__id="+$('medCase').value+"&s=VisitPrintService&m=printDocument&id=${param.id}" ;
      		}
      	}
  }
    printDocument();
    </script>
  </msh:ifInRole>
  <msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		if (confirm('Распечатать полностью?')) {
      			window.location.href = "print-documentBaseMedicalExamination.do?s=VisitPrintService&m=printDocument&id=${param.id}" ;
      		} else {
      			window.location.href = "print-documentBaseMedicalExamination1.do?s=VisitPrintService&m=printDocument&id=${param.id}" ;
      		}
      	}
      }
    printDocument();
    </script>
  </msh:ifNotInRole>
  </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="doc_baseMedicalExaminationForm" guid="22417d8b-beb9-42c6-aa27-14f794d73b32">
      <msh:sideMenu guid="32ef99d6-ea77-41c6-93bb-aeffa8ce9d55">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-doc_baseMedicalExamination" name="Изменить" roles="/Policy/Mis/MedCase/Document/Internal/BaseMedicalExamination/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-doc_baseMedicalExamination" name="Удалить" roles="/Policy/Mis/MedCase/Document/Internal/BaseMedicalExamination/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

