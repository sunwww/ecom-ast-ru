<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entityParentSaveGoParentView-mis_qualification.do" defaultField="institutName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="person" />
      <msh:panel title="Квалификация">
        <msh:row>
          <msh:autoComplete vocName="vocInstitut" property="institut" fieldColSpan="3" label="Организация" size="50" horizontalFill="true" />
          <msh:row>
            <msh:autoComplete vocName="vocSpec" property="spec" label="Специальность" fieldColSpan="3" horizontalFill="true" />
          </msh:row>
          <msh:autoComplete vocName="vocCertificateIssueBase" property="certificateIssueBase" label="Основание для выдачи сертификата" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField passwordEnabled="false" property="certificateNumber" viewOnlyField="false" label="Номер сертификата" horizontalFill="false" />
          <msh:textField passwordEnabled="false" property="awardingDate" viewOnlyField="false" label="Дата присвоения" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocCategory" property="category" label="Присвоенная категория" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocAcademicDegree" property="academicDegree" label="Присвоенная степень" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocAcademicStatus" property="academicStatus" label="Присвоенное звание" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField passwordEnabled="false" property="certificateIssueDate" viewOnlyField="false" label="Дата выдачи сертификата" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_qualificationForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="mis_qualificationForm" insideJavascript="false">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_qualification" name="Изменить" roles="/Policy/Mis/Worker/Qualification/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-mis_qualification" name="Удалить" roles="/Policy/Mis/Worker/Qualification/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

