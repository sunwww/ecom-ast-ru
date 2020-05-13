<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoView-doc_baseMedicalExamination.do" defaultField="dateFrom" >
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medCase" />
      <msh:panel>
      	<msh:row>
      		<msh:textField property="dateFrom" label="Дата оформления"/>
      	</msh:row>
      	<msh:row>
      		<msh:autoComplete property="departmentWork" horizontalFill="true" label="Отделение" vocName="lpu" fieldColSpan="8"/>
      	</msh:row>
      	<msh:row>
      		<msh:autoComplete property="profession" horizontalFill="true" label="Профессия" vocName="vocDocumentProfession" fieldColSpan="8"/>
      	</msh:row>
        <msh:row>
          <msh:textArea property="recommendations" label="Произ. факторы"
               size="100" rows="30" fieldColSpan="8" />
                    
        </msh:row>      	
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Poly" beginForm="doc_baseMedicalExaminationForm" />
  </tiles:put>

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="doc_baseMedicalExaminationForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-doc_baseMedicalExamination" name="Изменить" roles="/Policy/Mis/MedCase/Document/Internal/BaseMedicalExamination/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-doc_baseMedicalExamination" name="Удалить" roles="/Policy/Mis/MedCase/Document/Internal/BaseMedicalExamination/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

