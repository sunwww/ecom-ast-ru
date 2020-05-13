<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-rec_recording.do" defaultField="hello">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:panel>
        <msh:row>
          <msh:textField property="hello" label="Фамилия" />
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Имя" horizontalFill="false" />
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Отчество" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Дата рождения" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="СНИЛС" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Серия полиса ОМС" horizontalFill="false" />
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Номер полиса ОМС" horizontalFill="false" />
        </msh:row>
        <msh:row>
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="Дата действия с" horizontalFill="false" />
          <msh:textField passwordEnabled="false" property="hello" viewOnlyField="false" label="по" horizontalFill="false" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="6" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="rec_recordingForm" title="Запись на прием" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Запись на прием">
      <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-rec_recording" name="Записаться" title="Записаться на прием" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" />
  </tiles:put>
</tiles:insert>

