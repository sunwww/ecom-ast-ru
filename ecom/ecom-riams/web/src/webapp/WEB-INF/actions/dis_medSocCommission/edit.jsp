<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entityParentSaveGoParentView-dis_medSocCommission.do" defaultField="assignmentDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="disabilityDocument" />
      <msh:panel>
        <msh:row>
          <msh:textField property="assignmentDate" label="Дата направления" />
          <msh:textField property="registrationDate" label="Дата регистрации документов" />
        </msh:row>
        <msh:textField property="examinationDate" label="Дата освидетельствования" />
        <msh:row>
          <msh:autoComplete vocName="vocDisabilityDegree" property="disabilityDergee" horizontalFill="true" fieldColSpan="3" label="Степень ограничения трудоспособности" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="invalidityRegistration" fieldColSpan="3" horizontalFill="true" label="Установлена/изменена группа инвалидности"/>
        </msh:row>
          <msh:row>
            <msh:autoComplete vocName="vocInvalidity" property="invalidity" label="Инвалидность" fieldColSpan="3" horizontalFill="true" />
          </msh:row>
        <msh:row>
          <msh:textArea rows="2" property="comments" label="Комментарий" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_medSocCommissionForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="dis_medSocCommissionForm">
      <msh:sideMenu title="МСЭК">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-dis_medSocCommission" name="Изменить" roles="/Policy/Mis/Disability/Case/Document/MedSocCommission/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-dis_medSocCommission" name="Удалить" roles="/Policy/Mis/Disability/Case/Document/MedSocCommission/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

