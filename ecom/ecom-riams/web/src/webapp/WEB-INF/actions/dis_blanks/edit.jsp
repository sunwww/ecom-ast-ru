<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Бланки нетрудоспособности
    	  -->
    <msh:form action="/entitySaveGoView-dis_blanks.do" defaultField="receiptDate">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
      	<msh:row>
      		<msh:textField property="receiptDate" label="Дата получения"/>
      		<msh:textField property="expenditureDate" label="Дата расход."/>
      	</msh:row>
        <msh:row>
          <msh:textField property="numberFrom" label="С номера" size="12" />
          <msh:textField property="numberTo" label="По номер" size="12"/>
        </msh:row>
        <msh:row>
          <msh:textField property="spaceBefore" label="Отступ сверху" />
          <msh:textField property="leftIndent" label="Отступ слева" />
        </msh:row>
        <msh:row>
          <msh:textField property="blanksCount" label="Кол-во" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="workFunction" property="workFunction" fieldColSpan="3" horizontalFill="true" size="150" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Disability" beginForm="dis_blanksForm"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="dis_blanksForm">
      <msh:sideMenu>
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-dis_blanks" name="Изменить" roles="/Policy/Mis/Disability/Blanks/Edit"/>
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-dis_blanks" name="Удалить" roles="/Policy/Mis/Disability/Blanks/Delete"/>
      </msh:sideMenu>
      <tags:dis_menu currentAction="find_number"/>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

