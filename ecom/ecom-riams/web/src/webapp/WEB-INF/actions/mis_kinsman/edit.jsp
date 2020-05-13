<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="/entityParentSaveGoParentView-mis_kinsman.do" defaultField="kinsmanName" title="Родственник (представитель)">
      <msh:hidden property="id" />
      <msh:hidden property="person" />
      <msh:hidden property="saveType" />
      <msh:hidden property="person" />
      <msh:panel title="Родственник (представитель)" colsWidth="5%,25%,25%,25%,15%,15%">
          <msh:row>
          	<msh:autoComplete viewAction="entityView-mis_patient.do" property="kinsman" vocName="patient"
          		fieldColSpan="2" label="Родственник" horizontalFill="true"/>
<td align="right" width="1px"><div id="kinsmanButton"></div></td>
          </msh:row>
          
          <msh:row>
          	<msh:autoComplete property="kinsmanRole" vocName="vocKinsmanRole" horizontalFill="true"
          		fieldColSpan="3" label="Статус" 
          		/>
          </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <tags:mis_patient_new divForButton="kinsmanButton" name='Kinsman' title='Создание новой персоны' autoComplitePatient="kinsman"/>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_kinsmanForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="mis_kinsmanForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-mis_kinsman" name="Изменить" roles="/Policy/Mis/Patient/Kinsman/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-mis_kinsman" name="Удалить" roles="/Policy/Mis/Patient/Kinsman/Delete" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<msh:ifFormTypeIsNotView formName="mis_kinsmanForm">
  	<script type="text/javascript">
  		initKinsmanPatientDialog()</script>
  		</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

