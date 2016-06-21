<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form  action="/entitySaveGoView-mis_patientListRecord.do" defaultField="patient">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden property="patientList"/>
      <msh:panel guid="panel">
      <msh:autoComplete property="patient" vocName="patient" label = "Пациент" />
        <msh:row>
          <msh:textField property="message" label="Отображаемое сообщение" size="100"/>
        </msh:row>
       <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
      </msh:panel>
    </msh:form>

  </tiles:put>
  <tiles:put name="title" type="string">
  </tiles:put>
  <tiles:put name="side" type="string">
 <msh:sideLink key='ALT+E' params="id" action="/entityEdit-mis_patientListRecord" name="Изменить" />
 <msh:sideLink key='ALT+DEL' params="id" action="/entityDelete-mis_patientListRecord" name="Удалить" />
  </tiles:put>
</tiles:insert>

