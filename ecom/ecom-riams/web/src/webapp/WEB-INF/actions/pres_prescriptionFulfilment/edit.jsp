<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - 
    	  -->
    <msh:form action="/entitySaveGoView-pres_prescriptionFulfilment.do" defaultField="fulfilDate" title="Исполнение назначения">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
          <msh:label property="prescription" label="Назначение" viewOnlyField="true" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="fulfilDate" label="Дата выполнения" />
          <msh:textField property="fulfilTime" label="Время выполнения" />
        </msh:row>
        <msh:row>
          <msh:textField property="comments" label="Комментарий" horizontalFill="true" size="40" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="workFunction" property="executorWorkFunction" label="Исполнитель" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="dateCreate" label="Дата создания" viewOnlyField="true" />
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
        <input type="button" onclick="saveGoNext()" value="test_create">
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="pres_prescriptionFulfilmentForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="">
      <msh:ifFormTypeIsView formName="pres_prescriptionFulfilmentForm">
        <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Edit" key="ALT+2" params="id" action="/entityParentEdit-pres_prescriptionFulfilment" name="Изменить" />
        <msh:sideLink roles="/Policy/Mis/Prescription/PrescriptionFulfilment/Delete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-pres_prescriptionFulfilment" name="Удалить" />
      </msh:ifFormTypeIsView>
    </msh:sideMenu>
    <script type="text/javascript">
      function saveGoNext() {
          var form = $('mainForm');
          alert ("FFF="+form.serialize());
          var myAjax = new Ajax.Request(
              'entitySaveGoView-pres_prescriptionFulfilment.do',
               {
               method:"post",
               parameters:form.serialize(),
               onComplete:showResponse
              }
          );
          ///alert (myAjax);
      }
      function showResponse() {alert(123);}
    </script>

  </tiles:put>

</tiles:insert>

