<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form  action="/entityParentSaveGoParentView-mis_patientListRecord.do" defaultField="patient">
      <msh:hidden property="id" />
      <msh:hidden property="patientList"/>
         <msh:hidden property="saveType"/>
      <msh:panel>
      <msh:autoComplete property="patient" vocName="patient" label = "Пациент" size="50"/>
        <msh:row>
          <msh:textField property="message" label="Отображаемое сообщение" size="100"/>
        </msh:row>
          <msh:row>
              <msh:textField property="phoneNumber" label="Телефон" size="100"/>
          </msh:row>
       <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>

  </tiles:put>
  <tiles:put name="title" type="string">
  <ecom:titleTrail mainMenu="Voc" beginForm="mis_patientListRecordForm"/>
  </tiles:put>
  <tiles:put name="side" type="string"> <msh:sideMenu>
 <msh:sideLink key='ALT+E' params="id" action="/entityEdit-mis_patientListRecord" name="Изменить" />
 <msh:sideLink key='ALT+DEL' params="id" action="/entityParentDeleteGoParentView-mis_patientListRecord" name="Удалить" />
 </msh:sideMenu>
  </tiles:put>
</tiles:insert>

