<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-poly_short_ticket.do" defaultField="date">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="medcard" />
      <msh:panel colsWidth="15%,15%,15%,55%">
        <msh:row>
          <msh:textField label="Дата приема" property="date" fieldColSpan="1" />
        </msh:row>
        <msh:row>
          <msh:autoComplete parentId="poly_short_ticketForm.medcard" vocName="workFunctionByTicket" property="workFunction" label="Специалист" fieldColSpan="3" size="100" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Неотложная помощь" property="emergency" fieldColSpan="3"/>
        </msh:row>
        <msh:ifFormTypeAreViewOrEdit formName="poly_short_ticketForm">
        <msh:row>
        	<msh:textField label="Дата выдачи" property="dateCreate" fieldColSpan="1" viewOnlyField="true"/>
        	<msh:textField label="Время выдачи" property="timeCreate" fieldColSpan="1" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Пользователь" property="usernameCreate" viewOnlyField="true" />
        </msh:row>
        </msh:ifFormTypeAreViewOrEdit>
        <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="poly_short_ticketForm">
      <msh:sideMenu title="Талон">
        <msh:sideLink roles="/Policy/Poly/ShortTicket/Edit" key="ALT+3" params="id" action="/entityParentEdit-poly_short_ticket" name="Изменить" title="Изменить талон" />
        <msh:ifFormTypeAreViewOrEdit formName="poly_short_ticketForm">
          <msh:sideLink roles="/Policy/Poly/ShortTicket/Delete" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-poly_short_ticket" name="Удалить" confirm="Вы действительно хотите удалить талон?" title="Удалить талон" />
        </msh:ifFormTypeAreViewOrEdit>
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
        <msh:sideLink roles="/Policy/Poly/ShortTicket/View" key="SHIFT+8" params="id" action="/print-ticketshort.do?s=PrintTicketService&amp;m=printInfo" name="Талон" title="Печатать талон" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Medcard" beginForm="poly_short_ticketForm" />
  </tiles:put>
</tiles:insert>
